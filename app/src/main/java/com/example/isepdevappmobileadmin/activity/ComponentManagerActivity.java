package com.example.isepdevappmobileadmin.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.component_manager);

        // We now instantiate the value of the items presented in the ListView
        ListView listViewGroups = findViewById(R.id.list_of_groups_for_component_manager_list_view);
        databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        ArrayList<String> groupNamesArrayList = new ArrayList<>();
        for (int i = 0; i < allGroupsInDB.size(); i++) {
            groupNamesArrayList.add(allGroupsInDB.get(i).getName());
        }
        ArrayAdapter<String> adapterAllGroups = new ArrayAdapter<>(this, R.layout.list_view_item, R.id.list_view_item_text_view, groupNamesArrayList);
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
                ArrayAdapter<String> adapterGroupsDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_item, R.id.list_view_item_text_view, groupNamesDuringSearch);
                listViewGroups.setAdapter(adapterGroupsDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });




        ImageButton profileImageButton = findViewById(R.id.profile_image_button);
    }
}