package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class AddSkill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_skill);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_component_details_page_from_add_skill_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_add_skill_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We create the Add Skill Button Activity
        Button addSkillButton = findViewById(R.id.add_skill_to_database_button);
        addSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextSkillTitle = findViewById(R.id.skill_title_in_add_skill_page);
                String skillTitle = editTextSkillTitle.getText().toString();
                EditText editTextSkillDescription = findViewById(R.id.skill_description_in_add_skill_page);
                String skillDescription = editTextSkillDescription.getText().toString();
                EditText editTextSkillLinkToViewDetails = findViewById(R.id.skill_link_to_view_details_in_add_skill_page);
                String skillLinkToViewDetails = editTextSkillLinkToViewDetails.getText().toString();

                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
                int componentId = 0;
                for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                    if (Objects.equals(allComponentsInDB.get(componentIndex).getName(), ModuleManagerActivity.COMPONENT_NAME)) {
                        componentId = allComponentsInDB.get(componentIndex).getId();
                    }
                }
                // We insert the new Skill in the Database
                databaseManager.insertSkill(skillTitle, skillDescription, skillLinkToViewDetails, componentId);

                // We recover the id of the Skill just added in the Database
                ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
                int skillId = 0;
                for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
                    if (Objects.equals(allSkillsInDB.get(skillIndex).getTitle(), skillTitle)) {
                        skillId = allSkillsInDB.get(skillIndex).getId();
                    }
                }

                // For each Students in the Database, you add the SkillScore for the new Skill
                ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
                ArrayList<ComponentScore> allComponentScoresInDB = databaseManager.getAllComponentScores();
                for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
                    for (int index = 0; index < allComponentScoresInDB.size(); index++) {
                        if (allComponentScoresInDB.get(index).getStudentId() == allStudentsInDB.get(studentIndex).getId()
                                && allComponentScoresInDB.get(index).getComponentId() == componentId) {
                            databaseManager.insertSkillScore(skillId, allComponentScoresInDB.get(index).getId());
                        }
                    }
                }

                // We insert all TeamObservations for the Team
                ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
                for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex++) {
                    databaseManager.insertTeamObservation(skillId, teamIndex);
                }

                Intent intentAddSkill = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentAddSkill);
            }
        });
    }
}