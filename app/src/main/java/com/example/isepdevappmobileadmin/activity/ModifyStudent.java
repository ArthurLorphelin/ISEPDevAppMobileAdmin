package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Rating;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ModifyStudent extends AppCompatActivity {
    private String chosenTeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_student);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_student_details_page_from_modify_student);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), StudentDetails.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_modify_student_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We display the Student name
        TextView textViewStudentName = findViewById(R.id.student_name_in_student_details_page);
        textViewStudentName.setText(AllStudentsPage.STUDENT_NAME);

        // We insert the items in the Spinner
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<String> teamNames = new ArrayList<>();
        ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
        for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex++) {
            teamNames.add(allTeamsInDB.get(teamIndex).getName());
        }

        // We display all the Team names in the Spinner
        Spinner spinnerTeam = findViewById(R.id.modify_team_name_spinner);
        ArrayAdapter<String> adapterSpinnerItems2 = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, teamNames);
        adapterSpinnerItems2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTeam.setAdapter(adapterSpinnerItems2);
        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenTeamName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), chosenTeamName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chosenTeamName = StudentDetails.TEAM_NAME;
            }
        });

        // We create the Modify Student Activity
        Button buttonModifyStudent = findViewById(R.id.modify_skill_score_to_database_button);
        buttonModifyStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We get the team id and the group id
                int teamId = 0;
                int groupId = 0;
                for (int index = 0; index < allTeamsInDB.size(); index++) {
                    if (Objects.equals(allTeamsInDB.get(index).getName(), chosenTeamName)) {
                        teamId = allTeamsInDB.get(index).getId();
                        groupId = allTeamsInDB.get(index).getGroupId();
                    }
                }

                // We get the student id
                ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
                int studentId = 0;
                for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
                    if (AllStudentsPage.STUDENT_NAME.contains(allStudentsInDB.get(studentIndex).getFirstName())
                            && AllStudentsPage.STUDENT_NAME.contains(allStudentsInDB.get(studentIndex).getLastName())) {
                        studentId = allStudentsInDB.get(studentIndex).getId();
                    }
                }

                // We update the Student
                databaseManager.updateStudentWithGroupAndTeam(studentId, groupId, teamId);

                // We go back to the Student Details page
                Intent intentModifiedStudent = new Intent(getApplicationContext(), StudentDetails.class);
                startActivity(intentModifiedStudent);
            }
        });
    }
}