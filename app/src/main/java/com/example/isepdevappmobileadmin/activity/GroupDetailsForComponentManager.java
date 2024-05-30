package com.example.isepdevappmobileadmin.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class GroupDetailsForComponentManager extends AppCompatActivity {
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.group_details_for_component_manager);

        ListView listViewMenuItemGroupName = findViewById(R.id.group_name_for_component_manager_in_students_list);
        String groupName = ComponentManagerActivity.GROUP_NAME;
        ArrayList<String> groupNamesForMenuListView = new ArrayList<>();
        groupNamesForMenuListView.add(groupName);
        ArrayAdapter<String> adapterGroupName = new ArrayAdapter<>(this, R.layout.list_view_menu_item, R.id.list_view_menu_item_text_view, groupNamesForMenuListView);
        listViewMenuItemGroupName.setAdapter(adapterGroupName);

    }
}