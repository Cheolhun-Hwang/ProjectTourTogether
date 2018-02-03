package com.hch.hooney.tourtogether.DAO.TourAPI;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

/**
 * Created by hooney on 2018. 1. 28..
 */

public class Accomodation extends TourApiItem{
    private String intro_checkInTime;
    private String intro_checkOutTime;
    private String intro_chkCooking;
    private String intro_foodPlace;
    private String intro_infoCenter;
    private String intro_parking;
    private String intro_reservation;
    private String intro_roomtype;
    private String intro_subFacility;
    private String basic_homepage;


    private ArrayList<String> smallImageList;

    public Accomodation() {
        super();
        this.basic_homepage = "";
        this.intro_checkInTime = "";
        this.intro_checkOutTime = "";
        this.intro_chkCooking = "";
        this.intro_foodPlace = "";
        this.intro_infoCenter = "";
        this.intro_parking = "";
        this.intro_reservation = "";
        this.intro_roomtype = "";
        this.intro_subFacility = "";
        this.smallImageList = null;
        this.basic_homepage="";
    }

    public Accomodation(TourApiItem tourApiItem) {
        super(tourApiItem);
        this.basic_homepage = "";
        this.intro_checkInTime = "";
        this.intro_checkOutTime = "";
        this.intro_chkCooking = "";
        this.intro_foodPlace = "";
        this.intro_infoCenter = "";
        this.intro_parking = "";
        this.intro_reservation = "";
        this.intro_roomtype = "";
        this.intro_subFacility = "";
        this.smallImageList = null;
        this.basic_homepage="";
    }

    public String getIntro_checkInTime() {
        return intro_checkInTime;
    }

    public void setIntro_checkInTime(String intro_checkInTime) {
        this.intro_checkInTime = intro_checkInTime;
    }

    public String getIntro_checkOutTime() {
        return intro_checkOutTime;
    }

    public void setIntro_checkOutTime(String intro_checkOutTime) {
        this.intro_checkOutTime = intro_checkOutTime;
    }

    public String getIntro_chkCooking() {
        return intro_chkCooking;
    }

    public void setIntro_chkCooking(String intro_chkCooking) {
        this.intro_chkCooking = intro_chkCooking;
    }

    public String getIntro_foodPlace() {
        return intro_foodPlace;
    }

    public void setIntro_foodPlace(String intro_foodPlace) {
        this.intro_foodPlace = intro_foodPlace;
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

    public String getIntro_reservation() {
        return intro_reservation;
    }

    public void setIntro_reservation(String intro_reservation) {
        this.intro_reservation = intro_reservation;
    }

    public String getIntro_roomtype() {
        return intro_roomtype;
    }

    public void setIntro_roomtype(String intro_roomtype) {
        this.intro_roomtype = intro_roomtype;
    }

    public String getIntro_subFacility() {
        return intro_subFacility;
    }

    public void setIntro_subFacility(String intro_subFacility) {
        this.intro_subFacility = intro_subFacility;
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
