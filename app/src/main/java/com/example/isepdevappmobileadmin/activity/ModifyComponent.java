package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ModifyComponent extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private String chosenComponentManagerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_component);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_module_manager_page_from_modify_component_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_modify_component_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We modify the EditText hint with the current name of the component
        EditText editTextComponentName = findViewById(R.id.component_name_in_modify_component_page);
        editTextComponentName.setHint(ModuleManagerActivity.COMPONENT_NAME);

        // We insert the items in the Spinner
        ArrayList<String> componentManagersName = new ArrayList<>();
        componentManagersName.add("None");
        databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<ComponentManager> allComponentManagersInDB = databaseManager.getAllComponentManagers();
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        for (int componentManagerIndex = 0; componentManagerIndex < allComponentManagersInDB.size(); componentManagerIndex++) {
            for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
                if (allComponentManagersInDB.get(componentManagerIndex).getAdminId() == allAdminsInDB.get(adminIndex).getId()) {
                    componentManagersName.add(allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName());
                }
            }
        }

        Spinner spinner = findViewById(R.id.modify_component_manager_spinner);
        ArrayAdapter<String> adapterSpinnerItems = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, componentManagersName);
        adapterSpinnerItems.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerItems);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenComponentManagerName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), chosenComponentManagerName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // We insert the data written by the user in the Database
        Button addComponentToDatabaseButton = findViewById(R.id.modify_component_to_database_button);
        addComponentToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                if (editTextComponentName.getText().toString().isEmpty()) {
                    name = ModuleManagerActivity.COMPONENT_NAME;
                } else {
                    name = editTextComponentName.getText().toString();
                }

                ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
                int componentId = 0;
                for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                    if (Objects.equals(allComponentsInDB.get(componentIndex).getName(), ModuleManagerActivity.COMPONENT_NAME)) {
                        componentId = allComponentsInDB.get(componentIndex).getId();
                    }
                }
                int adminId = 0;
                if (!Objects.equals(chosenComponentManagerName, "None")) {
                    for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
                        if (Objects.equals(chosenComponentManagerName, allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName())) {
                            adminId = allAdminsInDB.get(adminIndex).getId();
                        }
                    }
                    int chosenComponentManagerId = 0;
                    for (int componentManagerIndex = 0; componentManagerIndex < allComponentManagersInDB.size(); componentManagerIndex++) {
                        if (allComponentManagersInDB.get(componentManagerIndex).getAdminId() == adminId) {
                            chosenComponentManagerId = allComponentManagersInDB.get(componentManagerIndex).getId();
                        }
                    }
                    databaseManager.updateComponentWithComponentManager(componentId, name, chosenComponentManagerId);
                } else {
                    databaseManager.updateComponentWithoutComponentManager(componentId, name);
                }

                // We go back to the Components list
                Intent intentComponentModified = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                startActivity(intentComponentModified);
            }
        });
    }
}