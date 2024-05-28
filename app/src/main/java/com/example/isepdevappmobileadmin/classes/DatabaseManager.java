package com.example.isepdevappmobileadmin.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;

import java.util.ArrayList;

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
                "VALUES ('" + email + "', '" + password + "', '" + firstName + "', '" + lastName + "')";
        this.getWritableDatabase().execSQL(insertNewItemSql);
    }

    // Function that returns all Admin in Database
    public ArrayList<Admin> getAllAdmins() {
        // We instantiate the array variable in which all Admins will be stored
        ArrayList<Admin> admins = new ArrayList<>();
        String sql = "select * from Admin";
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);

        // We run the SQL String and store each admin into the array list
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String firstName = cursor.getString(cursor.getColumnIndex("firstName"));
                String lastName = cursor.getString(cursor.getColumnIndex("lastName"));

                Admin admin = new Admin();
                admin.setId(id);
                admin.setEmail(email);
                admin.setPassword(password);
                admin.setFirstName(firstName);
                admin.setLastName(lastName);

                admins.add(admin);
                cursor.moveToNext();
            }
        }
        // We return the array list of admins
        return admins;
    }

}
