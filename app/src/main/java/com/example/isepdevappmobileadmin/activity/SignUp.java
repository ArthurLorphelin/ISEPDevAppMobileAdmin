package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.AdminRole;
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
    private int adminRoleId;
    private String adminRoleName;
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adminRoleName = (String) parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), adminRoleName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

                // We get the list of all Admins in the Database
                databaseManager = new DatabaseManager(getApplicationContext());
                ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();

                // We get the list of all AdminRoles in the Database and get the id of the corresponding role
                ArrayList<AdminRole> allAdminRolesInDB = databaseManager.getAllAdminRoles();
                for (int i = 0; i < allAdminRolesInDB.size(); i++) {
                    AdminRole adminRoleItem = allAdminRolesInDB.get(i);
                    if (Objects.equals(adminRoleItem.getName(), adminRoleName)) {
                        adminRoleId = adminRoleItem.getId();
                    }
                }

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
                    databaseManager.insertNewAdmin(email, password, firstName, lastName, adminRoleId);

                    // We get the list of all Admins in the database and get the one just created
                    allAdminsInDB = databaseManager.getAllAdmins();
                    Admin currentAdmin = new Admin();
                    for (int i = 0; i < allAdminsInDB.size(); i++) {
                        Admin admin = allAdminsInDB.get(i);
                        if (Objects.equals(admin.getEmail(), email)) {
                            currentAdmin = admin;
                        }
                    }

                    // Depending on the name of the Admin role of the user, we create an instance in different Tables in the Database
                    if (Objects.equals(adminRoleName, "Module Manager")) {
                        databaseManager.insertModuleManager(currentAdmin.getId());
                    } else if (Objects.equals(adminRoleName, "Tutor")) {
                        databaseManager.insertTutor(currentAdmin.getId());
                    } else if (Objects.equals(adminRoleName, "Component Manager")) {
                        databaseManager.insertComponentManager(currentAdmin.getId());
                    }

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