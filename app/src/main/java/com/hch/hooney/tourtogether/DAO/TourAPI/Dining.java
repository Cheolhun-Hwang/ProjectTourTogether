package com.hch.hooney.tourtogether.DAO.TourAPI;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-29.
 */

public class Dining extends TourApiItem {
    private String intro_menu;
    private String intro_parking;
    private String intro_openHour;
    private String intro_dayOff;
    private String intro_smoking;
    private String intro_reservation;
    private String basic_homepage;

    private ArrayList<String> smallImageList;

    public Dining() {
        super();
        this.intro_menu = "";
        this.intro_parking = "";
        this.intro_openHour = "";
        this.intro_dayOff = "";
        this.intro_smoking = "";
        this.intro_reservation = "";
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public Dining(TourApiItem item) {
        super(item);
        this.intro_menu = "";
        this.intro_parking = "";
        this.intro_openHour = "";
        this.intro_dayOff = "";
        this.intro_smoking = "";
        this.intro_reservation = "";
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public String getIntro_menu() {
        return intro_menu;
    }

    public void setIntro_menu(String intro_menu) {
        this.intro_menu = intro_menu;
    }

    public String getIntro_parking() {
        return intro_parking;
    }

    public void setIntro_parking(String intro_parking) {
        this.intro_parking = intro_parking;
    }

    public String getIntro_openHour() {
        return intro_openHour;
    }

    public void setIntro_openHour(String intro_openHour) {
        this.intro_openHour = intro_openHour;
    }

    public String getIntro_dayOff() {
        return intro_dayOff;
    }

    public void setIntro_dayOff(String intro_dayOff) {
        this.intro_dayOff = intro_dayOff;
    }

    public String getIntro_smoking() {
        return intro_smoking;
    }

    public void setIntro_smoking(String intro_smoking) {
        this.intro_smoking = intro_smoking;
    }

    public String getIntro_reservation() {
        return intro_reservation;
    }

    public void setIntro_reservation(String intro_reservation) {
        this.intro_reservation = intro_reservation;
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
