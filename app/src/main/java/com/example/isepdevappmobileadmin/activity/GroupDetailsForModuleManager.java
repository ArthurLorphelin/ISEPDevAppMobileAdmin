package com.example.isepdevappmobileadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.adapter.StudentAndComponentScoreAdapter;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.Client;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class GroupDetailsForModuleManager extends AppCompatActivity {
    public static String TEAM_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.group_details_for_module_manager);

        // We create the activity due to the user clicking the ProfilePage Image Button
        ImageButton profileImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_teams_list);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilePageIntent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(goToProfilePageIntent);
            }
        });

        // We create the activity due to the user clicking on the PreviousPage Image Button
        ImageButton previousPageImageButton = findViewById(R.id.back_to_all_groups_page_from_list_of_teams_in_group);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), AllGroupsPage.class);
                startActivity(intentPreviousPage);
            }
        });

        // Display of the group name at the top of the Activity page
        ListView listViewMenuItemGroupName = findViewById(R.id.group_name_for_module_manager_in_teams_list);
        String groupName = AllGroupsPage.GROUP_NAME;
        ArrayList<String> groupNamesForMenuListView = new ArrayList<>();
        groupNamesForMenuListView.add(groupName);
        ArrayAdapter<String> adapterGroupName = new ArrayAdapter<>(this, R.layout.list_view_menu_item, R.id.list_view_menu_item_text_view, groupNamesForMenuListView);
        listViewMenuItemGroupName.setAdapter(adapterGroupName);

        // We recover the id from the selected Group
        DatabaseManager databaseManager = new DatabaseManager(this);
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        int groupId = 0;
        int clientId = 0;
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex++) {
            if (Objects.equals(groupName, allGroupsInDB.get(groupIndex).getName())) {
                groupId = allGroupsInDB.get(groupIndex).getId();
                clientId = allGroupsInDB.get(groupIndex).getClientId();
            }
        }

        // We display the Group Tutor name
        TextView textViewGroupTutor = findViewById(R.id.group_tutor_name_in_group_details_text_view);
        ArrayList<Tutor> allTutorsInDB = databaseManager.getAllTutors();
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        int adminId = 0;
        String tutorName = "";
        for (int tutorIndex = 0; tutorIndex < allTutorsInDB.size(); tutorIndex++) {
            if (allTutorsInDB.get(tutorIndex).getGroupId() == groupId) {
                adminId = allTutorsInDB.get(tutorIndex).getAdminId();
            }
        }
        if (adminId == 0) {
            tutorName = " - ";
        } else {
            for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex ++) {
                if (allAdminsInDB.get(adminIndex).getId() == adminId) {
                    tutorName = allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName();
                }
            }
        }
        textViewGroupTutor.setText(tutorName);

        // We display the Client name
        TextView textViewClientName = findViewById(R.id.client_name_in_group_details_text_view);
        ArrayList<Client> allClientsInDB = databaseManager.getAllClients();
        String clientName = "";
        if (clientId != 0) {
            for (int clientIndex = 0; clientIndex < allClientsInDB.size(); clientIndex++) {
                if (allClientsInDB.get(clientIndex).getId() == clientId) {
                    clientName = allClientsInDB.get(clientIndex).getName();
                }
            }
        } else {
            clientName = " - ";
        }
        textViewClientName.setText(clientName);

        // We get the Teams that are in the group
        ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
        ArrayList<Team> teamsInSelectedGroup = new ArrayList<>();
        ArrayList<String> teamsNameInSelectedGroup = new ArrayList<>();
        for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex++) {
            if (allTeamsInDB.get(teamIndex).getGroupId() == groupId) {
                teamsInSelectedGroup.add(allTeamsInDB.get(teamIndex));
                teamsNameInSelectedGroup.add(allTeamsInDB.get(teamIndex).getName());
            }
        }

        // We display the needed information
        ListView teamsListView = findViewById(R.id.list_of_teams_in_selected_group_for_module_manager_list_view);
        ArrayAdapter<String> adapterTeamsList = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, teamsNameInSelectedGroup);
        teamsListView.setAdapter(adapterTeamsList);

        // We display the info during search
        EditText editTextTeamsSearch = findViewById(R.id.search_bar_team_in_selected_group_for_module_manager);
        editTextTeamsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> teamsNamesDuringSearch = new ArrayList<>();
                for (int teamIndex = 0; teamIndex < teamsNameInSelectedGroup.size(); teamIndex++) {
                    if (teamsNameInSelectedGroup.get(teamIndex).toUpperCase().contains(s.toString().toUpperCase())) {
                        teamsNamesDuringSearch.add(teamsNameInSelectedGroup.get(teamIndex));
                    }
                }
                ArrayAdapter<String> adapterDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, teamsNamesDuringSearch);
                teamsListView.setAdapter(adapterDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        teamsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TEAM_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentTeamDetails = new Intent(getApplicationContext(), TeamDetailsForModuleManager.class);
                startActivity(intentTeamDetails);
            }
        });

        // We create the Activity when the user wants to modify the group info
        Button buttonModifyGroupDetails = findViewById(R.id.modify_group_details_for_module_manager);
        buttonModifyGroupDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModifyGroup = new Intent(getApplicationContext(), ModifyGroup.class);
                startActivity(intentModifyGroup);
            }
        });
    }
}