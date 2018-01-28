package com.hch.hooney.tourtogether.DAO.TourAPI;

import java.util.ArrayList;

/**
 * Created by hooney on 2018. 1. 28..
 */

public class Accomodation {
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

    private String intro_checkInTime;
    private String intro_checkOutTime;
    private String intro_chkCooking;
    private String intro_foodPlace;
    private String intro_infoCenter;
    private String intro_parking;
    private String intro_reservation;
    private String intro_roomtype;
    private String intro_subFacility;

    private ArrayList<String> smallImageList;

    public Accomodation() {
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
    }

    public Accomodation(String basic_addr1, String basic_contentID, String basic_contentTypeID,
                        String basic_directory, String basic_firstImage, String basic_homepage,
                        double basic_mapX, double basic_mapY, String basic_modifyDate, String basic_overView,
                        String basic_tel, String basic_title, String intro_checkInTime, String intro_checkOutTime,
                        String intro_chkCooking, String intro_foodPlace, String intro_infoCenter, String intro_parking,
                        String intro_reservation, String intro_roomtype, String intro_subFacility, ArrayList<String> smallImageList) {
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
        this.intro_checkInTime = intro_checkInTime;
        this.intro_checkOutTime = intro_checkOutTime;
        this.intro_chkCooking = intro_chkCooking;
        this.intro_foodPlace = intro_foodPlace;
        this.intro_infoCenter = intro_infoCenter;
        this.intro_parking = intro_parking;
        this.intro_reservation = intro_reservation;
        this.intro_roomtype = intro_roomtype;
        this.intro_subFacility = intro_subFacility;
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
}
