package com.hch.hooney.tourtogether.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hch on 2018-02-03.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private final String TAG = "MySQLiteOpenHelper";

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate..");
        String sql1 = "CREATE TABLE IF NOT EXISTS b_spot("+
                "contentid text NOT NULL PRIMARY KEY, "+
                "contenttypeid text NOT NULL, "+
                "addr1 text NOT NULL, "+
                "addr2 text NOT NULL, "+
                "cat1 text NOT NULL, "+
                "cat2 text NOT NULL, "+
                "cat3 text NOT NULL, "+
                "mapx double NOT NULL, "+
                "mapy double NOT NULL, "+
                "areacode text NOT NULL, "+
                "sigungu text NOT NULL, "+
                "firstimage text NOT NULL, "+
                "modifydate text NOT NULL, "+
                "readcount text NOT NULL, "+
                "title text NOT NULL, "+
                "tel text NOT NULL, "+
                "directions text NOT NULL, "+
                "overview text NOT NULL, "+
                "ispost int Not NULL"+
                ")";

        String sql2 = "CREATE TABLE IF NOT EXISTS b_course("+
                "contentid text NOT NULL PRIMARY KEY, "+
                "contenttypeid text NOT NULL, "+
                "addr1 text NOT NULL, "+
                "addr2 text NOT NULL, "+
                "cat1 text NOT NULL, "+
                "cat2 text NOT NULL, "+
                "cat3 text NOT NULL, "+
                "mapx double NOT NULL, "+
                "mapy double NOT NULL, "+
                "areacode text NOT NULL, "+
                "sigungu text NOT NULL, "+
                "firstimage text NOT NULL, "+
                "modifydate text NOT NULL, "+
                "readcount text NOT NULL, "+
                "title text NOT NULL, "+
                "tel text NOT NULL, "+
                "directions text NOT NULL, "+
                "overview text NOT NULL, "+
                "ispost int Not NULL"+
                ")";

        String sql3 = "CREATE TABLE IF NOT EXISTS translate("+
                "t_id text NOT NULL PRIMARY KEY, "+
                "t_from int NOT NULL, "+
                "t_to int NOT NULL, "+
                "t_origin text NOT NULL, "+
                "t_trans text NOT NULL "+
                ")";


        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade.. to Delete");
        String sql1 = "DROP TABLE IF EXISTS b_spot";
        String sql2 = "DROP TABLE IF EXISTS b_course";
        String sql3 = "DROP TABLE IF EXISTS translate";

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);

        onCreate(db);
    }
}
