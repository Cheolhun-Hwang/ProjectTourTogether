package com.hch.hooney.tourtogether.DAO;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

/**
 * Created by qewqs on 2018-01-23.
 */

public class mainPostItem extends TourApiItem implements Serializable, ClusterItem{
    /*
    key : ContentID
    분야 : ContentTypeId
    작성날짜 : ModifyDate
    postImage : FirstImage
    postContext : Overview
    mapx : mapx
    mapy : mapy
    location : addr1
    readCount : bookMarking Count
    title : title ==> 작성자에게 정확한 포인트를 찝어 작성하도록 만들기

    !!must : ispost = true;
     */

    private String UId;
    private String UPROFILEIMAGE;
    private String UNAME;
    private String UCountry;
    private String uLanguage;

    private String commentCount;

    private String trtitle;

    private String krContext;
    private String enContext;

    public mainPostItem() {
        super();
        super.isPost = 1;
        this.UId = "";
        this.UPROFILEIMAGE = "";
        this.UNAME = "";
        this.UCountry = "";
        this.uLanguage = "";
        this.commentCount = "";
        this.krContext = "";
        this.enContext="";
        this.trtitle = "";
    }

    public mainPostItem(String UId, String UPROFILEIMAGE, String UNAME, String UCountry,
                        String uLanguage, String commentCount) {
        super();
        super.isPost = 1;
        this.UId = UId;
        this.UPROFILEIMAGE = UPROFILEIMAGE;
        this.UNAME = UNAME;
        this.UCountry = UCountry;
        this.uLanguage = uLanguage;
        this.commentCount = commentCount;
    }

    public mainPostItem(int isPost, String addr1, String addr2, String areaCode, String cat1,
                        String cat2, String cat3, String contentID, String contentTypeID,
                        String firstImage, double mapx, double mapy, String modifyDateTIme,
                        String readCount, String sigunguCode, String title, String tel,
                        String directions, String basic_overView, String UId, String UPROFILEIMAGE,
                        String UNAME, String UCountry, String uLanguage, String commentCount) {
        super(isPost, addr1, addr2, areaCode, cat1, cat2, cat3, contentID, contentTypeID,
                firstImage, mapx, mapy, modifyDateTIme, readCount, sigunguCode, title, tel,
                directions, basic_overView);
        super.isPost = 1;
        this.UId = UId;
        this.UPROFILEIMAGE = UPROFILEIMAGE;
        this.UNAME = UNAME;
        this.UCountry = UCountry;
        this.uLanguage = uLanguage;
        this.commentCount = commentCount;
    }

    public mainPostItem(TourApiItem item, String UId, String UPROFILEIMAGE, String UNAME,
                        String UCountry, String uLanguage, String commentCount) {
        super(item);
        super.isPost = 1;
        this.UId = UId;
        this.UPROFILEIMAGE = UPROFILEIMAGE;
        this.UNAME = UNAME;
        this.UCountry = UCountry;
        this.uLanguage = uLanguage;
        this.commentCount = commentCount;
    }

    public TourApiItem getSuper(){
        return new TourApiItem(
                super.isPost, super.addr1, super.addr2, super.areaCode, super.cat1,
                super.cat2, super.cat3, super.contentID, super.contentTypeID, super.firstImage,
                super.mapx, super.mapy, super.modifyDateTIme, super.readCount, super.sigunguCode,
                super.title, super.tel, super.directions, super.basic_overView
        );
    }

    public String getTrtitle() {
        return trtitle;
    }

    public void setTrtitle(String trtitle) {
        this.trtitle = trtitle;
    }

    public String getKrContext() {
        return krContext;
    }

    public void setKrContext(String krContext) {
        this.krContext = krContext;
    }

    public String getEnContext() {
        return enContext;
    }

    public void setEnContext(String enContext) {
        this.enContext = enContext;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getUPROFILEIMAGE() {
        return UPROFILEIMAGE;
    }

    public void setUPROFILEIMAGE(String UPROFILEIMAGE) {
        this.UPROFILEIMAGE = UPROFILEIMAGE;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getUCountry() {
        return UCountry;
    }

    public void setUCountry(String UCountry) {
        this.UCountry = UCountry;
    }

    public String getuLanguage() {
        return uLanguage;
    }

    public void setuLanguage(String uLanguage) {
        this.uLanguage = uLanguage;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(getMapy(), getMapx());
    }
}
