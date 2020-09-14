package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.inferrix.lightsmart.constant.Constants;


public class SiteDeviceClass implements Parcelable {

    long DeviceUID=0;
    String deriveType= Constants.PWM;
    String deviceName="";
//    String numberOne="";
//    String numberTwo="";
//    String numberThree="";
//    String numberFour="";
//    String numberFive="";
//    String numberSix="";
//    String numberSeven="";
//    String numberEight="";
//    String itemOne="";
//    String itemTwo="";
//    String itemThree="";
//    String itemFour="";
//    String itemFive="";
//    String itemSix="";
//    String itemSeven="";
//    String itemEight="";
//    String typeCode="";
//    String macAddress="";
    int groupSiteId=0;
    int masterStatus=0;
    int deviceDimming=0;
    boolean status=true;

    public String getDeriveType() {
        return deriveType;
    }

    public void setDeriveType(String deriveType) {
        this.deriveType = deriveType;
    }



    public void setMasterStatus(int masterStatus) {
        this.masterStatus = masterStatus;
    }

    public int getMasterStatus() {
        return masterStatus;
    }




    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getGroupSiteId() {
        return groupSiteId;
    }

    public void setGroupSiteId(int groupId) {
        this.groupSiteId = groupId;
    }

    public int getDeviceDimming() {
        return deviceDimming;
    }

    public void setDeviceDimming(int deviceDimming) {
        this.deviceDimming = deviceDimming;
    }



    public long getDeviceUID() {
        return DeviceUID;
    }

    public void setDeviceUID(long deviceUID) {
        DeviceUID = deviceUID;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.DeviceUID);
        dest.writeString(this.deviceName);
        dest.writeInt(this.groupSiteId);
        dest.writeInt(this.deviceDimming);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    public SiteDeviceClass() {
    }

    protected SiteDeviceClass(Parcel in) {

        this.DeviceUID = in.readLong();
        this.deviceName = in.readString();
        this.groupSiteId = in.readInt();
        this.deviceDimming = in.readInt();
        this.status = in.readByte() != 0;
    }

    public static final Creator<SiteDeviceClass> CREATOR = new Creator<SiteDeviceClass>() {
        @Override
        public SiteDeviceClass createFromParcel(Parcel source) {
            return new SiteDeviceClass(source);
        }

        @Override
        public SiteDeviceClass[] newArray(int size) {
            return new SiteDeviceClass[size];
        }
    };
}
