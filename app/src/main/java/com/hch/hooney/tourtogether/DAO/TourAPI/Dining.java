package com.hch.hooney.tourtogether.DAO.TourAPI;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-29.
 */

public class Dining {
    private String basic_addr1;
    private String basic_contentID;
    private String basic_contentTypeID;
    private String basic_directory;
    private String basic_firstImage;
    private String basic_homepage;
    private double basic_mapX;
    private double basic_mapY;
    private String basic_modifyDate;
    private String basic_overView;
    private String basic_tel;
    private String basic_title;

    private String intro_menu;
    private String intro_parking;
    private String intro_openHour;
    private String intro_dayOff;
    private String intro_smoking;
    private String intro_reservation;

    private ArrayList<String> smallImageList;

    public Dining() {
        this.basic_addr1 = "";
        this.basic_contentID = "";
        this.basic_contentTypeID = "";
        this.basic_directory = "";
        this.basic_firstImage = "";
        this.basic_homepage = "";
        this.basic_mapX = 0.0;
        this.basic_mapY = 0.0;
        this.basic_modifyDate = "";
        this.basic_overView = "";
        this.basic_tel = "";
        this.basic_title = "";
        this.intro_menu = "";
        this.intro_parking = "";
        this.intro_openHour = "";
        this.intro_dayOff = "";
        this.intro_smoking = "";
        this.intro_reservation = "";
        this.smallImageList = null;
    }

    public Dining(String basic_addr1, String basic_contentID, String basic_contentTypeID, String basic_directory,
                  String basic_firstImage, String basic_homepage, double basic_mapX, double basic_mapY,
                  String basic_modifyDate, String basic_overView, String basic_tel, String basic_title,
                  String intro_menu, String intro_parking, String intro_openHour, String intro_dayOff,
                  String intro_smoking, String intro_reservation, ArrayList<String> smallImageList) {
        this.basic_addr1 = basic_addr1;
        this.basic_contentID = basic_contentID;
        this.basic_contentTypeID = basic_contentTypeID;
        this.basic_directory = basic_directory;
        this.basic_firstImage = basic_firstImage;
        this.basic_homepage = basic_homepage;
        this.basic_mapX = basic_mapX;
        this.basic_mapY = basic_mapY;
        this.basic_modifyDate = basic_modifyDate;
        this.basic_overView = basic_overView;
        this.basic_tel = basic_tel;
        this.basic_title = basic_title;
        this.intro_menu = intro_menu;
        this.intro_parking = intro_parking;
        this.intro_openHour = intro_openHour;
        this.intro_dayOff = intro_dayOff;
        this.intro_smoking = intro_smoking;
        this.intro_reservation = intro_reservation;
        this.smallImageList = smallImageList;
    }

    public String getBasic_addr1() {
        return basic_addr1;
    }

    public void setBasic_addr1(String basic_addr1) {
        this.basic_addr1 = basic_addr1;
    }

    public String getBasic_contentID() {
        return basic_contentID;
    }

    public void setBasic_contentID(String basic_contentID) {
        this.basic_contentID = basic_contentID;
    }

    public String getBasic_contentTypeID() {
        return basic_contentTypeID;
    }

    public void setBasic_contentTypeID(String basic_contentTypeID) {
        this.basic_contentTypeID = basic_contentTypeID;
    }

    public String getBasic_directory() {
        return basic_directory;
    }

    public void setBasic_directory(String basic_directory) {
        this.basic_directory = basic_directory;
    }

    public String getBasic_firstImage() {
        return basic_firstImage;
    }

    public void setBasic_firstImage(String basic_firstImage) {
        this.basic_firstImage = basic_firstImage;
    }

    public String getBasic_homepage() {
        return basic_homepage;
    }

    public void setBasic_homepage(String basic_homepage) {
        this.basic_homepage = basic_homepage;
    }

    public double getBasic_mapX() {
        return basic_mapX;
    }

    public void setBasic_mapX(double basic_mapX) {
        this.basic_mapX = basic_mapX;
    }

    public double getBasic_mapY() {
        return basic_mapY;
    }

    public void setBasic_mapY(double basic_mapY) {
        this.basic_mapY = basic_mapY;
    }

    public String getBasic_modifyDate() {
        return basic_modifyDate;
    }

    public void setBasic_modifyDate(String basic_modifyDate) {
        this.basic_modifyDate = basic_modifyDate;
    }

    public String getBasic_overView() {
        return basic_overView;
    }

    public void setBasic_overView(String basic_overView) {
        this.basic_overView = basic_overView;
    }

    public String getBasic_tel() {
        return basic_tel;
    }

    public void setBasic_tel(String basic_tel) {
        this.basic_tel = basic_tel;
    }

    public String getBasic_title() {
        return basic_title;
    }

    public void setBasic_title(String basic_title) {
        this.basic_title = basic_title;
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
}
