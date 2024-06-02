package com.example.isepdevappmobileadmin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DBtable.Rating;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.SkillScore;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class SkillScoreAdapter extends ArrayAdapter<SkillScore> {
    private ArrayList<SkillScore> skillScores;
    private DatabaseManager databaseManager;

    public SkillScoreAdapter(Context context, ArrayList<SkillScore> skillScores) {
        super(context, 0, skillScores);
        this.skillScores = skillScores;
        this.databaseManager = new DatabaseManager(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @SuppressLint("SetTextI18n")
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_three_items, parent, false);
        }

        // We display the skill title
        TextView textViewSkillTitle = convertView.findViewById(R.id.list_view_three_items_first_item);
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        SkillScore currentSkillScore = getItem(position);
        String skillTitle = "";
        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
            if (allSkillsInDB.get(skillIndex).getId() == currentSkillScore.getSkillId()) {
                skillTitle = allSkillsInDB.get(skillIndex).getTitle();
            }
        }
        textViewSkillTitle.setText(skillTitle);

        // We display the skill observation
        TextView textViewSkillObservation = convertView.findViewById(R.id.list_view_three_items_second_item);
        String skillObservation = currentSkillScore.getSkillObservation();
        if (skillObservation == null) {
            skillObservation = " - ";
        }
        textViewSkillObservation.setText(skillObservation);

        // We display the skill rating
        TextView textViewSkillRating = convertView.findViewById(R.id.list_view_three_items_third_item);
        ArrayList<Rating> allRatingsInDB = databaseManager.getAllRatings();
        String skillRating = "";
        if (currentSkillScore.getRatingId() == 0) {
            skillRating = " - ";
        } else {
            for (int ratingIndex = 0; ratingIndex < allRatingsInDB.size(); ratingIndex++) {
                if (allRatingsInDB.get(ratingIndex).getId() == currentSkillScore.getRatingId()) {
                    skillRating = allRatingsInDB.get(ratingIndex).getValue() + "/20";
                }
            }
        }
        textViewSkillRating.setText(skillRating);

        return convertView;
    }
}