package com.acs.fileChecker.db.beans;

public class TblVariableReport {
    private String reportID;
    private String instanceID;
    private String fileName;
    private String frequencyID;
    private String checkTime;
    private String checkStop;
    private String ignoreFlag;
    private String disabled;
    private String hasSLA;
    private String ignoreMissing;

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFrequencyID() {
        return frequencyID;
    }

    public void setFrequencyID(String frequencyID) {
        this.frequencyID = frequencyID;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckStop() {
        return checkStop;
    }

    public void setCheckStop(String checkStop) {
        this.checkStop = checkStop;
    }

    public String getIgnoreFlag() {
        return ignoreFlag;
    }

    public void setIgnoreFlag(String ignoreFlag) {
        this.ignoreFlag = ignoreFlag;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getHasSLA() {
        return hasSLA;
    }

    public void setHasSLA(String hasSLA) {
        this.hasSLA = hasSLA;
    }

    public String getIgnoreMissing() {
        return ignoreMissing;
    }

    public void setIgnoreMissing(String ignoreMissing) {
        this.ignoreMissing = ignoreMissing;
    }
}
