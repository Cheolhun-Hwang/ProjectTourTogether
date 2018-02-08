package com.hch.hooney.tourtogether.DAO;

import java.util.ArrayList;

/**
 * Created by hch on 2018-02-03.
 */

public class MyCourse{
    private String mc_Title;
    private String mc_Date;
    private String mc_Coutry;
    private String mc_Language;
    private String mc_key;
    private ArrayList<TourApiItem> mc_routeList;

    public MyCourse() {
        this.mc_Title = "";
        this.mc_Date = "";
        this.mc_routeList = null;
        this.mc_key = "";

        this.mc_Coutry = "";
        this.mc_Language = "";
    }

    public MyCourse(String mc_Titme, String mc_Date,
                    String mc_Coutry, String mc_Language, String key,
                    ArrayList<TourApiItem> mc_routeList) {
        this.mc_Title = mc_Titme;
        this.mc_Date = mc_Date;
        this.mc_routeList = mc_routeList;
        this.mc_key = key;
        this.mc_Coutry = mc_Coutry;
        this.mc_Language = mc_Language;
    }

    public String getMc_key() {
        return mc_key;
    }

    public void setMc_key(String mc_key) {
        this.mc_key = mc_key;
    }

    public String getMc_Coutry() {
        return mc_Coutry;
    }

    public void setMc_Coutry(String mc_Coutry) {
        this.mc_Coutry = mc_Coutry;
    }

    public String getMc_Language() {
        return mc_Language;
    }

    public void setMc_Language(String mc_Language) {
        this.mc_Language = mc_Language;
    }

    public String getMc_Title() {
        return mc_Title;
    }

    public void setMc_Title(String mc_Titme) {
        this.mc_Title = mc_Titme;
    }

    public String getMc_Date() {
        return mc_Date;
    }

    public void setMc_Date(String mc_Date) {
        this.mc_Date = mc_Date;
    }

    public ArrayList<TourApiItem> getMc_routeList() {
        return mc_routeList;
    }

    public void setMc_routeList(ArrayList<TourApiItem> mc_routeList) {
        this.mc_routeList = mc_routeList;
    }
}
