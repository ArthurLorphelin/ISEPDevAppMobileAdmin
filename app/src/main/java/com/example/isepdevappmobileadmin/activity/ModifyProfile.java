package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class ModifyProfile extends AppCompatActivity {
    private Admin currentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_profile);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_profile_details_from_modify_profile);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the EditText Hints
        EditText editTextFirstName = findViewById(R.id.first_name_modify_profile);
        EditText editTextLastName = findViewById(R.id.last_name_modify_profile);
        EditText editTextEmail = findViewById(R.id.email_modify_profile);
        EditText editTextPassword = findViewById(R.id.password_modify_profile);

        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        currentAdmin = new Admin();
        for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
            if (allAdminsInDB.get(adminIndex).getId() == SignIn.ADMIN_ID) {
                currentAdmin = allAdminsInDB.get(adminIndex);
            }
        }
        editTextFirstName.setHint(currentAdmin.getFirstName());
        editTextLastName.setHint(currentAdmin.getLastName());
        editTextEmail.setHint(currentAdmin.getEmail());
        editTextPassword.setHint(currentAdmin.getPassword());

        Button buttonModifyProfile = findViewById(R.id.modify_profile_to_database_button);
        buttonModifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    databaseManager.updateAdminProfile(currentAdmin.getId(), firstName, lastName, email, password);

                    Intent intentModifiedProfile = new Intent(getApplicationContext(), ProfilePage.class);
                    startActivity(intentModifiedProfile);
                }
            }
        });
    }
}