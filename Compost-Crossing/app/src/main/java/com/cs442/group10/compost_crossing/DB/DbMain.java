package com.cs442.group10.compost_crossing.DB;


import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.cs442.group10.compost_crossing.constants.Constants;

public class DbMain extends SQLiteOpenHelper {

    public static final String DB_NAME = "CompostCrossing.db";
    public static final String COMPOST_REGISTER_TABLE_NAME = "composter_register";
    public static final String RESIDENT_REGISTER_TABLE_NAME = "resident_register";
    public static final String COMPOST_REGISTER_COLUMN_ID = "id";
    public static final String COMPOST_REGISTER_COLUMN_TS = "tstamp";
    public static final String COMPOST_REGISTER_COLUMN_NAME = "name";
    public static final String COMPOST_REGISTER_COLUMN_PHONE = "phone";
    public static final String COMPOST_REGISTER_COLUMN_CITY = "city";
    public static final String COMPOST_REGISTER_COLUMN_STATE = "state";
    public static final String COMPOST_REGISTER_COLUMN_ZIPCODE = "zipcode";
    public static final String COMPOST_REGISTER_COLUMN_ADDRESS = "address";

    public DbMain(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table composter_register " +
                        "(id integer primary key autoincrement, tstamp text, name text, phone text,city text,state text,zipcode text,address text)"
        );

        db.execSQL(
                "create table resident_register " +
                        "(id integer primary key autoincrement, tstamp text, name text, phone text,city text,state text,zipcode text,address text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS composter_register");
        db.execSQL("DROP TABLE IF EXISTS resident_register");
        onCreate(db);
    }

    public boolean insertComposter (String tstamp, String name, String phone, String city,String state, String zipcode,String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tstamp", tstamp);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("city", city);
        contentValues.put("state", state);
        contentValues.put("zipcode", zipcode);
        contentValues.put("address", address);
        db.insert("composter_register", null, contentValues);
        db.close();
        return true;
    }

    public boolean insertResident (String tstamp, String name, String phone, String city, String state, String zipcode, String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tstamp", tstamp);
        contentValues.put("name", name);
        contentValues.put("zipcode", zipcode);
        contentValues.put("phone", phone);
        contentValues.put("city", city);
        contentValues.put("state", state);
        contentValues.put("address", address);
        db.insert("resident_register", null, contentValues);
        db.close();
        return true;
    }

    public int numberOfDataComposter(){
        SQLiteDatabase db = this.getReadableDatabase();
        int ordCount = (int) DatabaseUtils.queryNumEntries(db, COMPOST_REGISTER_TABLE_NAME);
        return ordCount;
    }

    public int numberOfDataResident(){
        SQLiteDatabase db = this.getReadableDatabase();
        int ordCount = (int) DatabaseUtils.queryNumEntries(db, RESIDENT_REGISTER_TABLE_NAME);
        return ordCount;
    }

    public Integer deleteAllTables()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("resident_register",null,null);
//        db.delete("composter_register",null,null);
        return 0;
    }

    public ArrayList<Spanned> getAllOrders()
    {
        ArrayList<Spanned> array_list = new ArrayList<Spanned>();
        String retString = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from composter_register", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            String id  = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_ID));
            String time = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_NAME));
            String items = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_PHONE));
            String cost = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_STATE));

            String htmlString = "<br/><font color=\"#4153B6\"><b><i>Transaction Id: </font></i></b>" + id
                    + "<br/><font color=\"#4153B6\"><b><i>Dish(es): </font></i></b>" + items
                    + "<br/><font color=\"#4153B6\"><b><i>Total: </font></i></b>" + "$" + cost
                    + "<br/><font color=\"#4153B6\"><b><i>Time Log: </font></i></b>" + time + "<br/>";

            array_list.add(Html.fromHtml(htmlString));
            res.moveToNext();
        }
        return array_list;
    }

    public String getResidentPhoneNumber(){

        String phoneNumber = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( "select phone from resident_register", null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            phoneNumber = resultSet.getString(resultSet.getColumnIndex("phone"));
            break;
        }
        return phoneNumber;
    }

    public String getResidentZipCode(){

        String zipCode = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( "select zipcode from resident_register", null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            zipCode = resultSet.getString(resultSet.getColumnIndex("zipcode"));
            Log.i("zipCodeLocal", zipCode);
            break;
        }
        return zipCode;
    }

    public String getComposterZipCode(){

        String zipCode = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( "select zipcode from composter_register", null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            zipCode = resultSet.getString(resultSet.getColumnIndex("zipcode"));
            Log.i("zipCodeLocal", zipCode);
            break;
        }
        return zipCode;
    }

    public void setComposterUserDetails()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from composter_register", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            Constants.composterId = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_PHONE));
            Constants.composterName = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_NAME));
            Constants.composterZip = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_ZIPCODE));
            break;
        }
    }

    public String getResidentID()
    {
        ArrayList<Spanned> array_list = new ArrayList<Spanned>();
        String retString = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from resident_register", null );
        res.moveToFirst();
        String phone ="";
        while(res.isAfterLast() == false){

            phone  = res.getString(res.getColumnIndex(COMPOST_REGISTER_COLUMN_PHONE));
            break;
        }
        return phone;
    }
}
