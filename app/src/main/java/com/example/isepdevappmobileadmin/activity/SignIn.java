package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.AdminRole;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DBtable.ModuleManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;
import com.example.isepdevappmobileadmin.R;

import java.util.ArrayList;
import java.util.Objects;

public class SignIn extends AppCompatActivity {
    // We instantiate the variable to access the local Database
    private DatabaseManager databaseManager;

    // We instantiate the public variables that will store the adminId, the adminRole and the roleId;
    public static int ADMIN_ID;
    public static String ADMIN_ROLE_NAME;
    public static int ROLE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in);

        // We connect to the local Database
        databaseManager = new DatabaseManager(getApplicationContext());

        // Once the user has clicked on the Sign In button
        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We create the two variables to store the email and password entered by the user
                EditText emailEditText = findViewById(R.id.sign_in_email_edit_text);
                EditText passwordEditText = findViewById(R.id.sign_in_password_edit_text);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // We get the list of all Admins in Database and verify that the user is in the list
                ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
                boolean isUserInDatabase = false;
                int currentAdminIndex = 0;
                for (int i = 0; i < allAdminsInDB.size(); i++) {
                    Admin admin = allAdminsInDB.get(i);
                    if (Objects.equals(admin.getEmail(), email)) {
                        isUserInDatabase = true;
                        currentAdminIndex = i;
                    }
                }

                // If the user is in the list, we advance to the next stage
                if (isUserInDatabase) {
                    Admin currentAdmin = allAdminsInDB.get(currentAdminIndex);
                    ADMIN_ID = currentAdmin.getId();

                    // We verify that the password is the correct one for this admin
                    if (Objects.equals(currentAdmin.getPassword(), password)) {

                        // We get the current AdminRole of the Admin
                        ArrayList<AdminRole> adminRoles = databaseManager.getAllAdminRoles();
                        for (int i = 0; i < adminRoles.size(); i++) {
                            AdminRole adminRole = adminRoles.get(i);
                            if (currentAdmin.getAdminRoleId() == adminRole.getId()){
                                ADMIN_ROLE_NAME = adminRole.getName();
                            }
                        }

                        // We differentiate the cases depending on the Admin role : Component Manager, Tutor or Module Manager
                        if (Objects.equals(ADMIN_ROLE_NAME, "Component Manager")) {
                            ArrayList<ComponentManager> allComponentManagersInDB = databaseManager.getAllComponentManagers();
                            for (int i = 0; i < allComponentManagersInDB.size(); i++) {
                                ComponentManager componentManager = allComponentManagersInDB.get(i);
                                if (currentAdmin.getId() == componentManager.getAdminId()){
                                    ROLE_ID = componentManager.getId();
                                }
                            }
                            Intent componentManagerIntent = new Intent(getApplicationContext(), ComponentManagerActivity.class);
                            startActivity(componentManagerIntent);
                        } else if (Objects.equals(ADMIN_ROLE_NAME, "Module Manager")) {
                            ArrayList<ModuleManager> allModuleManagersInDB = databaseManager.getAllModuleManagers();
                            for (int i = 0; i < allModuleManagersInDB.size(); i++) {
                                ModuleManager moduleManager = allModuleManagersInDB.get(i);
                                if (currentAdmin.getId() == moduleManager.getAdminId()){
                                    ROLE_ID = moduleManager.getId();
                                }
                            }
                            Intent moduleManagerIntent = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                            startActivity(moduleManagerIntent);
                        } else if (Objects.equals(ADMIN_ROLE_NAME, "Tutor")) {
                            ArrayList<Tutor> allTutorsInDB = databaseManager.getAllTutors();
                            for (int i = 0; i < allTutorsInDB.size(); i++) {
                                Tutor tutor = allTutorsInDB.get(i);
                                if (currentAdmin.getId() == tutor.getAdminId()){
                                    ROLE_ID = tutor.getId();
                                }
                            }
                            Intent tutorIntent = new Intent(getApplicationContext(), TutorActivity.class);
                            startActivity(tutorIntent);
                        }
                    } else {
                        Intent incorrectPasswordIntent = new Intent(getApplicationContext(), IncorrectPassword.class);
                        startActivity(incorrectPasswordIntent);
                    }
                } else {
                    Intent userNotInDBIntent = new Intent(getApplicationContext(), UserNotInDB.class);
                    startActivity(userNotInDBIntent);
                }
            }
        });

        // Once the user has clicked on the Sign Up button
        Button redirectToSignUpButton = findViewById(R.id.redirect_to_sign_up_page_button);
        redirectToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We create a second page on which the user can sign up in the app
                Intent signUpIntent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signUpIntent);
            }
        });
    }
}