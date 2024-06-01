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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class AllStudentsPage extends AppCompatActivity {
    public static String STUDENT_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.all_students_page);

        // We connect the Image Button to the Profile Page
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_students_list);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We create the All Groups Page Button Activity
        Button allGroupsButton = findViewById(R.id.go_to_all_groups_list_button_from_all_students);
        allGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAllComponents = new Intent(getApplicationContext(), AllGroupsPage.class);
                startActivity(intentAllComponents);
            }
        });

        // We create the activity when the user clicks on the All Components PAge
        Button buttonAllComponents = findViewById(R.id.go_to_all_components_list_button_from_all_students);
        buttonAllComponents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAllStudentsPage = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                startActivity(intentAllStudentsPage);
            }
        });

        // We display the list of all Groups
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        ArrayList<String> allStudentNames = new ArrayList<>();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex ++) {
            String name = allStudentsInDB.get(studentIndex).getFirstName() + " " + allStudentsInDB.get(studentIndex).getLastName();
            allStudentNames.add(name);
        }
        ListView listViewAllStudents = findViewById(R.id.list_of_students_for_module_manager_list_view);
        ArrayAdapter<String> adapterStudentNames = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, allStudentNames);
        listViewAllStudents.setAdapter(adapterStudentNames);

        // We update the list displayed when the user types in the search bar
        EditText editTextSearchBarStudents = findViewById(R.id.search_bar_students_for_module_manager);
        editTextSearchBarStudents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> studentNamesDuringSearch = new ArrayList<>();
                for (int i = 0; i < allStudentNames.size(); i++) {
                    if (allStudentNames.get(i).toUpperCase().contains(s.toString().toUpperCase())) {
                        studentNamesDuringSearch.add(allStudentNames.get(i));
                    }
                }
                ArrayAdapter<String> adapterGroupNameDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, studentNamesDuringSearch);
                listViewAllStudents.setAdapter(adapterGroupNameDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We create the activity when the user clicks on a list item
        listViewAllStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                STUDENT_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentStudentDetails = new Intent(getApplicationContext(), StudentDetails.class);
                startActivity(intentStudentDetails);
            }
        });
    }
}