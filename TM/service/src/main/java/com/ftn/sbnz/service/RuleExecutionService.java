package com.ftn.sbnz.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Accident;
import com.ftn.sbnz.model.models.CauseFact;
import com.ftn.sbnz.model.models.Crossroad;
import com.ftn.sbnz.model.models.EmergencyVehicle;
import com.ftn.sbnz.model.models.EventDay;
import com.ftn.sbnz.model.models.IllegalParking;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.PedestrianDetected;
import com.ftn.sbnz.model.models.PublicTransportDelay;
import com.ftn.sbnz.model.models.TimeOfDay;
import com.ftn.sbnz.model.models.TrafficAction;
import com.ftn.sbnz.model.models.TrafficDensity;
import com.ftn.sbnz.model.models.Weather;
import com.ftn.sbnz.service.dto.ExecuteRequest;
import com.ftn.sbnz.service.dto.FactDto;
import com.ftn.sbnz.service.dto.QueryResultDto;

import jakarta.annotation.PreDestroy;

@Service
public class RuleExecutionService {

    private final KieContainer kieContainer;
    private final ValidationService validationService;
    private final KieSession kieSession; // Persistent session

    public RuleExecutionService(KieContainer kieContainer, ValidationService validationService) {
        this.kieContainer = kieContainer;
        this.validationService = validationService;
        
        // Create a single persistent session
        this.kieSession = kieContainer.newKieSession("ksession-rules");
        
        // Initialize global set for tracking visited crossroads
        this.kieSession.setGlobal("visitedCrossroads", new HashSet<String>());
        
        System.out.println("✅ Persistent KieSession created and initialized");
    }

    @PreDestroy
    public void cleanup() {
        if (kieSession != null) {
            kieSession.dispose();
            System.out.println("🔄 KieSession disposed on application shutdown");
        }
    }

    public List<QueryResultDto> execute(ExecuteRequest req) {
        List<QueryResultDto> validationErrors = new ArrayList<>();
        
        synchronized (kieSession) { // Thread safety for concurrent requests
            try {
                // Collect existing facts from the persistent session for validation
                List<Object> sessionFacts = new ArrayList<>();
                for (Object fact : kieSession.getObjects()) {
                    sessionFacts.add(fact);
                }
                
                System.out.println("📊 Current session contains " + sessionFacts.size() + " facts");
                
                // Insert incoming facts with validation
                if (req.getFacts() != null) {
                    for (FactDto f : req.getFacts()) {
                        // Validate the fact before creating it
                        ValidationService.ValidationResult validation = 
                            validationService.validateFactPayload(f.getType(), f.getPayload(), sessionFacts);
                        
                        if (!validation.isValid()) {
                            // Add validation error to results
                            QueryResultDto errorResult = new QueryResultDto("ValidationError", 
                                "SYSTEM", 
                                "❌ " + validation.getErrorMessage());
                            validationErrors.add(errorResult);
                            System.out.println("Validation failed for " + f.getType() + ": " + validation.getErrorMessage());
                            continue; // Skip this fact
                        }
                        
                        // If validation passed, create and insert the fact
                        Object obj = mapFact(f.getType(), f.getPayload());
                        if (obj != null) {
                            kieSession.insert(obj);
                            sessionFacts.add(obj); // Keep track for subsequent validations
                            System.out.println("✅ Successfully added " + f.getType() + " fact to persistent session");
                        }
                    }
                }

                // Fire rules on the persistent session
                kieSession.fireAllRules();

            List<QueryResultDto> out = new ArrayList<>();

            // Capture TrafficAction and Notification objects created by rules
            for (Object fact : kieSession.getObjects()) {
                if (fact instanceof TrafficAction action) {
                    QueryResultDto actionResult = new QueryResultDto("TrafficAction", 
                        action.getCrossroad(), 
                        action.getAction() + (action.getValue() != null ? " (" + action.getValue() + ")" : ""));
                    out.add(actionResult);
                    System.out.println("TrafficAction created: " + action.getCrossroad() + " -> " + action.getAction());
                } else if (fact instanceof Notification notification) {
                    QueryResultDto notificationResult = new QueryResultDto("Notification", 
                        "SYSTEM", 
                        notification.getMessage());
                    out.add(notificationResult);
                    System.out.println("Notification created: " + notification.getMessage());
                }
            }

            if (req.getQueries() != null) {
                for (var q : req.getQueries()) {
                    Object[] params = q.getParams() == null ? new Object[0] : q.getParams().toArray();
                    QueryResults results;

                    try {
                        results = kieSession.getQueryResults(q.getName(), params);
                    } catch (RuntimeException ex) {
                        if (ex.getMessage() != null && ex.getMessage().contains("wrong number of arguments")) {
                            Object[] paramsWithVar = Arrays.copyOf(params, params.length + 1);
                            paramsWithVar[params.length] = Variable.v;
                            results = kieSession.getQueryResults(q.getName(), paramsWithVar);
                        } else {
                            throw ex;
                        }
                    }

                    for (QueryResultsRow row : results) {
                        String crossroad = null;
                        String cause = null;

                        if (q.getName().equals("findCauseOfDensity")) {
                            crossroad = (String) q.getParams().get(0); // target parameter
                            cause = (String) row.get("result");        // result parameter
                            System.out.println("Found cause at " + crossroad + ": " + cause);
                        } else {
                            Object cfObj = row.get("$cf");
                            if (cfObj instanceof CauseFact cf) {
                                crossroad = cf.getCrossroad();
                                cause = cf.getCause();
                                System.out.println("Found cause at " + crossroad + ": " + cause);
                            }
                        }

                        if (crossroad != null && cause != null) {
                            QueryResultDto resultDto = new QueryResultDto(q.getName(), crossroad, cause);
                            out.add(resultDto);
                            
                            // Print detailed result
                            if (q.getName().equals("findCauseOfDensity")) {
                                System.out.println("Query result: " + resultDto);
                            }
                        }
                    }
                }
            }            // Print all results before returning
            if (!out.isEmpty()) {
                System.out.println("\nAll query results:");
                for (QueryResultDto result : out) {
                    System.out.println(result);
                }
            }

                // Combine validation errors with rule results
                List<QueryResultDto> finalResults = new ArrayList<>();
                finalResults.addAll(validationErrors); // Add validation errors first
                finalResults.addAll(out); // Then add rule execution results

                return finalResults;
            } catch (Exception ex) {
                ex.printStackTrace();
                List<QueryResultDto> errorResults = new ArrayList<>();
                errorResults.addAll(validationErrors);
                errorResults.add(new QueryResultDto("SystemError", "SYSTEM", "❌ Error executing rules: " + ex.getMessage()));
                return errorResults;
            }
        } // End synchronized block
    }

    /**
     * Get the current state of the persistent session
     */
    public List<QueryResultDto> getSessionState() {
        List<QueryResultDto> sessionState = new ArrayList<>();
        
        synchronized (kieSession) {
            // Count facts by type
            Map<String, Integer> factCounts = new HashMap<>();
            
            for (Object fact : kieSession.getObjects()) {
                String factType = fact.getClass().getSimpleName();
                factCounts.put(factType, factCounts.getOrDefault(factType, 0) + 1);
            }
            
            // Create session state report
            sessionState.add(new QueryResultDto("SessionInfo", "SYSTEM", 
                "📈 Total facts in session: " + kieSession.getObjects().size()));
            
            for (Map.Entry<String, Integer> entry : factCounts.entrySet()) {
                sessionState.add(new QueryResultDto("SessionInfo", "SYSTEM", 
                    "📊 " + entry.getKey() + ": " + entry.getValue()));
            }
        }
        
        return sessionState;
    }

    /**
     * Clear all facts from the persistent session (reset)
     */
    public List<QueryResultDto> clearSession() {
        synchronized (kieSession) {
            int factCount = kieSession.getObjects().size();
            
            for (Object fact : new ArrayList<>(kieSession.getObjects())) {
                kieSession.delete(kieSession.getFactHandle(fact));
            }
            
            // Reinitialize globals
            kieSession.setGlobal("visitedCrossroads", new HashSet<String>());
            
            System.out.println("🧹 Session cleared: " + factCount + " facts removed");
            
            return List.of(new QueryResultDto("SessionInfo", "SYSTEM", 
                "🧹 Session cleared: " + factCount + " facts removed"));
        }
    }
    private Object mapFact(String type, Map<String, Object> payload) {
        if (type == null)
            return null;
        switch (type) {
            case "Accident":
                return new Accident((String) payload.get("crossroad"), (String) payload.get("severity"));
            case "Crossroad":
                return new Crossroad((String) payload.get("id"),
                        (java.util.List<String>) payload.get("connectedRoads"));
            case "EmergencyVehicle":
                return new EmergencyVehicle((String) payload.get("location"), (String) payload.get("direction"));
            case "EventDay":
                return new EventDay((String) payload.get("name"), ((Number) payload.get("expectedTraffic")).intValue());
            case "IllegalParking":
                return new IllegalParking((String) payload.get("crossroad"));
            case "PedestrianDetected":
                return new PedestrianDetected((String) payload.get("crossroad"), ((Number) payload.get("count")).intValue());
            case "PublicTransportDelay":
                return new PublicTransportDelay((String) payload.get("line"),
                        ((Number) payload.get("delayMinutes")).intValue());
            case "TimeOfDay":
                return new TimeOfDay(((Number) payload.get("hour")).intValue());
            case "TrafficDensity":
                return new TrafficDensity((String) payload.get("crossroad"),
                        ((Number) payload.get("value")).intValue());
            case "Weather":
                return new Weather((String) payload.get("type"), (String) payload.get("intensity"));
            default:
                return null;
        }
    }
}
