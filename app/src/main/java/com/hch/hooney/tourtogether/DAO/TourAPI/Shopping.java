package com.hch.hooney.tourtogether.DAO.TourAPI;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-30.
 */

public class Shopping {
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

    private String Intro_infoCenter;
    private String Intro_openTime;
    private String Intro_rest;
    private String Intro_saleItem;
    private String Intro_restRoom;
    private String Intro_parking;

    private ArrayList<RepeatInfo> repeateList;

    private ArrayList<String> smallImageList;

    public Shopping() {
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
        Intro_infoCenter = "";
        Intro_openTime = "";
        Intro_rest = "";
        Intro_saleItem = "";
        Intro_restRoom = "";
        Intro_parking = "";
        this.repeateList = null;
        this.smallImageList = null;
        this.basic_areaCode = "";
        this.basic_sigungu = "";
    }

    public Shopping(String basic_addr1, String basic_contentID, String basic_contentTypeID,
                    String basic_directory, String basic_firstImage, String basic_homepage,
                    double basic_mapX, double basic_mapY, String basic_modifyDate, String basic_overView,
                    String basic_tel, String basic_title, String intro_infoCenter, String intro_openTime,
                    String intro_rest, String intro_saleItem, String intro_restRoom, String intro_parking,
                    ArrayList<RepeatInfo> repeateList, ArrayList<String> smallImageList,
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
        Intro_infoCenter = intro_infoCenter;
        Intro_openTime = intro_openTime;
        Intro_rest = intro_rest;
        Intro_saleItem = intro_saleItem;
        Intro_restRoom = intro_restRoom;
        Intro_parking = intro_parking;
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
}
