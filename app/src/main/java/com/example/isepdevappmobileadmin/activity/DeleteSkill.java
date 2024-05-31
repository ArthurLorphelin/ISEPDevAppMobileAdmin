package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class DeleteSkill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.delete_skill);

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_delete_skill_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We change the EditText text with the Component name
        TextView textViewComponentName = findViewById(R.id.skill_name_text_view_in_delete_skill);
        textViewComponentName.setText(ComponentDetailsForModuleManager.SKILL_NAME);

        // We create the No Button Activity
        Button buttonNo = findViewById(R.id.no_button_delete_skill);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNoDelete = new Intent(getApplicationContext(), SkillDetailsForModuleManager.class);
                startActivity(intentNoDelete);
            }
        });

        // We create the Yes Button Activity
        Button yesButton = findViewById(R.id.yes_button_delete_skill);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                // We delete the Skill and all TeamObservations for this Skill from the Database
                ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
                int componentId = 0;
                for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                    if (Objects.equals(allComponentsInDB.get(componentIndex).getName(), ModuleManagerActivity.COMPONENT_NAME)) {
                        componentId = allComponentsInDB.get(componentIndex).getId();
                    }
                }

                ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
                int skillId = 0;
                for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
                    if (allSkillsInDB.get(skillIndex).getComponentId() == componentId) {
                        skillId = allSkillsInDB.get(skillIndex).getId();
                        if (Objects.equals(allSkillsInDB.get(skillIndex).getTitle(), ComponentDetailsForModuleManager.SKILL_NAME)) {
                            // We delete the Skill from the Database
                            databaseManager.deleteSkill(skillId);

                            // We delete the TeamObservations for the Skill to be deleted
                            databaseManager.deleteTeamObservations(skillId);

                            // We delete the SkillScore for the Skill to be deleted
                            databaseManager.deleteSkillScore(skillId);
                        }
                    }
                }
                Intent intentYesDelete = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentYesDelete);
            }
        });
    }
}