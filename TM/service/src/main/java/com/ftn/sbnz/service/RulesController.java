package com.ftn.sbnz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.service.dto.ExecuteRequest;
import com.ftn.sbnz.service.dto.QueryResultDto;

@RestController
@RequestMapping("/api/rules")
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
}
