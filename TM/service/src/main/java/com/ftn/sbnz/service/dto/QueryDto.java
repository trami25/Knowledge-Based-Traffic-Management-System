package com.ftn.sbnz.service.dto;

import java.util.List;

public class QueryDto {
    private String name;
    private List<Object> params;

    public QueryDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
