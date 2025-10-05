package com.ftn.sbnz.service;

import com.ftn.sbnz.model.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    /**
     * Validates if a crossroad ID exists in the current session facts
     */
    public boolean validateCrossroadExists(String crossroadId, List<Object> sessionFacts) {
        if (crossroadId == null || crossroadId.trim().isEmpty()) {
            return false;
        }
        
        Set<String> existingCrossroads = sessionFacts.stream()
            .filter(fact -> fact instanceof Crossroad)
            .map(fact -> ((Crossroad) fact).getId())
            .collect(Collectors.toSet());
            
        return existingCrossroads.contains(crossroadId);
    }

    /**
     * Validates a fact payload before creating the fact object
     */
    public ValidationResult validateFactPayload(String factType, Map<String, Object> payload, List<Object> sessionFacts) {
        switch (factType) {
            case "Accident":
                return validateAccident(payload, sessionFacts);
            case "IllegalParking":
                return validateIllegalParking(payload, sessionFacts);
            case "PedestrianDetected":
                return validatePedestrianDetected(payload, sessionFacts);
            case "TrafficDensity":
                return validateTrafficDensity(payload, sessionFacts);
            case "Crossroad":
                return validateCrossroad(payload, sessionFacts);
            default:
                // For other fact types, no validation needed
                return ValidationResult.valid();
        }
    }

    private ValidationResult validateAccident(Map<String, Object> payload, List<Object> sessionFacts) {
        String crossroadId = (String) payload.get("crossroad");
        
        if (crossroadId == null || crossroadId.trim().isEmpty()) {
            return ValidationResult.invalid("Accident requires a valid crossroad ID");
        }
        
        if (!validateCrossroadExists(crossroadId, sessionFacts)) {
            return ValidationResult.invalid("Crossroad '" + crossroadId + "' does not exist in current session. Please add the crossroad first.");
        }
        
        return ValidationResult.valid();
    }

    private ValidationResult validateIllegalParking(Map<String, Object> payload, List<Object> sessionFacts) {
        String crossroadId = (String) payload.get("crossroad");
        
        if (crossroadId == null || crossroadId.trim().isEmpty()) {
            return ValidationResult.invalid("IllegalParking requires a valid crossroad ID");
        }
        
        if (!validateCrossroadExists(crossroadId, sessionFacts)) {
            return ValidationResult.invalid("Crossroad '" + crossroadId + "' does not exist in current session. Please add the crossroad first.");
        }
        
        return ValidationResult.valid();
    }

    private ValidationResult validatePedestrianDetected(Map<String, Object> payload, List<Object> sessionFacts) {
        String crossroadId = (String) payload.get("crossroad");
        
        if (crossroadId == null || crossroadId.trim().isEmpty()) {
            return ValidationResult.invalid("PedestrianDetected requires a valid crossroad ID");
        }
        
        if (!validateCrossroadExists(crossroadId, sessionFacts)) {
            return ValidationResult.invalid("Crossroad '" + crossroadId + "' does not exist in current session. Please add the crossroad first.");
        }
        
        return ValidationResult.valid();
    }

    private ValidationResult validateTrafficDensity(Map<String, Object> payload, List<Object> sessionFacts) {
        String crossroadId = (String) payload.get("crossroad");
        
        if (crossroadId == null || crossroadId.trim().isEmpty()) {
            return ValidationResult.invalid("TrafficDensity requires a valid crossroad ID");
        }
        
        if (!validateCrossroadExists(crossroadId, sessionFacts)) {
            return ValidationResult.invalid("Crossroad '" + crossroadId + "' does not exist in current session. Please add the crossroad first.");
        }
        
        return ValidationResult.valid();
    }

    private ValidationResult validateCrossroad(Map<String, Object> payload, List<Object> sessionFacts) {
        String crossroadId = (String) payload.get("id");
        
        if (crossroadId == null || crossroadId.trim().isEmpty()) {
            return ValidationResult.invalid("Crossroad requires a valid ID");
        }
        
        // Check if crossroad already exists
        if (validateCrossroadExists(crossroadId, sessionFacts)) {
            return ValidationResult.invalid("Crossroad '" + crossroadId + "' already exists in current session");
        }
        
        return ValidationResult.valid();
    }

    /**
     * Result class for validation operations
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;

        private ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        public static ValidationResult valid() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult invalid(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}