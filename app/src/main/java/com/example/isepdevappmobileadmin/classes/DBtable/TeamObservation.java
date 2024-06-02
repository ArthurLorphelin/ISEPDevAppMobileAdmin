package com.example.isepdevappmobileadmin.classes.DBtable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TeamObservation implements Parcelable {
    private int id;
    private int skillId;
    private int teamId;
    private String observation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    protected TeamObservation(Parcel in) {
    }

    public TeamObservation() {
    }

    public static final Creator<TeamObservation> CREATOR = new Creator<TeamObservation>() {
        @Override
        public TeamObservation createFromParcel(Parcel in) {
            return new TeamObservation(in);
        }

        @Override
        public TeamObservation[] newArray(int size) {
            return new TeamObservation[size];
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
