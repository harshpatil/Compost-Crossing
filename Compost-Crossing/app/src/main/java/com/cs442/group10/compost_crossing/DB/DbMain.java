package com.cs442.group10.compost_crossing.DB;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public static final String COMPOST_REGISTER_COLUMN_username = "username";
    public static final String COMPOST_REGISTER_COLUMN_passcode = "passcode";
    public static final String RESIDENT_REGISTER_COLUMN_NAME = "name";

    public DbMain(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table composter_register " +
                        "(id integer primary key autoincrement, tstamp text, name text, phone text,city text,state text,zipcode text,address text,username text, passcode text)"
        );

        db.execSQL(
                "create table resident_register " +
                        "(id integer primary key autoincrement, tstamp text, name text, phone text,city text,state text,zipcode text,address text,username text, passcode text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS composter_register");
        db.execSQL("DROP TABLE IF EXISTS resident_register");
        onCreate(db);
    }

    /**
     * Method to write composter user profile data to local database.
     * @param tstamp
     * @param name
     * @param phone
     * @param city
     * @param state
     * @param zipcode
     * @param address
     * @param username
     * @param passcode
     * @return
     */
    public boolean insertComposter (String tstamp, String name, String phone, String city,String state, String zipcode,String address,String username, String passcode)
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
        contentValues.put("username", username);
        contentValues.put("passcode", passcode);
        db.insert("composter_register", null, contentValues);
        db.close();
        return true;
    }

    /**
     * Method to write resident user profile data to local database.
     * @param tstamp
     * @param name
     * @param phone
     * @param city
     * @param state
     * @param zipcode
     * @param address
     * @param username
     * @param passcode
     * @return
     */
    public boolean insertResident (String tstamp, String name, String phone, String city, String state, String zipcode, String address,String username, String passcode)
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
        contentValues.put("username", username);
        contentValues.put("passcode", passcode);
        db.insert("resident_register", null, contentValues);
        db.close();
        return true;
    }

    /**
     * Method that gives the count of number of entries in Composter Registration table.
     * @return int
     */
    public int numberOfDataComposter(){
        SQLiteDatabase db = this.getReadableDatabase();
        int ordCount = (int) DatabaseUtils.queryNumEntries(db, COMPOST_REGISTER_TABLE_NAME);
        return ordCount;
    }

    /**
     * Method that gives the count of number of entries in Resident Registration table.
     * @return int
     */
    public int numberOfDataResident(){
        SQLiteDatabase db = this.getReadableDatabase();
        int ordCount = (int) DatabaseUtils.queryNumEntries(db, RESIDENT_REGISTER_TABLE_NAME);
        return ordCount;
    }

    /**
     * Method to get composter password from local database.
     * @param username
     * @return String
     */
    public String getComposterUserNameAndPassword(String username){

        String query ="select passcode from composter_register";
        String passcode = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( query, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            passcode = resultSet.getString(resultSet.getColumnIndex("passcode"));
            break;
        }
        return passcode;
    }

    /**
     * Method to get composter username from local database
     * @return String
     */
    public String getComposterUserName(){

        String query ="select username from composter_register";
        String username = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( query, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            username = resultSet.getString(resultSet.getColumnIndex("username"));
            break;
        }
        return username;
    }

    /**
     * Method to get resident username from local database.
     * @return String
     */
    public String getResidentUserName(){

        String query ="select username from resident_register";
        String username = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( query, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            username = resultSet.getString(resultSet.getColumnIndex("username"));
            break;
        }
        return username;
    }

    /**
     * Method to get resident password from local database.
     * @param username
     * @return String
     */
    public String getResidentUserNameAndPassword(String username){

        String query ="select passcode from resident_register";
        String passcode = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( query, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){

            passcode = resultSet.getString(resultSet.getColumnIndex("passcode"));
            break;
        }
        return passcode;
    }

    /**
     * Method to get Resident Phone number.
     * @return String
     */
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

    /**
     * Method to get Resident Zip code.
     * @return
     */
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

    /**
     * Method to get composter zip code.
     * @return
     */
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

    /**
     * Method to set Composter user details.
     */
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

    /**
     * Method to get Resident Phone number.
     * @return
     */
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
            Constants.residentName = res.getString(res.getColumnIndex(RESIDENT_REGISTER_COLUMN_NAME));
            break;
        }
        return phone;
    }

    public Map<String,String> getComposterDetails(){

        String query ="select "+COMPOST_REGISTER_COLUMN_username+","+COMPOST_REGISTER_COLUMN_passcode+" from composter_register";
        Map<String,String> userCredentialsMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( query, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){
            userCredentialsMap.put("userName", resultSet.getString(0));
            userCredentialsMap.put("password", resultSet.getString(1));
            break;
        }
        return userCredentialsMap;
    }

    public Map<String,String> getResidentDetails(){

        String query ="select username,passcode from resident_register";
        Map<String,String> userCredentialsMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( query, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){
            userCredentialsMap.put("userName", resultSet.getString(0));
            userCredentialsMap.put("password", resultSet.getString(1));
            break;
        }
        return userCredentialsMap;
    }
}
