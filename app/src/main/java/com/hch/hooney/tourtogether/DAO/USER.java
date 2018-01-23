package com.hch.hooney.tourtogether.DAO;

/**
 * Created by qewqs on 2018-01-23.
 */

public class USER {
    private String UID;
    private String UUID;
    private String UNAME;
    private String UPROFILEIMAGE;
    private String UCOUNTRY;

    public USER() {
        this.UID = "";
        this.UUID = "";
        this.UNAME = "";
        this.UPROFILEIMAGE = "";
        this.UCOUNTRY = "";
    }

    public USER(String UID, String UUID, String UNAME, String UPROFILEIMAGE, String UCOUNTRY) {
        this.UID = UID;
        this.UUID = UUID;
        this.UNAME = UNAME;
        this.UPROFILEIMAGE = UPROFILEIMAGE;
        this.UCOUNTRY = UCOUNTRY;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getUPROFILEIMAGE() {
        return UPROFILEIMAGE;
    }

    public void setUPROFILEIMAGE(String UPROFILEIMAGE) {
        this.UPROFILEIMAGE = UPROFILEIMAGE;
    }

    public String getUCOUNTRY() {
        return UCOUNTRY;
    }

    public void setUCOUNTRY(String UCOUNTRY) {
        this.UCOUNTRY = UCOUNTRY;
    }
}
