package com.example.isepdevappmobileadmin.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.TeamObservation;

public class ModifyTeamObservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_team_observation);

        TextView textView = findViewById(R.id.modify);
        TeamObservation teamObservation = TeamDetailsForModuleManager.TEAM_OBSERVATION;
        String obs = "TO id : " + teamObservation.getId() + ",\n TO skillID : " + teamObservation.getSkillId() + ",\n TO teamId : " + teamObservation.getTeamId();
        textView.setText(obs);
    }
}