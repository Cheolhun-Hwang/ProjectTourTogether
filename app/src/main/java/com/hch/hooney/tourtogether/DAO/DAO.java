package com.hch.hooney.tourtogether.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by qewqs on 2018-01-23.
 */

public class DAO {
    public static String ServiceKey="6tLZdNCgFtUUkC1aMEPPSDH5EqZB09HbJ9vEwO1DeRGItkpZQzyAxdTw2npenOfhIQdsklstTNt9qrj2RODhkQ%3D%3D";
    public static String Country="";
    public static String Language="";
    public static USER user;
    public static Setting setting;
    public static ArrayList<mainPostItem> mainPostList;
    public static ArrayList<TourApiItem> bookmarkSpotList;
    public static ArrayList<TourApiItem> bookmarkRouteList;
    public static ArrayList<TourApiItem> myCourseList;

    public static void setUser(USER user){
        DAO.user = user;
    }

    public static void init_mainPostList(){
        DAO.mainPostList = new ArrayList<mainPostItem>();
    }
    public static void init_bookmarkSpot(){
        DAO.bookmarkSpotList = new ArrayList<TourApiItem>();

        //temp
        TourApiItem tourApiItem = new TourApiItem();
        tourApiItem.setAddr1("161, Sajik-ro, Jongno-gu, Seoul");
        tourApiItem.setAddr2("");
        tourApiItem.setAreaCode("1");
        tourApiItem.setCat1("A02");
        tourApiItem.setCat2("A0201");
        tourApiItem.setCat3("A02010100");
        tourApiItem.setContentID("126508");
        tourApiItem.setContentTypeID("12");
        tourApiItem.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/40/1568040_image2_1.jpg");
        tourApiItem.setMapx(126.9767235747);
        tourApiItem.setMapy(37.5801859611);
        tourApiItem.setModifyDateTIme("20171111153343");
        tourApiItem.setReadCount("107744");
        tourApiItem.setSigunguCode("23");
        tourApiItem.setTitle("Gyeongbokgung Palace (경복궁)");

        DAO.bookmarkSpotList.add(tourApiItem);
    }
    public static void init_bookmarkRoute(){
        DAO.bookmarkRouteList = new ArrayList<TourApiItem>();

        //temp
        TourApiItem tourApiItem1 = new TourApiItem();
        tourApiItem1.setAreaCode("1");
        tourApiItem1.setCat1("C01");
        tourApiItem1.setCat2("C0112");
        tourApiItem1.setCat3("C01120001");
        tourApiItem1.setContentID("1914505");
        tourApiItem1.setContentTypeID("25");
        tourApiItem1.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/40/1570340_image2_1.jpg");
        tourApiItem1.setMapx(126.9786237129);
        tourApiItem1.setMapy(37.5691567219);
        tourApiItem1.setModifyDateTIme("20160927181406");
        tourApiItem1.setReadCount("15135");
        tourApiItem1.setSigunguCode("24");
        tourApiItem1.setTitle("청계천을 돌아 경복궁에서 인사동까지");

        DAO.bookmarkRouteList.add(tourApiItem1);

        TourApiItem tourApiItem2 = new TourApiItem();
        tourApiItem2.setAreaCode("1");
        tourApiItem2.setCat1("C01");
        tourApiItem2.setCat2("C0112");
        tourApiItem2.setCat3("C01120001");
        tourApiItem2.setContentID("2022929");
        tourApiItem2.setContentTypeID("25");
        tourApiItem2.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/72/1782972_image2_1.jpg");
        tourApiItem2.setMapx(126.9768292386);
        tourApiItem2.setMapy(37.5759947835);
        tourApiItem2.setModifyDateTIme("20170120141557");
        tourApiItem2.setReadCount("14028");
        tourApiItem2.setSigunguCode("99");
        tourApiItem2.setTitle("낮이 좋아? 밤이 좋아? 서울의 주경, 야경 여행코스");

        DAO.bookmarkRouteList.add(tourApiItem2);
    }
    public static void init_myCourse(){
        DAO.myCourseList = new ArrayList<TourApiItem>();

        //temp
        TourApiItem tourApiItem = new TourApiItem();
        tourApiItem.setAddr1("161, Sajik-ro, Jongno-gu, Seoul");
        tourApiItem.setAddr2("");
        tourApiItem.setAreaCode("1");
        tourApiItem.setCat1("A02");
        tourApiItem.setCat2("A0201");
        tourApiItem.setCat3("A02010100");
        tourApiItem.setContentID("126508");
        tourApiItem.setContentTypeID("12");
        tourApiItem.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/40/1568040_image2_1.jpg");
        tourApiItem.setMapx(126.9767235747);
        tourApiItem.setMapy(37.5801859611);
        tourApiItem.setModifyDateTIme("20171111153343");
        tourApiItem.setReadCount("107744");
        tourApiItem.setSigunguCode("23");
        tourApiItem.setTitle("Gyeongbokgung Palace (경복궁) 2");

        DAO.myCourseList.add(tourApiItem);
    }
    public static void loadData_mainPostList(){
        //temp
        mainPostItem mainPostItem1 = new mainPostItem();
        mainPostItem1.setPostID("post_1");
        mainPostItem1.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg");
        mainPostItem1.setUNAME("Hooney");
        mainPostItem1.setPostImage("http://tong.visitkorea.or.kr/cms/resource/40/1568040_image2_1.jpg");
        mainPostItem1.setPostContext("Temp Post Comment...");
        mainPostItem1.setLocation("161, Sajik-ro, Jongno-gu, Seoul, Korea");
        mainPostItem1.setMapy(37.5801859611);
        mainPostItem1.setMapx(126.9767235747);
        mainPostItem1.setPushLike(false);
        mainPostItem1.setLikeCount("Like 0");
        mainPostItem1.setCommentCount("Comment 0");

        DAO.mainPostList.add(mainPostItem1);

        mainPostItem mainPostItem2 = new mainPostItem();
        mainPostItem2.setPostID("post_2");
        mainPostItem2.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg");
        mainPostItem2.setUNAME("Hooney");
        mainPostItem2.setPostImage("http://tong.visitkorea.or.kr/cms/resource/67/1567867_image2_1.jpg");
        mainPostItem2.setPostContext("Temp Post Comment...");
        mainPostItem2.setLocation("240, Olympic-ro, Songpa-gu, Seoul, Korea");
        mainPostItem2.setMapy(37.5113516917);
        mainPostItem2.setMapx(127.0979006014);
        mainPostItem2.setPushLike(true);
        mainPostItem2.setLikeCount("Like 1");
        mainPostItem2.setCommentCount("Comment 0");

        DAO.mainPostList.add(mainPostItem2);

        mainPostItem mainPostItem3 = new mainPostItem();
        mainPostItem3.setPostID("post_2");
        mainPostItem3.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg");
        mainPostItem3.setUNAME("Hooney");
        mainPostItem3.setPostImage("http://tong.visitkorea.or.kr/cms/resource/36/1567936_image2_1.jpg");
        mainPostItem3.setPostContext("Temp Post Comment...");
        mainPostItem3.setLocation("50, 63-ro, Yeongdeungpo-gu, Seoul, Korea");
        mainPostItem3.setMapy(37.5197701048);
        mainPostItem3.setMapx(126.9401702800);
        mainPostItem3.setPushLike(false);
        mainPostItem3.setLikeCount("Like 0");
        mainPostItem3.setCommentCount("Comment 0");

        DAO.mainPostList.add(mainPostItem3);
    }

    @SuppressLint("LongLogTag")
    public static void setCountryAndLanguage(Context mContext){
        Locale systemLocale = mContext.getResources().getConfiguration().locale;
        DAO.Country = systemLocale.getCountry();
        DAO.Language = systemLocale.getLanguage();

        //  KR : ko       US : en

        Log.d("Set Country and Language", DAO.Country + " / " + DAO.Language);
    }
}
