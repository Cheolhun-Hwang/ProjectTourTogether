package com.hch.hooney.tourtogether.DAO;

import java.io.Serializable;

/**
 * Created by qewqs on 2018-01-24.
 */

public class TourApiItem implements Serializable {
    protected boolean isPost;
    protected String addr1;
    protected String addr2;
    protected String areaCode;
    protected String cat1;
    protected String cat2;
    protected String cat3;
    protected String contentID;
    protected String contentTypeID;
    protected String firstImage;
    protected double mapx;
    protected double mapy;
    protected String modifyDateTIme;
    protected String readCount;
    protected String sigunguCode;
    protected String title;
    protected String tel;
    protected String directions;
    protected String basic_overView;

    public TourApiItem() {
        this.isPost = false;
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
        this.tel="";
        this.directions = "";
        this.basic_overView = "";
    }

    public TourApiItem(boolean isPost, String addr1, String addr2, String areaCode, String cat1, String cat2, String cat3, String contentID, String contentTypeID, String firstImage, double mapx, double mapy, String modifyDateTIme, String readCount, String sigunguCode, String title, String tel, String directions, String basic_overView) {
        this.isPost = isPost;
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
        this.tel = tel;
        this.directions = directions;
        this.basic_overView = basic_overView;
    }

    public TourApiItem(TourApiItem item){
        this.isPost = false;
        this.addr1 = item.getAddr1();
        this.addr2 = item.getAddr2();
        this.areaCode = item.getAreaCode();
        this.cat1 = item.getCat1();
        this.cat2 = item.getCat2();
        this.cat3 = item.getCat3();
        this.contentID = item.getContentID();
        this.contentTypeID = item.getContentTypeID();
        this.firstImage = item.getFirstImage();
        this.mapx = item.getMapx();
        this.mapy = item.getMapy();
        this.modifyDateTIme = item.getModifyDateTIme();
        this.readCount = item.getReadCount();
        this.sigunguCode = item.getSigunguCode();
        this.title = item.getTitle();
        this.tel=item.getTel();
        this.directions = item.getDirections();
        this.basic_overView = item.getBasic_overView();
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getBasic_overView() {
        return basic_overView;
    }

    public void setBasic_overView(String basic_overView) {
        this.basic_overView = basic_overView;
    }
}
