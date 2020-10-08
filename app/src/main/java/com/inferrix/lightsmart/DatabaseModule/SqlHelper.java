package com.inferrix.lightsmart.DatabaseModule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.GroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.RoomGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.SiteGroupDetailsClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.ADD_DEVICE_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_UID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_BUILDINGID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_LEVELID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_ROOMID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_SITE_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_USER_EMAIL;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_USER_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_USER_PASSWORD;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_BUILDING_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_LEVEL_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_SITE_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_USER_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.DATABASE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.DATABASE_VERSION;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.DROP_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_BUILDING_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_LEVEL_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_ROOM_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_SITE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.TABLE_USER;


public class SqlHelper extends SQLiteOpenHelper {
    String TAG1 = "SqlHelper";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //context
    private Context mContext;

    public SqlHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseConstant.CREATE_USER_TABLE);
        db.execSQL(DatabaseConstant.CREATE_DEVICE_TABLE);
        db.execSQL(DatabaseConstant.CREATE_GROUP_TABLE);
        db.execSQL(DatabaseConstant.CREATE_GROUP_BUILDING_TABLE);
        db.execSQL(DatabaseConstant.CREATE_GROUP_SITE_TABLE);
        db.execSQL(DatabaseConstant.CREATE_GROUP_LEVEL_TABLE);
        db.execSQL(DatabaseConstant.CREATE_GROUP_ROOM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + CREATE_USER_TABLE);
        db.execSQL(DROP_TABLE + ADD_DEVICE_TABLE);
        db.execSQL(DROP_TABLE + GROUP_TABLE_NAME);
        db.execSQL(DROP_TABLE + CREATE_GROUP_SITE_TABLE);
        db.execSQL(DROP_TABLE + CREATE_GROUP_BUILDING_TABLE);
        db.execSQL(DROP_TABLE + CREATE_GROUP_LEVEL_TABLE);
        db.execSQL(DROP_TABLE + GROUP_TABLE_ROOM_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /************************  INSERT DATA ***********************************/

    public long insertData(String tableName, ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase();

        long a = db.insert(tableName, null, values);
//        Log.w(TAG1,"a="+a);
//        Cursor cursor=get
// AllData(tableName);
//        if (cursor!=null && cursor.getCount()>10)
//            db.execSQL("DELETE FROM "+tableName+" WHERE "+COLUMN_ID+" IN (" +
//                " SELECT "+COLUMN_ID+" FROM "+tableName+" ORDER BY "+COLUMN_TIMESTAMP+" DESC LIMIT "+cursor.getCount() +" OFFSET " +10+
//                "  )");
//        db.close();
        return a;
    }

    public boolean insertDataNotification(ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase();
        long a = db.insert(GROUP_TABLE_NAME, null, values);
//        Log.w(TAG1,"a="+a);

        return a > 0;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    //***********************  UPDATE DATA **********************************/

    public boolean updateGroupDimming(int groupId, ContentValues args) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GROUP_TABLE_NAME, args, COLUMN_GROUP_ID + "='" + groupId + "'", null) > 0;
    }

    public boolean updateGroupDevice(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(ADD_DEVICE_TABLE, args, COLUMN_GROUP_ID + "='" + ID + "'", null) > 0;
    }

    public boolean updateGroup(int ID, ContentValues args) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GROUP_TABLE_NAME, args, COLUMN_GROUP_ID + "='" + ID + "'", null) > 0;
    }

    public boolean updateSiteGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GROUP_TABLE_SITE_NAME, args, COLUMN_GROUP_SITE_ID + "='" + ID + "'", null) > 0;
    }

    public boolean updateBuildingGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GROUP_TABLE_BUILDING_NAME, args, COLUMN_GROUP_BUILDINGID + "='" + ID + "'", null) > 0;
    }

    public boolean updateLevelGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GROUP_TABLE_LEVEL_NAME, args, COLUMN_GROUP_LEVELID + "='" + ID + "'", null) > 0;
    }

    public boolean updateRoomGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GROUP_TABLE_ROOM_NAME, args, COLUMN_GROUP_ROOMID + "='" + ID + "'", null) > 0;
    }

    public boolean updateDevice(long ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(ADD_DEVICE_TABLE, args, COLUMN_DEVICE_UID + "='" + ID + "'", null) > 0;
    }

    public boolean updateDevice(String ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(ADD_DEVICE_TABLE, args, COLUMN_DEVICE_UID + "='" + ID + "'", null) > 0;
    }

    public boolean removeLight(int groupId, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(ADD_DEVICE_TABLE, args, COLUMN_GROUP_ID + "='" + groupId + "'", null) > 0;
    }

    //************************  GET DATA ***************************************/

    public GroupDetailsClass getGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GROUP_TABLE_NAME + " where " + COLUMN_GROUP_ID + "='" + groupId + "'", null);  ///
        GroupDetailsClass groupData = new GroupDetailsClass();
        if (cursor.moveToFirst()) {

            groupData.setGroupId(cursor.getInt(cursor.getColumnIndex(COLUMN_GROUP_ID)));
            groupData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_PROGRESS)));
            groupData.setGroupName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_NAME)));
            groupData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_STATUS)) == 1);

            // do what ever you want here
            // do what ever you want here
        }
        cursor.close();
        return groupData;
    }

    public SiteGroupDetailsClass getSiteGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GROUP_TABLE_SITE_NAME + " where " + COLUMN_GROUP_SITE_ID + "='" + groupId + "'", null);  ///
        SiteGroupDetailsClass sitegroupData = new SiteGroupDetailsClass();
        if (cursor.moveToFirst()) {

            sitegroupData.setGroupSiteId(cursor.getInt(cursor.getColumnIndex(COLUMN_GROUP_SITE_ID)));
            sitegroupData.setGroupSiteDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_SITE_GROUP_PROGRESS)));
            sitegroupData.setGroupSiteName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_SITENAME)));
            sitegroupData.setGroupSiteStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_SITESTATUS)) == 1);
            // do what ever you want here
        }
        cursor.close();
        return sitegroupData;
    }

    public BuildingGroupDetailsClass getBuildingGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GROUP_TABLE_BUILDING_NAME + " where " + COLUMN_GROUP_BUILDINGID + "='" + groupId + "'", null);  ///
        BuildingGroupDetailsClass buildinggroupData = new BuildingGroupDetailsClass();
        if (cursor.moveToFirst()) {

            buildinggroupData.setGroupBuildingId(cursor.getInt(cursor.getColumnIndex(COLUMN_GROUP_BUILDINGID)));
            buildinggroupData.setBuildingGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_BUILDING_GROUP_PROGRESS)));
            buildinggroupData.setGroupBuildingName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDINGNAME)));
            buildinggroupData.setBuildingGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_BUILDINGSTATUS)) == 1);
            // do what ever you want here
        }
        cursor.close();
        return buildinggroupData;
    }

    public LevelGroupDetailsClass getLevelGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GROUP_TABLE_LEVEL_NAME + " where " +
                COLUMN_GROUP_LEVELID + "='" + groupId + "'", null);  ///
        LevelGroupDetailsClass levelgroupData = new LevelGroupDetailsClass();
        if (cursor.moveToFirst()) {

            levelgroupData.setGroupLevelId(cursor.getInt(cursor.getColumnIndex(COLUMN_GROUP_LEVELID)));
            levelgroupData.setLevelGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS)));
            levelgroupData.setGroupLevelName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME)));
            levelgroupData.setLevelGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELSTATUS)) == 1);
            // do what ever you want here
        }
        cursor.close();
        return levelgroupData;
    }

    public RoomGroupDetailsClass getRoomGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GROUP_TABLE_ROOM_NAME + " where " + COLUMN_GROUP_ROOMID + "='" + groupId + "'", null);  ///
        RoomGroupDetailsClass roomgroupData = new RoomGroupDetailsClass();
        if (cursor.moveToFirst()) {

            roomgroupData.setRoomGroupId(cursor.getInt(cursor.getColumnIndex(COLUMN_GROUP_ROOMID)));
            roomgroupData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_PROGRESS)));
            roomgroupData.setGroupRoomName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME)));
            roomgroupData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMSTATUS)) == 1);
            // do what ever you want here
        }
        cursor.close();
        return roomgroupData;
    }

    public Cursor getDataNotification(String from1) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_NAME + " where " + COLUMN_DEVICE_UID + "='" + from1 + "'", null);
    }

    public Cursor getData(String tableName, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + COLUMN_ID + "='" + id + "'", null);
    }


    //************************  GET DATA ***************************************/
    public Cursor getAllGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_NAME /*+" where "+COLUMN_ID+"='"+id+"'"*/, null);
    }

    public Cursor getAllRoomGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_ROOM_NAME /*+" where "+COLUMN_ID+"='"+id+"'"*/, null);
    }

    public Cursor getAllSiteGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_SITE_NAME /*+" where "+COLUMN_ID+"='"+id+"'"*/, null);
    }

    public Cursor getAllBuildingGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_BUILDING_NAME /*+" where "+COLUMN_ID+"='"+id+"'"*/, null);
    }


    public Cursor getAllLevelGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_LEVEL_NAME /*+" where "+COLUMN_ID+"='"+id+"'"*/, null);
    }


    public Cursor getAllDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName, null);  ///+" where "+COLUMN_GROUP_ID+"='"+0+"'"
    }


    public Cursor getAllGroupLight() {
        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery( "select * from (SELECT GroupTable.GROUP_NAME,GroupTable.GROUP_ID,AddDeviceTable.DEVICE_UID,AddDeviceTable.DEVICE_NAME FROM 'GroupTable' INNER JOIN 'AddDeviceTable' ON GroupTable.GROUP_ID=AddDeviceTable.GROUP_ID) ORDER BY  GROUP_ID ASC", null );
        return db.rawQuery("select * from (SELECT GroupTable.GROUP_NAME,GroupTable.GROUP_ID,AddDeviceTable.DEVICE_UID,AddDeviceTable.DEVICE_NAME,AddDeviceTable.MASTER_STATUS FROM 'GroupTable' INNER JOIN 'AddDeviceTable' ON GroupTable.GROUP_ID=AddDeviceTable.GROUP_ID) ORDER BY  GROUP_ID ASC", null);
    }

    //select *from (SELECT GroupTable.GROUP_NAME,GroupTable.GROUP_ID,AddDeviceTable.DEVICE_UID,AddDeviceTable.DEVICE_NAME FROM 'GroupTable' INNER JOIN 'AddDeviceTable' ON GroupTable.GROUP_ID=AddDeviceTable.GROUP_ID) order by  GROUP_ID ASC;
    public Cursor getLightInGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_ID + "='" + id + "'", null);
    }

    public Cursor getLightInAllGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + GROUP_TABLE_NAME + " where " + COLUMN_DEVICE_UID + "='" + id + "'", null);
    }


    public Cursor getLightInSiteGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_SITE_ID + "='" + id + "'", null);
    }

    public Cursor getLightInBuildingeGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_BUILDINGID + "='" + id + "'", null);
    }

    public Cursor getLightInLevelGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_LEVELID + "='" + id + "'", null);
    }

    public Cursor getLightInRoomGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_ROOMID + "='" + id + "'", null);
    }

    public Cursor getNonGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + COLUMN_GROUP_ID + "='" + 0 + "'", null);  ///
    }

    public Cursor getNonSiteGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + COLUMN_GROUP_SITE_ID + "='" + 0 + "'", null);  ///
    }

    public Cursor getNonBuldingGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + COLUMN_GROUP_BUILDINGID + "='" + 0 + "'", null);  ///
    }

    public Cursor getNonLevelGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + COLUMN_GROUP_LEVELID + "='" + 0 + "'", null);  ///
    }

    public Cursor getNonRoomGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + COLUMN_GROUP_ROOMID + "='" + 0 + "'", null);  ///
    }


    public Cursor getLightDetails(long lightId) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_DEVICE_UID + "='" + lightId + "'", null);
    }

    public void getLightDetail(long ligthId, int groupId) {

    }

    public boolean isExist(String id) {
        Cursor cursor = getData(ADD_DEVICE_TABLE, id);
        if (cursor == null)
            return false;
//        Log.w(TAG1,"name="+tableName+cursor.getCount());
        return cursor.getCount() > 0;

    }

    //**************************  DELETE DATA ****************************************/
    public Integer deleteData(String tableName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,
                COLUMN_ID + " = ? ",
                new String[]{id});
    }

    public Integer deleteGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GROUP_TABLE_NAME,
                COLUMN_GROUP_ID + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public Integer deleteSiteGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GROUP_TABLE_SITE_NAME,
                COLUMN_GROUP_SITE_ID + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public Integer deleteBuildingGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GROUP_TABLE_BUILDING_NAME,
                COLUMN_GROUP_BUILDINGID + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public Integer deleteLevelGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GROUP_TABLE_LEVEL_NAME,
                COLUMN_GROUP_LEVELID + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public Integer deleteRoomGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GROUP_TABLE_ROOM_NAME,
                COLUMN_GROUP_ROOMID + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public Integer deleteLight(long uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ADD_DEVICE_TABLE,
                COLUMN_DEVICE_UID + " = ? ",
                new String[]{String.valueOf(uid)});
    }
}







//    //////////////////////////////////////////
//    public void backup(String outFileName) {
//
//        //database path
//        final  String inFileName = mContext.getDatabasePath(DATABASE_NAME).getAbsolutePath().toString();
//        try {
//
//            File dbFile = new File(inFileName);
//            FileInputStream fis = new FileInputStream(dbFile);
//
//            // Open the empty db as the output stream
//            OutputStream output = new FileOutputStream(outFileName);
//
//            // Transfer bytes from the input file to the output file
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = fis.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//
//            // Close the streams
//            output.flush();
//            output.close();
//            fis.close();
//
//            Toast.makeText(mContext, "Backup Completed", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            Toast.makeText(mContext, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
//
//    public void importDB(String inFileName) {
//
//        final String outFileName = mContext.getDatabasePath(DATABASE_NAME).toString();
//
//        try {
//
//            File dbFile = new File(inFileName);
//            FileInputStream fis = new FileInputStream(dbFile);
//
//            // Open the empty db as the output stream
//            OutputStream output = new FileOutputStream(outFileName);
//
//            // Transfer bytes from the input file to the output file
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = fis.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//
//            // Close the streams
//            output.flush();
//            output.close();
//            fis.close();
//
//            Toast.makeText(mContext, "Import Completed", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            Toast.makeText(mContext, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
//}

//}
