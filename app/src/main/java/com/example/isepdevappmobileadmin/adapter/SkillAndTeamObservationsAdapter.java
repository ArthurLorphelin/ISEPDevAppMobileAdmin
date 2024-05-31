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
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.TeamObservation;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class SkillAndTeamObservationsAdapter extends ArrayAdapter<TeamObservation> {
    private ArrayList<TeamObservation> teamObservations;
    private DatabaseManager databaseManager;

    public SkillAndTeamObservationsAdapter(Context context, ArrayList<TeamObservation> teamObservations) {
        super(context, 0, teamObservations);
        this.teamObservations = teamObservations;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_two_items, parent, false);
        }

        // We display in the first part : the name of the Skill
        TextView textViewFirstItem = convertView.findViewById(R.id.list_view_two_items_first_item);
        TextView textViewSecondItem = convertView.findViewById(R.id.list_view_two_items_second_item);
        TeamObservation currentTeamObservation = getItem(position);
        if (currentTeamObservation != null) {
            ArrayList<Skill> allSkillInDB = databaseManager.getAllSkills();
            int componentId = 0;
            String skillTitle = "";
            for (int skillIndex = 0; skillIndex < allSkillInDB.size(); skillIndex++) {
                if (allSkillInDB.get(skillIndex).getId() == currentTeamObservation.getSkillId()) {
                    skillTitle = allSkillInDB.get(skillIndex).getTitle();
                    componentId = allSkillInDB.get(skillIndex).getComponentId();
                }
            }
            ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
            String componentName = "";
            for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                if (allComponentsInDB.get(componentIndex).getId() == componentId) {
                    componentName = allComponentsInDB.get(componentIndex).getName();
                }
            }
            String componentNameAndSkillTitle = componentName + " - " + skillTitle;
            textViewFirstItem.setText(componentNameAndSkillTitle);

            // We instantiate the second part : the Team Observation
            textViewSecondItem.setText(currentTeamObservation.getObservation());
        }

        return convertView;
    }
}
