package com.inferrix.lightsmart.DatabaseModule;

public class DatabaseConstant {
    //information of database
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "NimbusLit.db";          /// Database Name


    // User table name
    public static final String TABLE_USER = "user";
    // User Table Columns names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";

    /******************** SINGLE DEVICE COLUMN KEYS  ****************/

    public static final String ADD_DEVICE_TABLE = "AddDeviceTable";         ////Device List Table
    public static final String GROUP_TABLE_NAME = "GroupTable";             ////Group Table
    public static final String GROUP_TABLE_SITE_NAME = "GroupSiteTable";
    public static final String COLUMN_ID = "KEY_ID";                        ///Table column
//    public static final String COLUMN_TIMESTAMP = "TIMESTAMP";

    public static final String COLUMN_DEVICE_ID = "DEVICE_ID";             ////Device IDc
    public static final String COLUMN_DEVICE_UID = "DEVICE_UID";             ////Device UIDc
    public static final String COLUMN_DEVICE_HEXUID = "DEVICE_HEXUID";
    public static final String COLUMN_DEVICE_STATUS = "DEVICE_STATUS";       ////Device Status (ON/OFF)
    public static final String COLUMN_DEVICE_MASTER_STATUS = "MASTER_STATUS";       ////Master Status (1/0)
    public static final String COLUMN_DEVICE_NAME = "DEVICE_NAME";            ////Device Name
    public static final String COLUMN_DEVICE_NUMBER_ONE = "DEVICE_NUMBER_ONE";
    public static final String COLUMN_DEVICE_NUMBER_TWO = "DEVICE_NUMBER_TWO";
    public static final String COLUMN_DEVICE_NUMBER_THREE = "DEVICE_NUMBER_THREE";
    public static final String COLUMN_DEVICE_NUMBER_FOUR = "DEVICE_NUMBER_FOUR";
    public static final String COLUMN_DEVICE_NUMBER_FIVE = "DEVICE_NUMBER_FIVE";
    public static final String COLUMN_DEVICE_NUMBER_SIX= "DEVICE_NUMBER_SIX";
    public static final String COLUMN_DEVICE_NUMBER_SEVEN = "DEVICE_NUMBER_SEVEN";
    public static final String COLUMN_DEVICE_NUMBER_EIGET = "DEVICE_NUMBER_EIGET";

    public static final String COLUMN_DEVICE_ITEM_ONE = "DEVICE_ITEM_ONE";
    public static final String COLUMN_DEVICE_ITEM_TWO = "DEVICE_ITEM_TWO";
    public static final String COLUMN_DEVICE_ITEM_THREE = "DEVICE_ITEM_THREE";
    public static final String COLUMN_DEVICE_ITEM_FOUR = "DEVICE_ITEM_FOUR";
    public static final String COLUMN_DEVICE_ITEM_FIVE = "DEVICE_ITEM_FIVE";
    public static final String COLUMN_DEVICE_ITEM_SIX = "DEVICE_ITEM_SIX";
    public static final String COLUMN_DEVICE_ITEM_SEVEN = "DEVICE_ITEM_SEVEN";
    public static final String COLUMN_DEVICE_ITEM_EIGET = "DEVICE_ITEM_EIGET";
    public static final String COLUMN_DEVICE_TYPE_CODE = "DEVICE_TYPE_CODE";
    public static final String COLUMN_DEVICE_MAC_ADDRESSS = "DEVICE_MAC_ADDRESS";
    public static final String COLUMN_DEVICE_LUX_LEVEL_ONE = "DEVICE_LUX_LEVEL_ONE";
    public static final String COLUMN_DEVICE_LUX_LEVEL_TWO = "DEVICE_LUX_LEVEL_TWO";
    public static final String COLUMN_DEVICE_LUX_LEVEL_THREE = "DEVICE_LUX_LEVEL_THREE";
    public static final String COLUMN_DEVICE_LUX_LEVEL_FOUR= "DEVICE_LUX_LEVEL_FOUR";
    public static final String COLUMN_DEVICE_LUX_LEVEL_FIVE= "DEVICE_LUX_LEVEL_FIVE";
    public static final String COLUMN_DEVICE_DIMMING_LEVEL_ONE = "DEVICE_DIMMING_LEVEL_ONE";
    public static final String COLUMN_DEVICE_DIMMING_LEVEL_TWO = "DEVICE_DIMMING_LEVEL_TWO";
    public static final String COLUMN_DEVICE_DIMMING_LEVEL_THREE = "DEVICE_DIMMING_LEVEL_THREE";
    public static final String COLUMN_DEVICE_DIMMING_LEVEL_Four= "DEVICE_DIMMING_LEVEL_FOUR";
    public static final String COLUMN_DEVICE_GROUP_TYPE_ONE = "DEVICE_GROUP_TYPE_ONE";
    public static final String COLUMN_DEVICE_GROUP_TYPE_TWO = "DEVICE_GROUP_TYPE_TWO";
    public static final String COLUMN_DEVICE_GROUP_TYPE_THREE = "DEVICE_GROUP_TYPE_THREE";
    public static final String COLUMN_DEVICE_GROUP_TYPE_FOUR = "DEVICE_GROUP_TYPE_FOUR";
    public static final String COLUMN_DEVICE_GROUP_TYPE_FIVE = "DEVICE_GROUP_TYPE_FIVE";
    public static final String COLUMN_DEVICE_GROUP_TYPE_SIX = "DEVICE_GROUP_TYPE_SIX";
    public static final String COLUMN_DEVICE_GROUP_TYPE_SEVEN = "DEVICE_GROUP_TYPE_SEVEN";
    public static final String COLUMN_DEVICE_GROUP_TYPE_EIGHT = "DEVICE_GROUP_TYPE_EIGHT";

//    public static final String COLUMN_DEVICE_TAG_NUMBER = "DEVICE_TAG";
    public static final String COLUMN_DEVICE_PROGRESS = "DEVICE_PROGRESS";   //// Device dim progress


    /************************** GROUP COLUMN KEYS ***********************************/
    public static final String COLUMN_GROUP_ID = "GROUP_ID";                 //// Gorup Id
    public static final String COLUMN_GROUP_NAME = "GROUP_NAME";             ////Group Name
    public static final String COLUMN_DERIVE_TYPE = "DERIVE_TYPE";             ////Group Name
    public static final String COLUMN_GROUP_PROGRESS = "GROUP_PROGRESS";     ////Group Progress
    public static final String COLUMN_GROUP_STATUS = "GROUP_STATUS";         ////Group Status(ON/OFF)


    public static final String COLUMN_GROUP_SITE_ID = "GROUP_SITE_ID";                 //// Gorup Id
    public static final String COLUMN_GROUP_DEVICE_SITENAME = "DEVICE_GROUP_SITENAME";
    public static final String COLUMN_SITE_GROUP_DERIVE_TYPE = "SITE_DERIVE_TYPE";             ////Group Name
    public static final String COLUMN_SITE_GROUP_PROGRESS = "SITE_GROUP_PROGRESS";     ////Group Progress
    public static final String COLUMN_GROUP_SITESTATUS = "GROUP_SITESTATUS";         ////Group Status(ON/OFF)


    public static final String GROUP_TABLE_BUILDING_NAME = "GroupBuildingTable";
    public static final String COLUMN_GROUP_BUILDINGID = "GROUP_BUILDINGID";                 //// Gorup Id
    public static final String COLUMN_GROUP_DEVICE_BUILDINGNAME = "GROUP_DEVICE_BUILDINGNAME";
    public static final String COLUMN_BUILDING_GROUP_DERIVE_TYPE = "BUILDING_GROUP_DERIVE_TYPE";             ////Group Name
    public static final String COLUMN_BUILDING_GROUP_PROGRESS = "BUILDING_GROUP_PROGRESS";     ////Group Progress
    public static final String COLUMN_GROUP_BUILDINGSTATUS = "GROUP_BUILDINGSTATUS";


    public static final String GROUP_TABLE_LEVEL_NAME = "GroupLevelTable";
    public static final String COLUMN_GROUP_LEVELID = "GROUP_LEVELID";                 //// Gorup Id
    public static final String COLUMN_GROUP_DEVICE_LEVELNAME = "GROUP_DEVICE_LEVELNAME";
    public static final String COLUMN_LEVEL_GROUP_DERIVE_TYPE = "LEVEL_GROUP_DERIVE_TYPE";             ////Group Name
    public static final String COLUMN_LEVEL_GROUP_PROGRESS = "LEVEL_GROUP_PROGRESS";     ////Group Progress
    public static final String COLUMN_GROUP_LEVELSTATUS = "GROUP_LEVELSTATUS";         ////Group Status(ON/OFF)


    public static final String GROUP_TABLE_ROOM_NAME = "GroupRoomTable";
    public static final String COLUMN_GROUP_ROOMID = "GROUP_ROOMID";                 //// Gorup Id
    public static final String COLUMN_GROUP_DEVICE_ROOMNAME = "DEVICE_GROUP_ROOMNAME";
    public static final String COLUMN_GROUP_DERIVE_TYPE = "DERIVE_TYPE";             ////Group Name
    public static final String COLUMN_ROOM_GROUP_PROGRESS = "GROUP_PROGRESS";     ////Group Progress
    public static final String COLUMN_GROUP_ROOMSTATUS = "GROUP_STATUS";         ////Group Status(ON/OFF)


    public static final String COLUMN_GROUP_DEVICE_LIST = "GroupDeviceList";

    // create table sql query
    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    /**** Creating Single Device Table ****/

    //+COLUMN_DEVICE_ID +"INTEGER PRIMARY KEY AUTOINCREMENT ,"
    public static final String CREATE_DEVICE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ADD_DEVICE_TABLE + "(" +
            COLUMN_DEVICE_UID + " INTEGER PRIMARY KEY ," +
            COLUMN_DEVICE_STATUS + " INTEGER DEFAULT 1 ," +
            COLUMN_DEVICE_MASTER_STATUS + " INTEGER DEFAULT 0 ," +
            COLUMN_DEVICE_PROGRESS + " INTEGER DEFAULT 100 ," +
            COLUMN_DEVICE_NAME + " TEXT ," +
            COLUMN_DEVICE_HEXUID + " TEXT ," +
            COLUMN_DEVICE_NUMBER_ONE + " TEXT ," +
            COLUMN_DEVICE_NUMBER_TWO + " TEXT ," +
            COLUMN_DEVICE_NUMBER_THREE + " TEXT ," +
            COLUMN_DEVICE_NUMBER_FOUR + " TEXT ," +
            COLUMN_DEVICE_NUMBER_FIVE + " TEXT ," +
            COLUMN_DEVICE_NUMBER_SIX + " TEXT ," +
            COLUMN_DEVICE_NUMBER_SEVEN + " TEXT ," +
            COLUMN_DEVICE_NUMBER_EIGET + " TEXT ," +

            COLUMN_DEVICE_ITEM_ONE + " TEXT ," +
            COLUMN_DEVICE_ITEM_TWO + " TEXT ," +
            COLUMN_DEVICE_ITEM_THREE + " TEXT ," +
            COLUMN_DEVICE_ITEM_FOUR + " TEXT ," +
            COLUMN_DEVICE_ITEM_FIVE + " TEXT ," +
            COLUMN_DEVICE_ITEM_SIX + " TEXT ," +
            COLUMN_DEVICE_ITEM_SEVEN + " TEXT ," +
            COLUMN_DEVICE_ITEM_EIGET + " TEXT ," +
            COLUMN_DEVICE_TYPE_CODE + " TEXT ," +
            COLUMN_DEVICE_MAC_ADDRESSS + " TEXT ," +
            COLUMN_DEVICE_LUX_LEVEL_ONE + " TEXT ," +
            COLUMN_DEVICE_LUX_LEVEL_TWO + " TEXT ," +
            COLUMN_DEVICE_LUX_LEVEL_THREE + " TEXT ," +
            COLUMN_DEVICE_LUX_LEVEL_FOUR+ " TEXT ," +
            COLUMN_DEVICE_LUX_LEVEL_FIVE + " TEXT ," +
            COLUMN_DEVICE_DIMMING_LEVEL_ONE + " TEXT ," +
            COLUMN_DEVICE_DIMMING_LEVEL_TWO + " TEXT ," +
            COLUMN_DEVICE_DIMMING_LEVEL_THREE + " TEXT ," +
            COLUMN_DEVICE_DIMMING_LEVEL_Four + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_ONE + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_TWO + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_THREE + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_FOUR + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_FIVE + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_SIX + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_SEVEN + " TEXT ," +
            COLUMN_DEVICE_GROUP_TYPE_EIGHT+ " TEXT ," +
            COLUMN_DERIVE_TYPE + " TEXT ," +
            COLUMN_GROUP_SITE_ID + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_BUILDINGID + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_LEVELID + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_ROOMID + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_ID + " INTEGER DEFAULT 0 " + ")";

    /**** Delete Table ****/
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS ";

    /**** Creating Group Table ****/
    public static final String CREATE_GROUP_TABLE = "CREATE TABLE IF NOT EXISTS " +
            GROUP_TABLE_NAME + "(" +
            COLUMN_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_GROUP_NAME + " TEXT , " +
            COLUMN_DERIVE_TYPE + " TEXT , " +
            COLUMN_GROUP_PROGRESS + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_STATUS + " INTEGER DEFAULT 0 " + ")";


    /**** Creating Group Site Table ****/
    public static final String CREATE_GROUP_SITE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            GROUP_TABLE_SITE_NAME + "(" +
            COLUMN_GROUP_SITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_GROUP_DEVICE_SITENAME + " TEXT ," +
            COLUMN_SITE_GROUP_DERIVE_TYPE + " TEXT , " +
            COLUMN_SITE_GROUP_PROGRESS + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_SITESTATUS + " INTEGER DEFAULT 0 " + ")";


    /**** Creating Group Building Table ****/
    public static final String CREATE_GROUP_BUILDING_TABLE = "CREATE TABLE IF NOT EXISTS " +
            GROUP_TABLE_BUILDING_NAME + "(" + COLUMN_GROUP_BUILDINGID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_GROUP_DEVICE_BUILDINGNAME + " TEXT ," +
            COLUMN_BUILDING_GROUP_DERIVE_TYPE + " TEXT , " +
            COLUMN_BUILDING_GROUP_PROGRESS + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_BUILDINGSTATUS + " INTEGER DEFAULT 0 " + ")";

    /**** Creating Group Level Table ****/
    public static final String CREATE_GROUP_LEVEL_TABLE = "CREATE TABLE IF NOT EXISTS " +
            GROUP_TABLE_LEVEL_NAME + "(" + COLUMN_GROUP_LEVELID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_GROUP_DEVICE_LEVELNAME + " TEXT ," +
            COLUMN_LEVEL_GROUP_DERIVE_TYPE + " TEXT , " +
            COLUMN_LEVEL_GROUP_PROGRESS + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_LEVELSTATUS + " INTEGER DEFAULT 0 " + ")";

    /**** Creating Group Room Table ****/
    public static final String CREATE_GROUP_ROOM_TABLE = "CREATE TABLE IF NOT EXISTS " +
            GROUP_TABLE_ROOM_NAME + "(" + COLUMN_GROUP_ROOMID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_GROUP_DEVICE_ROOMNAME + " TEXT ," +
            COLUMN_GROUP_DERIVE_TYPE + " TEXT , " +
            COLUMN_ROOM_GROUP_PROGRESS + " INTEGER DEFAULT 0 ," +
            COLUMN_GROUP_ROOMSTATUS + " INTEGER DEFAULT 0 " + ")";
}
