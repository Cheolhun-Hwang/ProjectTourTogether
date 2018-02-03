package com.hch.hooney.tourtogether.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hch.hooney.tourtogether.Database.MySQLiteHandler;

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

    public static MySQLiteHandler handler;
    public static ArrayList<mainPostItem> mainPostList;
    public static ArrayList<TourApiItem> bookmarkSpotList;
    public static ArrayList<TourApiItem> bookmarkRouteList;
    public static ArrayList<TourApiItem> myCourseList;

    public static void setUser(USER user){
        DAO.user = user;
    }

    public static void init_static(){
        USER user = new USER();
        user.setUID("0000");
        user.setUNAME("Honney");
        user.setUCOUNTRY("KR");
        user.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s108-c-k-no-mo-rj-c0xffffff/photo.jpg");
        user.setUUID("qewqsa");
        DAO.setUser(user);

        DAO.init_mainPostList();
        DAO.init_bookmarkSpot();
        DAO.init_bookmarkRoute();
        DAO.init_myCourse();
    }

    public static void init_handler(Context mContext){
        handler = MySQLiteHandler.open(mContext);
    }

    public static void init_mainPostList(){
        DAO.mainPostList = new ArrayList<mainPostItem>();
    }
    public static void init_bookmarkSpot(){
        DAO.bookmarkSpotList = new ArrayList<TourApiItem>();
        DAO.bookmarkSpotList.clear();
    }

    public static void load_bookmarkSpot(){
        DAO.bookmarkSpotList.clear();
        Cursor cursor = handler.selectAll_spot();

        if(cursor.moveToFirst()){
            do{
                TourApiItem tourApiItem = new TourApiItem();
                tourApiItem.setContentID(cursor.getString(0));
                tourApiItem.setContentTypeID(cursor.getString(1));
                tourApiItem.setAddr1(cursor.getString(2));
                tourApiItem.setAddr2(cursor.getString(3));
                tourApiItem.setCat1(cursor.getString(4));
                tourApiItem.setCat2(cursor.getString(5));
                tourApiItem.setCat3(cursor.getString(6));
                tourApiItem.setMapx(cursor.getDouble(7));
                tourApiItem.setMapy(cursor.getDouble(8));
                tourApiItem.setAreaCode(cursor.getString(9));
                tourApiItem.setSigunguCode(cursor.getString(10));
                tourApiItem.setFirstImage(cursor.getString(11));
                tourApiItem.setModifyDateTIme(cursor.getString(12));
                tourApiItem.setReadCount(cursor.getString(13));
                tourApiItem.setTitle(cursor.getString(14));
                tourApiItem.setTel(cursor.getString(15));
                tourApiItem.setDirections(cursor.getString(16));
                tourApiItem.setBasic_overView(cursor.getString(17));
                DAO.bookmarkSpotList.add(tourApiItem);
            }while (cursor.moveToNext());
        }
    }

    public static void init_bookmarkRoute(){
        DAO.bookmarkRouteList = new ArrayList<TourApiItem>();
        DAO.bookmarkRouteList.clear();
    }

    public static void load_bookmarkCourse(){
        DAO.bookmarkRouteList.clear();
        Cursor cursor = handler.selectAll_course();

        if(cursor.moveToFirst()){
            do{
                TourApiItem tourApiItem = new TourApiItem();
                tourApiItem.setContentID(cursor.getString(0));
                tourApiItem.setContentTypeID(cursor.getString(1));
                tourApiItem.setAddr1(cursor.getString(2));
                tourApiItem.setAddr2(cursor.getString(3));
                tourApiItem.setCat1(cursor.getString(4));
                tourApiItem.setCat2(cursor.getString(5));
                tourApiItem.setCat3(cursor.getString(6));
                tourApiItem.setMapx(cursor.getDouble(7));
                tourApiItem.setMapy(cursor.getDouble(8));
                tourApiItem.setAreaCode(cursor.getString(9));
                tourApiItem.setSigunguCode(cursor.getString(10));
                tourApiItem.setFirstImage(cursor.getString(11));
                tourApiItem.setModifyDateTIme(cursor.getString(12));
                tourApiItem.setReadCount(cursor.getString(13));
                tourApiItem.setTitle(cursor.getString(14));
                tourApiItem.setTel(cursor.getString(15));
                tourApiItem.setDirections(cursor.getString(16));
                tourApiItem.setBasic_overView(cursor.getString(17));
                DAO.bookmarkRouteList.add(tourApiItem);
            }while (cursor.moveToNext());
        }
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

    public static boolean chkAddBookmarking(String contentId){
        for (TourApiItem item: DAO.bookmarkSpotList) {
            if(item.getContentID().equals(contentId)){
                return true;
            }
        }
        return false;
    }
}
