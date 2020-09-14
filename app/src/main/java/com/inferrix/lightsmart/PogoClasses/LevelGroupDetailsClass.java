package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class LevelGroupDetailsClass implements Parcelable {
    String groupLevelName="";
    int groupLevelId=0;
    boolean groupLevelStatus=false;
    int groupLevelDimming=0;

    public int getGroupLevelId() {
        return groupLevelId;
    }

    public String getGroupLevelName() {
        return groupLevelName;
    }

    public void setLevelGroupDimming(int groupLevelDimming) {
        this.groupLevelDimming = groupLevelDimming;
    }

    public int getLevelGroupDimming() {
        return groupLevelDimming;
    }

    public boolean getLevelGroupStatus() {
        return groupLevelStatus;
    }

    public void setGroupLevelId(int groupLevelId) {
        this.groupLevelId = groupLevelId;
    }

    public void setGroupLevelName(String groupLevelName) {
        this.groupLevelName = groupLevelName;
    }

    public void setLevelGroupStatus(boolean groupLevelStatus) {
        this.groupLevelStatus = groupLevelStatus;
    }

    public LevelGroupDetailsClass() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupLevelName);
        dest.writeInt(this.groupLevelId);
        dest.writeByte(this.groupLevelStatus ? (byte) 1 : (byte) 0);
        dest.writeInt(this.groupLevelDimming);
    }

    protected LevelGroupDetailsClass(Parcel in) {
        this.groupLevelName = in.readString();
        this.groupLevelId = in.readInt();
        this.groupLevelStatus = in.readByte() != 0;
        this.groupLevelDimming = in.readInt();
    }

    public static final Creator<LevelGroupDetailsClass> CREATOR = new Creator<LevelGroupDetailsClass>() {
        @Override
        public LevelGroupDetailsClass createFromParcel(Parcel source) {
            return new LevelGroupDetailsClass(source);
        }

        @Override
        public LevelGroupDetailsClass[] newArray(int size) {
            return new LevelGroupDetailsClass[size];
        }
    };
}
