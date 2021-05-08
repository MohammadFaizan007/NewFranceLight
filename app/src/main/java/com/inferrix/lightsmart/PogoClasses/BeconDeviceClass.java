package com.inferrix.lightsmart.PogoClasses;

public class BeconDeviceClass {
    int deviceID = 0;
    int fk_userID=0;
    String deviceUid="";
    String TypeCode="";
    String deviceName="";
    long beaconUID=0;
    int deriveType=0;
    int masterStatus=0;
    boolean isAdded=false;
    boolean status=true;
    String devicehexUid="";
    String details_uid = "";
    String details_groupId = "";
    String deviceMacAddress="";
    String iBeaconUuid = "";

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
    int deviceDimming=0;


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

//    fk_userID

    public void setDeriveType(int deriveType) {
        this.deriveType = deriveType;
    }

    public int getDeriveType() {
        return deriveType;
    }

    public void setFk_userID(int fk_userID) {
        this.fk_userID = fk_userID;
    }

    public int getFk_userID() {
        return fk_userID;
    }
    public void setAdded(boolean added) {
        isAdded = added;
    }

    public boolean isAdded() {
        return isAdded;
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

    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    public String getNumberOne() {
        return numberOne;
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

    public void setItemOne(String itemOne) {
        this.itemOne = itemOne;
    }

    public String getItemOne() {
        return itemOne;
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

    public String getDevicehexUid() {
        return devicehexUid;
    }

    public void setDevicehexUid(String devicehexUid) {
        this.devicehexUid = devicehexUid;
    }


    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }


}



