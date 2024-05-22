package com.chunjae.allforclass.dto;

public class CalDTO {
    private int lid;
    private String lname;
    private String tname;
    private String subject;
    private String startdate;
    private String timesession;

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getTimesession() {
        return timesession;
    }

    public void setTimesession(String timesession) {
        this.timesession = timesession;
    }
}
