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
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class AllGroupsPage extends AppCompatActivity {
    public static String GROUP_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.all_groups_page);

        // We connect the Image Button to the Profile Page
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_groups_list);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We create the All Groups Page Button Activity
        Button allComponentsButton = findViewById(R.id.go_to_all_components_list_button);
        allComponentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAllComponents = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                startActivity(intentAllComponents);
            }
        });

        // We create the activity when the user clicks on the All Students PAge
        Button buttonAllStudents = findViewById(R.id.go_to_all_students_list_button_from_all_groups);
        buttonAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAllStudentsPage = new Intent(getApplicationContext(), AllStudentsPage.class);
                startActivity(intentAllStudentsPage);
            }
        });

        // We display the list of all Groups
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        ArrayList<String> allGroupNames = new ArrayList<>();
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex ++) {
            allGroupNames.add(allGroupsInDB.get(groupIndex).getName());
        }
        ListView listViewAllGroups = findViewById(R.id.list_of_groups_for_module_manager_list_view);
        ArrayAdapter<String> adapterGroupNames = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, allGroupNames);
        listViewAllGroups.setAdapter(adapterGroupNames);

        // We update the list displayed when the user types in the search bar
        EditText editTextSearchBarGroups = findViewById(R.id.search_bar_groups_for_module_manager);
        editTextSearchBarGroups.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> groupNamesDuringSearch = new ArrayList<>();
                for (int i = 0; i < allGroupNames.size(); i++) {
                    if (allGroupNames.get(i).toUpperCase().contains(s.toString().toUpperCase())) {
                        groupNamesDuringSearch.add(allGroupNames.get(i));
                    }
                }
                ArrayAdapter<String> adapterGroupNameDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, groupNamesDuringSearch);
                listViewAllGroups.setAdapter(adapterGroupNameDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We create the activity when the user clicks on a list item
        listViewAllGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GROUP_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentGroupDetails = new Intent(getApplicationContext(), GroupDetailsForModuleManager.class);
                startActivity(intentGroupDetails);
            }
        });
    }
}