package com.ftn.sbnz.service.dto;

public class QueryResultDto {
    private String query;
    private String crossroad;
    private String cause;

    public QueryResultDto() {}

    public QueryResultDto(String query, String crossroad, String cause) {
        this.query = query;
        this.crossroad = crossroad;
        this.cause = cause;
    }

    public void setCause(String cause){
        this.cause = cause;
    }

    public String getCause(){
        return this.cause;
    }

    public void setCrossroad(String crossroad){
        this.crossroad = crossroad;
    }

    public String getCrossroad(){
        return this.crossroad;
    }

    public void setQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return this.query;
    }

    @Override
    public String toString() {
        return String.format("Query '%s' found cause '%s' at crossroad '%s'", 
            query, cause, crossroad);
    }

}