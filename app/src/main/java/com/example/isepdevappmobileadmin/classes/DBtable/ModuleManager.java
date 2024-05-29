package com.example.isepdevappmobileadmin.classes.DBtable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ModuleManager implements Parcelable {
    // We instantiate the variables of Module Manager
    private int id;
    private int adminId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    protected ModuleManager(Parcel in) {
    }

    public ModuleManager() {
    }

    public static final Creator<ModuleManager> CREATOR = new Creator<ModuleManager>() {
        @Override
        public ModuleManager createFromParcel(Parcel in) {
            return new ModuleManager(in);
        }

        @Override
        public ModuleManager[] newArray(int size) {
            return new ModuleManager[size];
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
