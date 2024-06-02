package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.adapter.SkillScoreAdapter;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DBtable.SkillScore;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ComponentScoreDetails extends AppCompatActivity {
    public static SkillScore SKILL_SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.component_score_details);

        // We display the Student Name at the top
        ListView listViewComponentName = findViewById(R.id.component_name_for_module_manager_in_skill_scores_list);
        ComponentScore currentComponentScore = new ComponentScore();
        if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Module Manager")) {
            currentComponentScore = StudentDetailsForModuleManager.COMPONENT_SCORE;
        } else if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Tutor")) {
            currentComponentScore = StudentDetailsForTutor.COMPONENT_SCORE;
        }


        // We get the name of the Component
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        String componentName = "";
        ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
        for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
            if (allComponentsInDB.get(componentIndex).getId() == currentComponentScore.getComponentId()) {
                componentName = allComponentsInDB.get(componentIndex).getName();
            }
        }
        ArrayList<String> arrayListComponentName = new ArrayList<>();
        arrayListComponentName.add(componentName);
        ArrayAdapter<String> adapterComponentName = new ArrayAdapter<>(this, R.layout.list_view_single_item, R.id.list_view_item_text_view, arrayListComponentName);
        listViewComponentName.setAdapter(adapterComponentName);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_student_details_page_from_students_details_page);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Module Manager")) {
                    Intent intentPreviousPage = new Intent(getApplicationContext(), StudentDetailsForModuleManager.class);
                    startActivity(intentPreviousPage);
                } else if (Objects.equals(SignIn.ADMIN_ROLE_NAME, "Tutor")) {
                    Intent intentPreviousPage = new Intent(getApplicationContext(), StudentDetailsForTutor.class);
                    startActivity(intentPreviousPage);
                }

            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_skill_scores_list_with_skill_observations);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });



        // We get the list of all SkillScores for this student and this Component
        ArrayList<SkillScore> allSkillScoresInDB = databaseManager.getAllSkillScores();
        ArrayList<SkillScore> skillScoresForThisComponentScore = new ArrayList<>();
        for (int scoreIndex = 0; scoreIndex < allSkillScoresInDB.size(); scoreIndex++) {
            if (allSkillScoresInDB.get(scoreIndex).getComponentScoreId() == currentComponentScore.getId()) {
                skillScoresForThisComponentScore.add(allSkillScoresInDB.get(scoreIndex));
            }
        }

        // We display the List View items
        ListView listViewSkillScores = findViewById(R.id.skill_scores_list_view_in_component_scores_details);
        SkillScoreAdapter skillScoreAdapter = new SkillScoreAdapter(getApplicationContext(), skillScoresForThisComponentScore);
        listViewSkillScores.setAdapter(skillScoreAdapter);

        // We create the Activity when the user selects a skillScore
        listViewSkillScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SKILL_SCORE = (SkillScore) parent.getItemAtPosition(position);
                Intent intentSkillScoreDetails = new Intent(getApplicationContext(), SkillScoreDetailsForModuleManager.class);
                startActivity(intentSkillScoreDetails);
            }
        });
    }
}