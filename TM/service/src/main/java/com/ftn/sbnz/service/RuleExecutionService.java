package com.ftn.sbnz.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ftn.sbnz.model.models.PublicTransportDelay;
import com.ftn.sbnz.model.models.TimeOfDay;
import com.ftn.sbnz.model.models.TrafficDensity;
import com.ftn.sbnz.model.models.Weather;
import com.ftn.sbnz.service.dto.ExecuteRequest;
import com.ftn.sbnz.service.dto.FactDto;
import com.ftn.sbnz.service.dto.QueryResultDto;

@Service
public class RuleExecutionService {

    private final KieContainer kieContainer;

    public RuleExecutionService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public List<QueryResultDto> execute(ExecuteRequest req) {
        KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        try {
            // Insert incoming facts
            if (req.getFacts() != null) {
                for (FactDto f : req.getFacts()) {
                    Object obj = mapFact(f.getType(), f.getPayload());
                    if (obj != null) kieSession.insert(obj);
                }
            }

            // Initialize global set for tracking visited crossroads
            kieSession.setGlobal("visitedCrossroads", new HashSet<String>());
           
            kieSession.fireAllRules();

            List<QueryResultDto> out = new ArrayList<>();

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

            return out;
        } finally {
            kieSession.dispose();
        }
    }


    private Object mapFact(String type, Map<String, Object> payload) {
        if (type == null) return null;
        return switch (type) {
            case "Accident" -> new Accident((String) payload.get("crossroad"), (String) payload.get("severity"));
            case "Crossroad" -> new Crossroad((String) payload.get("id"), (java.util.List<String>) payload.get("connectedRoads"));
            case "EmergencyVehicle" -> new EmergencyVehicle((String) payload.get("location"), (String) payload.get("direction"));
            case "EventDay" -> new EventDay((String) payload.get("name"), ((Number) payload.get("expectedTraffic")).intValue());
            case "IllegalParking" -> new IllegalParking((String) payload.get("crossroad"));
            case "PublicTransportDelay" -> new PublicTransportDelay((String) payload.get("line"), ((Number) payload.get("delayMinutes")).intValue());
            case "TimeOfDay" -> new TimeOfDay(((Number) payload.get("hour")).intValue());
            case "TrafficDensity" -> new TrafficDensity((String) payload.get("crossroad"), ((Number) payload.get("value")).intValue());
            case "Weather" -> new Weather((String) payload.get("type"), (String) payload.get("intensity"));
            default -> null;
        };
    }
}
