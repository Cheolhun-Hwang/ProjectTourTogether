package com.hch.hooney.tourtogether.DAO.TourAPI;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-29.
 */

public class Facility extends TourApiItem {
    private String intro_infocenterculture;
    private String intro_parking;
    private String intro_parkingfee;
    private String intro_restdateculture;
    private String intro_spendtime;
    private String intro_usefee;
    private String intro_usetimeculture;
    private String basic_homepage;

    private ArrayList<RepeatInfo> repeateList;

    private ArrayList<String> smallImageList;

    public Facility() {
        super();
        this.intro_infocenterculture = "";
        this.intro_parking = "";
        this.intro_parkingfee = "";
        this.intro_restdateculture = "";
        this.intro_spendtime = "";
        this.intro_usefee = "";
        this.intro_usetimeculture = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public Facility(TourApiItem item) {
        super(item);
        this.intro_infocenterculture = "";
        this.intro_parking = "";
        this.intro_parkingfee = "";
        this.intro_restdateculture = "";
        this.intro_spendtime = "";
        this.intro_usefee = "";
        this.intro_usetimeculture = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public String getIntro_infocenterculture() {
        return intro_infocenterculture;
    }

    public void setIntro_infocenterculture(String intro_infocenterculture) {
        this.intro_infocenterculture = intro_infocenterculture;
    }

    public String getIntro_parking() {
        return intro_parking;
    }

    public void setIntro_parking(String intro_parking) {
        this.intro_parking = intro_parking;
    }

    public String getIntro_parkingfee() {
        return intro_parkingfee;
    }

    public void setIntro_parkingfee(String intro_parkingfee) {
        this.intro_parkingfee = intro_parkingfee;
    }

    public String getIntro_restdateculture() {
        return intro_restdateculture;
    }

    public void setIntro_restdateculture(String intro_restdateculture) {
        this.intro_restdateculture = intro_restdateculture;
    }

    public String getIntro_spendtime() {
        return intro_spendtime;
    }

    public void setIntro_spendtime(String intro_spendtime) {
        this.intro_spendtime = intro_spendtime;
    }

    public String getIntro_usefee() {
        return intro_usefee;
    }

    public void setIntro_usefee(String intro_usefee) {
        this.intro_usefee = intro_usefee;
    }

    public String getIntro_usetimeculture() {
        return intro_usetimeculture;
    }

    public void setIntro_usetimeculture(String intro_usetimeculture) {
        this.intro_usetimeculture = intro_usetimeculture;
    }

    public ArrayList<RepeatInfo> getRepeateList() {
        return repeateList;
    }

    public void setRepeateList(ArrayList<RepeatInfo> repeateList) {
        this.repeateList = repeateList;
    }

    public ArrayList<String> getSmallImageList() {
        return smallImageList;
    }

    public void setSmallImageList(ArrayList<String> smallImageList) {
        this.smallImageList = smallImageList;
    }

    public String getBasic_homepage() {
        return basic_homepage;
    }

    public void setBasic_homepage(String basic_homepage) {
        this.basic_homepage = basic_homepage;
    }
}
