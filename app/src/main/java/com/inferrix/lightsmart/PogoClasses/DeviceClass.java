package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.inferrix.lightsmart.constant.Constants;


public class DeviceClass implements Parcelable {

    long DeviceUID=0;
    String deriveType= Constants.PWM;
    String deviceName="";
    String numberOne="";
    String numberTwo="";
    String numberThree="";
    String numberFour="";
    String numberFive="";
    String numberSix="";
    String numberSeven="";
    String numberEight="";
    String itemOne="";
    String itemTwo="";
    String itemThree="";
    String itemFour="";
    String itemFive="";
    String itemSix="";
    String itemSeven="";
    String itemEight="";
    String typeCode="";
    String macAddress="";
    int groupId=0;
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


    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    public String getNumberOne() {
        return numberOne;
    }

    public void setItemOne(String itemOne) {
        this.itemOne = itemOne;
    }

    public String getItemOne() {
        return itemOne;
    }


    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public void setNumberThree(String numberThree) {
        this.numberThree = numberThree;
    }

    public String getNumberThree() {
        return numberThree;
    }

    public void setNumberFour(String numberFour) {
        this.numberFour = numberFour;
    }

    public String getNumberFour() {
        return numberFour;
    }

    public void setNumberFive(String numberFive) {
        this.numberFive = numberFive;
    }

    public String getNumberFive() {
        return numberFive;
    }

    public void setNumberSix(String numberSix) {
        this.numberSix = numberSix;
    }

    public String getNumberSix() {
        return numberSix;
    }

    public void setNumberSeven(String numberSeven) {
        this.numberSeven = numberSeven;
    }

    public String getNumberSeven() {
        return numberSeven;
    }

    public void setNumberEight(String numberEight) {
        this.numberEight = numberEight;
    }

    public String getNumberEight() {
        return numberEight;
    }

    public void setItemTwo(String itemTwo) {
        this.itemTwo = itemTwo;
    }

    public String getItemTwo() {
        return itemTwo;
    }

    public void setItemThree(String itemThree) {
        this.itemThree = itemThree;
    }

    public String getItemThree() {
        return itemThree;
    }

    public void setItemFour(String itemFour) {
        this.itemFour = itemFour;
    }

    public String getItemFour() {
        return itemFour;
    }

    public void setItemFive(String itemFive) {
        this.itemFive = itemFive;
    }

    public String getItemFive() {
        return itemFive;
    }

    public void setItemSix(String itemSix) {
        this.itemSix = itemSix;
    }

    public String getItemSix() {
        return itemSix;
    }

    public void setItemSeven(String itemSeven) {
        this.itemSeven = itemSeven;
    }

    public String getItemSeven() {
        return itemSeven;
    }

    public void setItemEight(String itemEight) {
        this.itemEight = itemEight;
    }

    public String getItemEight() {
        return itemEight;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.DeviceUID);
        dest.writeString(this.deviceName);
        dest.writeString(this.numberOne);
        dest.writeString(this.numberTwo);
        dest.writeString(this.numberThree);
        dest.writeString(this.numberFour);
        dest.writeString(this.numberFive);
        dest.writeString(this.numberSix);
        dest.writeString(this.numberSeven);
        dest.writeString(this.numberEight);
        dest.writeString(this.itemOne);
        dest.writeString(this.itemTwo);
        dest.writeString(this.itemThree);
        dest.writeString(this.itemFour);
        dest.writeString(this.itemFive);
        dest.writeString(this.itemSix);
        dest.writeString(this.itemSeven);
        dest.writeString(this.itemEight);
        dest.writeString(this.typeCode);
        dest.writeString(this.macAddress);
        dest.writeInt(this.groupId);
        dest.writeInt(this.deviceDimming);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    public DeviceClass() {
    }

    protected DeviceClass(Parcel in) {

        this.DeviceUID = in.readLong();
        this.deviceName = in.readString();
        this.numberOne = in.readString();
        this.numberTwo = in.readString();
        this.numberThree = in.readString();
        this.numberFour = in.readString();
        this.numberFive = in.readString();
        this.numberSix = in.readString();
        this.numberSeven = in.readString();
        this.numberEight = in.readString();
        this.itemOne = in.readString();
        this.itemTwo = in.readString();
        this.itemThree = in.readString();
        this.itemFour = in.readString();
        this.itemFive = in.readString();
        this.itemSix = in.readString();
        this.itemSeven = in.readString();
        this.itemEight = in.readString();
        this.typeCode = in.readString();
        this.macAddress = in.readString();
        this.groupId = in.readInt();
        this.deviceDimming = in.readInt();
        this.status = in.readByte() != 0;
    }

    public static final Creator<DeviceClass> CREATOR = new Creator<DeviceClass>() {
        @Override
        public DeviceClass createFromParcel(Parcel source) {
            return new DeviceClass(source);
        }

        @Override
        public DeviceClass[] newArray(int size) {
            return new DeviceClass[size];
        }
    };
}
