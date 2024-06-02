package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ModifySkill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_skill);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_module_manager_page_from_modify_skill_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_modify_skill_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We modify the EditText hint with the current name of the component
        EditText editTextSkillTitle = findViewById(R.id.skill_title_in_modify_skill_page);
        EditText editTextSkillDescription = findViewById(R.id.skill_description_in_modify_skill_page);
        EditText editTextSkillLink = findViewById(R.id.skill_link_to_view_details_in_modify_skill_page);
        editTextSkillTitle.setHint(ComponentDetailsForModuleManager.SKILL_NAME);

        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        Skill currentSkill = new Skill();
        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
            if (Objects.equals(allSkillsInDB.get(skillIndex).getTitle(), ComponentDetailsForModuleManager.SKILL_NAME)) {
                currentSkill = allSkillsInDB.get(skillIndex);
            }
        }
        int id = currentSkill.getId();
        editTextSkillDescription.setHint(currentSkill.getDescription());
        editTextSkillLink.setHint(currentSkill.getLinkToViewDetails());

        // We create the Modify Skill Button Activity
        Button modifySkillButton = findViewById(R.id.modify_skill_to_database_button);
        modifySkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextSkillTitle.getText().toString();
                String description = editTextSkillDescription.getText().toString();
                String linkToViewDetails = editTextSkillLink.getText().toString();

                databaseManager.updateSkill(id, title, description, linkToViewDetails);
                Intent intentModifySkill = new Intent(getApplicationContext(), SkillDetailsForModuleManager.class);
                startActivity(intentModifySkill);
            }
        });

    }
}