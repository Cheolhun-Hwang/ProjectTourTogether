package com.hch.hooney.tourtogether.DAO.TourAPI;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-30.
 */

public class Leisure {
    private String basic_addr1;
    private String basic_areaCode;
    private String basic_sigungu;
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

    private String intro_infoCenter;
    private String intro_parking;
    private String intro_parkingFee;
    private String intro_openPeriod;
    private String intro_useTime;
    private String intro_fee;
    private String intro_rest;
    private String intro_reservation;

    private ArrayList<RepeatInfo> repeateList;

    private ArrayList<String> smallImageList;

    public Leisure( ) {
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
        this.basic_areaCode = "";
        this.basic_sigungu = "";
    }

    public Leisure(String basic_addr1, String basic_contentID, String basic_contentTypeID,
                   String basic_directory, String basic_firstImage, String basic_homepage,
                   double basic_mapX, double basic_mapY, String basic_modifyDate, String basic_overView,
                   String basic_tel, String basic_title, String intro_infoCenter, String intro_parking,
                   String intro_parkingFee, String intro_openPeriod, String intro_useTime, String intro_fee,
                   String intro_rest, String intro_reservation, ArrayList<RepeatInfo> repeateList, ArrayList<String> smallImageList,
                   String areacode, String sigungu) {
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
        this.intro_infoCenter = intro_infoCenter;
        this.intro_parking = intro_parking;
        this.intro_parkingFee = intro_parkingFee;
        this.intro_openPeriod = intro_openPeriod;
        this.intro_useTime = intro_useTime;
        this.intro_fee = intro_fee;
        this.intro_rest = intro_rest;
        this.intro_reservation = intro_reservation;
        this.repeateList = repeateList;
        this.smallImageList = smallImageList;
        this.basic_areaCode = areacode;
        this.basic_sigungu = sigungu;
    }

    public String getBasic_areaCode() {
        return basic_areaCode;
    }

    public void setBasic_areaCode(String basic_areaCode) {
        this.basic_areaCode = basic_areaCode;
    }

    public String getBasic_sigungu() {
        return basic_sigungu;
    }

    public void setBasic_sigungu(String basic_sigungu) {
        this.basic_sigungu = basic_sigungu;
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
}
