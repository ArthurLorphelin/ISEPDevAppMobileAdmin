package com.example.isepdevappmobileadmin.activity;

import android.os.Bundle;
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


        ListView listViewGroups = findViewById(R.id.list_of_groups_for_component_manager_list_view);
        databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        ArrayList<String> groupNamesArrayList = new ArrayList<>();
        for (int i = 0; i < allGroupsInDB.size(); i++) {
            groupNamesArrayList.add(allGroupsInDB.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_item, R.id.list_view_item_text_view, groupNamesArrayList);
        listViewGroups.setAdapter(adapter);

        EditText searchBarListGroups = findViewById(R.id.search_bar_group_for_component_manager);
        ImageButton profileImageButton = findViewById(R.id.profile_image_button);
    }
}