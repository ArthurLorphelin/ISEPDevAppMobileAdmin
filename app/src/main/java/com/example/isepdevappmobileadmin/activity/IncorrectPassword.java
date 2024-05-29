package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;

public class IncorrectPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.incorrect_password);

        Button backToSignInPageButton = findViewById(R.id.back_to_sign_in_page_button_because_incorrect_password);
        backToSignInPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSignInPageIntent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(backToSignInPageIntent);
            }
        });
    }
}