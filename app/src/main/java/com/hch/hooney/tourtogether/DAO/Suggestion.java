package com.hch.hooney.tourtogether.DAO;

/**
 * Created by hch on 2018-03-06.
 */

public class Suggestion {
    private String Who;
    private String When;
    private String Reason;
    private int ReasonNum;
    private String WContext;
    private String WCountry;
    private String Login;

    public Suggestion() {
        Who = "";
        When = "";
        Reason = "";
        ReasonNum = 0;
        WContext = "";
        this.WCountry = "";
        Login = "";
    }

    public Suggestion(String who, String when, String reason,
                      int reasonNum, String context, String WCountry, String login) {
        Who = who;
        When = when;
        Reason = reason;
        ReasonNum = reasonNum;
        WContext = context;
        this.WCountry = WCountry;
        Login = login;
    }

    public String getWho() {
        return Who;
    }

    public void setWho(String who) {
        Who = who;
    }

    public String getWhen() {
        return When;
    }

    public void setWhen(String when) {
        When = when;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int getReasonNum() {
        return ReasonNum;
    }

    public void setReasonNum(int reasonNum) {
        ReasonNum = reasonNum;
    }

    public String getWContext() {
        return WContext;
    }

    public void setWContext(String context) {
        WContext = context;
    }

    public String getWCountry() {
        return WCountry;
    }

    public void setWCountry(String WCountry) {
        this.WCountry = WCountry;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }
}
