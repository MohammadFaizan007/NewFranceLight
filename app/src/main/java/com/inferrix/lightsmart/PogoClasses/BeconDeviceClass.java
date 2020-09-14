package com.inferrix.lightsmart.PogoClasses;

public class BeconDeviceClass {
    String deviceUid="";
    String TypeCode="";
    String deviceName="";
    long beaconUID=0;
    int deriveType=0;
    int masterStatus=0;
    boolean isAdded=false;
    boolean status=true;

    String details_uid = "";
    String details_groupId = "";
    String deviceMacAddress="";
    String iBeaconUuid = "";


    public String getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(String TypeCode) {
        this.TypeCode = TypeCode;
    }

    public String getDetails_groupId() {
        return details_groupId;
    }

    public void setDetails_groupId(String details_groupId) {
        this.details_groupId = details_groupId;
    }

    public String getDetails_uid() {
        return details_uid;
    }

    public void setDetails_uid(String details_uid) {
        this.details_uid = details_uid;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setDeriveType(int deriveType) {
        this.deriveType = deriveType;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public int getDeriveType() {
        return deriveType;
    }

    public void setMasterStatus(int masterStatus) {
        this.masterStatus = masterStatus;
    }

    public int getMasterStatus() {
        return masterStatus;
    }

    public void setBeaconUID(long beaconUID) {
        this.beaconUID = beaconUID;
    }

    public long getBeaconUID() {
        return beaconUID;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public String getiBeaconUuid() {
        return iBeaconUuid;
    }

    public void setiBeaconUuid(String iBeaconUuid) {
        this.iBeaconUuid = iBeaconUuid;
    }

}
