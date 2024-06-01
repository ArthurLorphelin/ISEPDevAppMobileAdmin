package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_page);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_menu_from_profile_page);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Tutor")) {
                    Intent intentPreviousPageTutor = new Intent(getApplicationContext(), TutorActivity.class);
                    startActivity(intentPreviousPageTutor);
                } else if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Module Manager")) {
                    Intent intentPreviousPageModuleManager = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                    startActivity(intentPreviousPageModuleManager);
                } else if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Component Manager")) {
                    Intent intentPreviousPageComponentManager = new Intent(getApplicationContext(), ComponentManagerActivity.class);
                    startActivity(intentPreviousPageComponentManager);
                }
            }
        });

        // We get all the Admin Information
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        Admin currentAdmin = new Admin();
        for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
            if (allAdminsInDB.get(adminIndex).getId() == SignIn.ADMIN_ID) {
                currentAdmin = allAdminsInDB.get(adminIndex);
            }
        }

        // We display all the Information
        TextView textViewFirstName = findViewById(R.id.first_name_in_profile_page);
        textViewFirstName.setText(currentAdmin.getFirstName());
        TextView textViewLastName = findViewById(R.id.last_name_in_profile_page);
        textViewLastName.setText(currentAdmin.getLastName());
        TextView textViewEmail = findViewById(R.id.email_in_profile_page);
        textViewEmail.setText(currentAdmin.getEmail());
        TextView textViewPassword = findViewById(R.id.password_in_profile_page);
        textViewPassword.setText(currentAdmin.getPassword());

        // We create the Activity for the modify Button
        Button buttonModifyProfile = findViewById(R.id.modify_profile_details_button);
        buttonModifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModifyProfile = new Intent(getApplicationContext(), ModifyProfile.class);
                startActivity(intentModifyProfile);
            }
        });

        // We create the Activity for the modify Button
        Button buttonLogOut = findViewById(R.id.log_out_button);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogOut = new Intent(getApplicationContext(), LogOut.class);
                startActivity(intentLogOut);
            }
        });
    }
}