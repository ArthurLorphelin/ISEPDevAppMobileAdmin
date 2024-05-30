package com.example.isepdevappmobileadmin.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.AdminRole;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.ModuleManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    // We instantiate the Database name and version that will be stored locally
    private static final String DATABASE_NAME = "IsepDevAppMobileArthurLorphelin20.db";
    private static final int DATABASE_VERSION = 1;

    // We instantiate the number of Groups per SchoolYear and the number of Teams per Group
    private static final int NUMBER_OF_GROUPS_PER_SCHOOL_YEAR = 10;
    private static final int NUMBER_OF_TEAMS_PER_GROUP = 6;
    private static final int NUMBER_OF_INITIAL_COMPONENTS = 7;

    // We create the constructor function of the class
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This function is called when the app is launched for the first time and it creates the local Database
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
                                        CREATION OF ALL ESSENTIAL DATA IN THE DB
                                -----------------------------------------------------------
         */

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

        // We create the ComponentTable in the Database with an id, a name and a componentManagerId
        String createComponentTable = "create table Component (" +
                "id integer primary key autoincrement," +
                "name text not null," +
                "componentManagerId int)";
        db.execSQL(createComponentTable);

        // We create the ComponentScore Table in the Database with an id, a score, a componentId and a studentId
        String createComponentScoreTable = "create table ComponentScore (" +
                "id integer primary key autoincrement," +
                "score int," +
                "componentId int not null," +
                "studentId int not null)";
        db.execSQL(createComponentScoreTable);

        // We create the Skill Table in the Database with an id, a title, a description, a linkToViewDetails and a componentId
        String createSkillTable = "create table Skill (" +
                "id integer primary key autoincrement," +
                "title text not null," +
                "description text not null," +
                "linkToViewDetails text," +
                "componentId int not null)";
        db.execSQL(createSkillTable);


        /*
                                        INSERTION OF ALL ESSENTIAL DATA IN THE DB
                                --------------------------------------------------------------
         */

        // We insert in the Database the Admin Roles
        String insertTutorRoleSql = "INSERT INTO AdminRole (name) VALUES ('Tutor')";
        String insertComponentManagerRoleSql = "INSERT INTO AdminRole (name) VALUES ('Component Manager')";
        String insertModuleManagerRoleInSql = "INSERT INTO AdminRole (name) VALUES ('Module Manager')";
        db.execSQL(insertTutorRoleSql);
        db.execSQL(insertComponentManagerRoleSql);
        db.execSQL(insertModuleManagerRoleInSql);

        // We insert one ComponentManager in the Database
        String insertComponentManagerAdminSql = "INSERT INTO Admin (email, password, firstName, lastName, adminRoleId) " +
                "VALUES ('component@manager.com', 'component', 'Component', 'Manager', 2)";
        db.execSQL(insertComponentManagerAdminSql);
        String insertComponentManagerSql = "INSERT INTO ComponentManager (adminId) VALUES (1)";
        db.execSQL(insertComponentManagerSql);

        // We insert one ModuleManager in the Database
        String insertModuleManagerAdminSql = "INSERT INTO Admin (email, password, firstName, lastName, adminRoleId) " +
                "VALUES ('module@manager.com', 'module', 'Module', 'Manager', 3)";
        db.execSQL(insertModuleManagerAdminSql);
        String insertModuleManagerSql = "INSERT INTO ModuleManager (adminId) VALUES (2)";
        db.execSQL(insertModuleManagerSql);

        // We insert one Tutor in the Database
        String insertTutorAdminSql = "INSERT INTO Admin (email, password, firstName, lastName, adminRoleId) " +
                "VALUES ('tutor@manager.com', 'tutor', 'Tutor', 'Manager', 1)";
        db.execSQL(insertTutorAdminSql);
        String insertTutorSql = "INSERT INTO Tutor (adminId, groupId, componentId) VALUES (3, 1, 1)";
        db.execSQL(insertTutorSql);


        // We insert the schoolYear in the Database
        String insertSchoolYearInDB = "INSERT INTO SchoolYear (name) VALUES ('2023-2024')";
        db.execSQL(insertSchoolYearInDB);


        // We insert the initial Components in the Database
        String insertComponentGeneralESInDB = "INSERT INTO Component (name, componentManagerId) VALUES ('Général E-S', 1)";
        db.execSQL(insertComponentGeneralESInDB);
        String insertComponentElectroniqueInDB = "INSERT INTO Component (name) VALUES ('Électronique')";
        db.execSQL(insertComponentElectroniqueInDB);
        String insertComponentSignalInDB = "INSERT INTO Component (name) VALUES ('Signal')";
        db.execSQL(insertComponentSignalInDB);
        String insertComponentGeneralITInDB = "INSERT INTO Component (name) VALUES ('Général I-T')";
        db.execSQL(insertComponentGeneralITInDB);
        String insertComponentInformatiqueInDB = "INSERT INTO Component (name) VALUES ('Informatique')";
        db.execSQL(insertComponentInformatiqueInDB);
        String insertComponentTelecommunicationInDB = "INSERT INTO Component (name) VALUES ('Télécommunication')";
        db.execSQL(insertComponentTelecommunicationInDB);
        String insertComponentIntegrationInDB = "INSERT INTO Component (name) VALUES ('Intégration')";
        db.execSQL(insertComponentIntegrationInDB);

        // We insert one Skill per Component in the Database
        String title = "Communiquer à l oral";
        String description = "Concerne le discours mais également le support de présentation" +
                "- Faire preuve d aisance à l oral" +
                "- Présenter un exposé clair structuré et synthétique" +
                "- S avoir analyser et synthétiser ses idées scientifiques en s adaptant à son public" +
                "- Savoir dialoguer avec le jury de façon qualitative";
        int componentId = 1;
        String insertSkill1InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill1InDB);

        title = "Contextualisation et regard critique";
        description = "- Contextualiser les tests en situation réelle" +
                "- Exercer un regard critique sur les niveaux et critères du CDC" +
                "- Faire fonctionner le système en gardant ce contexte en tête";
        componentId = 2;
        String insertSkill2InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill2InDB);

        title = "Calcul d une puissance";
        description = "- Savoir estimer la puissance moyenne d un signal en W et en dBm " +
                "- Savoir estimer la puissance instantannée d un signal stochastique " +
                "- Savoir détecter la présence ou l absence d un signal utile";
        componentId = 3;
        String insertSkill3InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill3InDB);

        title = "Répondre au cahier des charges";
        description = "- Réaliser les fonctionalités principales" +
                "- Réaliser les fonctionnalités de confort" +
                "- Réaliser les fonctionnalités de luxe";
        componentId = 4;
        String insertSkill4InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill4InDB);

        title = "Spécification des besoins";
        description = "- Extraire et comprendre les besoins fonctionnels et non fonctionnels" +
                "- Modéliser les fonctionnalités (UML & scénario & basique & algorithme & processus & langage naturel & etc.)";
        componentId = 5;
        String insertSkill5InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill5InDB);

        title = "Vue systématique";
        description = "- Spécifier l architecture générale d un réseau de capteurs" +
                "- Etude et compréhension d une architecture et des technologies selectionnées" +
                "- Spécification d une architecture évoluée et prise en compte des éléments clivants des technologies sélectionnées";
        componentId = 6;
        String insertSkill6InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill6InDB);

        title = "Aisance à l oral";
        description = "- Aisance à l oral & audibilité & débit et tonalité adaptés au discours" +
                "- Expression non verbale (gestuelle & occupation de l espace & maintien de l intérêt et de l auditoire)" +
                "Il s agit d une évaluation individuelle";
        componentId = 7;
        String insertSkill7InDB = "INSERT INTO Skill (title, description, componentId) VALUES ('"+ title + "', '" + description + "', " + componentId + ")";
        db.execSQL(insertSkill7InDB);

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

                // We insert the ComponentScores in the Database
                for (int componentIndex = 1; componentIndex < NUMBER_OF_INITIAL_COMPONENTS + 1; componentIndex++) {
                    String insertComponentScoreInDB = "INSERT INTO ComponentScore (componentId, studentId) " +
                            "VALUES (" + componentIndex + ", " + teamId + ")";
                    db.execSQL(insertComponentScoreInDB);
                }
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

    public ArrayList<ComponentScore> getAllComponentScores() {
        ArrayList<ComponentScore> componentScores = new ArrayList<>();
        String sql = "select * from ComponentScore";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex("score"));
                @SuppressLint("Range") int componentId = cursor.getInt(cursor.getColumnIndex("componentId"));
                @SuppressLint("Range") int studentId = cursor.getInt(cursor.getColumnIndex("studentId"));

                ComponentScore componentScore = new ComponentScore();
                componentScore.setId(id);
                componentScore.setScore(score);
                componentScore.setComponentId(componentId);
                componentScore.setStudentId(studentId);

                componentScores.add(componentScore);
                cursor.moveToNext();
            }
        }
        return componentScores;
    }

    public ArrayList<Component> getAllComponents() {
        ArrayList<Component> components = new ArrayList<>();
        String sql = "select * from Component";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int componentManagerId = cursor.getInt(cursor.getColumnIndex("componentManagerId"));

                Component component = new Component();
                component.setId(id);
                component.setName(name);
                component.setComponentManagerId(componentManagerId);

                components.add(component);
                cursor.moveToNext();
            }
        }
        return components;
    }

    public void insertComponentWithoutComponentManager(String name) {
        String sql = "INSERT INTO Component (name) VALUES ('" + name + "')";
        this.getWritableDatabase().execSQL(sql);
    }

    public void insertComponentWithComponentManager(String name, int componentManagerId) {
        String sql = "INSERT INTO Component (name, componentManagerId) VALUES ('" + name + "', " + componentManagerId + ")";
        this.getWritableDatabase().execSQL(sql);
    }

    public ArrayList<Skill> getAllSkills() {
        ArrayList<Skill> skills = new ArrayList<>();
        String sql = "select * from Skill";
        @SuppressLint("Recycle") Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String linkToViewDetails = cursor.getString(cursor.getColumnIndex("linkToViewDetails"));
                @SuppressLint("Range") int componentId = cursor.getInt(cursor.getColumnIndex("componentId"));

                Skill skill = new Skill();
                skill.setId(id);
                skill.setTitle(title);
                skill.setDescription(description);
                skill.setLinkToViewDetails(linkToViewDetails);
                skill.setComponentId(componentId);

                skills.add(skill);
                cursor.moveToNext();
            }
        }
        return skills;
    }

    public void updateComponentWithoutComponentManager(int componentId, String name) {
        String sql = "UPDATE Component SET name = '" + name + "' WHERE id = " + componentId;
        this.getWritableDatabase().execSQL(sql);
    }

    public void updateComponentWithComponentManager(int componentId, String name, int componentManagerId) {
        String sql = "UPDATE Component SET name = '" + name + "', componentManagerId = " + componentManagerId + " WHERE id = " + componentId;
        this.getWritableDatabase().execSQL(sql);
    }

    public void deleteComponent(String name) {
        String sql = "DELETE FROM Component WHERE name = '" + name + "'";
        this.getWritableDatabase().execSQL(sql);
    }

    public void insertSkill(String title, String description, String linkToViewDetails, int componentId) {
        String sql = "INSERT INTO Skill (title, description, linkToViewDetails, componentId) " +
                "VALUES ('" + title + "', '" + description + "', '" + linkToViewDetails + "', " + componentId + ")";
        this.getWritableDatabase().execSQL(sql);
    }
}
