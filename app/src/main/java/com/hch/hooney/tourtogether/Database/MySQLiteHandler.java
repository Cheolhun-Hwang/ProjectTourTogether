package com.hch.hooney.tourtogether.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hch.hooney.tourtogether.DAO.TourApiItem;

/**
 * Created by hch on 2018-02-03.
 */

public class MySQLiteHandler {
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public MySQLiteHandler(Context mContext) {
        this.helper = new MySQLiteOpenHelper(mContext, "sqlite_project.sqlite", null, 1);
    }

    public static MySQLiteHandler open(Context ctx){
        return new MySQLiteHandler(ctx);
    }

    public void close(){
        helper.close();
    }

    public void insert_spot(TourApiItem item){
        db = helper.getWritableDatabase();
        ContentValues vales = new ContentValues();
        vales.put("contentid", item.getContentID());
        vales.put("contenttypeid", item.getContentTypeID());
        vales.put("addr1", item.getAddr1());
        vales.put("addr2", item.getAddr2());
        vales.put("cat1", item.getCat1());
        vales.put("cat2", item.getCat2());
        vales.put("cat3", item.getCat3());
        vales.put("mapx", item.getMapx());
        vales.put("mapy", item.getMapy());
        vales.put("areacode", item.getAreaCode());
        vales.put("sigungu", item.getSigunguCode());
        vales.put("firstimage", item.getFirstImage());
        vales.put("modifydate", item.getModifyDateTIme());
        vales.put("readcount", item.getReadCount());
        vales.put("title", item.getTitle());
        vales.put("tel", item.getTel());
        vales.put("directions", item.getDirections());
        vales.put("overview", item.getBasic_overView());
        vales.put("ispost", item.isPost());
        db.insert("b_spot", null, vales);
    }

    public void insert_course(TourApiItem item){
        db = helper.getWritableDatabase();
        ContentValues vales = new ContentValues();
        vales.put("contentid", item.getContentID());
        vales.put("contenttypeid", item.getContentTypeID());
        vales.put("addr1", item.getAddr1());
        vales.put("addr2", item.getAddr2());
        vales.put("cat1", item.getCat1());
        vales.put("cat2", item.getCat2());
        vales.put("cat3", item.getCat3());
        vales.put("mapx", item.getMapx());
        vales.put("mapy", item.getMapy());
        vales.put("areacode", item.getAreaCode());
        vales.put("sigungu", item.getSigunguCode());
        vales.put("firstimage", item.getFirstImage());
        vales.put("modifydate", item.getModifyDateTIme());
        vales.put("readcount", item.getReadCount());
        vales.put("title", item.getTitle());
        vales.put("tel", item.getTel());
        vales.put("directions", item.getDirections());
        vales.put("overview", item.getBasic_overView());
        vales.put("ispost", item.isPost());
        db.insert("b_course", null, vales);
    }

    public void updeate_spot(TourApiItem item){
        db = helper.getWritableDatabase();
        ContentValues vales = new ContentValues();
        vales.put("contenttypeid", item.getContentTypeID());
        vales.put("addr1", item.getAddr1());
        vales.put("addr2", item.getAddr2());
        vales.put("cat1", item.getCat1());
        vales.put("cat2", item.getCat2());
        vales.put("cat3", item.getCat3());
        vales.put("mapx", item.getMapx());
        vales.put("mapy", item.getMapy());
        vales.put("areacode", item.getAreaCode());
        vales.put("sigungu", item.getSigunguCode());
        vales.put("firstimage", item.getFirstImage());
        vales.put("modifydate", item.getModifyDateTIme());
        vales.put("readcount", item.getReadCount());
        vales.put("title", item.getTitle());
        vales.put("tel", item.getTel());
        vales.put("directions", item.getDirections());
        vales.put("overview", item.getBasic_overView());
        vales.put("ispost", item.isPost());
        db.update("b_spot", vales, "contentid = ?", new String[]{item.getContentID()});
    }

    public void updeate_course(TourApiItem item){
        db = helper.getWritableDatabase();
        ContentValues vales = new ContentValues();
        vales.put("contenttypeid", item.getContentTypeID());
        vales.put("addr1", item.getAddr1());
        vales.put("addr2", item.getAddr2());
        vales.put("cat1", item.getCat1());
        vales.put("cat2", item.getCat2());
        vales.put("cat3", item.getCat3());
        vales.put("mapx", item.getMapx());
        vales.put("mapy", item.getMapy());
        vales.put("areacode", item.getAreaCode());
        vales.put("sigungu", item.getSigunguCode());
        vales.put("firstimage", item.getFirstImage());
        vales.put("modifydate", item.getModifyDateTIme());
        vales.put("readcount", item.getReadCount());
        vales.put("title", item.getTitle());
        vales.put("tel", item.getTel());
        vales.put("directions", item.getDirections());
        vales.put("overview", item.getBasic_overView());
        vales.put("ispost", item.isPost());
        db.update("b_course", vales, "contentid = ?", new String[]{item.getContentID()});
    }

    public void delete_spot(String contentid){
        ContentValues vales = new ContentValues();
        db.delete("b_spot", "contentid = ?", new String[]{contentid});
    }

    public void delete_course(String contentid){
        ContentValues vales = new ContentValues();
        db.delete("b_course", "contentid = ?", new String[]{contentid});
    }

    public Cursor selectAll_spot(){
        db = helper.getWritableDatabase();
        Cursor c = db.query("b_spot", null, null, null, null, null, null);
        return c;
    }

    public Cursor selectAll_course(){
        db = helper.getWritableDatabase();
        Cursor c = db.query("b_course", null, null, null, null, null, null);
        return c;
    }

    public Cursor selectAll_setting(){
        db = helper.getWritableDatabase();
        Cursor c = db.query("setting", null, null, null, null, null, null);
        return c;
    }

    public Cursor selectByID_spot(String contentid){
        db = helper.getWritableDatabase();
        Cursor c = db.query("b_spot", null, "contentid = ?", new String[]{contentid}, null, null, null);
        return c;
    }

    public Cursor selectByID_course(String contentid){
        db = helper.getWritableDatabase();
        Cursor c = db.query("b_course", null, "contentid = ?", new String[]{contentid}, null, null, null);
        return c;
    }


}
