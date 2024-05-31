package com.example.isepdevappmobileadmin.classes.DBtable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SkillScore implements Parcelable {
    private int id;
    private int ratingId;
    private int skillId;
    private int componentScoreId;
    private String skillObservation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getComponentScoreId() {
        return componentScoreId;
    }

    public void setComponentScoreId(int componentScoreId) {
        this.componentScoreId = componentScoreId;
    }

    public String getSkillObservation() {
        return skillObservation;
    }

    public void setSkillObservation(String skillObservation) {
        this.skillObservation = skillObservation;
    }

    protected SkillScore(Parcel in) {
    }

    public SkillScore() {
    }

    public static final Creator<SkillScore> CREATOR = new Creator<SkillScore>() {
        @Override
        public SkillScore createFromParcel(Parcel in) {
            return new SkillScore(in);
        }

        @Override
        public SkillScore[] newArray(int size) {
            return new SkillScore[size];
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
