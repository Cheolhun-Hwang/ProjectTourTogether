package com.hch.hooney.tourtogether.DAO;

/**
 * Created by qewqs on 2018-01-24.
 */

public class TourApiItem {
    private String addr1;
    private String addr2;
    private String areaCode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String contentID;
    private String contentTypeID;
    private String firstImage;
    private double mapx;
    private double mapy;
    private String modifyDateTIme;
    private String readCount;
    private String sigunguCode;
    private String title;

    public TourApiItem() {
        this.addr1 = "";
        this.addr2 = "";
        this.areaCode = "";
        this.cat1 = "";
        this.cat2 = "";
        this.cat3 = "";
        this.contentID = "";
        this.contentTypeID = "";
        this.firstImage = "";
        this.mapx = 0.0;
        this.mapy = 0.0;
        this.modifyDateTIme = "";
        this.readCount = "";
        this.sigunguCode = "";
        this.title = "";
    }

    public TourApiItem(String addr1, String addr2, String areaCode, String cat1,
                       String cat2, String cat3, String contentID, String contentTypeID,
                       String firstImage, double mapx, double mapy, String modifyDateTIme,
                       String readCount, String sigunguCode, String title) {
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areaCode = areaCode;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.contentID = contentID;
        this.contentTypeID = contentTypeID;
        this.firstImage = firstImage;
        this.mapx = mapx;
        this.mapy = mapy;
        this.modifyDateTIme = modifyDateTIme;
        this.readCount = readCount;
        this.sigunguCode = sigunguCode;
        this.title = title;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getContentTypeID() {
        return contentTypeID;
    }

    public void setContentTypeID(String contentTypeID) {
        this.contentTypeID = contentTypeID;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

    public String getModifyDateTIme() {
        return modifyDateTIme;
    }

    public void setModifyDateTIme(String modifyDateTIme) {
        this.modifyDateTIme = modifyDateTIme;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getSigunguCode() {
        return sigunguCode;
    }

    public void setSigunguCode(String sigunguCode) {
        this.sigunguCode = sigunguCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
