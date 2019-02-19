package com.chx.yifei.expandablelistviewapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chx.yifei.expandablelistviewapplication.MainActivity;
import com.chx.yifei.expandablelistviewapplication.bean.Chapter;
import com.chx.yifei.expandablelistviewapplication.bean.ChapterItem;

public class ChapterDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="db_chapter.db";
    private static final int VERSION =1;

    private static ChapterDbHelper sInstance;

    public  static synchronized ChapterDbHelper getsInstance(Context context){
        if(sInstance==null){
            sInstance = new ChapterDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public ChapterDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(MainActivity.Tag,"dbhelper");
     db.execSQL(
             "CREATE TABLE IF NOT EXISTS " + Chapter.TABLE_NAME + " ("
                     + Chapter.COL_ID + " INTEGER PRIMARY KEY, "
                     + Chapter.COL_NAMe + " VARCHAR"
                     + ")"
     );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ChapterItem.TABLE_NAME + " ("
                        + ChapterItem.COL_ID + " INTEGER PRIMARY KEY, "
                        + ChapterItem.COL_NAMe + " VARCHAR, "
                        + ChapterItem.COL_PID + " INTEGER"
                        + ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
