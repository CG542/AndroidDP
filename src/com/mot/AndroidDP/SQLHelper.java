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
    private final static int DBVer = 1;
    public SQLHelper(Context context){
        super(context,DBNAme,null,DBVer);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE status (id INTERGE primary key autocreame, dpname text, status text, logtime text);";
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

    public void insert(String dpname, String status, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dpname",dpname);
        cv.put("status",status);
        cv.put("logtime",time);
        db.insert("status",null,cv);
    }

    public List<StatusData> query(int num){
        String sql = "select dpname, status, logtime FROM status order by logtime desc";
        Cursor result = this.getReadableDatabase().rawQuery(sql, new String[]{});
        List<StatusData> l = new ArrayList<>();
        while(result.moveToNext()){
            StatusData data = new StatusData();
            data.DPNAme=result.getString(result.getColumnIndex("dpname"));
            data.Status=result.getString(result.getColumnIndex("status"));
            data.Time=result.getString(result.getColumnIndex("logtime"));
            l.add(data);
        }

        return l;
    }
}
