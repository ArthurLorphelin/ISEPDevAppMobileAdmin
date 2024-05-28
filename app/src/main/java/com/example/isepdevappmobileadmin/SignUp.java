package com.example.isepdevappmobileadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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

                // We verify that all fields have data
                if (!email.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !adminRole.isEmpty()) {
                    // We verify that the user is not in the system
                    if (databaseManager.isUniqueAdmin(email)) {
                        // We store in the Database the new Admin
                        databaseManager.insertNewAdmin(email, password, firstName, lastName);

                        Intent goBackToSignInPage = new Intent(getApplicationContext(), SignIn.class);
                        startActivity(goBackToSignInPage);
                    } else {
                        // We need to indicate that the user is already in the system
                    }
                } else {
                    // We need to make a pop-up appear to tell the user to insert data into all fields
                }
            }
        });
    }
}