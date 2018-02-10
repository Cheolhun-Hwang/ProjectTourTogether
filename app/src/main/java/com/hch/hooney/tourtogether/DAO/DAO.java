package com.hch.hooney.tourtogether.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public static String nowVersion;
    public static FirebaseUser fUser;
    public static FirebaseAuth mAuth;
    public static boolean isPush;

    public static MySQLiteHandler handler;

    public static ArrayList<TourApiItem> bookmarkSpotList;
    public static ArrayList<TourApiItem> bookmarkRouteList;

    public static ArrayList<MyCourse> myCoursesList;

    public static void init_static(){
        DAO.user = new USER();
        DAO.isPush = true;
        DAO.init_bookmarkSpot();
        DAO.init_bookmarkRoute();
        DAO.init_myCourse();
    }

    public static void init_handler(Context mContext){
        handler = MySQLiteHandler.open(mContext);
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
                tourApiItem.setPost(cursor.getInt(18));
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
                tourApiItem.setPost(cursor.getInt(18));
                DAO.bookmarkRouteList.add(tourApiItem);
            }while (cursor.moveToNext());
        }
    }

    public static void init_myCourse(){
        DAO.myCoursesList = new ArrayList<MyCourse>();
    }

    public static void setCountryAndLanguage(Context mContext){
        Locale systemLocale = mContext.getResources().getConfiguration().locale;
        DAO.Country = systemLocale.getCountry();
        DAO.Language = systemLocale.getLanguage();

        //  KR : ko       US : en

        Log.d("C and L", DAO.Country + " / " + DAO.Language);
    }

    public static boolean chkAddBookmarking(String contentId){
        for (TourApiItem item: DAO.bookmarkSpotList) {
            if(item.getContentID().equals(contentId)){
                return true;
            }
        }
        return false;
    }

    public static boolean chkAddBookmarking_course(String contentId){
        for (TourApiItem item: DAO.bookmarkRouteList) {
            if(item.getContentID().equals(contentId)){
                return true;
            }
        }
        return false;
    }
}
