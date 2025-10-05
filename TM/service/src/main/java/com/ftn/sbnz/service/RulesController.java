package com.ftn.sbnz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dto.ExecuteRequest;
import com.ftn.sbnz.service.dto.QueryResultDto;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "http://localhost:4200")
public class RulesController {

    private final RuleExecutionService executionService;

    public RulesController(RuleExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("/execute")
    public ResponseEntity<List<QueryResultDto>> execute(@RequestBody ExecuteRequest req) {
        List<QueryResultDto> res = executionService.execute(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/session/state")
    public ResponseEntity<List<QueryResultDto>> getSessionState() {
        List<QueryResultDto> res = executionService.getSessionState();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/session/clear")
    public ResponseEntity<List<QueryResultDto>> clearSession() {
        List<QueryResultDto> res = executionService.clearSession();
        return ResponseEntity.ok(res);
    }
}
