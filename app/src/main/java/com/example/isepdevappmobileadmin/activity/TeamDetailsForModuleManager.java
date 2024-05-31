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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.adapter.SkillAndTeamObservationsAdapter;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DBtable.Team;
import com.example.isepdevappmobileadmin.classes.DBtable.TeamObservation;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class TeamDetailsForModuleManager extends AppCompatActivity {
    public static String STUDENT_NAME;
    public static TeamObservation TEAM_OBSERVATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.team_details_for_module_manager);

        // We create the activity due to the user clicking the ProfilePage Image Button
        ImageButton profileImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_students_list_with_team_observations);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilePageIntent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(goToProfilePageIntent);
            }
        });

        // We create the activity due to the user clicking on the PreviousPage Image Button
        ImageButton previousPageImageButton = findViewById(R.id.back_to_group_details_page_from_team_details_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), GroupDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // Display of the Team name at the top of the Activity page
        ListView listViewMenuItemTeamName = findViewById(R.id.team_name_for_module_manager_in_students_list);
        String teamName = GroupDetailsForModuleManager.TEAM_NAME;
        ArrayList<String> teamNamesForMenuListView = new ArrayList<>();
        teamNamesForMenuListView.add(teamName);
        ArrayAdapter<String> adapterGroupName = new ArrayAdapter<>(this, R.layout.list_view_menu_item, R.id.list_view_menu_item_text_view, teamNamesForMenuListView);
        listViewMenuItemTeamName.setAdapter(adapterGroupName);

        // We get the id of the selected Team
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Team> allTeamsInDB = databaseManager.getAllTeams();
        int teamId = 0;
        for (int teamIndex = 0; teamIndex < allTeamsInDB.size(); teamIndex++) {
            if (Objects.equals(allTeamsInDB.get(teamIndex).getName(), teamName)) {
                teamId = allTeamsInDB.get(teamIndex).getId();
            }
        }

        // We get the Students that are in the Team
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        ArrayList<Student> studentsInSelectedTeam = new ArrayList<>();
        ArrayList<String> studentsNameInSelectedTeam = new ArrayList<>();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (allStudentsInDB.get(studentIndex).getTeamId() == teamId) {
                studentsInSelectedTeam.add(allStudentsInDB.get(studentIndex));
                studentsNameInSelectedTeam.add(allStudentsInDB.get(studentIndex).getFirstName() + " " + allStudentsInDB.get(studentIndex).getLastName());
            }
        }

        // We display the needed information
        ListView studentsListView = findViewById(R.id.list_of_students_in_selected_group_for_component_manager_list_view);
        ArrayAdapter<String> adapterStudentsList = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, studentsNameInSelectedTeam);
        studentsListView.setAdapter(adapterStudentsList);

        // We display the info during search
        EditText editTextStudentsSearch = findViewById(R.id.search_bar_student_in_selected_team_for_module_manager);
        editTextStudentsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> studentsNamesDuringSearch = new ArrayList<>();
                for (int studentIndex = 0; studentIndex < studentsNameInSelectedTeam.size(); studentIndex++) {
                    if (studentsNameInSelectedTeam.get(studentIndex).toUpperCase().contains(s.toString().toUpperCase())) {
                        studentsNamesDuringSearch.add(studentsNameInSelectedTeam.get(studentIndex));
                    }
                }
                ArrayAdapter<String> adapterDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, studentsNamesDuringSearch);
                studentsListView.setAdapter(adapterDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We display the TeamObservations corresponding to the Team
        ArrayList<TeamObservation> allTeamObservationsInDB = databaseManager.getAllTeamObservations();
        ArrayList<TeamObservation> teamObservationsForThisTeam = new ArrayList<>();
        for (int teamObservationIndex = 0; teamObservationIndex < allTeamObservationsInDB.size(); teamObservationIndex++) {
            if (allTeamObservationsInDB.get(teamObservationIndex).getTeamId() == teamId) {
                teamObservationsForThisTeam.add(allTeamObservationsInDB.get(teamObservationIndex));
            }
        }

        ListView listViewComponentsAndTeamObservations = findViewById(R.id.list_of_components_and_team_observations_for_module_manager_list_view);
        SkillAndTeamObservationsAdapter skillAndTeamObservationsAdapter = new SkillAndTeamObservationsAdapter(this, teamObservationsForThisTeam);
        listViewComponentsAndTeamObservations.setAdapter(skillAndTeamObservationsAdapter);

        ArrayList<Skill> allSkillInDB = databaseManager.getAllSkills();
        ArrayList<String> componentNameAndSkillTitleList = new ArrayList<>();
        String skillTitle = "";
        String componentName = "";
        for (int teamObservationIndex = 0; teamObservationIndex < teamObservationsForThisTeam.size(); teamObservationIndex++) {
            int componentId = 0;
            for (int skillIndex = 0; skillIndex < allSkillInDB.size(); skillIndex++) {
                if (allSkillInDB.get(skillIndex).getId() == teamObservationsForThisTeam.get(teamObservationIndex).getSkillId()) {
                    skillTitle = allSkillInDB.get(skillIndex).getTitle();
                    componentId = allSkillInDB.get(skillIndex).getComponentId();
                }
            }
            ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
            for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                if (allComponentsInDB.get(componentIndex).getId() == componentId) {
                    componentName = allComponentsInDB.get(componentIndex).getName();
                }
            }
            componentNameAndSkillTitleList.add(componentName + " - " + skillTitle);
        }



        EditText editTextTeamObservationsSearch = findViewById(R.id.search_bar_team_observations_in_selected_team_for_module_manager);
        editTextTeamObservationsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
                ArrayList<TeamObservation> teamObservationsDuringSearch = new ArrayList<>();

                for (int stringIndex = 0; stringIndex < componentNameAndSkillTitleList.size(); stringIndex++) {
                    if (componentNameAndSkillTitleList.get(stringIndex).toUpperCase().contains(s.toString().toUpperCase())) {

                        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
                            if (componentNameAndSkillTitleList.get(stringIndex).contains(allSkillsInDB.get(skillIndex).getTitle())) {

                                for (int teamObservationIndex = 0; teamObservationIndex < teamObservationsForThisTeam.size(); teamObservationIndex++) {
                                    if (allSkillsInDB.get(skillIndex).getId() == teamObservationsForThisTeam.get(teamObservationIndex).getSkillId()) {
                                        teamObservationsDuringSearch.add(teamObservationsForThisTeam.get(teamObservationIndex));
                                    }
                                }
                            }
                        }
                    }
                }

                SkillAndTeamObservationsAdapter adapterSkillAndTeamObservationsDuringSearch = new SkillAndTeamObservationsAdapter(getApplicationContext(), teamObservationsDuringSearch);
                listViewComponentsAndTeamObservations.setAdapter(adapterSkillAndTeamObservationsDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We create the Activity when the user clicks on the Student Name
        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                STUDENT_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentStudentDetails = new Intent(getApplicationContext(), StudentDetailsForModuleManager.class);
                startActivity(intentStudentDetails);
            }
        });

        // We create the Activity when the user clicks on the TeamObservations
        listViewComponentsAndTeamObservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TEAM_OBSERVATION = (TeamObservation) parent.getItemAtPosition(position);
                Intent intentModifyTeamObservation = new Intent(getApplicationContext(), ModifyTeamObservation.class);
                startActivity(intentModifyTeamObservation);
            }
        });
    }
}