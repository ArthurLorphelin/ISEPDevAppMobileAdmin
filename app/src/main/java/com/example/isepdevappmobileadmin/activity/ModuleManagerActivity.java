package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class ModuleManagerActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    public String COMPONENT_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.module_manager);

        // We display the list of all Components
        databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
        ArrayList<String> allComponentNames = new ArrayList<>();
        for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex ++) {
            allComponentNames.add(allComponentsInDB.get(componentIndex).getName());
        }
        ListView listViewAllComponents = findViewById(R.id.list_of_components_for_module_manager_list_view);
        ArrayAdapter<String> adapterComponentNames = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, allComponentNames);
        listViewAllComponents.setAdapter(adapterComponentNames);

        // We update the list displayed when the user types in the search bar
        EditText editTextSearchBarComponents = findViewById(R.id.search_bar_components_for_module_manager);
        editTextSearchBarComponents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> componentNamesDuringSearch = new ArrayList<>();
                for (int i = 0; i < allComponentNames.size(); i++) {
                    if (allComponentNames.get(i).toUpperCase().contains(s.toString().toUpperCase())) {
                        componentNamesDuringSearch.add(allComponentNames.get(i));
                    }
                }
                ArrayAdapter<String> adapterComponentNameDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, componentNamesDuringSearch);
                listViewAllComponents.setAdapter(adapterComponentNameDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We create the activity when the user clicks on a list item
        listViewAllComponents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMPONENT_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentComponentDetails = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentComponentDetails);
            }
        });

        // We connect the Image Button to the Profile Page
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_components_list);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We create the activity when the Module Manager click on the Add Button
        Button addComponentButton = findViewById(R.id.add_components_button);
        addComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddComponent = new Intent(getApplicationContext(), AddComponent.class);
                startActivity(intentAddComponent);
            }
        });
    }
}