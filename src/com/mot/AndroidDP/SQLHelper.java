package com.mot.AndroidDP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 6/15/16.
 */
public class SQLHelper extends SQLiteOpenHelper
{
    private final static String DBNAme="DP.db";
    private final static int DBVer = 3;
    public SQLHelper(Context context){
        super(context,DBNAme,null,DBVer);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE status (id INTEGER primary key autoincrement, dpname TEXT, status TEXT, logtime TEXT, type TEXT);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "Drop TABLE IF EXISTS status";
        db.execSQL(sql);
        onCreate(db);

    }

    public Cursor select(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("status",null,null,null,null,null,null);
        return cursor;
    }

    public void insert(String dpname, String status, String time,String type){
        if(!isExist(dpname,status,time)) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("dpname", dpname);
            cv.put("status", status);
            cv.put("logtime", time);
            cv.put("type", type);
            db.insert("status", null, cv);
            db.close();
        }
    }

    private boolean isExist(String dpname,String status,String time){
        boolean result =false;
        String sql = String.format( "select count(*) FROM status where dpname='%s' and status='%s' and logtime='%s'",dpname,status,time);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  c= db.rawQuery(sql,new String[]{});
        if(c.moveToNext()){
            result=c.getInt(0)>0;
        }
        db.close();
        return result;
    }

    public List<StatusData> query(){
        String sql = "select * FROM status order by logtime desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(sql, new String[]{});
        List<StatusData> l = new ArrayList<>();
        while(result.moveToNext()){
            StatusData data = new StatusData();
            data.DPName=result.getString(result.getColumnIndex("dpname"));
            data.Status=result.getString(result.getColumnIndex("status"));
            data.Time=result.getString(result.getColumnIndex("logtime"));
            data.Type=result.getString(result.getColumnIndex("type"));
            l.add(data);
        }
        db.close();
        return l;
    }

    public void clearLocalDB(){
        String sql = "Delete from status";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
}
