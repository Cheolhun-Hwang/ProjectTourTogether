package com.hch.hooney.tourtogether.DAO;

/**
 * Created by qewqs on 2018-01-23.
 */

public class USER {
    private String UID;
    private String UUID;
    private String UNAME;
    private String UPROFILEIMAGE;
    private String UEMAIL;
    private String UPHONE;

    public USER() {
        this.UID = "";
        this.UUID = "";
        this.UNAME = "";
        this.UPROFILEIMAGE = "";
        this.UEMAIL = "";
        this.UPHONE="";
    }

    public String getUEMAIL() {
        return UEMAIL;
    }

    public void setUEMAIL(String UEMAIL) {
        this.UEMAIL = UEMAIL;
    }

    public String getUPHONE() {
        return UPHONE;
    }

    public void setUPHONE(String UPHONE) {
        this.UPHONE = UPHONE;
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

}
