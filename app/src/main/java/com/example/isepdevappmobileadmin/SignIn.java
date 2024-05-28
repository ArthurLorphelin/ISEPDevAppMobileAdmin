package com.example.isepdevappmobileadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

                // We verify that the email and password correspond to each other
            }
        });
    }
}