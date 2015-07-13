package com.mztown.projectcasino;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/7/12.
 */
public class DBOperation {
    private SQLConnection sqlConnection;
    private SQLiteDatabase db;
    private String OnlineUser;
    private static String table="user";
    Context context;
    public DBOperation(Context context){                         //������ݿ����ӣ���õ�ǰ�û���
        sqlConnection=new SQLConnection(context);
        db=sqlConnection.getWritableDatabase();
        try{GetOnlineUser();}catch (Exception e){AutoNewUser();}
        this.context=context;
    }

    public String GetOnlineUser(){                //���ص�ǰ�û���
        Cursor a=db.rawQuery("SELECT uid FROM user WHERE inplay = 1",null);
        a.moveToFirst();
        return a.getString(0);
//        return "";
    }

    public int GetCheckpoint(){                   //��õ�ǰ�ؿ�
        Cursor a=db.rawQuery("SELECT checkpoint FROM user WHERE inplay = 1",null);
        a.moveToFirst();
        return a.getInt(0);
    }

    public void PutCheckpoint(int level){                  //���浱ǰ�ؿ���
        db.execSQL("UPDATE user SET checkpoint = "+level+" WHERE inplay = 1");
    }

    private void AutoNewUser(){                    //��һ����Ϸ�Զ������û����ڲ����ã�.
        String a = String.valueOf((int)(Math.random() * 9999)) ;
        String sqlexe="UPDATE user SET inplay = '0' WHERE inplay = 1";
        db.execSQL(sqlexe);
        sqlexe="INSERT INTO user VALUES ('NewPlayer"+a+"','1','1','1000')";
        db.execSQL(sqlexe);
    }

//    public void UserCheckout(){                     //�л��û�
//    }

    public void NewUser(){                     //�û�ѡ�񴴽��û�
        AutoNewUser();
    }

    public void Reset(){                           //���ùؿ�����
        db.execSQL("UPDATE user SET checkpoint = 1 WHERE inplay = 1");
        Toast.makeText(context,"Game Progress Reset",Toast.LENGTH_SHORT).show();
    }

    public void UserRename(String newname){        //�û�ѡ�����
        String sqlexe="UPDATE user SET uid = '"+newname+"'WHERE inplay = 1";
        db.execSQL(sqlexe);
    }

    public int CashRegister(int cashout){          //������Ǯ��
        Cursor a=db.rawQuery("SELECT checkout FROM user WHERE inplay = 1",null);
        a.moveToFirst();
        cashout+=a.getInt(0);
        db.execSQL("UPDATE user SET cash = "+cashout+" WHERE inplay = 1");
        return cashout;
    }

    public String MasterDBControl(String sqlexec){ //������sql����
        return sqlexec;
    }

    public void Close(){
        db.close();
    }
}

