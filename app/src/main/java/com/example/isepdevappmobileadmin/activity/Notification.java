package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.notification);

        // We display the info in the Text View
        TextView textView = findViewById(R.id.notification_text_view);
        String dataReceived = getIntent().getStringExtra("data");
        textView.setText(dataReceived);

        // We create the Activity to SignIn Page
        Button buttonSignIn = findViewById(R.id.go_to_sign_in_from_notifications);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignIn = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intentSignIn);
            }
        });
    }
}