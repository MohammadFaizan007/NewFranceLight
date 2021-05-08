package com.inferrix.lightsmart.DatabaseModule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.GroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.Project;
import com.inferrix.lightsmart.PogoClasses.RoomGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.SiteGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.ADD_DEVICE_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_UID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_BUILDINGID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_LEVELID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_ROOMID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_SITE_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_PROJECT_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_DEVICE_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_BUILDING_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_LEVEL_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_SITE_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_GROUP_ZONE_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.CREATE_PROJECT_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.DATABASE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.DATABASE_VERSION;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.DROP_TABLE;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.FK_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_BUILDING_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_LEVEL_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_ROOM_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.GROUP_TABLE_SITE_NAME;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.PROJECT_ID;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.TABLE_PROJECT;


public class SqlHelper extends SQLiteOpenHelper {
    String TAG1 = "SqlHelper";
    private static SqlHelper instance;

    public SqlHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SqlHelper sqlHelper(Context context) {
        if (instance == null)
            instance = new SqlHelper (context);
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen (db);
        if (!db.isReadOnly ()) {
            // Enable foreign key constraints
            db.execSQL ("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL (DatabaseConstant.CREATE_PROJECT_TABLE);
        db.execSQL (DatabaseConstant.CREATE_DEVICE_TABLE);
        db.execSQL (DatabaseConstant.CREATE_GROUP_TABLE);
        db.execSQL (DatabaseConstant.CREATE_GROUP_BUILDING_TABLE);
        db.execSQL (DatabaseConstant.CREATE_GROUP_SITE_TABLE);
        db.execSQL (DatabaseConstant.CREATE_GROUP_LEVEL_TABLE);
        db.execSQL (DatabaseConstant.CREATE_GROUP_ROOM_TABLE);
        db.execSQL (CREATE_GROUP_ZONE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL (DROP_TABLE + CREATE_PROJECT_TABLE);
        db.execSQL (DROP_TABLE + CREATE_DEVICE_TABLE);
        db.execSQL (DROP_TABLE + GROUP_TABLE_NAME);
        db.execSQL (DROP_TABLE + CREATE_GROUP_SITE_TABLE);
        db.execSQL (DROP_TABLE + CREATE_GROUP_BUILDING_TABLE);
        db.execSQL (DROP_TABLE + CREATE_GROUP_LEVEL_TABLE);
        db.execSQL (DROP_TABLE + GROUP_TABLE_ROOM_NAME);
        db.execSQL (DROP_TABLE + CREATE_GROUP_ZONE_TABLE);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade (db, oldVersion, newVersion);
    }


    /************************  INSERT DATA ***********************************/
    public long insertData(String tableName, ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase ();
        long a = db.insert (tableName, null, values);
        return a;
    }

    public List<Project> getAllProject() {
        // array of columns to fetch
        String[] columns = {
                PROJECT_ID,
                COLUMN_PROJECT_NAME
        };
        // sorting orders
        String sortOrder =
                COLUMN_PROJECT_NAME + " ASC";
        List<Project> projectList = new ArrayList<Project> ();

        SQLiteDatabase db = this.getReadableDatabase ();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query (TABLE_PROJECT, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst ()) {
            do {
                Project project = new Project ();
                project.setProjectId (Integer.parseInt (cursor.getString (cursor.getColumnIndex (PROJECT_ID))));
                project.setProjectNname (cursor.getString (cursor.getColumnIndex (COLUMN_PROJECT_NAME)));
                // Adding user record to list
                projectList.add (project);
            } while (cursor.moveToNext ());
        }
        cursor.close ();
        db.close ();

        // return user list
        return projectList;
    }

    public boolean checkProject(String name) {

        // array of columns to fetch
        String[] columns = {
                PROJECT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase ();
        // selection criteria
        String selection = COLUMN_PROJECT_NAME + " = ?";

        // selection arguments
        String[] selectionArgs = {name};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query (TABLE_PROJECT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount ();

        cursor.close ();
        db.close ();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public Cursor getAllDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName, null);  ///+" where "+COLUMN_GROUP_ID+"='"+0+"'"
    }

    public Cursor getALLLight(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + FK_ID + "='" + id + "'", null );

    }

    public Cursor getLightDetails(long lightId) {
        SQLiteDatabase db = this.getReadableDatabase ();

        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_DEVICE_UID + "='" + lightId + "'", null );
    }

    public Cursor getLightInSiteGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_SITE_ID + "='" + id + "'", null );
    }

    public Cursor getNonSiteGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + tableName + " where " + COLUMN_GROUP_SITE_ID + "='" + 0 + "'", null );  ///
    }

    public boolean updateSiteGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( GROUP_TABLE_SITE_NAME, args, COLUMN_GROUP_SITE_ID + "='" + ID + "'", null ) > 0;
    }

    public boolean updateGroupDevice(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( ADD_DEVICE_TABLE, args, COLUMN_GROUP_ID + "='" + ID + "'", null ) > 0;
    }

    public boolean removeLight(int groupId, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( ADD_DEVICE_TABLE, args, COLUMN_GROUP_ID + "='" + groupId + "'", null ) > 0;
    }


    //
    public boolean updateDevice(long ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(ADD_DEVICE_TABLE, args, COLUMN_DEVICE_UID + "='" + ID + "'", null) > 0;
    }

    public boolean updateDeviceNew(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(ADD_DEVICE_TABLE, args, COLUMN_DEVICE_ID + "='" + ID + "'", null) > 0;
    }

    public boolean updateProject(String ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_PROJECT, args, PROJECT_ID + "='" + ID + "'", null) > 0;
    }




    //************************  GET DATA ***************************************/
    public Cursor getAllGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ("select * from " + GROUP_TABLE_NAME +" where "+FK_ID+"='"+id+"'", null);
    }

    public Cursor getAllRoomGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ("select * from " + GROUP_TABLE_ROOM_NAME +" where "+FK_ID+"='"+id+"'", null);
    }

    public Cursor getAllSiteGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ("select * from " + GROUP_TABLE_SITE_NAME +" where "+FK_ID+"='"+id+"'", null);
    }

    public Cursor getAllBuildingGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ("select * from " + GROUP_TABLE_BUILDING_NAME +" where "+FK_ID+"='"+id+"'", null);
    }


    public Cursor getAllLevelGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ("select * from " + GROUP_TABLE_LEVEL_NAME +" where "+FK_ID+"='"+id+"'", null);
    }

    public Cursor getAllGroupLight() {
        SQLiteDatabase db = this.getReadableDatabase ();
//        return db.rawQuery( "select * from (SELECT GroupTable.GROUP_NAME,GroupTable.GROUP_ID,AddDeviceTable.DEVICE_UID,AddDeviceTable.DEVICE_NAME FROM 'GroupTable' INNER JOIN 'AddDeviceTable' ON GroupTable.GROUP_ID=AddDeviceTable.GROUP_ID) ORDER BY  GROUP_ID ASC", null );
        return db.rawQuery ("select * from (SELECT GroupTable.GROUP_NAME,GroupTable.GROUP_ID,AddDeviceTable.DEVICE_UID,AddDeviceTable.DEVICE_NAME,AddDeviceTable.MASTER_STATUS FROM 'GroupTable' INNER JOIN 'AddDeviceTable' ON GroupTable.GROUP_ID=AddDeviceTable.GROUP_ID) ORDER BY  GROUP_ID ASC", null);
    }

    public Cursor getLightInGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_ID + "='" + id + "'", null );
    }

    public boolean updateBuildingGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( GROUP_TABLE_BUILDING_NAME, args, COLUMN_GROUP_BUILDINGID + "='" + ID + "'", null ) > 0;
    }

    public boolean updateLevelGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( GROUP_TABLE_LEVEL_NAME, args, COLUMN_GROUP_LEVELID + "='" + ID + "'", null ) > 0;
    }

    public boolean updateRoomGroup(int ID, ContentValues args) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( GROUP_TABLE_ROOM_NAME, args, COLUMN_GROUP_ROOMID + "='" + ID + "'", null ) > 0;
    }

    public Cursor getLightInBuildingeGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_BUILDINGID + "='" + id + "'", null );
    }

    public Cursor getLightInLevelGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_LEVELID + "='" + id + "'", null );
    }

    public Cursor getLightInRoomGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + ADD_DEVICE_TABLE + " where " + COLUMN_GROUP_ROOMID + "='" + id + "'", null );
    }

    public Cursor getNonGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + tableName + " where " + COLUMN_GROUP_ID + "='" + 0 + "'", null );  ///
    }

    public boolean updateGroup(int ID, ContentValues args) {

        SQLiteDatabase db = this.getWritableDatabase ();
        return db.update ( GROUP_TABLE_NAME, args, COLUMN_GROUP_ID + "='" + ID + "'", null ) > 0;
    }


    public Cursor getNonBuldingGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + tableName + " where " + COLUMN_GROUP_BUILDINGID + "='" + 0 + "'", null );  ///
    }

    public Cursor getNonLevelGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + tableName + " where " + COLUMN_GROUP_LEVELID + "='" + 0 + "'", null );  ///
    }

    public Cursor getNonRoomGroupDevice(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery ( "select * from " + tableName + " where " + COLUMN_GROUP_ROOMID + "='" + 0 + "'", null );  ///
    }


    public Integer deleteGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( GROUP_TABLE_NAME,
                COLUMN_GROUP_ID + " = ? ",
                new String[]{String.valueOf ( id )} );
    }

    public Integer deleteSiteGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( GROUP_TABLE_SITE_NAME,
                COLUMN_GROUP_SITE_ID + " = ? ",
                new String[]{String.valueOf ( id )} );
    }

    public Integer deleteBuildingGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( GROUP_TABLE_BUILDING_NAME,
                COLUMN_GROUP_BUILDINGID + " = ? ",
                new String[]{String.valueOf ( id )} );
    }

    public Integer deleteLevelGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( GROUP_TABLE_LEVEL_NAME,
                COLUMN_GROUP_LEVELID + " = ? ",
                new String[]{String.valueOf ( id )} );
    }

    public Integer deleteRoomGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( GROUP_TABLE_ROOM_NAME,
                COLUMN_GROUP_ROOMID + " = ? ",
                new String[]{String.valueOf ( id )} );
    }

    public void deleteEnterGroup(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting from users table
        db.delete(TABLE_PROJECT, PROJECT_ID + " = ?",new String[]{String.valueOf(id)});

//        //deleting from users_hobby table
//        db.delete(TABLE_USER_HOBBY, KEY_ID + " = ?", new String[]{String.valueOf(id)});
//
//        //deleting from users_city table
//        db.delete(TABLE_USER_CITY, KEY_ID + " = ?",new String[]{String.valueOf(id)});
    }

//    public Integer deleteEnterGroup(int id) {
//        SQLiteDatabase db = this.getWritableDatabase ();
//        return db.delete ( TABLE_PROJECT,
//                PROJECT_ID + " = ? ",
//                new String[]{String.valueOf ( id )} );
//    }

    public Integer deleteLight(int id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( ADD_DEVICE_TABLE,
                COLUMN_DEVICE_ID + " = ? ",
                new String[]{String.valueOf ( id )} );
    }



    public GroupDetailsClass getGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery ( "select * from " + GROUP_TABLE_NAME + " where " + COLUMN_GROUP_ID + "='" + groupId + "'", null );  ///
        GroupDetailsClass groupData = new GroupDetailsClass ();
        if (cursor.moveToFirst ()) {

            groupData.setGroupId ( cursor.getInt ( cursor.getColumnIndex ( COLUMN_GROUP_ID ) ) );
            groupData.setGroupDimming ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_PROGRESS ) ) );
            groupData.setGroupName ( cursor.getString ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_NAME ) ) );
            groupData.setGroupStatus ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_STATUS ) ) == 1 );

            // do what ever you want here
            // do what ever you want here
        }
        cursor.close ();
        return groupData;
    }

    public SiteGroupDetailsClass getSiteGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery ( "select * from " + GROUP_TABLE_SITE_NAME + " where " + COLUMN_GROUP_SITE_ID + "='" + groupId + "'", null );  ///
        SiteGroupDetailsClass sitegroupData = new SiteGroupDetailsClass ();
        if (cursor.moveToFirst ()) {

            sitegroupData.setGroupSiteId ( cursor.getInt ( cursor.getColumnIndex ( COLUMN_GROUP_SITE_ID ) ) );
            sitegroupData.setGroupSiteDimming ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_SITE_GROUP_PROGRESS ) ) );
            sitegroupData.setGroupSiteName ( cursor.getString ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_DEVICE_SITENAME ) ) );
            sitegroupData.setGroupSiteStatus ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_SITESTATUS ) ) == 1 );
            // do what ever you want here
        }
        cursor.close ();
        return sitegroupData;
    }

    public BuildingGroupDetailsClass getBuildingGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery ( "select * from " + GROUP_TABLE_BUILDING_NAME + " where " + COLUMN_GROUP_BUILDINGID + "='" + groupId + "'", null );  ///
        BuildingGroupDetailsClass buildinggroupData = new BuildingGroupDetailsClass ();
        if (cursor.moveToFirst ()) {

            buildinggroupData.setGroupBuildingId ( cursor.getInt ( cursor.getColumnIndex ( COLUMN_GROUP_BUILDINGID ) ) );
            buildinggroupData.setBuildingGroupDimming ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_BUILDING_GROUP_PROGRESS ) ) );
            buildinggroupData.setGroupBuildingName ( cursor.getString ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDINGNAME ) ) );
            buildinggroupData.setBuildingGroupStatus ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_BUILDINGSTATUS ) ) == 1 );
            // do what ever you want here
        }
        cursor.close ();
        return buildinggroupData;
    }

    public LevelGroupDetailsClass getLevelGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery ( "select * from " + GROUP_TABLE_LEVEL_NAME + " where " +
                COLUMN_GROUP_LEVELID + "='" + groupId + "'", null );  ///
        LevelGroupDetailsClass levelgroupData = new LevelGroupDetailsClass ();
        if (cursor.moveToFirst ()) {

            levelgroupData.setGroupLevelId ( cursor.getInt ( cursor.getColumnIndex ( COLUMN_GROUP_LEVELID ) ) );
            levelgroupData.setLevelGroupDimming ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS ) ) );
            levelgroupData.setGroupLevelName ( cursor.getString ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME ) ) );
            levelgroupData.setLevelGroupStatus ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_LEVELSTATUS ) ) == 1 );
            // do what ever you want here
        }
        cursor.close ();
        return levelgroupData;
    }

    public RoomGroupDetailsClass getRoomGroupDetails(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery ( "select * from " + GROUP_TABLE_ROOM_NAME + " where " + COLUMN_GROUP_ROOMID + "='" + groupId + "'", null );  ///
        RoomGroupDetailsClass roomgroupData = new RoomGroupDetailsClass ();
        if (cursor.moveToFirst ()) {

            roomgroupData.setRoomGroupId ( cursor.getInt ( cursor.getColumnIndex ( COLUMN_GROUP_ROOMID ) ) );
            roomgroupData.setGroupDimming ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_PROGRESS ) ) );
            roomgroupData.setGroupRoomName ( cursor.getString ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME ) ) );
            roomgroupData.setGroupStatus ( cursor.getInt ( cursor.getColumnIndex ( DatabaseConstant.COLUMN_GROUP_ROOMSTATUS ) ) == 1 );
            // do what ever you want here
        }
        cursor.close ();
        return roomgroupData;
    }


}
