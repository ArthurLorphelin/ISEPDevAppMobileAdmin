package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {
    public static String GROUP_NAME;
    public static String TEAM_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_details);

        // We display the Student Name at the top
        ListView listViewStudentName = findViewById(R.id.student_name_for_module_manager_in_students_details);
        String studentName = AllStudentsPage.STUDENT_NAME;
        ArrayList<String> arrayListStudentName = new ArrayList<>();
        arrayListStudentName.add(studentName);
        ArrayAdapter<String> adapterComponentName = new ArrayAdapter<>(this, R.layout.list_view_single_item, R.id.list_view_item_text_view, arrayListStudentName);
        listViewStudentName.setAdapter(adapterComponentName);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_all_students_page_from_students_details_page);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), AllStudentsPage.class);
                startActivity(intentPreviousPage);
            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_student_details_page);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We get all the information of the student
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        Student currentStudent = new Student();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (studentName.contains(allStudentsInDB.get(studentIndex).getFirstName())
                    && studentName.contains(allStudentsInDB.get(studentIndex).getLastName())) {
                currentStudent = allStudentsInDB.get(studentIndex);
            }
        }

        // We display the name, mail and student number for this Student
        TextView textViewStudentName = findViewById(R.id.student_name_in_student_details_text_view);
        textViewStudentName.setText(studentName);
        TextView textViewStudentNumber = findViewById(R.id.student_number_in_student_details_text_view);
        textViewStudentNumber.setText(String.valueOf(currentStudent.getStudentNumber()));
        TextView textViewStudentEmail = findViewById(R.id.student_email_in_student_details_text_view);
        textViewStudentEmail.setText(currentStudent.getEmail());

        // We display the Group Name
        TextView textViewGroupName = findViewById(R.id.student_group_in_student_details_text_view);
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        String groupName = "";
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex++) {
            if (allGroupsInDB.get(groupIndex).getId() == currentStudent.getGroupId()) {
                groupName = allGroupsInDB.get(groupIndex).getName();
            }
        }
        GROUP_NAME = groupName;
        textViewGroupName.setText(groupName);

        // We display the Team Name
        TextView textViewTeamName = findViewById(R.id.student_team_in_student_details_text_view);
        String teamName = "";
        ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
        for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex++) {
            if (allTeamsInDB.get(teamIndex).getId() == currentStudent.getTeamId()) {
                teamName = allTeamsInDB.get(teamIndex).getName();
            }
        }
        TEAM_NAME = teamName;
        textViewTeamName.setText(teamName);

        // We create the Activity when the users clicks the Modify Button
        Button buttonModifyStudent = findViewById(R.id.modify_student_group_and_team_in_student_details);
        buttonModifyStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModifyStudent = new Intent(getApplicationContext(), ModifyStudent.class);
                startActivity(intentModifyStudent);
            }
        });
    }
}