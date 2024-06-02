package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;

public class LogOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.log_out);

        // We create the Yes Button Activity
        Button buttonYes = findViewById(R.id.yes_button_log_out);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentYesLogOut = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intentYesLogOut);
            }
        });

        // We create the Yes Button Activity
        Button buttonNo = findViewById(R.id.no_button_log_out);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNoLogOut = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentNoLogOut);
            }
        });
    }
}