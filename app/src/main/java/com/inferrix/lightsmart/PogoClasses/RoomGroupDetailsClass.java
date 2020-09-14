package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomGroupDetailsClass implements Parcelable {
    String groupRoomName="";
    int groupRoomId=0;
    boolean groupRoomStatus=false;
    int groupRoomDimming=0;

    public int getRoomGroupId() {
        return groupRoomId;
    }

    public String getGroupRoomName() {
        return groupRoomName;
    }

    public void setGroupDimming(int groupRoomDimming) {
        this.groupRoomDimming = groupRoomDimming;
    }

    public int getGroupDimming() {
        return groupRoomDimming;
    }

    public boolean getGroupStatus() {
        return groupRoomStatus;
    }

    public void setRoomGroupId(int groupRoomId) {
        this.groupRoomId = groupRoomId;
    }

    public void setGroupRoomName(String groupName) {
        this.groupRoomName = groupName;
    }

    public void setGroupStatus(boolean groupRoomStatus) {
        this.groupRoomStatus = groupRoomStatus;
    }

    public RoomGroupDetailsClass() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupRoomName);
        dest.writeInt(this.groupRoomId);
        dest.writeByte(this.groupRoomStatus ? (byte) 1 : (byte) 0);
        dest.writeInt(this.groupRoomDimming);
    }

    protected RoomGroupDetailsClass(Parcel in) {
        this.groupRoomName = in.readString();
        this.groupRoomId = in.readInt();
        this.groupRoomStatus = in.readByte() != 0;
        this.groupRoomDimming = in.readInt();
    }

    public static final Creator<RoomGroupDetailsClass> CREATOR = new Creator<RoomGroupDetailsClass>() {
        @Override
        public RoomGroupDetailsClass createFromParcel(Parcel source) {
            return new RoomGroupDetailsClass(source);
        }

        @Override
        public RoomGroupDetailsClass[] newArray(int size) {
            return new RoomGroupDetailsClass[size];
        }
    };
}
