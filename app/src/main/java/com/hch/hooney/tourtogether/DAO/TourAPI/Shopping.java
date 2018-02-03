package com.hch.hooney.tourtogether.DAO.TourAPI;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-30.
 */

public class Shopping extends TourApiItem {
    private String Intro_infoCenter;
    private String Intro_openTime;
    private String Intro_rest;
    private String Intro_saleItem;
    private String Intro_restRoom;
    private String Intro_parking;

    private String basic_homepage;

    private ArrayList<RepeatInfo> repeateList;

    private ArrayList<String> smallImageList;

    public Shopping() {
        super();
        Intro_infoCenter = "";
        Intro_openTime = "";
        Intro_rest = "";
        Intro_saleItem = "";
        Intro_restRoom = "";
        Intro_parking = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public Shopping(TourApiItem item) {
        super(item);
        Intro_infoCenter = "";
        Intro_openTime = "";
        Intro_rest = "";
        Intro_saleItem = "";
        Intro_restRoom = "";
        Intro_parking = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_homepage = "";
    }

    public String getIntro_infoCenter() {
        return Intro_infoCenter;
    }

    public void setIntro_infoCenter(String intro_infoCenter) {
        Intro_infoCenter = intro_infoCenter;
    }

    public String getIntro_openTime() {
        return Intro_openTime;
    }

    public void setIntro_openTime(String intro_openTime) {
        Intro_openTime = intro_openTime;
    }

    public String getIntro_rest() {
        return Intro_rest;
    }

    public void setIntro_rest(String intro_rest) {
        Intro_rest = intro_rest;
    }

    public String getIntro_saleItem() {
        return Intro_saleItem;
    }

    public void setIntro_saleItem(String intro_saleItem) {
        Intro_saleItem = intro_saleItem;
    }

    public String getIntro_restRoom() {
        return Intro_restRoom;
    }

    public void setIntro_restRoom(String intro_restRoom) {
        Intro_restRoom = intro_restRoom;
    }

    public String getIntro_parking() {
        return Intro_parking;
    }

    public void setIntro_parking(String intro_parking) {
        Intro_parking = intro_parking;
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
