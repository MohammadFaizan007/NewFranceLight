package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class BuildingGroupDetailsClass implements Parcelable {
    String groupBuildingName="";
    int groupBuildingId=0;
    boolean groupBuildingStatus=false;
    int groupBuildingDimming=0;

    public int getGroupBuildingId() {
        return groupBuildingId;
    }

    public String getGroupBuildingName() {
        return groupBuildingName;
    }

    public void setBuildingGroupDimming(int groupBuildingDimming) {
        this.groupBuildingDimming = groupBuildingDimming;
    }

    public int getBuildingGroupDimming() {
        return groupBuildingDimming;
    }

    public boolean getBuildingGroupStatus() {
        return groupBuildingStatus;
    }

    public void setGroupBuildingId(int groupBuildingId) {
        this.groupBuildingId = groupBuildingId;
    }

    public void setGroupBuildingName(String groupName) {
        this.groupBuildingName = groupName;
    }

    public void setBuildingGroupStatus(boolean groupBuildingStatus) {
        this.groupBuildingStatus = groupBuildingStatus;
    }

    public BuildingGroupDetailsClass() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupBuildingName);
        dest.writeInt(this.groupBuildingId);
        dest.writeByte(this.groupBuildingStatus ? (byte) 1 : (byte) 0);
        dest.writeInt(this.groupBuildingDimming);
    }

    protected BuildingGroupDetailsClass(Parcel in) {
        this.groupBuildingName = in.readString();
        this.groupBuildingId = in.readInt();
        this.groupBuildingStatus = in.readByte() != 0;
        this.groupBuildingDimming = in.readInt();
    }

    public static final Creator<BuildingGroupDetailsClass> CREATOR = new Creator<BuildingGroupDetailsClass>() {
        @Override
        public BuildingGroupDetailsClass createFromParcel(Parcel source) {
            return new BuildingGroupDetailsClass(source);
        }

        @Override
        public BuildingGroupDetailsClass[] newArray(int size) {
            return new BuildingGroupDetailsClass[size];
        }
    };
}
