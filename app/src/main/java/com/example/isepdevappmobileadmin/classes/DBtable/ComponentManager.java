package com.example.isepdevappmobileadmin.classes.DBtable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ComponentManager implements Parcelable {
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

    protected ComponentManager(Parcel in) {
        id = in.readInt();
        adminId = in.readInt();
    }

    public ComponentManager() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(adminId);
    }

    public static final Creator<ComponentManager> CREATOR = new Creator<ComponentManager>() {
        @Override
        public ComponentManager createFromParcel(Parcel in) {
            return new ComponentManager(in);
        }

        @Override
        public ComponentManager[] newArray(int size) {
            return new ComponentManager[size];
        }
    };
}
