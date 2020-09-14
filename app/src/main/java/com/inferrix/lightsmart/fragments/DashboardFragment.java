package com.inferrix.lightsmart.fragments;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.PogoClasses.GroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.GroupedLight;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.RoomGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.SiteGroupDetailsClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.adapter.DashboardBuildingAdapter;
import com.inferrix.lightsmart.adapter.DashboardItemAdapter;
import com.inferrix.lightsmart.adapter.DashboardLevelAdapter;
import com.inferrix.lightsmart.adapter.DashboardRoomAdapter;
import com.inferrix.lightsmart.adapter.DashboardSiteAdapter;
import com.inferrix.lightsmart.adapter.GroupedLightAdapter;
import com.inferrix.lightsmart.adapter.IndividualBandAdapter;
import com.inferrix.lightsmart.adapter.IndividualLightAdapter;
import com.inferrix.lightsmart.adapter.IndividualPirSwitchAdapter;
import com.inferrix.lightsmart.adapter.IndividualSwitchAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class DashboardFragment extends Fragment {
    @BindView(R.id.dashboard_home_setting_layout)
    LinearLayout dashboardHomeSettingLayout;
    @BindView(R.id.group_list_layout)
    LinearLayout groupListLayout;
    @BindView(R.id.group_site_list_layout)
    LinearLayout group_site_list_layout;
    @BindView(R.id.group_building_list_layout)
    LinearLayout group_building_list_layout;
    @BindView(R.id.individual_light_list_layout)
    LinearLayout individualLightListLayout;
    @BindView(R.id.individual_band_list_layout)
    LinearLayout individual_band_list_layout;
    @BindView(R.id.individual_switch_list_layout)
    LinearLayout individual_switch_list_layout;
    @BindView(R.id.individual_pirswitch_list_layout)
    LinearLayout individual_pirswitch_list_layout;
    @BindView(R.id.dashboard_spinner)
    Spinner dashboardSpinner;
    @BindView(R.id.dash_group_list)
    ListView dashboargItemList;
    @BindView(R.id.dash_site_group_list)
    ListView dash_site_group_list;
    @BindView(R.id.dash_building_group_list)
    ListView dash_building_group_list;
    @BindView(R.id.individual_light_list)
    ListView individualLightList;
    @BindView(R.id.individual_Band_list)
    ListView individual_Band_list;
    @BindView(R.id.individual_switch_list)
    ListView individual_switch_list;
    @BindView(R.id.individual_pirswitch_list)
    ListView individual_pirswitch_list;
    Unbinder unbinder;
    DashboardSiteAdapter dashboardSiteAdapter;
    DashboardItemAdapter dashboardItemAdapter;
    DashboardBuildingAdapter dashboardBuildingAdapter;
    DashboardLevelAdapter dashboardLevelAdapter;
    DashboardRoomAdapter dashboardRoomAdapter;
    IndividualLightAdapter individualLightAdapter;
    IndividualBandAdapter individualBandAdapter;
    IndividualSwitchAdapter individualSwitchAdapter;
    IndividualPirSwitchAdapter individualPirSwitchAdapter;
    GroupedLightAdapter groupedLightAdapter;
    Activity activity;
    ArrayList<DeviceClass> deviceList;
    ArrayAdapter<GroupDetailsClass> adapter;
    ArrayList<GroupDetailsClass> list;
    ArrayList<RoomGroupDetailsClass> listRoom;
    ArrayList<SiteGroupDetailsClass> listSite;
    ArrayList<BuildingGroupDetailsClass> listBuilding;
    ArrayList<LevelGroupDetailsClass> listLevel;
    ArrayList<GroupDetailsClass> spinnerList;
    ArrayList<GroupedLight> groupedLightArrayList;
    @BindView(R.id.dash_level_group_list)
    ListView dashLevelGroupList;
    @BindView(R.id.group_level_list_layout)
    LinearLayout groupLevelListLayout;
    @BindView(R.id.dash_room_group_list)
    ListView dashRoomGroupList;
    @BindView(R.id.group_room_list_layout)
    LinearLayout groupRoomListLayout;

    public DashboardFragment() {
        // Required empty public constructor
        list = new ArrayList<>();
        groupedLightArrayList = new ArrayList<>();
        deviceList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        listSite = new ArrayList<>();
        listRoom = new ArrayList<>();
        listBuilding = new ArrayList<>();
        listLevel = new ArrayList<>();

    }

    public void setSpinner() {
        spinnerList.clear();
        GroupDetailsClass lightData = new GroupDetailsClass();
        lightData.setGroupId(1);
        lightData.setGroupName("All Light");
        GroupDetailsClass bandtData = new GroupDetailsClass();
        bandtData.setGroupId(2);
        bandtData.setGroupName("All Band");
        GroupDetailsClass switchData = new GroupDetailsClass();
        switchData.setGroupId(3);
        switchData.setGroupName("All Switches");
        GroupDetailsClass pirSwitchData = new GroupDetailsClass();
        pirSwitchData.setGroupId(4);
        pirSwitchData.setGroupName("All PIR Switches");
        GroupDetailsClass groupData = new GroupDetailsClass();
        groupData.setGroupId(5);
        groupData.setGroupName("All Group");

        GroupDetailsClass groupSiteData = new GroupDetailsClass();
        groupSiteData.setGroupId(6);
        groupSiteData.setGroupName("All Site Group");

        GroupDetailsClass groupBuildingData = new GroupDetailsClass();
        groupBuildingData.setGroupId(7);
        groupBuildingData.setGroupName("All Building Group");

        GroupDetailsClass groupLevelData = new GroupDetailsClass();
        groupLevelData.setGroupId(8);
        groupLevelData.setGroupName("All Level Group");

        GroupDetailsClass groupRoomData = new GroupDetailsClass();
        groupRoomData.setGroupId(9);
        groupRoomData.setGroupName("All Room Group");

        spinnerList.add(lightData);
        spinnerList.add(bandtData);
        spinnerList.add(switchData);
        spinnerList.add(pirSwitchData);
        spinnerList.add(groupData);
        spinnerList.add(groupSiteData);
        spinnerList.add(groupBuildingData);
        spinnerList.add(groupLevelData);
        spinnerList.add(groupRoomData);
        adapter.notifyDataSetChanged();
    }

    public void getAllGroups() {
        setSpinner();
        list.clear();
        Cursor cursor = sqlHelper.getAllGroup();
        if (cursor.moveToFirst()) {
            do {
                GroupDetailsClass groupData = new GroupDetailsClass();
                groupData.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                groupData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_PROGRESS)));
                groupData.setGroupName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_NAME)));
//                groupData.setGroupbuilding(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDING)));
//                groupData.setGroupsite(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_SITE)));
//                groupData.setGrouplevel(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_LEVEL)));
//                groupData.setGrouproom(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_ROOM)));
                groupData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_STATUS)) == 1);
                list.add(groupData);
                // do what ever you want here
            }
            while (cursor.moveToNext());
        }

//
        cursor.close();
//        adapter.notifyDataSetChanged();
        dashboardItemAdapter.setList(list);
    }

    public void getAllSiteGroup() {
        setSpinner();
        listSite.clear();
        Cursor cursor = sqlHelper.getAllSiteGroup();
        if (cursor.moveToFirst()) {
            do {
                SiteGroupDetailsClass roomData = new SiteGroupDetailsClass();
                roomData.setGroupSiteId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_SITE_ID)));
                roomData.setGroupSiteDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_SITE_GROUP_PROGRESS)));
                roomData.setGroupSiteName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_SITENAME)));
                roomData.setGroupSiteStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_SITESTATUS)) == 1);
                listSite.add(roomData);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        dashboardSiteAdapter.setList(listSite);
    }

    public void getAllBUILDINGGroups() {
        setSpinner();
        listBuilding.clear();
        Cursor cursor = sqlHelper.getAllBuildingGroup();

        if (cursor.moveToFirst()) {
            do {
                BuildingGroupDetailsClass groupBuildingData = new BuildingGroupDetailsClass();
                groupBuildingData.setGroupBuildingId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_BUILDINGID)));
                groupBuildingData.setBuildingGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_BUILDING_GROUP_PROGRESS)));
                groupBuildingData.setGroupBuildingName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDINGNAME)));
                groupBuildingData.setBuildingGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_BUILDINGSTATUS)) == 1);
                listBuilding.add(groupBuildingData);
                // do what ever you want here
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        dashboardBuildingAdapter.setList(listBuilding);
    }

    public void getAllLevelGroups() {
        setSpinner();
        listLevel.clear();
        Cursor cursor = sqlHelper.getAllLevelGroup();
        if (cursor.moveToFirst()) {
            do {
                LevelGroupDetailsClass levelGroupDetailsClass = new LevelGroupDetailsClass();
                levelGroupDetailsClass.setGroupLevelId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELID)));
                levelGroupDetailsClass.setLevelGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS)));
                levelGroupDetailsClass.setGroupLevelName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME)));
                levelGroupDetailsClass.setLevelGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELSTATUS)) == 1);
                listLevel.add(levelGroupDetailsClass);

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        dashboardLevelAdapter.setList(listLevel);
    }

    public void getAllRoomGroup() {
        setSpinner();
        listRoom.clear();
        Cursor cursorS = sqlHelper.getAllRoomGroup();
        if (cursorS.moveToFirst()) {
            do {
                RoomGroupDetailsClass roomData = new RoomGroupDetailsClass();
                roomData.setRoomGroupId(cursorS.getInt(cursorS.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMID)));
                roomData.setGroupDimming(cursorS.getInt(cursorS.getColumnIndex(DatabaseConstant.COLUMN_ROOM_GROUP_PROGRESS)));
                roomData.setGroupRoomName(cursorS.getString(cursorS.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME)));
                roomData.setGroupStatus(cursorS.getInt(cursorS.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMSTATUS)) == 1);
                listRoom.add(roomData);
            }
            while (cursorS.moveToNext());
        }
        cursorS.close();
        dashboardRoomAdapter.setList(listRoom);

    }

    public void getAllGroupedLight() {
        groupedLightArrayList.clear();

        Cursor cursor = sqlHelper.getAllGroupLight();

        if (cursor.moveToFirst()) {
            do {
                GroupedLight groupData = new GroupedLight();
                groupData.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                groupData.setGroupName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_NAME)));
                groupData.setMasterStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS)));
                groupData.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                groupData.setDeviceUid(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                groupedLightArrayList.add(groupData);
                // do what ever you want here
            }
            while (cursor.moveToNext());
        }

//
        cursor.close();
        groupedLightAdapter.setList(groupedLightArrayList);
    }


    public void getDevice() {
        deviceList = new ArrayList<>();
        Cursor cursor = sqlHelper.getAllDevice(DatabaseConstant.ADD_DEVICE_TABLE);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)).equalsIgnoreCase("16")) {
                    DeviceClass deviceClass = new DeviceClass();
                    deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                    deviceClass.setTypeCode(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)));
                    deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                    deviceClass.setDeriveType(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DERIVE_TYPE)));
                    deviceClass.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_PROGRESS)));
                    deviceClass.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                    deviceClass.setMasterStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS)));
                    deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS)) == 1);
                    deviceList.add(deviceClass);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        individualLightAdapter.setList(deviceList);
    }

    public void getBand() {
        deviceList = new ArrayList<>();
        Cursor cursor = sqlHelper.getAllDevice(DatabaseConstant.ADD_DEVICE_TABLE);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)).equalsIgnoreCase("533")) {
                    DeviceClass deviceClass = new DeviceClass();
                    deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                    deviceClass.setTypeCode(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)));
                    deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                    deviceClass.setDeriveType(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DERIVE_TYPE)));
                    deviceClass.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_PROGRESS)));
                    deviceClass.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                    deviceClass.setMasterStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS)));
                    deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS)) == 1);
                    deviceList.add(deviceClass);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        individualBandAdapter.setList(deviceList);
    }

    public void getSwitch() {
        deviceList = new ArrayList<>();
        Cursor cursor = sqlHelper.getAllDevice(DatabaseConstant.ADD_DEVICE_TABLE);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)).equalsIgnoreCase("55811") && cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS)).startsWith("E2:15")) {
                    DeviceClass deviceClass = new DeviceClass();
                    deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                    deviceClass.setTypeCode(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)));
                    deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                    deviceClass.setDeriveType(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DERIVE_TYPE)));
                    deviceClass.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_PROGRESS)));
                    deviceClass.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                    deviceClass.setMasterStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS)));
                    deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS)) == 1);
                    deviceList.add(deviceClass);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        individualSwitchAdapter.setList(deviceList);
    }

    public void getPirSwitch() {
        deviceList = new ArrayList<>();
        Cursor cursor = sqlHelper.getAllDevice(DatabaseConstant.ADD_DEVICE_TABLE);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)).equalsIgnoreCase("55811") && cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS)).startsWith("E5:00")) {
                    DeviceClass deviceClass = new DeviceClass();
                    deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                    deviceClass.setTypeCode(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)));
                    deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                    deviceClass.setDeriveType(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DERIVE_TYPE)));
                    deviceClass.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_PROGRESS)));
                    deviceClass.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                    deviceClass.setMasterStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS)));
                    deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS)) == 1);
                    deviceList.add(deviceClass);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        individualPirSwitchAdapter.setList(deviceList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getDevice();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        activity = getActivity();
        if (activity == null)
            return view;
        dashboardItemAdapter = new DashboardItemAdapter(activity);
        individualLightAdapter = new IndividualLightAdapter(activity);
        groupedLightAdapter = new GroupedLightAdapter(activity);
        individualBandAdapter = new IndividualBandAdapter(activity);
        individualSwitchAdapter = new IndividualSwitchAdapter(activity);
        individualPirSwitchAdapter = new IndividualPirSwitchAdapter(activity);
        dashboardSiteAdapter = new DashboardSiteAdapter(activity);
        dashboardBuildingAdapter = new DashboardBuildingAdapter(activity);
        dashboardLevelAdapter = new DashboardLevelAdapter(activity);
        dashboardRoomAdapter = new DashboardRoomAdapter(activity);


        adapter = new ArrayAdapter<GroupDetailsClass>(activity, R.layout.spinerlayout, spinnerList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(Color.GRAY);
                tv.setText(spinnerList.get(position).getGroupName());
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                tv.setTextColor(Color.BLACK);
                tv.setText(spinnerList.get(position).getGroupName());

                return tv;
            }
        };


        dashboargItemList.setAdapter(dashboardItemAdapter);
        individualLightList.setAdapter(individualLightAdapter);
        individual_Band_list.setAdapter(individualBandAdapter);
        individual_switch_list.setAdapter(individualSwitchAdapter);
        individual_pirswitch_list.setAdapter(individualPirSwitchAdapter);
        dash_site_group_list.setAdapter(dashboardSiteAdapter);
        dash_building_group_list.setAdapter(dashboardBuildingAdapter);
        dashLevelGroupList.setAdapter(dashboardLevelAdapter);
        dashRoomGroupList.setAdapter(dashboardRoomAdapter);
        dashboardSpinner.setAdapter(adapter);
        dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getAllGroups();
                getAllSiteGroup();
                getDevice();
                getBand();
                getSwitch();
                getPirSwitch();
                getAllBUILDINGGroups();
                getAllLevelGroups();
                getAllRoomGroup();
                if (position == 0) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.VISIBLE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);
                } else if (position == 1) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.VISIBLE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);
                } else if (position == 2) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.VISIBLE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);

                } else if (position == 3) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.VISIBLE);
                } else if (position == 4) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.VISIBLE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);

                } else if (position == 5) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.VISIBLE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);

                } else if (position == 6) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.VISIBLE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);

                } else if (position == 7) {
                    groupRoomListLayout.setVisibility(View.GONE);
                    groupLevelListLayout.setVisibility(View.VISIBLE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);

                }else if (position == 8) {
                    groupRoomListLayout.setVisibility(View.VISIBLE);
                    groupLevelListLayout.setVisibility(View.GONE);
                    group_building_list_layout.setVisibility(View.GONE);
                    groupListLayout.setVisibility(View.GONE);
                    group_site_list_layout.setVisibility(View.GONE);
                    individualLightListLayout.setVisibility(View.GONE);
                    individual_band_list_layout.setVisibility(View.GONE);
                    individual_switch_list_layout.setVisibility(View.GONE);
                    individual_pirswitch_list_layout.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        getAllGroups();
        getAllSiteGroup();
//        getAllGroupedLight();
        getDevice();
        getBand();
        getSwitch();
        getPirSwitch();
        getAllBUILDINGGroups();
        getAllLevelGroups();
        getAllRoomGroup();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnClick(R.id.dashboard_home_setting_layout)
//    public void onViewClicked() {
//    }
}
