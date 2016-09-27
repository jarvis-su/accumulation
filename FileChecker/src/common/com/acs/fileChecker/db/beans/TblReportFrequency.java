package com.acs.fileChecker.db.beans;

public class TblReportFrequency {
    private String frequencyID;
    private String desc;
    private String month;
    private String dayOfMonth;
    private String dayOfWeek;

    public String getFrequencyID() {
        return frequencyID;
    }

    public void setFrequencyID(String frequencyID) {
        this.frequencyID = frequencyID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
