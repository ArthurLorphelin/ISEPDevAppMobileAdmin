package com.example.isepdevappmobileadmin.activity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
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
    private String adminName;
    private String groupName;

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
        for (int groupIndex = 0; groupIndex < allGroupsInDB.size(); groupIndex++) {
            if (currentTutor.getGroupId() == allGroupsInDB.get(groupIndex).getId()) {
                groupName = allGroupsInDB.get(groupIndex).getName();
            }
        }
        textViewGroupName.setText(groupName);

        // We get the Admin First and Last Name;
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex ++) {
            if (allAdminsInDB.get(adminIndex).getId() == SignIn.ADMIN_ID) {
                adminName = allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName();
            }
        }

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

        // We create the Activity when the Module Manager wants to send a Notification to the Team
        Button buttonSendNotification = findViewById(R.id.send_notifications_for_tutor);

        // We have to ask for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(TutorActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TutorActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        buttonSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotification(adminName, groupName);
            }
        });
    }

    public void makeNotification(String adminName, String groupName) {
        String notificationText = adminName + "(Tutor) has updated the scores and observations for Group : " + groupName;
        String channelId = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);

        // We instantiate the what the Notification is going to send and how it will be sent
        builder.setSmallIcon(R.drawable.ic_notifications);
        builder.setContentTitle("Score and Observations updated");
        builder.setContentText(notificationText);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // We create the Intent for the notification
        Intent intentNotification = new Intent(getApplicationContext(), Notification.class);
        intentNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNotification.putExtra("data", notificationText);

        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, intentNotification, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // We create a Notification Channel
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
            if (notificationChannel == null) {
                // If the Notification Channel is null, we instantiate it with new values
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelId, "Some description", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);

                // We create the Notification
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());
    }
}