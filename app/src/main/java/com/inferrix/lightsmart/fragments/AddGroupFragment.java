package com.inferrix.lightsmart.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.InterfaceModule.AdvertiseResultInterface;
import com.inferrix.lightsmart.InterfaceModule.ReceiverResultInterface;
import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.PogoClasses.GroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.RoomGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.SiteGroupDetailsClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.ServiceModule.AdvertiseTask;
import com.inferrix.lightsmart.ServiceModule.ScannerTask;
import com.niftymodaldialogeffects.Effectstype;
import com.niftymodaldialogeffects.NiftyDialogBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_BUILDING_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_LEVEL_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_ROOM_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_SITE_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ALL_GROUP_INFO;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.REMOVE_ASSOCIATE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class AddGroupFragment extends Fragment implements AdvertiseResultInterface, ReceiverResultInterface {
    Unbinder unbinder;
    ScannerTask scannerTask;
    AnimatedProgress animatedProgress;
    String TAG = this.getClass().getSimpleName();
    Activity activity;
    DeviceClass deviceClass;
    int spinnerSelectedPosition = 0;
    int spinnerSiteSelectedPosition = 0;
    int spinnerBuildingSelectedPosition = 0;
    int spinnerLevelSelectedPosition = 0;
    int spinnerRoomSelectedPosition = 0;

    ArrayAdapter<GroupDetailsClass> adapter;
    ArrayAdapter<SiteGroupDetailsClass> adapterSite;
    ArrayAdapter<RoomGroupDetailsClass> adapterRoom;
    ArrayAdapter<BuildingGroupDetailsClass> adapterBuilding;
    ArrayAdapter<LevelGroupDetailsClass> adapterLevel;
    ArrayList<GroupDetailsClass> list;
    ArrayList<RoomGroupDetailsClass> groupRoomDetailsClasses;
    ArrayList<SiteGroupDetailsClass> groupSiteDetailsClasses;
    ArrayList<BuildingGroupDetailsClass> groupBuildingDetailsClasses;
    ArrayList<LevelGroupDetailsClass> groupLevelDetailsClasses;
    int levelProgress = 0;
    int requestCode;
    @BindView(R.id.deviceName)
    EditText deviceName;
    @BindView(R.id.site_spinner)
    Spinner siteSpinner;
    @BindView(R.id.building_spinner)
    Spinner buildingSpinner;
    @BindView(R.id.level_spinner)
    Spinner levelSpinner;
    @BindView(R.id.room_spinner)
    Spinner roomSpinner;
    @BindView(R.id.group_spinner)
    Spinner groupSpinner;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    int groupIDOne, groupIDTwo;

    public AddGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.add_dialog, container, false );
        activity = getActivity();
        unbinder = ButterKnife.bind( this, view );

        if (deviceClass == null) {
            deviceClass = new DeviceClass();
        }

        deviceName.setText( deviceClass.getDeviceName() );
        scannerTask = new ScannerTask( activity, this );
        animatedProgress = new AnimatedProgress( activity );
        animatedProgress.setCancelable( false );
        //animatedProgress.showProgress();
        list = new ArrayList<>();
        groupSiteDetailsClasses = new ArrayList<>();
        groupRoomDetailsClasses = new ArrayList<>();
        groupBuildingDetailsClasses = new ArrayList<>();
        groupLevelDetailsClasses = new ArrayList<>();


        adapter = new ArrayAdapter<GroupDetailsClass>( activity, R.layout.spinerlayout, list ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView( position, convertView, parent );

                // Set the text color of spinner item
                tv.setTextColor( Color.GRAY );
                tv.setText( list.get( position ).getGroupName() );
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView( position, convertView, parent );

                // Set the text color of drop down items
                tv.setTextColor( Color.BLACK );
                tv.setText( list.get( position ).getGroupName() );

                return tv;
            }
        };
        groupSpinner.setAdapter( adapter );
        adapterSite = new ArrayAdapter<SiteGroupDetailsClass>( activity, R.layout.spinerlayout, groupSiteDetailsClasses ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( groupSiteDetailsClasses.get( position ).getGroupSiteName() );

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView( position, convertView, parent );

                // Set the text color of drop down items
                tv.setTextColor( Color.BLACK );
                tv.setText( groupSiteDetailsClasses.get( position ).getGroupSiteName() );
                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        siteSpinner.setAdapter( adapterSite );
        adapterBuilding = new ArrayAdapter<BuildingGroupDetailsClass>( activity, R.layout.spinerlayout, groupBuildingDetailsClasses ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( groupBuildingDetailsClasses.get( position ).getGroupBuildingName() );
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView( position, convertView, parent );

                // Set the text color of drop down items
                tv.setTextColor( Color.BLACK );
                tv.setText( groupBuildingDetailsClasses.get( position ).getGroupBuildingName() );
                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        buildingSpinner.setAdapter( adapterBuilding );
        adapterLevel = new ArrayAdapter<LevelGroupDetailsClass>( activity, R.layout.spinerlayout, groupLevelDetailsClasses ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( groupLevelDetailsClasses.get( position ).getGroupLevelName() );
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView( position, convertView, parent );

                // Set the text color of drop down items
                tv.setTextColor( Color.BLACK );
                tv.setText( groupLevelDetailsClasses.get( position ).getGroupLevelName() );

                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        levelSpinner.setAdapter( adapterLevel );

        adapterRoom = new ArrayAdapter<RoomGroupDetailsClass>( activity, R.layout.spinerlayout, groupRoomDetailsClasses ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView( position, convertView, parent );

                // Set the text color of spinner item
//                tv.setTextColor("#0000");
                tv.setTextColor( Color.GRAY );
                tv.setText( groupRoomDetailsClasses.get( position ).getGroupRoomName() );
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView( position, convertView, parent );

                // Set the text color of drop down items
                tv.setTextColor( Color.BLACK );
                tv.setText( groupRoomDetailsClasses.get( position ).getGroupRoomName() );
                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        roomSpinner.setAdapter( adapterRoom );

        getAllGroups();
        getAllSITEGroups();
        getAllBUILDINGGroups();
        getAllLEVELGroups();
        getAllROOMGroups();
        btnSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put( DatabaseConstant.COLUMN_GROUP_SITE_ID, ((SiteGroupDetailsClass) siteSpinner.getSelectedItem()).getGroupSiteId() );
                contentValues.put( DatabaseConstant.COLUMN_GROUP_BUILDINGID, ((BuildingGroupDetailsClass) buildingSpinner.getSelectedItem()).getGroupBuildingId() );
                contentValues.put( DatabaseConstant.COLUMN_GROUP_LEVELID, ((LevelGroupDetailsClass) levelSpinner.getSelectedItem()).getGroupLevelId() );
                contentValues.put( DatabaseConstant.COLUMN_GROUP_ROOMID, ((RoomGroupDetailsClass) roomSpinner.getSelectedItem()).getRoomGroupId() );
                contentValues.put( DatabaseConstant.COLUMN_GROUP_ID, ((GroupDetailsClass) groupSpinner.getSelectedItem()).getGroupId() );
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues )) {
                    Log.e( "UID===>", String.valueOf( deviceClass.getDeviceUID() ) );
                    AdvertiseTask advertiseTask;
                    ByteQueue byteQueue = new ByteQueue();
                    byteQueue.push( ALL_GROUP_INFO );
                    byteQueue.push( 0x04 );
                    byteQueue.pushU4B( deviceClass.getDeviceUID() );
                    if (siteSpinner.getSelectedItem().equals( "No Site Group" )) {
                    } else {
                        byteQueue.push( ((SiteGroupDetailsClass) siteSpinner.getSelectedItem()).getGroupSiteId() );
                    }
                    if (buildingSpinner.getSelectedItem().equals( "No Building Group" )) {
                    } else {
                        byteQueue.push( ((BuildingGroupDetailsClass) buildingSpinner.getSelectedItem()).getGroupBuildingId() );
                    }
                    if (levelSpinner.getSelectedItem().equals( "No Level Group" )) {
                    } else {
                        byteQueue.push( ((LevelGroupDetailsClass) levelSpinner.getSelectedItem()).getGroupLevelId() );
                    }
                    if (groupSpinner.getSelectedItem().equals( "No Group" )) {

                    } else {

                        byteQueue.push( ((GroupDetailsClass) groupSpinner.getSelectedItem()).getGroupId() );
                    }

                    if (roomSpinner.getSelectedItem().equals( "No Room Group" )) {
                    } else {
                        byteQueue.push( ((RoomGroupDetailsClass) roomSpinner.getSelectedItem()).getRoomGroupId() );
                    }
                    advertiseTask = new AdvertiseTask( AddGroupFragment.this, activity, 5 * 1000 );
                    animatedProgress.setText( "Uploading" );
                    advertiseTask.setByteQueue( byteQueue );
                    Log.e( "Check>>>>", byteQueue.toString() );
                    advertiseTask.startAdvertising();
                } else {
                    Toast.makeText( activity, "Some error to edit group", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        return view;
    }


    public void setDeviceData(DeviceClass deviceData) {
        this.deviceClass = deviceData;
    }

    public void getAllGroups() {
        list.clear();
        GroupDetailsClass noGroup = new GroupDetailsClass();
        noGroup.setGroupName( "No Group" );
        list.add( noGroup );
        Cursor cursor = sqlHelper.getAllGroup();
        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                GroupDetailsClass groupData = new GroupDetailsClass();
                groupData.setGroupId( cursor.getInt( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_ID ) ) );
                groupData.setGroupDimming( cursor.getInt( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_PROGRESS ) ) );
                groupData.setGroupName( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_NAME ) ) );
                groupData.setGroupStatus( cursor.getInt( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_STATUS ) ) == 1 );
                list.add( groupData );
                if (groupData.getGroupId() == deviceClass.getGroupId()) {
                    spinnerSelectedPosition = i;
                }
                i++;

                // do what ever you want here
            }
            while (cursor.moveToNext());
            cursor.close();
            GroupDetailsClass allGroup = new GroupDetailsClass();
            allGroup.setGroupName( "All Group" );
            allGroup.setGroupId( 254 );
            list.add( allGroup );
            if (allGroup.getGroupId() == deviceClass.getGroupId()) {
                spinnerSelectedPosition = i;
            }
            i++;
        }

        adapter.notifyDataSetChanged();
        groupSpinner.setSelection( spinnerSelectedPosition );
    }

    public void getAllSITEGroups() {
        groupSiteDetailsClasses.clear();
        SiteGroupDetailsClass noGroupData = new SiteGroupDetailsClass();
        noGroupData.setGroupSiteName( "No Site" );
        groupSiteDetailsClasses.add( noGroupData );
        Cursor cursorS = sqlHelper.getAllSiteGroup();
        int i = 1;
        if (cursorS.moveToFirst()) {
            do {
                SiteGroupDetailsClass groupSiteData = new SiteGroupDetailsClass();
                groupSiteData.setGroupSiteId( cursorS.getInt( cursorS.getColumnIndex( DatabaseConstant.COLUMN_GROUP_SITE_ID ) ) );
                groupSiteData.setGroupSiteDimming( cursorS.getInt( cursorS.getColumnIndex( DatabaseConstant.COLUMN_SITE_GROUP_PROGRESS ) ) );
                groupSiteData.setGroupSiteName( cursorS.getString( cursorS.getColumnIndex( DatabaseConstant.COLUMN_GROUP_DEVICE_SITENAME ) ) );
                groupSiteData.setGroupSiteStatus( cursorS.getInt( cursorS.getColumnIndex( DatabaseConstant.COLUMN_GROUP_SITESTATUS ) ) == 1 );
                groupSiteDetailsClasses.add( groupSiteData );
                if (groupSiteData.getGroupSiteId() == deviceClass.getGroupSiteId()) {
                    spinnerSiteSelectedPosition = i;
                }
                i++;
                // do what ever you want here
            }
            while (cursorS.moveToNext());
            cursorS.close();
            SiteGroupDetailsClass allSite = new SiteGroupDetailsClass();
            allSite.setGroupSiteName( "All Site" );
            allSite.setGroupSiteId( 254 );
            groupSiteDetailsClasses.add( allSite );
            if (allSite.getGroupSiteId() == deviceClass.getGroupSiteId()) {
                spinnerSiteSelectedPosition = i;
            }
            i++;
        }
        adapterSite.notifyDataSetChanged();
        siteSpinner.setSelection( spinnerSiteSelectedPosition );

    }

    public void getAllBUILDINGGroups() {
        groupBuildingDetailsClasses.clear();
        BuildingGroupDetailsClass noGroupData = new BuildingGroupDetailsClass();
        noGroupData.setGroupBuildingName( "No Building" );
        groupBuildingDetailsClasses.add( noGroupData );
        Cursor cursorB = sqlHelper.getAllBuildingGroup();
        int i = 1;
        if (cursorB.moveToFirst()) {
            do {
                BuildingGroupDetailsClass groupBuildingData = new BuildingGroupDetailsClass();
                groupBuildingData.setGroupBuildingId( cursorB.getInt( cursorB.getColumnIndex( DatabaseConstant.COLUMN_GROUP_BUILDINGID ) ) );
                groupBuildingData.setBuildingGroupDimming( cursorB.getInt( cursorB.getColumnIndex( DatabaseConstant.COLUMN_BUILDING_GROUP_PROGRESS ) ) );
                groupBuildingData.setGroupBuildingName( cursorB.getString( cursorB.getColumnIndex( DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDINGNAME ) ) );
                groupBuildingData.setBuildingGroupStatus( cursorB.getInt( cursorB.getColumnIndex( DatabaseConstant.COLUMN_GROUP_BUILDINGSTATUS ) ) == 1 );
                groupBuildingDetailsClasses.add( groupBuildingData );
                // do what ever you want here
                if (groupBuildingData.getGroupBuildingId() == deviceClass.getGroupBuildingId()) {
                    spinnerBuildingSelectedPosition = i;
                }
                i++;
            }
            while (cursorB.moveToNext());
            cursorB.close();
            BuildingGroupDetailsClass allBulding = new BuildingGroupDetailsClass();
            allBulding.setGroupBuildingName( "All Building" );
            allBulding.setGroupBuildingId( 254 );
            groupBuildingDetailsClasses.add( allBulding );
            if (allBulding.getGroupBuildingId() == deviceClass.getGroupBuildingId()) {
                spinnerBuildingSelectedPosition = i;
            }
            i++;
        }
        adapterBuilding.notifyDataSetChanged();
        buildingSpinner.setSelection( spinnerBuildingSelectedPosition );


    }

    public void getAllLEVELGroups() {
        groupLevelDetailsClasses.clear();
        LevelGroupDetailsClass noGroupData = new LevelGroupDetailsClass();
        noGroupData.setGroupLevelName( "No Level" );
        groupLevelDetailsClasses.add( noGroupData );
        Cursor cursorB = sqlHelper.getAllLevelGroup();
        int i = 1;
        if (cursorB.moveToFirst()) {
            do {
                LevelGroupDetailsClass groupLevelData = new LevelGroupDetailsClass();
                groupLevelData.setGroupLevelId( cursorB.getInt( cursorB.getColumnIndex( DatabaseConstant.COLUMN_GROUP_LEVELID ) ) );
                groupLevelData.setLevelGroupDimming( cursorB.getInt( cursorB.getColumnIndex( DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS ) ) );
                groupLevelData.setGroupLevelName( cursorB.getString( cursorB.getColumnIndex( DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME ) ) );
                groupLevelData.setLevelGroupStatus( cursorB.getInt( cursorB.getColumnIndex( DatabaseConstant.COLUMN_GROUP_LEVELSTATUS ) ) == 1 );
                groupLevelDetailsClasses.add( groupLevelData );
                if (groupLevelData.getGroupLevelId() == deviceClass.getGroupLevelId()) {
                    spinnerLevelSelectedPosition = i;
//                    Toast.makeText(activity, "i=" + i, Toast.LENGTH_SHORT).show();
                }
                i++;
                // do what ever you want here
            }
            while (cursorB.moveToNext());
            cursorB.close();
            LevelGroupDetailsClass allBulding = new LevelGroupDetailsClass();
            allBulding.setGroupLevelName( "All Level" );
            allBulding.setGroupLevelId( 254 );
            groupLevelDetailsClasses.add( allBulding );
            if (allBulding.getGroupLevelId() == deviceClass.getGroupLevelId()) {
                spinnerLevelSelectedPosition = i;
            }
            i++;
        }
        adapterLevel.notifyDataSetChanged();
        levelSpinner.setSelection( spinnerLevelSelectedPosition );

    }

    public void getAllROOMGroups() {
        groupRoomDetailsClasses.clear();
        RoomGroupDetailsClass noGroupData = new RoomGroupDetailsClass();
        noGroupData.setGroupRoomName( "No Room" );
        groupRoomDetailsClasses.add( noGroupData );
        Cursor cursor = sqlHelper.getAllRoomGroup();
        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                RoomGroupDetailsClass groupRoomData = new RoomGroupDetailsClass();
                groupRoomData.setRoomGroupId( cursor.getInt( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_ROOMID ) ) );
                groupRoomData.setGroupDimming( cursor.getInt( cursor.getColumnIndex( DatabaseConstant.COLUMN_ROOM_GROUP_PROGRESS ) ) );
                groupRoomData.setGroupRoomName( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME ) ) );
                groupRoomData.setGroupStatus( cursor.getInt( cursor.getColumnIndex( DatabaseConstant.COLUMN_GROUP_ROOMSTATUS ) ) == 1 );
                groupRoomDetailsClasses.add( groupRoomData );
                if (groupRoomData.getRoomGroupId() == deviceClass.getGroupRoomId()) {
                    spinnerRoomSelectedPosition = i;
//                    Toast.makeText(activity, "i=" + i, Toast.LENGTH_SHORT).show();
                }
                i++;
                // do what ever you want here
            }
            while (cursor.moveToNext());
            cursor.close();
            RoomGroupDetailsClass allBulding = new RoomGroupDetailsClass();
            allBulding.setGroupRoomName( "All Room" );
            allBulding.setRoomGroupId( 254 );
            groupRoomDetailsClasses.add( allBulding );
            if (allBulding.getRoomGroupId() == deviceClass.getGroupRoomId()) {
                spinnerRoomSelectedPosition = i;
            }
            i++;
        }
        adapterRoom.notifyDataSetChanged();
        roomSpinner.setSelection( spinnerRoomSelectedPosition );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(String message) {
        animatedProgress.showProgress();
        Log.w( TAG, "Uploading" );

    }

    @Override
    public void onFailed(String errorMessage) {
        if (animatedProgress == null)
            return;
        Toast.makeText( activity, "Uploading", Toast.LENGTH_SHORT ).show();
        animatedProgress.hideProgress();
//        activity.onBackPressed();
        Log.w( TAG, "onScanFailed " + errorMessage );

    }

    @Override
    public void onStop(String stopMessage, int resultCode) {
        if (animatedProgress != null)
            animatedProgress.hideProgress();
        activity.onBackPressed();
        ContentValues contentValues = new ContentValues();


    }

    @Override
    public void onScanSuccess(int successCode, ByteQueue byteQueue) {
        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();
//        activity.onBackPressed();

    }

    @Override
    public void onScanFailed(int errorCode) {
        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();
        activity.onBackPressed();

    }

    @Override
    public void onResume() {
        super.onResume();
        getAllGroups();
        getAllSITEGroups();
        getAllBUILDINGGroups();
        getAllLEVELGroups();
        getAllROOMGroups();
    }

}
