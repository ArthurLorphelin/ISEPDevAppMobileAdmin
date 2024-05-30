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
import com.example.isepdevappmobileadmin.activity.SignIn;
import com.example.isepdevappmobileadmin.classes.DBtable.Component;
import com.example.isepdevappmobileadmin.classes.DBtable.ComponentScore;
import com.example.isepdevappmobileadmin.classes.DBtable.Student;
import com.example.isepdevappmobileadmin.classes.DatabaseManager;

import java.util.ArrayList;

public class StudentAndComponentScoreAdapter extends ArrayAdapter<Student> {
    private ArrayList<Student> students;
    private DatabaseManager databaseManager;

    public StudentAndComponentScoreAdapter(Context context, ArrayList<Student> students) {
        super(context, 0, students);
        this.students = students;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_multiple_items, parent, false);
        }

        // We display in the first part : the name of the Student
        TextView textViewFirstItem = convertView.findViewById(R.id.list_view_first_item_text_view);
        Student currentStudent = getItem(position);
        if (currentStudent != null) {
            textViewFirstItem.setText(currentStudent.getFirstName() + " " + currentStudent.getLastName());
        }

        // We recover the componentId of the current Component Manager
        ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
        int componentId = 0;
        for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
            if (allComponentsInDB.get(componentIndex).getComponentManagerId() == SignIn.ROLE_ID) {
                componentId = allComponentsInDB.get(componentId).getId();
            }
        }

        // We display the second part : the ComponentScore of the Student
        TextView textViewSecondItem = convertView.findViewById(R.id.list_view_second_item_text_view);
        ArrayList<ComponentScore> allComponentScoresInDb = databaseManager.getAllComponentScores();
        for (int componentScoreIndex = 0; componentScoreIndex < allComponentScoresInDb.size(); componentScoreIndex++) {
            assert currentStudent != null;
            if (allComponentScoresInDb.get(componentScoreIndex).getStudentId() == currentStudent.getId()) {
                if (allComponentScoresInDb.get(componentScoreIndex).getComponentId() == componentId) {
                    textViewSecondItem.setText(String.valueOf(allComponentScoresInDb.get(componentScoreIndex).getScore()));
                }
            }
        }


        return convertView;
    }
}
