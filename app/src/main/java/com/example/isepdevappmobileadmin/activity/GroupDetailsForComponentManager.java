package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class GroupDetailsForComponentManager extends AppCompatActivity {
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.group_details_for_component_manager);

        // Display of the group name at the top of the Activity page
        ListView listViewMenuItemGroupName = findViewById(R.id.group_name_for_component_manager_in_students_list);
        String groupName = ComponentManagerActivity.GROUP_NAME;
        ArrayList<String> groupNamesForMenuListView = new ArrayList<>();
        groupNamesForMenuListView.add(groupName);
        ArrayAdapter<String> adapterGroupName = new ArrayAdapter<>(this, R.layout.list_view_menu_item, R.id.list_view_menu_item_text_view, groupNamesForMenuListView);
        listViewMenuItemGroupName.setAdapter(adapterGroupName);

        // We recover the id from the selected Group
        databaseManager = new DatabaseManager(this);
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        int groupId = 0;
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex++) {
            if (Objects.equals(groupName, allGroupsInDB.get(groupIndex).getName())) {
                groupId = allGroupsInDB.get(groupIndex).getId();
            }
        }

        // We create a list in which is added all Students of the selected Group by first and last Name
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        ArrayList<String> studentNamesInSelectedGroup = new ArrayList<>();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (allStudentsInDB.get(studentIndex).getGroupId() == groupId) {
                studentNamesInSelectedGroup.add(allStudentsInDB.get(studentIndex).getFirstName() + " " + allStudentsInDB.get(studentIndex).getLastName());
            }
        }

        // We display the corresponding Students in the ListView
        ListView listViewStudentsInGroup = findViewById(R.id.list_of_students_in_selected_group_for_component_manager_list_view);
        ArrayAdapter<String> adapterStudentNamesInGroup = new ArrayAdapter<>(this, R.layout.list_view_multiple_items, R.id.list_view_first_item_text_view, studentNamesInSelectedGroup);
        listViewStudentsInGroup.setAdapter(adapterStudentNamesInGroup);

        // We create the activity due to the user clicking on the PreviousPage Image Button
        ImageButton previousPageImageButton = findViewById(R.id.back_to_component_manager_page_from_list_of_students_in_group);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentManagerActivity.class);
                startActivity(intentPreviousPage);
            }
        });

        // We create the activity due to the user clicking the ProfilePage Image Button
        ImageButton profileImageButton = findViewById(R.id.profile_image_button_for_component_manager_in_students_list_with_component_scores);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilePageIntent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(goToProfilePageIntent);
            }
        });

        // We now create the effect due to the user typing in the search bar
        EditText searchBarStudentsList = findViewById(R.id.search_bar_student_in_selected_group_for_component_manager);
        searchBarStudentsList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> studentNameListDuringSearch = new ArrayList<>();
                for (int i = 0; i < studentNamesInSelectedGroup.size(); i++) {
                    if (studentNamesInSelectedGroup.get(i).toUpperCase().contains(s.toString().toUpperCase())) {
                        studentNameListDuringSearch.add(studentNamesInSelectedGroup.get(i));
                    }
                }
                ArrayAdapter<String> adapterStudentNamesDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_multiple_items, R.id.list_view_first_item_text_view, studentNameListDuringSearch);
                listViewStudentsInGroup.setAdapter(adapterStudentNamesDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}