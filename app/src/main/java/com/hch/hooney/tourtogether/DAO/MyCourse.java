package com.hch.hooney.tourtogether.DAO;

import java.util.ArrayList;

/**
 * Created by hch on 2018-02-03.
 */

public class MyCourse {
    private String mc_Title;
    private String mc_Date;
    private ArrayList<TourApiItem> mc_routeList;

    public MyCourse() {
        this.mc_Title = "";
        this.mc_Date = "";
        this.mc_routeList = null;
    }

    public MyCourse(String mc_Titme, String mc_Date, ArrayList<TourApiItem> mc_routeList) {
        this.mc_Title = mc_Titme;
        this.mc_Date = mc_Date;
        this.mc_routeList = mc_routeList;
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
