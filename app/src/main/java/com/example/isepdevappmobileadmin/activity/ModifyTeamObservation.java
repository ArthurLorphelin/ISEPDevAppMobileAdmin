package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DBtable.TeamObservation;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class ModifyTeamObservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_team_observation);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_team_details_page_from_modify_team_observation_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), TeamDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_modify_team_observation_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We display the Skill Title
        TextView textViewSkillTitle = findViewById(R.id.skill_title_in_modify_team_observation_page);
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        TeamObservation chosenTeamObservation = TeamDetailsForModuleManager.TEAM_OBSERVATION;
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        String skillTitle = "";
        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
            if (allSkillsInDB.get(skillIndex).getId() == chosenTeamObservation.getSkillId()) {
                skillTitle = allSkillsInDB.get(skillIndex).getTitle();
            }
        }
        textViewSkillTitle.setText(skillTitle);

        // We display the Team name
        TextView textViewTeamName = findViewById(R.id.team_name_in_modify_team_observation_page);
        String teamName = "";
        ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
        for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex++) {
            if (allTeamsInDB.get(teamIndex).getId() == chosenTeamObservation.getTeamId()) {
                teamName = allTeamsInDB.get(teamIndex).getName();
            }
        }
        textViewTeamName.setText(teamName);

        // We display the old observation in the Edit Text
        EditText editTextObservation = findViewById(R.id.observation_in_modify_team_observation_page);
        editTextObservation.setHint(chosenTeamObservation.getObservation());

        // We create the Modify TO Activity
        Button modifyTeamObservationButton = findViewById(R.id.modify_team_observation_to_database_button);
        modifyTeamObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String observation = editTextObservation.getText().toString();
                databaseManager.updateTeamObservation(chosenTeamObservation.getId(), observation);

                Intent intentObservationModified = new Intent(getApplicationContext(), TeamDetailsForModuleManager.class);
                startActivity(intentObservationModified);
            }
        });
    }
}