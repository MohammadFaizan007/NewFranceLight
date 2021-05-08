package com.inferrix.lightsmart.EncodeDecodeModule;

public class TxMethodType {
    public static final int info=0x4f;
    public static final int LIGHT_STATE_RESPONSE=0x50;
    public static final int LIGHT_STATE_COMMAND_RESPONSE=0x51;
    public static final int GROUP_STATE_RESPONSE=0x52;
    public static final int GROUP_STATE_COMMAND_RESPONSE=0x53;
    public static final int GROUP_RESPONSE=0x54;
    public static final int ADD_GROUP_RESPONSE=0x55;
    public static final int REMOVE_GROUP_RESPONSE=0x56;
    public static final int UPDATE_GROUP_RESPONSE=0x57;
    public static final int LIGHT_LEVEL_RESPONSE=0x58;
    public static final int LIGHT_LEVEL_COMMAND_RESPONSE=0x59;
    public static final int LIGHT_LEVEL_GROUP_RESPONSE=0x5a;
    public static final int LIGHT_LEVEL_GROUP_COMMAND_RESPONSE=0x5b;
    public static final int SELECT_MASTER_RESPONSE=0x5c;
    public static final int UNSELECT_MASTER_RESPONSE=0x5d;
    public static final int RESET_RESPONSE=0x9d;
    public static final int ADD_RESPONSE=0x8d;



    public static final int LIGHT_LEVEL_SITE_GROUP_RESPONSE=0x6a;
    public static final int LIGHT_LEVEL_SITE_GROUP_COMMAND_RESPONSE=0x6b;

    public static final int LIGHT_LEVEL_BUILDING_GROUP_RESPONSE=0x7a;
    public static final int LIGHT_LEVEL_BUILDING_GROUP_COMMAND_RESPONSE=0x7b;

    public static final int LIGHT_LEVEL_LEVEL_GROUP_RESPONSE=0x8a;
    public static final int LIGHT_LEVEL_LEVEL_GROUP_COMMAND_RESPONSE=0x8b;

    public static final int LIGHT_LEVEL_ROOM_GROUP_RESPONSE=0x9a;
    public static final int LIGHT_LEVEL_ROOM_GROUP_COMMAND_RESPONSE=0x9b;
}
