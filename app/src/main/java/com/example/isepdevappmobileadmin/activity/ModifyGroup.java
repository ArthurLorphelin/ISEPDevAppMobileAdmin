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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.Client;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Group;
import com.example.isepdevappmobileadmin.classes.DBtable.Tutor;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ModifyGroup extends AppCompatActivity {
    private String chosenTutorName;
    private String clientName;
    private Group currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_group);

        // We create the activity due to the user clicking the ProfilePage Image Button
        ImageButton profileImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_modify_group_page);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilePageIntent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(goToProfilePageIntent);
            }
        });

        // We create the activity due to the user clicking on the PreviousPage Image Button
        ImageButton previousPageImageButton = findViewById(R.id.back_to_group_details_page_from_modify_group_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), GroupDetailsForModuleManager.class);
                startActivity(intentPreviousPage);
            }
        });

        // We modify the TextView with the current name of the Group
        TextView textViewGroupName = findViewById(R.id.group_name_in_modify_group_page);
        textViewGroupName.setText(AllGroupsPage.GROUP_NAME);

        // We get the current Group Info
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Group> allGroupsInDB = databaseManager.getAllGroups();
        currentGroup = new Group();
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex++) {
            if (Objects.equals(allGroupsInDB.get(groupIndex).getName(), AllGroupsPage.GROUP_NAME)) {
                currentGroup = allGroupsInDB.get(groupIndex);
            }
        }

        // We get the client name
        ArrayList<Client> allClientsInDB = databaseManager.getAllClients();
        if (currentGroup.getClientId() == 0) {
            clientName = " - ";
        } else {
            for (int clientIndex = 0; clientIndex < allClientsInDB.size(); clientIndex++) {
                if (allClientsInDB.get(clientIndex).getId() == currentGroup.getClientId()) {
                    clientName = allClientsInDB.get(clientIndex).getName();
                }
            }
        }

        // We modify the EditText hint with the current Client name
        EditText editTextClientName = findViewById(R.id.client_name_in_modify_group_page);
        editTextClientName.setHint(clientName);

        // We insert the items in the Spinner
        ArrayList<String> tutorsName = new ArrayList<>();
        tutorsName.add("None");
        ArrayList<Tutor> allTutorsInDB = databaseManager.getAllTutors();
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        for (int tutorIndex = 0; tutorIndex < allTutorsInDB.size(); tutorIndex++) {
            for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
                if (allTutorsInDB.get(tutorIndex).getAdminId() == allAdminsInDB.get(adminIndex).getId()) {
                    tutorsName.add(allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName());
                }
            }
        }

        // We display the data in the spinner
        Spinner spinner = findViewById(R.id.modify_group_tutor_spinner);
        ArrayAdapter<String> adapterSpinnerItems = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, tutorsName);
        adapterSpinnerItems.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerItems);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenTutorName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), chosenTutorName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // We insert the modifications in the Database
        Button buttonModifyGroup = findViewById(R.id.modify_group_to_database_button);
        buttonModifyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newClientName;
                int newClientId = 0;
                int adminId = 0;
                int chosenTutorId = 0;

                // We verify if a Client name has been entered
                if (editTextClientName.getText().toString().isEmpty()) {
                    newClientName = clientName;
                } else {
                    newClientName = editTextClientName.getText().toString();
                    for (int clientIndex = 0; clientIndex < allClientsInDB.size(); clientIndex++) {
                        if (Objects.equals(allClientsInDB.get(clientIndex).getName(), newClientName)) {
                            newClientId = allClientsInDB.get(clientIndex).getId();
                        }
                    }
                }

                // We insert the entered client in the Database if he is not already in it
                if (newClientId == 0 && !Objects.equals(newClientName, " - ")) {
                    databaseManager.insertClient(newClientName);
                    for (int i = 0; i < allClientsInDB.size(); i++) {
                        if (Objects.equals(allClientsInDB.get(i).getName(), newClientName)) {
                            newClientId = allClientsInDB.get(i).getId();
                        }
                    }
                }

                // We update the Group in the Database if there is a Client
                if (newClientId != 0) {
                    databaseManager.updateGroupWithClient(currentGroup.getId(), newClientId);
                }

                // We get the id of the chosen Tutor and we modify the Tutor in DB with the groupId
                if (!Objects.equals(chosenTutorName, "None")) {
                    for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
                        if (Objects.equals(chosenTutorName, allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName())) {
                            adminId = allAdminsInDB.get(adminIndex).getId();
                        }
                    }

                    for (int tutorIndex = 0; tutorIndex < allTutorsInDB.size(); tutorIndex++) {
                        if (allTutorsInDB.get(tutorIndex).getAdminId() == adminId) {
                            chosenTutorId = allTutorsInDB.get(tutorIndex).getId();
                        }
                    }

                    databaseManager.updateTutorWithGroup(chosenTutorId, currentGroup.getId());
                }

                // We redirect to the Group Details page
                Intent intentGroupModified = new Intent(getApplicationContext(), GroupDetailsForModuleManager.class);
                startActivity(intentGroupModified);
            }
        });
    }
}