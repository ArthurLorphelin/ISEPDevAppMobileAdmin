package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Rating;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.SkillScore;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class SkillScoreDetailsForModuleManager extends AppCompatActivity {
    private String chosenRatingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.skill_score_details_for_module_manager);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_component_scores_details_page_from_skill_scores_details_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentScoreDetails.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_skill_scores_details_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We modify the EditText hint with the current title of the skill
        EditText editTextObservation = findViewById(R.id.skill_score_observation_in_skill_scores_details_page);
        TextView textViewSkillTitle = findViewById(R.id.skill_title_in_skill_scores_details_page);
        SkillScore currentSkillScore = ComponentScoreDetails.SKILL_SCORE;
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        String skillTitle = "";
        for (int index = 0; index < allSkillsInDB.size(); index++) {
            if (allSkillsInDB.get(index).getId() == currentSkillScore.getSkillId()) {
                skillTitle = allSkillsInDB.get(index).getTitle();
            }
        }
        textViewSkillTitle.setText(skillTitle);
        editTextObservation.setHint(currentSkillScore.getSkillObservation());

        // We insert the items in the Spinner
        ArrayList<String> ratingNames = new ArrayList<>();
        ratingNames.add("None");
        ArrayList<Rating> allRatingsInDB = databaseManager.getAllRatings();
        for (int ratingIndex = 0; ratingIndex < allRatingsInDB.size(); ratingIndex++) {
            ratingNames.add(allRatingsInDB.get(ratingIndex).getName());
        }

        // We display all the Tutor names in the Spinner
        Spinner spinner = findViewById(R.id.modify_skill_score_rating_spinner);
        ArrayAdapter<String> adapterSpinnerItems = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, ratingNames);
        adapterSpinnerItems.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerItems);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenRatingName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), chosenRatingName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // We create the Modify Button Activity
        Button buttonModifySkillScore = findViewById(R.id.modify_skill_score_to_database_button);
        buttonModifySkillScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We get
                String observation = editTextObservation.getText().toString();
                int ratingId = 0;
                if (!Objects.equals(chosenRatingName, "None")) {
                    for (int ratingIndex = 0; ratingIndex < allRatingsInDB.size(); ratingIndex++) {
                        if (Objects.equals(allRatingsInDB.get(ratingIndex).getName(), chosenRatingName)) {
                            ratingId = allRatingsInDB.get(ratingIndex).getId();
                        }
                    }
                    databaseManager.updateSkillScoreWithRating(currentSkillScore.getId(), observation, ratingId);
                } else {
                    databaseManager.updateSkillScoreWithoutRating(currentSkillScore.getId(), observation);
                }
                calculateComponentScore(currentSkillScore);

                // We redirect the user to the previous page
                Intent intentModifiedSkillScore = new Intent(getApplicationContext(), ComponentScoreDetails.class);
                startActivity(intentModifiedSkillScore);
            }
        });

    }

    private void calculateComponentScore(SkillScore skillScore) {
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        int componentScoreId = skillScore.getComponentScoreId();
        int score = 0;
        ArrayList<SkillScore> allSkillScoresInDB = databaseManager.getAllSkillScores();
        ArrayList<Integer> ratingIds = new ArrayList<>();
        for (int index = 0; index < allSkillScoresInDB.size(); index++) {
            if (allSkillScoresInDB.get(index).getComponentScoreId() == componentScoreId) {
                ratingIds.add(allSkillScoresInDB.get(index).getRatingId());
            }
        }

        // We calculate the Component score
        ArrayList<Rating> allRatingsInDB = databaseManager.getAllRatings();
        for (int idIndex = 0; idIndex < ratingIds.size(); idIndex++) {
            for (int ratingIndex = 0; ratingIndex < allRatingsInDB.size(); ratingIndex++) {
                if (ratingIds.get(idIndex) == allRatingsInDB.get(ratingIndex).getId()) {
                    score += allRatingsInDB.get(ratingIndex).getValue();
                }
            }
        }

        databaseManager.updateComponentScoreTableWithScore(componentScoreId, score);
    }
}