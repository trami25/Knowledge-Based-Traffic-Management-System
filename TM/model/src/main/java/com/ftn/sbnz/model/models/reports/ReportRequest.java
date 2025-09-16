package com.ftn.sbnz.model.models.reports;

import java.util.Date;

public class ReportRequest {
    private String type; // "Normal", "Trend", "MaxPeriod"
    private String crossroadId;
    private Date fromDate;
    private Date toDate;
    private String period;

    public ReportRequest() {}

    public ReportRequest(String type, String crossroadId, Date fromDate, Date toDate, String period) {
        this.type = type;
        this.crossroadId = crossroadId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.period = period;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCrossroadId() { return crossroadId; }
    public void setCrossroadId(String crossroadId) { this.crossroadId = crossroadId; }

    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
