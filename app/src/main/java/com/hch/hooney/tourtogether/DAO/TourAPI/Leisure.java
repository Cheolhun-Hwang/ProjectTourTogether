package com.hch.hooney.tourtogether.DAO.TourAPI;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-30.
 */

public class Leisure extends TourApiItem {
    private String intro_infoCenter;
    private String intro_parking;
    private String intro_parkingFee;
    private String intro_openPeriod;
    private String intro_useTime;
    private String intro_fee;
    private String intro_rest;
    private String intro_reservation;
    private String basic_homepage;

    private ArrayList<RepeatInfo> repeateList;

    private ArrayList<String> smallImageList;

    public Leisure( ) {
        super();
        this.intro_infoCenter = "";
        this.intro_parking = "";
        this.intro_parkingFee = "";
        this.intro_openPeriod = "";
        this.intro_useTime = "";
        this.intro_fee = "";
        this.intro_rest = "";
        this.intro_reservation = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public Leisure(TourApiItem item ) {
        super(item);
        this.intro_infoCenter = "";
        this.intro_parking = "";
        this.intro_parkingFee = "";
        this.intro_openPeriod = "";
        this.intro_useTime = "";
        this.intro_fee = "";
        this.intro_rest = "";
        this.intro_reservation = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public String getIntro_infoCenter() {
        return intro_infoCenter;
    }

    public void setIntro_infoCenter(String intro_infoCenter) {
        this.intro_infoCenter = intro_infoCenter;
    }

    public String getIntro_parking() {
        return intro_parking;
    }

    public void setIntro_parking(String intro_parking) {
        this.intro_parking = intro_parking;
    }

    public String getIntro_parkingFee() {
        return intro_parkingFee;
    }

    public void setIntro_parkingFee(String intro_parkingFee) {
        this.intro_parkingFee = intro_parkingFee;
    }

    public String getIntro_openPeriod() {
        return intro_openPeriod;
    }

    public void setIntro_openPeriod(String intro_openPeriod) {
        this.intro_openPeriod = intro_openPeriod;
    }

    public String getIntro_useTime() {
        return intro_useTime;
    }

    public void setIntro_useTime(String intro_useTime) {
        this.intro_useTime = intro_useTime;
    }

    public String getIntro_fee() {
        return intro_fee;
    }

    public void setIntro_fee(String intro_fee) {
        this.intro_fee = intro_fee;
    }

    public String getIntro_rest() {
        return intro_rest;
    }

    public void setIntro_rest(String intro_rest) {
        this.intro_rest = intro_rest;
    }

    public String getIntro_reservation() {
        return intro_reservation;
    }

    public void setIntro_reservation(String intro_reservation) {
        this.intro_reservation = intro_reservation;
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
