package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.inferrix.lightsmart.constant.Constants;


public class BandClass implements Parcelable {
    String bandName = "";
    long BandUID = 0;
    int groupId = 0;


    public void setBandeName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandName() {
        return bandName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


    public long getBandUID() {
        return BandUID;
    }

    public void setBandUID(long deviceUID) {
        BandUID = BandUID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.BandUID);
        dest.writeString(this.bandName);
        dest.writeInt(this.groupId);
    }

    public BandClass() {
    }

    protected BandClass(Parcel in) {

        this.BandUID = in.readLong();
        this.bandName = in.readString();
        this.groupId = in.readInt();
    }

    public static final Creator<BandClass> CREATOR = new Creator<BandClass>() {
        @Override
        public BandClass createFromParcel(Parcel source) {
            return new BandClass(source);
        }

        @Override
        public BandClass[] newArray(int size) {
            return new BandClass[size];
        }
    };
}
