package com.example.isepdevappmobileadmin.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.AdminRole;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.ModuleManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    // We instantiate the Database name and version that will be stored locally
    private static final String DATABASE_NAME = "IsepDevAppMobileArthurLorphelin7.db";
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
                "lastName text not null," +
                "adminRoleId int not null)";
        db.execSQL(creationAdminTableSql);

        // We create the AdminRole Table withe an id and a name
        String creationAdminRoleTableSql = "create table AdminRole(" +
                "id integer primary key autoincrement," +
                "name text not null)";
        db.execSQL(creationAdminRoleTableSql);

        // We insert in the Database the Admin Roles
        String insertTutorRoleSql = "INSERT INTO AdminRole (name) VALUES ('Tutor')";
        String insertComponentManagerRoleSql = "INSERT INTO AdminRole (name) VALUES ('Component Manager')";
        String insertModuleManagerRoleInSql = "INSERT INTO AdminRole (name) VALUES ('Module Manager')";
        db.execSQL(insertTutorRoleSql);
        db.execSQL(insertComponentManagerRoleSql);
        db.execSQL(insertModuleManagerRoleInSql);

        // We create the Tutor Admin Table with an id, an adminId, a groupId and a componentId
        String creationTutorTable = "create table Tutor (" +
                "id integer primary key autoincrement," +
                "adminId integer not null," +
                "groupId integer," +
                "componentId integer)";
        db.execSQL(creationTutorTable);

        // We create the Component Manager Table with an id and an adminId
        String creationComponentManagerTable = "create table ComponentManager (" +
                "id integer primary key autoincrement," +
                "adminId integer not null)";
        db.execSQL(creationComponentManagerTable);

        // We create the Module Manager Table with an id and an adminId
        String creationModuleManagerTable = "create table ModuleManager (" +
                "id integer primary key autoincrement," +
                "adminId integer not null)";
        db.execSQL(creationModuleManagerTable);

        // We create the Groupe Table with an id, a name, a schoolYearId and a clientId
        String creationGroupeTable = "create table Groupe (" +
                "id integer primary key autoincrement," +
                "name text not null," +
                "schoolYearId int not null," +
                "clientId int)";
        db.execSQL(creationGroupeTable);

        // We create the SchoolYear Table with an id and a name
        String creationSchoolYearTable = "create table SchoolYear (" +
                "id integer primary key autoincrement," +
                "name text not null)";
        db.execSQL(creationSchoolYearTable);

        // We insert the schoolYear in the Database and some groups
        String insertSchoolYearInDB = "INSERT INTO SchoolYear (name) VALUES ('2023-2024')";
        db.execSQL(insertSchoolYearInDB);
        for (int i = 1; i < 11; i++) {
            String insertGroupInDB = "INSERT INTO Groupe (name, schoolYearId) " +
                    "VALUES ('Group " + i + "', 1)";
            db.execSQL(insertGroupInDB);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertNewAdmin(String email, String password, String firstName, String lastName, int adminRoleId) {
        String insertNewItemSql = "INSERT INTO Admin " +
                "(email, password, firstName, lastName, adminRoleId) " +
                "VALUES ('" + email + "', '" + password + "', '" + firstName + "', '" + lastName + "', "+ adminRoleId + ")";
        this.getWritableDatabase().execSQL(insertNewItemSql);
    }

    // Function that returns all Admin in Database
    public ArrayList<Admin> getAllAdmins() {
        // We instantiate the array variable in which all Admins will be stored
        ArrayList<Admin> admins = new ArrayList<>();
        String sql = "select * from Admin";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);

        // We run the SQL String and store each admin into the array list
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("firstName"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("lastName"));
                @SuppressLint("Range") int adminRoleId = cursor.getInt(cursor.getColumnIndex("adminRoleId"));

                Admin admin = new Admin();
                admin.setId(id);
                admin.setEmail(email);
                admin.setPassword(password);
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                admin.setAdminRoleId(adminRoleId);

                admins.add(admin);
                cursor.moveToNext();
            }
        }
        // We return the array list of admins
        return admins;
    }

    public ArrayList<AdminRole> getAllAdminRoles() {
        ArrayList<AdminRole> adminRoles = new ArrayList<>();
        String sql = "SELECT * FROM AdminRole";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);

        // We run the SQL String and store each admin into the array list
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                AdminRole adminRole = new AdminRole();
                adminRole.setId(id);
                adminRole.setName(name);

                adminRoles.add(adminRole);
                cursor.moveToNext();
            }
        }
        return adminRoles;
    }

    public ArrayList<Tutor> getAllTutors() {
        ArrayList<Tutor> tutors = new ArrayList<>();
        String sql = "select * from Tutor";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int adminId = cursor.getInt(cursor.getColumnIndex("adminId"));
                @SuppressLint("Range") int groupId = cursor.getInt(cursor.getColumnIndex("groupId"));
                @SuppressLint("Range") int componentId = cursor.getInt(cursor.getColumnIndex("componentId"));

                Tutor tutor = new Tutor();
                tutor.setId(id);
                tutor.setAdminId(adminId);
                tutor.setGroupId(groupId);
                tutor.setComponentId(componentId);

                tutors.add(tutor);
                cursor.moveToNext();
            }
        }
        return tutors;
    }

    public ArrayList<ComponentManager> getAllComponentManagers() {
        ArrayList<ComponentManager> componentManagers = new ArrayList<>();
        String sql = "select* from ComponentManager";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int adminId = cursor.getInt(cursor.getColumnIndex("adminId"));

                ComponentManager componentManager = new ComponentManager();
                componentManager.setId(id);
                componentManager.setAdminId(adminId);

                componentManagers.add(componentManager);
                cursor.moveToNext();
            }
        }
        return componentManagers;
    }

    public ArrayList<ModuleManager> getAllModuleManagers() {
        ArrayList<ModuleManager> moduleManagers = new ArrayList<>();
        String sql = "select * from ModuleManager";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int adminId = cursor.getInt(cursor.getColumnIndex("adminId"));

                ModuleManager moduleManager = new ModuleManager();
                moduleManager.setId(id);
                moduleManager.setAdminId(adminId);

                moduleManagers.add(moduleManager);
                cursor.moveToNext();
            }
        }
        return moduleManagers;
    }
    public void insertTutor(int adminId) {
        String insertNewTutorSql = "INSERT INTO Tutor " +
                "(adminId) VALUES (" + adminId + ")";
        this.getWritableDatabase().execSQL(insertNewTutorSql);
    }

    public void insertModuleManager(int adminId) {
        String insertNewModuleManagerSql = "INSERT INTO ModuleManager " +
                "(adminId) VALUES (" + adminId + ")";
        this.getWritableDatabase().execSQL(insertNewModuleManagerSql);
    }

    public void insertComponentManager(int adminId) {
        String insertNewComponentManagerSql = "INSERT INTO ComponentManager " +
                "(adminId) VALUES (" + adminId + ")";
        this.getWritableDatabase().execSQL(insertNewComponentManagerSql);
    }

    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        String sql = "select * from Groupe";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while ((!cursor.isAfterLast())) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int schoolYearId = cursor.getInt(cursor.getColumnIndex("schoolYearId"));
                @SuppressLint("Range") int clientId = cursor.getInt(cursor.getColumnIndex("clientId"));

                Group group = new Group();
                group.setId(id);
                group.setName(name);
                group.setSchoolYearId(schoolYearId);
                group.setClientId(clientId);

                groups.add(group);
                cursor.moveToNext();
            }
        }
        return groups;
    }

}
