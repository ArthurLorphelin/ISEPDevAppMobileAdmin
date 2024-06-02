package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class TutorActivity extends AppCompatActivity {
    public static String TEAM_NAME;
    public static int COMPONENT_ID;
    private Tutor currentTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tutor);

        // We connect the Image Button to the Profile Page
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_tutor_in_groups_list);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We get the current Tutor
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Tutor> allTutorsInDB = databaseManager.getAllTutors();
        for (int tutorIndex = 0; tutorIndex < allTutorsInDB.size(); tutorIndex++) {
            if (allTutorsInDB.get(tutorIndex).getId() == SignIn.ROLE_ID) {
                currentTutor = allTutorsInDB.get(tutorIndex);
            }
        }

        // We display the Group Name
        TextView textViewGroupName = findViewById(R.id.group_name_in_tutor_menu);
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        String groupName = "";
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex++) {
            if (currentTutor.getGroupId() == allGroupsInDB.get(groupIndex).getId()) {
                groupName = allGroupsInDB.get(groupIndex).getName();
            }
        }
        textViewGroupName.setText(groupName);

        // We display the Component Name
        TextView textViewComponentName = findViewById(R.id.component_name_in_tutor_menu);
        ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
        String componentName = "";
        for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
            if (currentTutor.getComponentId() == allComponentsInDB.get(componentIndex).getId()) {
                componentName = allComponentsInDB.get(componentIndex).getName();
            }
        }
        textViewComponentName.setText(componentName);

        // We display the list of all Teams
        ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
        ArrayList<String> allTeamNames = new ArrayList<>();
        for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex ++) {
            if (allTeamsInDB.get(teamIndex).getGroupId() == currentTutor.getGroupId()) {
                allTeamNames.add(allTeamsInDB.get(teamIndex).getName());
            }
        }

        ListView listViewAllTeams = findViewById(R.id.list_of_teams_for_tutor_list_view);
        ArrayAdapter<String> adapterTeamNames = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, allTeamNames);
        listViewAllTeams.setAdapter(adapterTeamNames);

        // We update the list displayed when the user types in the search bar
        EditText editTextSearchBarTeams = findViewById(R.id.search_bar_teams_for_tutor);
        editTextSearchBarTeams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> teamNamesDuringSearch = new ArrayList<>();
                for (int i = 0; i < allTeamNames.size(); i++) {
                    if (allTeamNames.get(i).toUpperCase().contains(s.toString().toUpperCase())) {
                        teamNamesDuringSearch.add(allTeamNames.get(i));
                    }
                }
                ArrayAdapter<String> adapterTeamNameDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, teamNamesDuringSearch);
                listViewAllTeams.setAdapter(adapterTeamNameDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We create the activity when the user clicks on a list item
        listViewAllTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMPONENT_ID = currentTutor.getComponentId();
                TEAM_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentGroupDetails = new Intent(getApplicationContext(), TeamDetailsForTutor.class);
                startActivity(intentGroupDetails);
            }
        });
    }
}