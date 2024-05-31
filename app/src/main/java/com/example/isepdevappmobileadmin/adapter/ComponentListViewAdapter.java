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
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class ComponentListViewAdapter extends ArrayAdapter<ComponentScore> {
    private ArrayList<ComponentScore> componentScores;
    private DatabaseManager databaseManager;

    public ComponentListViewAdapter(Context context, ArrayList<ComponentScore> componentScores) {
        super(context, 0, componentScores);
        this.componentScores = componentScores;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_in_list_view, parent, false);
        }

        // We display the name of the Component in the TextView
        TextView textViewComponentName = convertView.findViewById(R.id.component_name_in_list_view_in_list_view_text_view);
        ComponentScore currentComponentScore = getItem(position);
        String componentName = "";
        if (currentComponentScore != null) {
            ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
            for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                if (allComponentsInDB.get(componentIndex).getId() == currentComponentScore.getComponentId()) {
                    componentName = allComponentsInDB.get(componentIndex).getName();
                }
            }
        }
        textViewComponentName.setText(componentName);

        return convertView;
    }
}
