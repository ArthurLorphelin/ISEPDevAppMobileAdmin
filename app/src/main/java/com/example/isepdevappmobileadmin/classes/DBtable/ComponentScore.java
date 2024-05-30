package com.example.isepdevappmobileadmin.classes.DBtable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ComponentScore implements Parcelable {
    private int id;
    private int score;
    private int componentId;
    private int studentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    protected ComponentScore(Parcel in) {
    }

    public ComponentScore() {
    }

    public static final Creator<ComponentScore> CREATOR = new Creator<ComponentScore>() {
        @Override
        public ComponentScore createFromParcel(Parcel in) {
            return new ComponentScore(in);
        }

        @Override
        public ComponentScore[] newArray(int size) {
            return new ComponentScore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
