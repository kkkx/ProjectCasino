package com.mztown.projectcasino;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/6/27.
 */
public class SQLConnection extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "userset.db";
    private static Context context;

    public static void setContext(Context context){
        SQLConnection.context=context;
    }

    public SQLConnection(){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void close(SQLiteDatabase db){
        db.close();
    }
}
