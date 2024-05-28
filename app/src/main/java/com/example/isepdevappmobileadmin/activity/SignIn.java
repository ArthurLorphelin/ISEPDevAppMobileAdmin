package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;
import com.example.isepdevappmobileadmin.R;

import java.util.ArrayList;
import java.util.Objects;

public class SignIn extends AppCompatActivity {
    // We instantiate the variable to access the local Database
    private DatabaseManager databaseManager;

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

                /*
                // We verify that the email is in the Database
                if (databaseManager.isAdmin(email)) {
                    // We verify that the email and password are linked together
                    if (password.equals(databaseManager.getPasswordFromAdmin(email))) {
                        Intent componentManagerIntent = new Intent(getApplicationContext(), ComponentManager.class);
                        startActivity(componentManagerIntent);
                    }
                } else {
                    // We need to indicate that the user is not in the system
                }
                 */

                // We get the list of all Admins in Database and verify that the user is in the list
                ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
                boolean isUserInDatabase = false;
                for (int i = 0; i < allAdminsInDB.size(); i++) {
                    Admin admin = allAdminsInDB.get(i);
                    if (Objects.equals(admin.getEmail(), email)) {
                        isUserInDatabase = true;
                    }
                }

                // If the user is in the list, we advance to the next stage
                if (isUserInDatabase) {
                    Intent componentManagerIntent = new Intent(getApplicationContext(), ComponentManager.class);
                    startActivity(componentManagerIntent);
                } else {
                    Intent userNotInDBIntent = new Intent(getApplicationContext(), UserNotInDB.class);
                    startActivity(userNotInDBIntent);
                }

                 /*
                Intent componentManagerIntent = new Intent(getApplicationContext(), ComponentManager.class);
                startActivity(componentManagerIntent);
                */
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