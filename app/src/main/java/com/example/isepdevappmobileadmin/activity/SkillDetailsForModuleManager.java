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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class SkillDetailsForModuleManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.skill_details_for_module_manager);

        // We display the Component Name at the top
        ListView listViewComponentName = findViewById(R.id.skill_name_for_module_manager_in_skills_details);
        String skillName = ComponentDetailsForModuleManager.SKILL_NAME;
        ArrayList<String> arrayListComponentName = new ArrayList<>();
        arrayListComponentName.add(skillName);
        ArrayAdapter<String> adapterComponentName = new ArrayAdapter<>(this, R.layout.list_view_single_item, R.id.list_view_item_text_view, arrayListComponentName);
        listViewComponentName.setAdapter(adapterComponentName);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_list_of_skills_page_from_skills_details_for_module_manager);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_skill_details);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We modify the TextView texts
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        Skill currentSkill = new Skill();
        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
            if (Objects.equals(allSkillsInDB.get(skillIndex).getTitle(), skillName)) {
                currentSkill = allSkillsInDB.get(skillIndex);
            }
        }
        TextView textViewTitle = findViewById(R.id.skill_title_text_view_in_skill_details);
        textViewTitle.setText(currentSkill.getTitle());
        TextView textViewDescription = findViewById(R.id.skill_description_text_view_in_skill_details);
        textViewDescription.setText(currentSkill.getDescription());
        TextView textViewLinkToViewDetails = findViewById(R.id.skill_link_to_view_details_text_view_in_skill_details);
        textViewLinkToViewDetails.setText(currentSkill.getLinkToViewDetails());

        // We create the Modify Component Button Activity
        Button modifySkilltButton = findViewById(R.id.modify_skill_details_for_module_manager);
        modifySkilltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModifySkill = new Intent(getApplicationContext(), ModifySkill.class);
                startActivity(intentModifySkill);
            }
        });

        // We create the Modify Component Button Activity
        Button deleteSkilltButton = findViewById(R.id.delete_skill_for_module_manager);
        deleteSkilltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDeleteSkill = new Intent(getApplicationContext(), DeleteSkill.class);
                startActivity(intentDeleteSkill);
            }
        });
    }
}