package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

public class DeleteComponent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.delete_component);

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_delete_component_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We change the EditText text with the Component name
        TextView textViewComponentName = findViewById(R.id.component_name_text_view_in_delete_components);
        textViewComponentName.setText(ModuleManagerActivity.COMPONENT_NAME);

        // We create the No Button Activity
        Button buttonNo = findViewById(R.id.no_button_delete_component);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNoDelete = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentNoDelete);
            }
        });

        // We create the Yes Button Activity
        Button yesButton = findViewById(R.id.yes_button_delete_component);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                databaseManager.deleteComponent(ModuleManagerActivity.COMPONENT_NAME);
                Intent intentYesDelete = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                startActivity(intentYesDelete);
            }
        });
    }
}