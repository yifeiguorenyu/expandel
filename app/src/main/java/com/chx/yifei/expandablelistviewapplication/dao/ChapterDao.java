package com.chx.yifei.expandablelistviewapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chx.yifei.expandablelistviewapplication.bean.Chapter;
import com.chx.yifei.expandablelistviewapplication.bean.ChapterItem;

import java.util.ArrayList;
import java.util.List;

public class ChapterDao {
    public List<Chapter> loadFromDb(Context context){
        ChapterDbHelper dbHelper = ChapterDbHelper.getsInstance(context);
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        List<Chapter> chapterList = new ArrayList<Chapter>();
        Cursor cursor = db.rawQuery("select * from " + Chapter.TABLE_NAME, null);
        Chapter chapter = null;
        while (cursor.moveToNext()){
            chapter = new Chapter();
            int  id =cursor.getInt(cursor.getColumnIndex(Chapter.COL_ID));
            String name =cursor.getString(cursor.getColumnIndex(Chapter.COL_NAMe));
            chapter.setId(id);
            chapter.setName(name);
            chapterList.add(chapter);
        }
        cursor.close();
        ChapterItem chapterItem  = null;
        for (Chapter parent:chapterList) {
            int pid =parent.getId();
            Cursor cursorChild = db.rawQuery("select * from " + ChapterItem.TABLE_NAME + " where pid=?",new String[]{pid+""});
            while (cursorChild.moveToNext()){
                chapterItem = new ChapterItem();
                int id =cursorChild.getInt(cursorChild.getColumnIndex(ChapterItem.COL_ID));
                String name =cursorChild.getString(cursorChild.getColumnIndex(ChapterItem.COL_NAMe));
                chapterItem.setId(id);
                chapterItem.setName(name);
                parent.addChild(chapterItem);

            }
            cursorChild.close();
        }

        return chapterList;
    }



    public void insertToDb(Context context,List<Chapter> chapterList){
        try{
            if(context==null){
                throw new  IllegalArgumentException("context can not be null");
            }
            if(chapterList==null||chapterList.isEmpty()){
                return;
            }

            ChapterDbHelper dbHelper = ChapterDbHelper.getsInstance(context);
            SQLiteDatabase db  = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = null;
            for (Chapter chapter:chapterList){
                contentValues = new ContentValues();
                contentValues.put(Chapter.COL_ID,chapter.getId());
                contentValues.put(Chapter.COL_NAMe,chapter.getName());
                db.insertWithOnConflict(Chapter.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);

                List<ChapterItem> chapterItems = chapter.getChildren();
                if(chapterItems!=null){
                    for (ChapterItem chapteritem:chapterItems) {
                        contentValues =new ContentValues();
                        contentValues.put(ChapterItem.COL_ID,chapteritem.getId());
                        contentValues.put(ChapterItem.COL_NAMe,chapteritem.getName());
                        contentValues.put(ChapterItem.COL_PID,chapter.getId());
                        db.insertWithOnConflict(ChapterItem.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
                    }
                }

            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
