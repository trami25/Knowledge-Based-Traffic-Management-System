package com.ftn.sbnz.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Accident;
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
            if (req.getFacts() != null) {
                for (FactDto f : req.getFacts()) {
                    Object obj = mapFact(f.getType(), f.getPayload());
                    if (obj != null)
                        kieSession.insert(obj);
                }
            }
            kieSession.fireAllRules();

            // return type change: List<QueryResultDto>
            List<QueryResultDto> out = new ArrayList<>();
            if (req.getQueries() != null) {
                for (var q : req.getQueries()) {
                    Object[] params = q.getParams() == null ? new Object[0] : q.getParams().toArray();
                    QueryResults results;
                    try {
                        results = kieSession.getQueryResults(q.getName(), params);
                    } catch (RuntimeException ex) {
                        // Some queries expect an extra return variable (Variable.v). Retry with it.
                        if (ex.getMessage() != null && ex.getMessage().contains("wrong number of arguments")) {
                            Object[] paramsWithVar = Arrays.copyOf(params, params.length + 1);
                            paramsWithVar[params.length] = Variable.v;
                            results = kieSession.getQueryResults(q.getName(), paramsWithVar);
                        } else
                            throw ex;
                    }
                    for (QueryResultsRow row : results) {
                        Object cfObj = null;
                        try {
                            cfObj = row.get("$cf");
                        } catch (IllegalArgumentException iae) {
                            // $cf not bound in this query row
                            cfObj = null;
                        }
                        if (cfObj != null && cfObj instanceof com.ftn.sbnz.model.models.CauseFact) {
                            com.ftn.sbnz.model.models.CauseFact cf = (com.ftn.sbnz.model.models.CauseFact) cfObj;
                            out.add(new QueryResultDto(q.getName(), cf.getCrossroad(), cf.getCause()));
                        } else {
                            // fallback if query doesn't bind $cf — try to read $crossroad/$cause safely
                            Object cr = null;
                            Object cause = null;
                            try {
                                cr = row.get("$crossroad");
                            } catch (IllegalArgumentException e2) {
                                cr = null;
                            }
                            try {
                                cause = row.get("$cause");
                            } catch (IllegalArgumentException e3) {
                                cause = null;
                            }
                            out.add(new QueryResultDto(q.getName(),
                                    cr == null ? null : String.valueOf(cr),
                                    cause == null ? null : String.valueOf(cause)));
                        }
                    }
                }
            }
            return out;
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        } finally {
            kieSession.dispose();
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
