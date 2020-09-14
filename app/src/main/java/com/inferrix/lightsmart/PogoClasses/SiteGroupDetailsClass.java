package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteGroupDetailsClass implements Parcelable {
    String groupSiteName="";
    int groupSiteId=0;
    boolean groupSiteStatus=false;
    int groupSiteDimming=0;

    public int getGroupSiteId() {
        return groupSiteId;
    }

    public void setGroupSiteId(int groupSiteId) {
        this.groupSiteId = groupSiteId;
    }

    public String getGroupSiteName() {
        return groupSiteName;
    }
    public void setGroupSiteName(String groupSiteName) {
        this.groupSiteName = groupSiteName;
    }

    public void setGroupSiteDimming(int groupSiteDimming) {
        this.groupSiteDimming = groupSiteDimming;
    }

    public int getGroupSiteDimming() {
        return groupSiteDimming;
    }

    public boolean getGroupSiteStatus() {
        return groupSiteStatus;
    }

    public void setGroupSiteStatus(boolean groupSiteStatus) {
        this.groupSiteStatus = groupSiteStatus;
    }

    public SiteGroupDetailsClass() {
    }



    protected SiteGroupDetailsClass(Parcel in) {
        this.groupSiteName = in.readString();
        this.groupSiteId = in.readInt();
        this.groupSiteStatus = in.readByte() != 0;
        this.groupSiteDimming = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupSiteName);
        dest.writeInt(this.groupSiteId);
        dest.writeByte(this.groupSiteStatus ? (byte) 1 : (byte) 0);
        dest.writeInt(this.groupSiteDimming);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SiteGroupDetailsClass> CREATOR = new Creator<SiteGroupDetailsClass>() {
        @Override
        public SiteGroupDetailsClass createFromParcel(Parcel in) {
            return new SiteGroupDetailsClass(in);
        }

        @Override
        public SiteGroupDetailsClass[] newArray(int size) {
            return new SiteGroupDetailsClass[size];
        }
    };
}
