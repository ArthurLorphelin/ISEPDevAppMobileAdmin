package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.adapter.ComponentListViewAdapter;
import com.example.isepdevappmobileadmin.adapter.SummaryStudentAdapter;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class StudentDetailsForModuleManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_details_for_module_manager);

        // We display the Student Name at the top
        ListView listViewComponentName = findViewById(R.id.student_name_for_module_manager_in_students_scores_list);
        String studentName = TeamDetailsForModuleManager.STUDENT_NAME;
        ArrayList<String> arrayListStudentName = new ArrayList<>();
        arrayListStudentName.add(studentName);
        ArrayAdapter<String> adapterComponentName = new ArrayAdapter<>(this, R.layout.list_view_single_item, R.id.list_view_item_text_view, arrayListStudentName);
        listViewComponentName.setAdapter(adapterComponentName);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_team_details_page_from_students_details_page);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), TeamDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_student_scores_list_with_skill_observations);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We get the Student id
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        int studentId = 0;
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (studentName.contains(allStudentsInDB.get(studentIndex).getFirstName())
                    && studentName.contains(allStudentsInDB.get(studentIndex).getLastName())) {
                studentId = allStudentsInDB.get(studentIndex).getId();
            }
        }

        // We get all ComponentScores for this Student
        ArrayList<ComponentScore> allComponentScoresInDB = databaseManager.getAllComponentScores();
        ArrayList<ComponentScore> componentScoresForThisStudent = new ArrayList<>();
        for (int index = 0; index < allComponentScoresInDB.size(); index++) {
            if (allComponentScoresInDB.get(index).getStudentId() == studentId) {
                componentScoresForThisStudent.add(allComponentScoresInDB.get(index));
            }
        }

        // We display the Summary for the Student
        ListView listViewSummary = findViewById(R.id.summary_list_view_in_student_details);
        SummaryStudentAdapter summaryStudentAdapter = new SummaryStudentAdapter(getApplicationContext(), componentScoresForThisStudent);
        listViewSummary.setAdapter(summaryStudentAdapter);

        // We display the SkillScore for each Skill for each Component
        ListView componentListView = findViewById(R.id.list_view_of_all_components_in_student_details_page_for_module_manager);
        ComponentListViewAdapter componentListViewAdapter = new ComponentListViewAdapter(getApplicationContext(), componentScoresForThisStudent);
        componentListView.setAdapter(componentListViewAdapter);
    }
}