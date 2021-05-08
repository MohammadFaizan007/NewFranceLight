package com.inferrix.lightsmart.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.activity.HelperActivity;
import com.inferrix.lightsmart.adapter.BuildingAdapter;
import com.inferrix.lightsmart.adapter.GroupAdapter;
import com.inferrix.lightsmart.adapter.LevelAdapter;
import com.inferrix.lightsmart.adapter.RoomAdapter;
import com.inferrix.lightsmart.adapter.SiteAdapter;
import com.inferrix.lightsmart.constant.Constants;
import com.inferrix.lightsmart.constant.PreferencesManager;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.GroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.RoomGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.SiteGroupDetailsClass;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;

public class GroupFragment extends Fragment {

//    @BindView(R.id.group_device_list)
//    ListView groupDeviceList;
//    GroupAdapter groupAdapter;
    Activity activity;
    Unbinder unbinder;
    @BindView(R.id.create_new_group)
    Button createNewGroup;
    @BindView(R.id.create_site_group)
    Button create_site_group;
    @BindView(R.id.create_building_group)
    Button create_building_group;
    @BindView(R.id.create_level_group)
    Button create_level_group;
    @BindView(R.id.create_room_group)
    Button create_room_group;
    @BindView(R.id.dashboard_spinner)
    Spinner dashboardSpinner;

    @BindView(R.id.group_list_layout)
    LinearLayout group_list_layout;
    @BindView(R.id.site_list_layout)
    LinearLayout site_list_layout;
    @BindView(R.id.building_list_layout)
    LinearLayout building_list_layout;
    @BindView(R.id.level_list_layout)
    LinearLayout level_list_layout;
    @BindView(R.id.room_list_layout)
    LinearLayout room_list_layout;

    @BindView(R.id.dash_group_list)
    ListView dash_group_list;
    @BindView(R.id.site_light_list)
    ListView site_light_list;
    @BindView(R.id.building_light_list)
    ListView building_light_list;
    @BindView(R.id.level_light_list)
    ListView level_light_list;
    @BindView(R.id.room_light_list)
    ListView room_light_list;

    ArrayList<GroupDetailsClass> spinnerList;
    ArrayList<GroupDetailsClass> list;
    ArrayAdapter<GroupDetailsClass> adapter;

    ArrayList<RoomGroupDetailsClass> listRoom;
    ArrayList<SiteGroupDetailsClass> listSite;
    ArrayList<BuildingGroupDetailsClass> listBuilding;
    ArrayList<LevelGroupDetailsClass> listLevel;

    GroupAdapter groupAdapter;
    RoomAdapter roomAdapter;
    SiteAdapter siteAdapter;
    BuildingAdapter buildingAdapter;
    LevelAdapter levelAdapter;

    public GroupFragment() {
        spinnerList = new ArrayList<>();
        list = new ArrayList<>();
        listRoom = new ArrayList<>();
        listSite = new ArrayList<>();
        listBuilding = new ArrayList<>();
        listLevel = new ArrayList<>();
    }

    public void setSpinner() {
        spinnerList.clear();
        GroupDetailsClass groupData = new GroupDetailsClass();
        groupData.setGroupId(1);
        groupData.setGroupName("Site Group");
        GroupDetailsClass lightData = new GroupDetailsClass();
        lightData.setGroupId(2);
        lightData.setGroupName("Building Group");
        GroupDetailsClass lightData2 = new GroupDetailsClass();
        lightData2.setGroupId(3);
        lightData2.setGroupName("Floor Group");
        GroupDetailsClass lightData3 = new GroupDetailsClass();
        lightData3.setGroupId(4);
        lightData3.setGroupName("Room Group");
        GroupDetailsClass lightData4 = new GroupDetailsClass();
        lightData4.setGroupId(4);
        lightData4.setGroupName("Group");
        spinnerList.add(groupData);
        spinnerList.add(lightData);
        spinnerList.add(lightData2);
        spinnerList.add(lightData3);
        spinnerList.add(lightData4);
        adapter.notifyDataSetChanged();
    }
    public void getAllGroups() {
        setSpinner();
        list.clear();
        Cursor cursor = sqlHelper.getAllGroup(Integer.parseInt (PreferencesManager.getInstance (getActivity ()).getFkProjectId ()));
        if (cursor.moveToFirst()) {
            do {
                GroupDetailsClass groupData = new GroupDetailsClass();
                groupData.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                groupData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_PROGRESS)));
                groupData.setGroupName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_NAME)));
                groupData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_STATUS)) == 1);
                list.add(groupData);
                // do what ever you want here
            }
            while (cursor.moveToNext());
        }

//
        cursor.close();
//        adapter.notifyDataSetChanged();
        groupAdapter.setList(list);
    }

    public void getAllSiteGroup() {
        setSpinner();
        listSite.clear();
        Cursor cursor = sqlHelper.getAllSiteGroup(Integer.parseInt (PreferencesManager.getInstance (getActivity ()).getFkProjectId ()));
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
        siteAdapter.setList(listSite);
    }

    public void getAllBUILDINGGroups() {
        setSpinner();
        listBuilding.clear();
        Cursor cursor = sqlHelper.getAllBuildingGroup(Integer.parseInt (PreferencesManager.getInstance (getActivity ()).getFkProjectId ()));

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
        buildingAdapter.setList(listBuilding);
    }

    public void getAllLevelGroups(){
        setSpinner();
        listLevel.clear();
        Cursor cursor = sqlHelper.getAllLevelGroup(Integer.parseInt (PreferencesManager.getInstance (getActivity ()).getFkProjectId ()));
        if (cursor.moveToFirst()){
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
        levelAdapter.setList(listLevel);
    }

    public void getAllRoomGroup() {
        setSpinner();
        listRoom.clear();
        Cursor cursorS = sqlHelper.getAllRoomGroup(Integer.parseInt (PreferencesManager.getInstance (getActivity ()).getFkProjectId ()));
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
        roomAdapter.setList(listRoom);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getDevice();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        unbinder = ButterKnife.bind(this, view);

        activity = getActivity();
        if (activity == null)
            return view;
        groupAdapter = new GroupAdapter (activity);
        roomAdapter = new RoomAdapter (activity);
        siteAdapter = new SiteAdapter (activity);
        buildingAdapter =new BuildingAdapter (activity);
        levelAdapter =new LevelAdapter (activity);
        adapter = new ArrayAdapter<GroupDetailsClass>(activity, R.layout.spinerlayout, spinnerList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.GRAY);
                tv.setText(spinnerList.get(position).getGroupName());
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(spinnerList.get(position).getGroupName());

                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        level_light_list.setAdapter(levelAdapter);
        room_light_list.setAdapter(roomAdapter);
        site_light_list.setAdapter(siteAdapter);
        building_light_list .setAdapter(buildingAdapter);
        dash_group_list.setAdapter(groupAdapter);
        dashboardSpinner.setAdapter(adapter);
        dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    level_list_layout.setVisibility(View.GONE);
                    room_list_layout.setVisibility(View.GONE);
                    site_list_layout.setVisibility(View.VISIBLE);
                    building_list_layout.setVisibility(View.GONE);
                    group_list_layout.setVisibility(View.GONE);
                } else if (position == 1) {
                    level_list_layout.setVisibility(View.GONE);
                    room_list_layout.setVisibility(View.GONE);
                    site_list_layout.setVisibility(View.GONE);
                    building_list_layout.setVisibility(View.VISIBLE);
                    group_list_layout.setVisibility(View.GONE);
                } else if (position == 2) {
                    level_list_layout.setVisibility(View.VISIBLE);
                    room_list_layout.setVisibility(View.GONE);
                    site_list_layout.setVisibility(View.GONE);
                    building_list_layout.setVisibility(View.GONE);
                    group_list_layout.setVisibility(View.GONE);
                }else if (position==3) {
                    level_list_layout.setVisibility(View.GONE);
                    room_list_layout.setVisibility(View.VISIBLE);
                    site_list_layout.setVisibility(View.GONE);
                    building_list_layout.setVisibility(View.GONE);
                    group_list_layout.setVisibility(View.GONE);

                }else if (position==4) {
                    level_list_layout.setVisibility(View.GONE);
                    room_list_layout.setVisibility(View.GONE);
                    site_list_layout.setVisibility(View.GONE);
                    building_list_layout.setVisibility(View.GONE);
                    group_list_layout.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        if (activity == null)
//            return view;
//        groupAdapter = new GroupAdapter(activity);
//        groupDeviceList.setAdapter(groupAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllGroups();

//        getAllGroupedLight();
        getAllRoomGroup();
        getAllSiteGroup();
        getAllBUILDINGGroups();
        getAllLevelGroups();
//        if (groupAdapter!=null)
//            groupAdapter.getAllGroups();
    }


    @OnClick({R.id.create_new_group,R.id.create_site_group,R.id.create_building_group,R.id.create_level_group,R.id.create_room_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_new_group:
                Intent intent = new Intent(activity, HelperActivity.class);
                intent.putExtra(Constants.MAIN_KEY, Constants.CREATE_GROUP);
                intent.putExtra ("projectId", PreferencesManager.getInstance (getActivity ()).getFkProjectId ());
                intent.putExtra("type","group");
                activity.startActivity(intent);
                break;

            case R.id.create_site_group:
                Intent site = new Intent(activity, HelperActivity.class);
                site.putExtra(Constants.MAIN_KEY, Constants.CREATE_GROUP);
                site.putExtra ("projectId", PreferencesManager.getInstance (getActivity ()).getFkProjectId ());
                site.putExtra("type","site");
                activity.startActivity(site);
                break;

            case R.id.create_building_group:
                Intent building = new Intent(activity, HelperActivity.class);
                building.putExtra(Constants.MAIN_KEY, Constants.CREATE_GROUP);
                building.putExtra ("projectId", PreferencesManager.getInstance (getActivity ()).getFkProjectId ());
                building.putExtra("type","building");
                activity.startActivity(building);
                break;

            case R.id.create_level_group:
                Intent level = new Intent(activity, HelperActivity.class);
                level.putExtra(Constants.MAIN_KEY, Constants.CREATE_GROUP);
                level.putExtra ("projectId", PreferencesManager.getInstance (getActivity ()).getFkProjectId ());
                level.putExtra("type","level");
                activity.startActivity(level);
                break;

            case R.id.create_room_group:
                Intent room = new Intent(activity, HelperActivity.class);
                room.putExtra(Constants.MAIN_KEY, Constants.CREATE_GROUP);
                room.putExtra ("projectId", PreferencesManager.getInstance (getActivity ()).getFkProjectId ());
                room.putExtra("type","room");
                activity.startActivity(room);
                break;
        }

    }

}
