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
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    // We instantiate the Database name and version that will be stored locally
    private static final String DATABASE_NAME = "IsepDevAppMobileArthurLorphelin12.db";
    private static final int DATABASE_VERSION = 1;

    // We instantiate the number of Groups per SchoolYear and the number of Teams per Group
    private static final int NUMBER_OF_GROUPS_PER_SCHOOL_YEAR = 10;
    private static final int NUMBER_OF_TEAMS_PER_GROUP = 6;

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

        // We insert the schoolYear in the Database
        String insertSchoolYearInDB = "INSERT INTO SchoolYear (name) VALUES ('2023-2024')";
        db.execSQL(insertSchoolYearInDB);

        // We create the Team Table in the Database with an id, a name and a groupId
        String createTeamTable = "create table Team (" +
                "id integer primary key autoincrement," +
                "name text not null," +
                "groupId int not null)";
        db.execSQL(createTeamTable);

        // We create the Student Table in the Database with an id, an email, a password, a  firstName, a lastName, a studentNumber, a groupId and a teamId
        String createStudentTable = "create table Student (" +
                "id integer primary key autoincrement," +
                "email text not null," +
                "password text not null," +
                "firstName text not null," +
                "lastName text not null," +
                "studentNumber int not null," +
                "groupId int," +
                "teamId int)";
        db.execSQL(createStudentTable);

        // We insert the Groups in the Database
        for (int groupIndex = 1; groupIndex < NUMBER_OF_GROUPS_PER_SCHOOL_YEAR + 1; groupIndex++) {
            String insertGroupInDB = "INSERT INTO Groupe (name, schoolYearId) " +
                    "VALUES ('Group " + groupIndex + "', 1)";
            db.execSQL(insertGroupInDB);
            // We insert the Teams in the Database
            for (int teamIndex = 1; teamIndex < NUMBER_OF_TEAMS_PER_GROUP + 1; teamIndex++) {
                String insertTeamInDB = "INSERT INTO Team (name, groupId) " +
                        "VALUES ('Team " + groupIndex + "-" + String.valueOf(Character.toChars(teamIndex + 64)) + "', " + groupIndex + ")";
                db.execSQL(insertTeamInDB);

                // We insert the Students in the Database (one Student per Team)
                int teamId = ((groupIndex - 1) * NUMBER_OF_TEAMS_PER_GROUP) + teamIndex;
                String studentEmail = "student" + teamId + "@isep.fr";
                String studentPassword = "student" + teamId;
                String firstName = "Student";
                String lastName = String.valueOf(teamId);
                int studentNumber = 6000 + teamId;
                String insertStudentInDB = "INSERT INTO Student (email, password, firstName, lastName, studentNumber, groupId, teamId) " +
                        "VALUES ('" + studentEmail + "', '" + studentPassword + "', '" + firstName + "', '" + lastName + "', " + studentNumber + ", " + groupIndex + ", " + teamId + ")";
                db.execSQL(insertStudentInDB);
            }
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
            while (!cursor.isAfterLast()) {
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

    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        String sql = "select * from Team";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int groupId = cursor.getInt(cursor.getColumnIndex("groupId"));

                Team team = new Team();
                team.setId(id);
                team.setName(name);
                team.setGroupId(groupId);

                teams.add(team);
                cursor.moveToNext();
            }
        }
        return teams;
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "select * from Student";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("firstName"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("lastName"));
                @SuppressLint("Range") int studentNumber = cursor.getInt(cursor.getColumnIndex("studentNumber"));
                @SuppressLint("Range") int groupId = cursor.getInt(cursor.getColumnIndex("groupId"));
                @SuppressLint("Range") int teamId = cursor.getInt(cursor.getColumnIndex("teamId"));

                Student student = new Student();
                student.setId(id);
                student.setEmail(email);
                student.setPassword(password);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setStudentNumber(studentNumber);
                student.setGroupId(groupId);
                student.setTeamId(teamId);

                students.add(student);
                cursor.moveToNext();
            }
        }
        return students;
    }
}
