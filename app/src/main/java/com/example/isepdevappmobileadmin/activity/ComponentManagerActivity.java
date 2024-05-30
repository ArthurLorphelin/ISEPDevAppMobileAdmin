package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class ComponentManagerActivity extends AppCompatActivity {
    public static String GROUP_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.component_manager);

        // We now instantiate the value of the items presented in the ListView
        ListView listViewGroups = findViewById(R.id.list_of_groups_for_component_manager_list_view);
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        ArrayList<String> groupNamesArrayList = new ArrayList<>();
        for (int i = 0; i < allGroupsInDB.size(); i++) {
            groupNamesArrayList.add(allGroupsInDB.get(i).getName());
        }
        ArrayAdapter<String> adapterAllGroups = new ArrayAdapter<>(this, R.layout.list_view_single_item, R.id.list_view_item_text_view, groupNamesArrayList);
        listViewGroups.setAdapter(adapterAllGroups);

        // We now create the effect linked to the search bar
        EditText searchBarListGroups = findViewById(R.id.search_bar_group_for_component_manager);
        searchBarListGroups.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Group> groupListDuringSearch = new ArrayList<>();
                ArrayList<String> groupNamesDuringSearch = new ArrayList<>();
                for (int i = 0; i < allGroupsInDB.size(); i++) {
                    if (allGroupsInDB.get(i).getName().toUpperCase().contains(s.toString().toUpperCase())) {
                        groupListDuringSearch.add(allGroupsInDB.get(i));
                    }
                }
                for (int i = 0; i < groupListDuringSearch.size(); i++) {
                    groupNamesDuringSearch.add(groupListDuringSearch.get(i).getName());
                }
                ArrayAdapter<String> adapterGroupsDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, groupNamesDuringSearch);
                listViewGroups.setAdapter(adapterGroupsDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We now define the activity linked to the user clicking on the user Icon
        ImageButton profileImageButton = findViewById(R.id.profile_image_button_for_component_manager_in_group_list);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilePageIntent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(goToProfilePageIntent);
            }
        });

        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GROUP_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent groupDetailsIntent = new Intent(getApplicationContext(), GroupDetailsForComponentManager.class);
                startActivity(groupDetailsIntent);
            }
        });
    }
}