package com.example.isepdevappmobileadmin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    // We instantiate the Database name and version that will be stored locally
    private static final String DATABASE_NAME = "IsepDevAppMobileArthurLorphelin.db";
    private static final int DATABASE_VERSION = 1;

    // We create the constructor function of the class
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This function is called when the app is launched for the first time and it creates the local Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        // We create the Admin Table in the Database with an id, an email, a password, a firstName and a lastName
        String creationAdminTableSql = "create table Admin (" +
                "id integer primary key autoincrement," +
                "email text not null," +
                "password text not null," +
                "firstName text not null," +
                "lastName text not null)";
        db.execSQL(creationAdminTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertNewAdmin(String email, String password, String firstName, String lastName) {
        String insertNewItemSql = "INSERT INTO Admin " +
                "(email, password, firstName, lastName) " +
                "VALUES ('" + email + "', '" + password + "', '" + firstName + "', '" + lastName + "', ')";
        this.getWritableDatabase().execSQL(insertNewItemSql);
    }

    public boolean isAdmin(String email) {
        String sql = "select id from Admin WHERE email = '" + email + "'";
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        return !cursor.moveToFirst();
    }

    public String getPasswordFromAdmin(String email) {
        String sql = "select password from Admin WHERE email = '" + email + "'";
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        return cursor.getString(cursor.getColumnIndex("password"));
    }
}
