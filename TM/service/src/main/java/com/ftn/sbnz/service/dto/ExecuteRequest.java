package com.ftn.sbnz.service.dto;

import java.util.List;

public class ExecuteRequest {
    private List<FactDto> facts;
    private List<QueryDto> queries;

    public ExecuteRequest() {}

    public List<FactDto> getFacts() {
        return facts;
    }

    public void setFacts(List<FactDto> facts) {
        this.facts = facts;
    }

    public List<QueryDto> getQueries() {
        return queries;
    }

    public void setQueries(List<QueryDto> queries) {
        this.queries = queries;
    }
}
