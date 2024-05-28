package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;
import com.example.isepdevappmobileadmin.R;

import java.util.ArrayList;
import java.util.Objects;

public class SignUp extends AppCompatActivity {
    // We create variables to store the value entered by the user in the different EditText into the Database
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String adminRole;
    private DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);

        // We instantiate the value in the spinner dropdown list
        Spinner spinner = (Spinner) findViewById(R.id.admin_roles_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.admin_role, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // We now create the Activity related to the user pressing the Sign Up button
        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We recover the values of the different variables
                EditText emailEditText = findViewById(R.id.sign_up_email_edit_text);
                email = emailEditText.getText().toString();
                EditText passwordEditText = findViewById(R.id.sign_up_password_edit_text);
                password = passwordEditText.getText().toString();
                EditText firstNameEditText = findViewById(R.id.sign_up_first_name_edit_text);
                firstName = firstNameEditText.getText().toString();
                EditText lastNameEditText = findViewById(R.id.sign_up_last_name_edit_text);
                lastName = lastNameEditText.getText().toString();
                Spinner adminRoleSpinner = (Spinner) findViewById(R.id.admin_roles_spinner);
                adminRole = adminRoleSpinner.getSelectedItem().toString();
                SignIn.ADMIN_ROLE = adminRole;

                // We get the list of all Admins in the Database
                databaseManager = new DatabaseManager(getApplicationContext());
                ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();

                // We create a Boolean variable to know if the user is already in the Admin Table
                boolean isUserInDatabase = false;
                if (!allAdminsInDB.isEmpty()) {
                    for (int i = 0; i < allAdminsInDB.size(); i++) {
                        Admin admin = allAdminsInDB.get(i);
                        if (Objects.equals(admin.getEmail(), email)) {
                            isUserInDatabase = true;
                        }
                    }
                }
                if (!isUserInDatabase) {
                    // We store in the Database the new Admin
                    databaseManager.insertNewAdmin(email, password, firstName, lastName);

                    // We get the list of all Admins in the database and get the last created Admin
                    allAdminsInDB = databaseManager.getAllAdmins();
                    Admin currentAdmin = new Admin();
                    for (int i = 0; i < allAdminsInDB.size(); i++) {
                        Admin admin = allAdminsInDB.get(i);
                        if (Objects.equals(admin.getEmail(), email)) {
                            currentAdmin = admin;
                        }
                    }

                    // Depending on the value of the Admin role of the user, we create an instance in different Tables in the Database
                    String adminRoleTable = null;
                    if (Objects.equals(adminRole, "Module Manager")) {
                        adminRoleTable = "ModuleManager";
                    } else if (Objects.equals(adminRole, "Tutor")) {
                        adminRoleTable = "Tutor";
                    } else if (Objects.equals(adminRole, "Component Manager")) {
                        adminRoleTable = "ComponentManager";
                    }
                    SignIn.ROLE_ID = databaseManager.insertAdminRole(adminRoleTable, currentAdmin.getId());

                    // We show the user that he has been registered in the DB
                    Intent userRegisteredInDB = new Intent(getApplicationContext(), UserRegisteredInDatabase.class);
                    startActivity(userRegisteredInDB);
                } else {
                    Intent userAlreadyInDBIntent = new Intent(getApplicationContext(), UserAlreadyInDB.class);
                    startActivity(userAlreadyInDBIntent);
                }
            }
        });

        // We create the action when a user clicks on the Back to Sign In Page button
        Button backToSignInPageButton = findViewById(R.id.return_to_sign_in_page_from_sign_up_page);
        backToSignInPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSignInPageIntent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(backToSignInPageIntent);
            }
        });
    }
}