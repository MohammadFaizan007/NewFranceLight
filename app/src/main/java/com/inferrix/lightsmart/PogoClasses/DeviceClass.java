package com.inferrix.lightsmart.PogoClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.inferrix.lightsmart.constant.Constants;


public class DeviceClass implements Parcelable {
    int deviceId=0;
    long DeviceUID=0;
    String devicehexUid="";
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

    String uidNameOne="";
    String uidNameTwo="";
    String uidNameThree="";
    String uidNameFour="";
    String uidNameFive="";
    String uidNameSix="";
    String uidNameSeven="";
    String uidNameEight="";


    String groupTypeOne="";
    String groupTypeTwo="";
    String groupTypeThree="";
    String groupTypeFour="";
    String groupTypeFive="";
    String groupTypeSix="";
    String groupTypeSeven="";
    String groupTypeEight="";
    String typeCode="";
    String macAddress="";
    String luxLevelOne="";
    String luxLevelTwo="";
    String luxLevelThree="";
    String luxLevelFour="";
    String luxLevelFive="";
    String dimmingLevelOne="";
    String dimmingLevelTwo="";
    String dimmingLevelThre="";
    String dimmingLevelFour="";
    int groupId=0;
    int groupSiteId=0;
    int groupBuildingId=0;
    int groupLevelId=0;
    int groupRoomId=0;
    int masterStatus=0;
    int deviceDimming=0;
    boolean status=true;

    public String getDevicehexUid() {
        return devicehexUid;
    }

    public void setDevicehexUid(String devicehexUid) {
        this.devicehexUid = devicehexUid;
    }

    public int getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

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

    public int getGroupSiteId() {
        return groupSiteId;
    }

    public void setGroupSiteId(int groupSiteId) {
        this.groupSiteId = groupSiteId;
    }
    public int getGroupBuildingId() {
        return groupBuildingId;
    }

    public void setGroupBuildingId(int groupBuildingId) {
        this.groupBuildingId = groupBuildingId;
    }
    public int getGroupLevelId() {
        return groupLevelId;
    }

    public void setGroupLevelId(int groupLevelId) {
        this.groupLevelId = groupLevelId;
    }
    public int getGroupRoomId() {
        return groupRoomId;
    }

    public void setGroupRoomId(int groupRoomId) {
        this.groupRoomId = groupRoomId;
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

    public void setGroupTypeOne(String groupTypeOne) {
        this.groupTypeOne = groupTypeOne;
    }

    public String getGroupTypeOne() {
        return groupTypeOne;
    }

    public void setGroupTypeTwo(String groupTypeTwo) {
        this.groupTypeTwo = groupTypeTwo;
    }

    public String getGroupTypeTwo() {
        return groupTypeTwo;
    }

    public void setGroupTypeThree(String groupTypeThree) {
        this.groupTypeThree = groupTypeThree;
    }

    public String getGroupTypeThree() {
        return groupTypeThree;
    }

    public void setGroupTypeFour(String groupTypeFour) {
        this.groupTypeFour = groupTypeFour;
    }

    public String getGroupTypeFour() {
        return groupTypeFour;
    }

    public void setGroupTypeFive(String groupTypeFive) {
        this.groupTypeFive = groupTypeFive;
    }

    public String getGroupTypeFive() {
        return groupTypeFive;
    }

    public void setGroupTypeSix(String groupTypeSix) {
        this.groupTypeSix = groupTypeSix;
    }

    public String getGroupTypeSix() {
        return groupTypeSix;
    }

    public void setGroupTypeSeven(String groupTypeSeven) {
        this.groupTypeSeven = groupTypeSeven;
    }

    public String getGroupTypeSeven() {
        return groupTypeSeven;
    }
    public void setGroupTypeEight(String groupTypeEight) {
        this.groupTypeEight = groupTypeEight;
    }

    public String getGroupTypeEight() {
        return groupTypeEight;
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

    public void setLuxLevelOne(String luxLevelOne) {
        this.luxLevelOne = luxLevelOne;
    }

    public String getLuxLevelOne() {
        return luxLevelOne;
    }
    public void setLuxLevelTwo(String luxLevelTwo) {
        this.luxLevelTwo = luxLevelTwo;
    }

    public String getLuxLevelTwo() {
        return luxLevelTwo;
    }
    public void setLuxLevelThree(String luxLevelThree) {
        this.luxLevelThree = luxLevelThree;
    }

    public String getLuxLevelThree() {
        return luxLevelThree;
    }
    public void setLuxLevelFour(String luxLevelFour) {
        this.luxLevelFour = luxLevelFour;
    }

    public String getLuxLevelFour() {
        return luxLevelFour;
    }
    public void setLuxLevelFive(String luxLevelFive) {
        this.luxLevelFive = luxLevelFive;
    }

    public String getLuxLevelFive() {
        return luxLevelFive;
    }
    public void setDimmingLevelOne(String dimmingLevelOne) {
        this.dimmingLevelOne = dimmingLevelOne;
    }

    public String getDimmingLevelOne() {
        return dimmingLevelOne;
    }
    public void setDimmingLevelTwo(String dimmingLevelTwo) {
        this.dimmingLevelTwo = dimmingLevelTwo;
    }

    public String getDimmingLevelTwo() {
        return dimmingLevelTwo;
    }

    public void setDimmingLevelThre(String dimmingLevelThre) {
        this.dimmingLevelThre = dimmingLevelThre;
    }

    public String getDimmingLevelThre() {
        return dimmingLevelThre;
    }

    public void setDimmingLevelFour(String dimmingLevelFour) {
        this.dimmingLevelFour = dimmingLevelFour;
    }

    public String getDimmingLevelFour() {
        return dimmingLevelFour;
    }

    public void setUidNameOne(String uidNameOne) {
        this.uidNameOne = uidNameOne;
    }

    public String getUidNameOne() {
        return uidNameOne;
    }
    public void setUidNameTwo(String uidNameTwo) {
        this.uidNameTwo = uidNameTwo;
    }

    public String getUidNameTwo() {
        return uidNameTwo;
    }
    public void setUidNameThree(String uidNameThree) {
        this.uidNameThree = uidNameThree;
    }

    public String getUidNameThree() {
        return uidNameThree;
    }
    public void setUidNameFour(String uidNameFour) {
        this.uidNameFour = uidNameFour;
    }

    public String getUidNameFour() {
        return uidNameFour;
    }
    public void setUidNameFive(String uidNameFive) {
        this.uidNameFive = uidNameFive;
    }

    public String getUidNameFive() {
        return uidNameFive;
    }
    public void setUidNameSix(String uidNameSix) {
        this.uidNameSix = uidNameSix;
    }

    public String getUidNameSix() {
        return uidNameSix;
    }

    public void setUidNameSeven(String uidNameSeven) {
        this.uidNameSeven = uidNameSeven;
    }

    public String getUidNameSeven() {
        return uidNameSeven;
    }

    public void setUidNameEight(String uidNameEight) {
        this.uidNameEight = uidNameEight;
    }

    public String getUidNameEight() {
        return uidNameEight;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.deviceId );
        dest.writeLong(this.DeviceUID);
        dest.writeString(this.deviceName);
        dest.writeString(this.devicehexUid);
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
        dest.writeString(this.groupTypeOne);
        dest.writeString(this.groupTypeTwo);
        dest.writeString(this.groupTypeThree);
        dest.writeString(this.groupTypeFour);
        dest.writeString(this.groupTypeFive);
        dest.writeString(this.groupTypeSix);
        dest.writeString(this.groupTypeSeven);
        dest.writeString(this.groupTypeEight);
        dest.writeString(this.typeCode);
        dest.writeString(this.macAddress);
        dest.writeString(this.luxLevelOne);
        dest.writeString(this.luxLevelTwo);
        dest.writeString(this.luxLevelThree);
        dest.writeString(this.luxLevelFour);
        dest.writeString(this.luxLevelFive);
        dest.writeString(this.dimmingLevelOne);
        dest.writeString(this.dimmingLevelTwo);
        dest.writeString(this.dimmingLevelThre);
        dest.writeString(this.dimmingLevelFour);
        dest.writeString(this.uidNameOne);
        dest.writeString(this.uidNameTwo);
        dest.writeString(this.uidNameThree);
        dest.writeString(this.uidNameFour);
        dest.writeString(this.uidNameFive);
        dest.writeString(this.uidNameSix);
        dest.writeString(this.uidNameSeven);
        dest.writeString(this.uidNameEight);
        dest.writeInt(this.groupId);
        dest.writeInt(this.groupSiteId);
        dest.writeInt(this.groupBuildingId);
        dest.writeInt(this.groupLevelId);
        dest.writeInt(this.groupRoomId);
        dest.writeInt(this.deviceDimming);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    public DeviceClass() {
    }

    protected DeviceClass(Parcel in) {

        this.deviceId = in.readInt();
        this.DeviceUID = in.readLong();
        this.deviceName = in.readString();
        this.devicehexUid = in.readString();
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
        this.groupTypeOne = in.readString();
        this.groupTypeTwo = in.readString();
        this.groupTypeThree = in.readString();
        this.groupTypeFour = in.readString();
        this.groupTypeFive = in.readString();
        this.groupTypeSix = in.readString();
        this.groupTypeSeven = in.readString();
        this.groupTypeEight = in.readString();
        this.typeCode = in.readString();
        this.macAddress = in.readString();
        this.luxLevelOne = in.readString();
        this.luxLevelTwo = in.readString();
        this.luxLevelThree = in.readString();
        this.luxLevelFour = in.readString();
        this.luxLevelFive = in.readString();
        this.dimmingLevelOne = in.readString();
        this.dimmingLevelTwo = in.readString();
        this.dimmingLevelThre = in.readString();
        this.dimmingLevelFour = in.readString();

        this.uidNameOne = in.readString();
        this.uidNameTwo = in.readString();
        this.uidNameThree = in.readString();
        this.uidNameFour = in.readString();
        this.uidNameFive = in.readString();
        this.uidNameSix = in.readString();
        this.uidNameSeven = in.readString();
        this.uidNameEight = in.readString();

        this.groupId = in.readInt();
        this.groupSiteId = in.readInt();
        this.groupBuildingId = in.readInt();
        this.groupLevelId = in.readInt();
        this.groupRoomId = in.readInt();
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
