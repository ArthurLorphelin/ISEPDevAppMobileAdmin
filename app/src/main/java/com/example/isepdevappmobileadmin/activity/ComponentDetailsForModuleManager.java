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

import com.example.isepdevappmobileadmin.R;
import com.example.isepdevappmobileadmin.classes.DBtable.Admin;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentManager;
import com.example.isepdevappmobileadmin.classes.DBtable.Skill;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class ComponentDetailsForModuleManager extends AppCompatActivity {
    private DatabaseManager databaseManager;
    public static String SKILL_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.component_details_for_module_manager);

        // We display the Component Name at the top
        ListView listViewComponentName = findViewById(R.id.component_name_for_module_manager_in_skills_list);
        String componentName = ModuleManagerActivity.COMPONENT_NAME;
        ArrayList<String> arrayListComponentName = new ArrayList<>();
        arrayListComponentName.add(componentName);
        ArrayAdapter<String> adapterComponentName = new ArrayAdapter<>(this, R.layout.list_view_single_item, R.id.list_view_item_text_view, arrayListComponentName);
        listViewComponentName.setAdapter(adapterComponentName);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_module_manager_page_from_list_of_skills_in_component);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ModuleManagerActivity.class);
                startActivity(intentPreviousPage);
            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_module_manager_in_skills_list_in_component_details);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We modify the text of the TextViews
        databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
        Component currentComponent = new Component();
        for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
            if (Objects.equals(allComponentsInDB.get(componentIndex).getName(), componentName)) {
                currentComponent.setId(allComponentsInDB.get(componentIndex).getId());
                currentComponent.setName(allComponentsInDB.get(componentIndex).getName());
                currentComponent.setComponentManagerId(allComponentsInDB.get(componentIndex).getComponentManagerId());
            }
        }
        ArrayList<ComponentManager> allComponentManagersInDB = databaseManager.getAllComponentManagers();
        int adminId = 0;
        for (int componentManagerIndex = 0; componentManagerIndex < allComponentManagersInDB.size(); componentManagerIndex++) {
            if (allComponentManagersInDB.get(componentManagerIndex).getId() == currentComponent.getComponentManagerId()) {
                adminId = allComponentManagersInDB.get(componentManagerIndex).getAdminId();
            }
        }
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        String componentManagerName = "";
        for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
            if (allAdminsInDB.get(adminIndex).getId() == adminId) {
                componentManagerName = allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName();
            }
        }

        TextView textViewComponentName = findViewById(R.id.component_name_text_view_in_component_details);
        textViewComponentName.setText(currentComponent.getName());

        TextView textViewComponentManager = findViewById(R.id.component_manager_text_view_in_component_details);
        textViewComponentManager.setText(componentManagerName);

        // We create the Modify Component Button Activity
        Button modifyComponentButton = findViewById(R.id.modify_component_details_for_module_manager);
        modifyComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModifyComponent = new Intent(getApplicationContext(), ModifyComponent.class);
                startActivity(intentModifyComponent);
            }
        });

        // We create the Delete Component Activity Button
        Button deleteComponentButton = findViewById(R.id.delete_component_for_module_manager);
        deleteComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDeleteComponent = new Intent(getApplicationContext(), DeleteComponent.class);
                startActivity(intentDeleteComponent);
            }
        });

        // We display the list of Skills in the Component
        ListView listViewSkillInComponent = findViewById(R.id.list_of_skills_in_component_details);
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        ArrayList<String> skillNamesInComponent = new ArrayList<>();
        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
            if (allSkillsInDB.get(skillIndex).getComponentId() == currentComponent.getId()) {
                skillNamesInComponent.add(allSkillsInDB.get(skillIndex).getTitle());
            }
        }
        ArrayAdapter<String> adapterSkillsInComponent = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, skillNamesInComponent);
        listViewSkillInComponent.setAdapter(adapterSkillsInComponent);

        // We display the list of Skills depending of the user input in the Search bar
        EditText searchBarSkills = findViewById(R.id.search_bar_for_skills_in_component_details);
        searchBarSkills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> skillsTitleDuringSearch = new ArrayList<>();
                for (int skillIndex = 0; skillIndex < skillNamesInComponent.size(); skillIndex++) {
                    if (skillNamesInComponent.get(skillIndex).toUpperCase().contains(s.toString().toUpperCase())) {
                        skillsTitleDuringSearch.add(skillNamesInComponent.get(skillIndex));
                    }
                }
                ArrayAdapter<String> adapterSkillsNameDuringSearch = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_single_item, R.id.list_view_item_text_view, skillsTitleDuringSearch);
                listViewSkillInComponent.setAdapter(adapterSkillsNameDuringSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // We create the activity when th user clicks on a Skill
        listViewSkillInComponent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SKILL_NAME = parent.getItemAtPosition(position).toString().trim();
                Intent intentSkillDetails = new Intent(getApplicationContext(), SkillDetailsForModuleManager.class);
                startActivity(intentSkillDetails);
            }
        });

        // We create the Add Skill Button Activity
        Button buttonAddSkill = findViewById(R.id.add_skills_button);
        buttonAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddSkill = new Intent(getApplicationContext(), AddSkill.class);
                startActivity(intentAddSkill);
            }
        });
    }
}