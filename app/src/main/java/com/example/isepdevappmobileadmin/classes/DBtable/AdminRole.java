package com.example.isepdevappmobileadmin.classes.DBtable;

import android.os.Parcel;
import android.os.Parcelable;

public class AdminRole implements Parcelable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected AdminRole(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public AdminRole(){
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminRole> CREATOR = new Creator<AdminRole>() {
        @Override
        public AdminRole createFromParcel(Parcel in) {
            return new AdminRole(in);
        }

        @Override
        public AdminRole[] newArray(int size) {
            return new AdminRole[size];
        }
    };
}
