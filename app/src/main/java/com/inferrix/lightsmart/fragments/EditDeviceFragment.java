package com.inferrix.lightsmart.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.google.android.material.textfield.TextInputEditText;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ArrayUtilities;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType;
import com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType;
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
import com.inferrix.lightsmart.activity.HelperActivity;
import com.inferrix.lightsmart.constant.Constants;
import com.inferrix.lightsmart.constant.MinMaxFilter;
import com.inferrix.lightsmart.constant.PreferencesManager;
import com.niftymodaldialogeffects.Effectstype;
import com.niftymodaldialogeffects.NiftyDialogBuilder;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.Gravity.CENTER;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_PROGRESS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_STATUS;
import static com.inferrix.lightsmart.EncodeDecodeModule.ArrayUtilities.bytesToHex;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_BUILDING_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_LEVEL_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_ROOM_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_SITE_GROUP;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.LIGHT_LEVEL_COMMAND;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.LUX_LEVEL_FOUR_INFO;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.LUX_LEVEL_ONE_INFO;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.LUX_LEVEL_THREE_INFO;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.LUX_LEVEL_TWO_INFO;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.GROUP_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_LEVEL_COMMAND_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_LEVEL_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_STATE_COMMAND_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_STATE_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.SELECT_MASTER_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.UNSELECT_MASTER_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.UPDATE_GROUP_RESPONSE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;

public class EditDeviceFragment extends Fragment implements AdvertiseResultInterface, ReceiverResultInterface {


    @BindView(R.id.group_name_text)
    TextView groupNameText;

    @BindView(R.id.edit_light_status)
    TextView editLightStatus;

    @BindView(R.id.edit_light_deriveType)
    TextView lightDeriveType;

    @BindView(R.id.edit_light_name)
    EditText editLightName;

    @BindView(R.id.group_list_spinner)
    Spinner groupListSpinner;

    //    @BindView(R.id.light_save)
//    ImageView lightSave;
//    @BindView(R.id.light_delete)
//    ImageView lightDelete;

    @BindView(R.id.status_switch)
    JellyToggleButton lightStatus;

    @BindView(R.id.group_save)
    Button lightSave;
    //    @BindView(R.id.details)
//    Button details;
    @BindView(R.id.set_level)
    Button set_level;
    @BindView(R.id.light_set_master)
    Button light_set_master;
    //    @BindView(R.id.edit_light_save)
//    LinearLayout editLightSave;
    Unbinder unbinder;
    ScannerTask scannerTask;
    AnimatedProgress animatedProgress;
    String TAG = this.getClass().getSimpleName();
    Activity activity;
    DeviceClass deviceClass;
    int spinnerSelectedPosition = 0;
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
    @BindView(R.id.light_edit)
    ImageView lightEdit;
    @BindView(R.id.light_save)
    ImageView editLightSave;

    @BindView(R.id.light_delete)
    ImageView editLightDelete;
    String Type;
    @BindView(R.id.add_group)
    Button addGroup;
    @BindView(R.id.edit_layout)
    RelativeLayout editLayout;
    @BindView(R.id.unset_master)
    Button unsetMaster;
    @BindView(R.id.reset_node)
    Button reset_node;
    @BindView(R.id.lux_leve)
    Button lux_leve;
    @BindView(R.id.enable_attribute)
    Button enable_attribute;
    String group = "";
    AdvertiseTask advertiseTask;
    String name = "";
    int position;
    EditText lux_level_one, dimming_level1, lux_level_two, lux_level_three, lux_level_four, lux_level_five, dimming_level2, dimming_level3, dimming_level4;
    String enableSite = "", enableBuilding = "", enableLevel = "", enableRoom = "", enablegroup = "";
    String luxLevelOne = "", luxLevelTwo = "", luxLevelThree = "", luxLevelFour = "", luxLevelFive = "", dimingLevelOne = "", dimingLevelTwo = "", dimingLevelThree = "", dimingLevelFour = "";


    public EditDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_device, container, false);
        activity = getActivity();
        unbinder = ButterKnife.bind(this, view);
        Type = getActivity().getIntent().getStringExtra("type");
        name = getActivity().getIntent().getStringExtra("name");
        position = getActivity().getIntent().getIntExtra("pos", 0);
        if (deviceClass == null) {
            deviceClass = new DeviceClass();
        }
        list = new ArrayList<>();
        groupSiteDetailsClasses = new ArrayList<>();
        groupRoomDetailsClasses = new ArrayList<>();
        groupBuildingDetailsClasses = new ArrayList<>();
        groupLevelDetailsClasses = new ArrayList<>();

        groupNameText.setText(deviceClass.getDeviceName());
        editLightName.setText(deviceClass.getDeviceName());
        scannerTask = new ScannerTask(activity, this);
        animatedProgress = new AnimatedProgress(activity);
        animatedProgress.setCancelable(false);
        //animatedProgress.showProgress();


//        lightDeriveType.setText(String.format("Device Type:%s", deviceClass.getDeriveType()));

//        ByteQueue byteQueue1 = new ByteQueue();
//        byteQueue1.push(RxMethodType.LIGHT_LEVEL);
//        byteQueue1.pushU4B(deviceClass.getDeviceUID());
//        byteQueue1.push(0x00);
//        AdvertiseTask advertiseTask1;
//        advertiseTask1 = new AdvertiseTask(this, activity);
//        advertiseTask1.setByteQueue(byteQueue1);
//        advertiseTask1.setSearchRequestCode(LIGHT_LEVEL_RESPONSE);
//        advertiseTask1.startAdvertising();


        if (deviceClass.getStatus())
            editLightStatus.setText("Light Status:On");
        else
            editLightStatus.setText("Light Status:Off");

        if (Type.equalsIgnoreCase("light")) {
            lightStatus.setVisibility(View.VISIBLE);
            set_level.setVisibility(View.VISIBLE);
            light_set_master.setVisibility(View.VISIBLE);
        } else {
            lightStatus.setVisibility(View.GONE);
            set_level.setVisibility(View.GONE);
            light_set_master.setVisibility(View.GONE);
        }
//        details.setOnClickListener(v -> {
//            Intent intent = new Intent(activity, HelperActivity.class);
//            intent.putExtra(Constants.MAIN_KEY, Constants.READ_DETAILS);
//            intent.putExtra(Constants.LIGHT_DETAIL_KEY, deviceClass);
//            activity.startActivity(intent);
//        });
//if(deviceClass.getGroupId()==0)
//{
//    if(deviceClass.getStatus())
//        editLightStatus.setText("Light Status:On");
//    else
//        editLightStatus.setText("Light Status:Off");
//
//
//    ByteQueue byteQueue = new ByteQueue();
//    byteQueue.push(RxMethodType.LIGHT_STATE);
//    byteQueue.pushU4B(deviceClass.getDeviceUID());
//    byteQueue.push(0x00);
//    AdvertiseTask advertiseTask;
//    advertiseTask = new AdvertiseTask(this, activity);
//    advertiseTask.setByteQueue(byteQueue);
//    advertiseTask.setSearchRequestCode(LIGHT_STATE_RESPONSE);
//    advertiseTask.startAdvertising();

        setLightStatus();
        lightStatus.setChecked(deviceClass.getStatus());
//}
//else {
//    editLightStatus.setText("");
//    Toast.makeText(activity, "group command", Toast.LENGTH_SHORT).show();
//    ByteQueue byteQueue = new ByteQueue();
//    byteQueue.push(RxMethodType.GROUP);
//    byteQueue.pushU4B(deviceClass.getDeviceUID());
//    byteQueue.push(0x00);
//    AdvertiseTask advertiseTask;
//    advertiseTask = new AdvertiseTask(this, activity);
//    advertiseTask.setByteQueue(byteQueue);
//    advertiseTask.setSearchRequestCode(GROUP_RESPONSE);
//    advertiseTask.startAdvertising();
//}
        adapter = new ArrayAdapter<GroupDetailsClass>(activity, R.layout.spinerlayout, list) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.GRAY);
                tv.setText(list.get(position).getGroupName());
                Log.e("Group======>", list.get(position).getGroupName());
                group = list.get(position).getGroupName();
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(list.get(position).getGroupName());

                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        adapterSite = new ArrayAdapter<SiteGroupDetailsClass>(activity, R.layout.spinerlayout, groupSiteDetailsClasses) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(Color.GRAY);
                tv.setText(groupSiteDetailsClasses.get(position).getGroupSiteName());
                Log.e("Site=====>", groupSiteDetailsClasses.get(position).getGroupSiteName());

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(groupSiteDetailsClasses.get(position).getGroupSiteName());
                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };
        adapterBuilding = new ArrayAdapter<BuildingGroupDetailsClass>(activity, R.layout.spinerlayout, groupBuildingDetailsClasses) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(Color.GRAY);
                tv.setText(groupBuildingDetailsClasses.get(position).getGroupBuildingName());
                Log.e("Building=====>", groupBuildingDetailsClasses.get(position).getGroupBuildingName());
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(groupBuildingDetailsClasses.get(position).getGroupBuildingName());
                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };


        adapterLevel = new ArrayAdapter<LevelGroupDetailsClass>(activity, R.layout.spinerlayout, groupLevelDetailsClasses) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(Color.GRAY);
                tv.setText(groupLevelDetailsClasses.get(position).getGroupLevelName());
                Log.e("Level=====>", groupLevelDetailsClasses.get(position).getGroupLevelName());
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(groupLevelDetailsClasses.get(position).getGroupLevelName());

                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };

        adapterRoom = new ArrayAdapter<RoomGroupDetailsClass>(activity, R.layout.spinerlayout, groupRoomDetailsClasses) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
//                tv.setTextColor("#0000");
                tv.setTextColor(Color.GRAY);
                tv.setText(groupRoomDetailsClasses.get(position).getGroupRoomName());
                Log.e("Room======>", groupRoomDetailsClasses.get(position).getGroupRoomName());
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(groupRoomDetailsClasses.get(position).getGroupRoomName());
                /*// If this item is selected item
                if(position == mSelectedIndex){
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }*/

                // Return the modified view
                return tv;
            }
        };

        groupListSpinner.setAdapter(adapter);
        return view;
    }

    public void getAllGroups() {
        list.clear();
        GroupDetailsClass noGroup = new GroupDetailsClass();
        noGroup.setGroupName("No Group");
        list.add(noGroup);
        Cursor cursor = sqlHelper.getAllGroup();
        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                GroupDetailsClass groupData = new GroupDetailsClass();
                groupData.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                groupData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_PROGRESS)));
                groupData.setGroupName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_NAME)));
                groupData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_STATUS)) == 1);
                list.add(groupData);
                if (groupData.getGroupId() == deviceClass.getGroupId()) {
                    spinnerSelectedPosition = i;
//                    Toast.makeText(activity, "i=" + i, Toast.LENGTH_SHORT).show();
                }
                i++;
                // do what ever you want here
            }
            while (cursor.moveToNext());
        }

//
        cursor.close();
        adapter.notifyDataSetChanged();
        groupListSpinner.setSelection(spinnerSelectedPosition);
    }

    public GroupDetailsClass getGroup(int id) {

        for (GroupDetailsClass groupDetailsClass : list) {
            if (groupDetailsClass.getGroupId() == id)
                return groupDetailsClass;
        }

        return new GroupDetailsClass();
    }

    public void getAllROOMGroups() {
        groupRoomDetailsClasses.clear();
        RoomGroupDetailsClass noGroupData = new RoomGroupDetailsClass();
        noGroupData.setGroupRoomName("No Room Group");
        groupRoomDetailsClasses.add(noGroupData);
        Cursor cursor = sqlHelper.getAllRoomGroup();

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                RoomGroupDetailsClass groupRoomData = new RoomGroupDetailsClass();
                groupRoomData.setRoomGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMID)));
                groupRoomData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_ROOM_GROUP_PROGRESS)));
                groupRoomData.setGroupRoomName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME)));
                groupRoomData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMSTATUS)) == 1);
                groupRoomDetailsClasses.add(groupRoomData);
                if (noGroupData.getRoomGroupId() == deviceClass.getGroupId()) {
                    spinnerSelectedPosition = i;
//                    Toast.makeText(activity, "i=" + i, Toast.LENGTH_SHORT).show();
                }
                i++;
                // do what ever you want here
            }
            while (cursor.moveToNext());
        }

//
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    public void getAllSITEGroups() {
        groupSiteDetailsClasses.clear();
        SiteGroupDetailsClass noGroupData = new SiteGroupDetailsClass();
        noGroupData.setGroupSiteId(0);
        noGroupData.setGroupSiteName("No Site Group");
        noGroupData.setGroupSiteStatus(true);
        groupSiteDetailsClasses.add(noGroupData);
        Cursor cursorS = sqlHelper.getAllSiteGroup();

        if (cursorS.moveToFirst()) {
            do {
                SiteGroupDetailsClass groupSiteData = new SiteGroupDetailsClass();
                groupSiteData.setGroupSiteId(cursorS.getInt(cursorS.getColumnIndex(DatabaseConstant.COLUMN_GROUP_SITE_ID)));
                groupSiteData.setGroupSiteDimming(cursorS.getInt(cursorS.getColumnIndex(DatabaseConstant.COLUMN_SITE_GROUP_PROGRESS)));
                groupSiteData.setGroupSiteName(cursorS.getString(cursorS.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_SITENAME)));
                groupSiteData.setGroupSiteStatus(cursorS.getInt(cursorS.getColumnIndex(DatabaseConstant.COLUMN_GROUP_SITESTATUS)) == 1);
                groupSiteDetailsClasses.add(groupSiteData);
                // do what ever you want here
            }
            while (cursorS.moveToNext());
        }
        cursorS.close();
        adapterSite.notifyDataSetChanged();

    }

    public void getAllBUILDINGGroups() {
        groupBuildingDetailsClasses.clear();
        BuildingGroupDetailsClass noGroupData = new BuildingGroupDetailsClass();
        noGroupData.setGroupBuildingId(0);
        noGroupData.setGroupBuildingName("No Building Group");
        noGroupData.setBuildingGroupStatus(true);
        groupBuildingDetailsClasses.add(noGroupData);
        Cursor cursorB = sqlHelper.getAllBuildingGroup();

        if (cursorB.moveToFirst()) {
            do {
                BuildingGroupDetailsClass groupBuildingData = new BuildingGroupDetailsClass();
                groupBuildingData.setGroupBuildingId(cursorB.getInt(cursorB.getColumnIndex(DatabaseConstant.COLUMN_GROUP_BUILDINGID)));
                groupBuildingData.setBuildingGroupDimming(cursorB.getInt(cursorB.getColumnIndex(DatabaseConstant.COLUMN_BUILDING_GROUP_PROGRESS)));
                groupBuildingData.setGroupBuildingName(cursorB.getString(cursorB.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDINGNAME)));
                groupBuildingData.setBuildingGroupStatus(cursorB.getInt(cursorB.getColumnIndex(DatabaseConstant.COLUMN_GROUP_BUILDINGSTATUS)) == 1);
                groupBuildingDetailsClasses.add(groupBuildingData);
                // do what ever you want here
            }
            while (cursorB.moveToNext());
        }
        cursorB.close();
        adapterBuilding.notifyDataSetChanged();


    }

    public void getAllLEVELGroups() {
        groupLevelDetailsClasses.clear();
        LevelGroupDetailsClass noGroupData = new LevelGroupDetailsClass();
        noGroupData.setGroupLevelId(0);
        noGroupData.setGroupLevelName("No Level Group");
        noGroupData.setLevelGroupStatus(true);
        groupLevelDetailsClasses.add(noGroupData);
        Cursor cursorB = sqlHelper.getAllLevelGroup();
        if (cursorB.moveToFirst()) {
            do {
                LevelGroupDetailsClass groupLevelData = new LevelGroupDetailsClass();
                groupLevelData.setGroupLevelId(cursorB.getInt(cursorB.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELID)));
                groupLevelData.setLevelGroupDimming(cursorB.getInt(cursorB.getColumnIndex(DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS)));
                groupLevelData.setGroupLevelName(cursorB.getString(cursorB.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME)));
                groupLevelData.setLevelGroupStatus(cursorB.getInt(cursorB.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELSTATUS)) == 1);
                groupLevelDetailsClasses.add(groupLevelData);
                // do what ever you want here
            }
            while (cursorB.moveToNext());
        }
        cursorB.close();
        adapterLevel.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setLightStatus() {
        lightStatus.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {

                boolean switchStatus = state != State.LEFT;
                if (deviceClass.getStatus() == switchStatus) {
//                    Log.w("Advertise","state is same");
                    return;
                }
                Log.w("DeviceID", deviceClass.getDeviceUID() + "");

                AdvertiseTask advertiseTask;
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push(RxMethodType.LIGHT_STATE_COMMAND);       ////State Command
                byteQueue.push(0x01);
                byteQueue.pushU4B(deviceClass.getDeviceUID());      ////  12 is static vale for Node id
//                byteQueue.push(0x00);                                    ///0x00 – OFF    0x01 – ON
//                scannerTask.setRequestCode(TxMethodType.LIGHT_STATE_COMMAND_RESPONSE);
                Log.w(TAG, state + "");
                switch (state) {
                    case LEFT:
                        //// remove group method type
//                        byteQueue.pushS4B(12);
                        Log.w("SwitchStatus", "Left");
                        byteQueue.push(0x00);   //0x00 – OFF    0x01 – ON
//                        arrayList.get(position).setStatus(false);

                        break;
                    case RIGHT:
                        Log.w("SwitchStatus", "Right");
                        byteQueue.push(0x01);   //0x00 – OFF    0x01 – ON
//                        arrayList.get(position).setStatus(true);
                        break;
                    case LEFT_TO_RIGHT:

                        return;

                    case RIGHT_TO_LEFT:
                        return;

                }
                advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
                advertiseTask.setByteQueue(byteQueue);
                advertiseTask.setSearchRequestCode(LIGHT_STATE_COMMAND_RESPONSE);
                advertiseTask.startAdvertising();
            }
        });
    }

    void showDialog() {
        levelProgress = deviceClass.getDeviceDimming();

        final Dialog dialog = new Dialog(activity);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setGravity(CENTER);

        dialog.setContentView(R.layout.customize_group);

        TextView deviceName = dialog.findViewById(R.id.customize_group_name);
        SeekBar seekBar = dialog.findViewById(R.id.customizeGroupSeekBar);
        Button button = dialog.findViewById(R.id.customiseGroupSave);

        TextView levelPercentage = dialog.findViewById(R.id.level_percentage);
        levelPercentage.setText(levelProgress + " %");

        seekBar.setProgress(deviceClass.getDeviceDimming());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                levelProgress = i;
                levelPercentage.setText(levelProgress + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                String hex = Integer.toHexString(levelProgress);
                Log.w("IndividualLight", hex + " " + String.format("%02X", levelProgress));
                AdvertiseTask advertiseTask;
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push(LIGHT_LEVEL_COMMAND);   //// Light Level Command method type
                byteQueue.push(0x01);
                byteQueue.pushU4B(deviceClass.getDeviceUID());   ////deviceDetail.getGroupId()   node id;
                byteQueue.push(levelProgress);    ////0x00-0x64
                scannerTask.setRequestCode(TxMethodType.LIGHT_LEVEL_COMMAND_RESPONSE);
                advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
                advertiseTask.setByteQueue(byteQueue);
                advertiseTask.setSearchRequestCode(LIGHT_LEVEL_COMMAND_RESPONSE);
                advertiseTask.startAdvertising();

            }
        });
//        button.setOnClickListener(view -> {
//
//
//            String hex = Integer.toHexString(levelProgress);
//            Log.w("IndividualLight", hex + " " + String.format("%02X", levelProgress));
//            AdvertiseTask advertiseTask;
//            ByteQueue byteQueue = new ByteQueue();
//            byteQueue.push(LIGHT_LEVEL_COMMAND);   //// Light Level Command method type
//            byteQueue.pushU4B(deviceClass.getDeviceUID());   ////deviceDetail.getGroupId()   node id;
//            byteQueue.push(levelProgress);    ////0x00-0x64
////            scannerTask.setRequestCode(TxMethodType.LIGHT_LEVEL_COMMAND_RESPONSE);
//            advertiseTask = new AdvertiseTask(this, activity);
//            advertiseTask.setByteQueue(byteQueue);
//            advertiseTask.setSearchRequestCode(LIGHT_LEVEL_COMMAND_RESPONSE);
//            advertiseTask.startAdvertising();
////            Log.w("IndividualLight",AppHelper.sqlHelper.updateDevice(deviceDetail.getDeviceUID(),contentValues)+"");
//            dialog.dismiss();
//        });

        deviceName.setText(deviceClass.getDeviceName());

        dialog.show();

    }

    public void checkMaster() {
        DeviceClass deviceClass1 = null;
        Cursor cursor = sqlHelper.getLightDetails(deviceClass.getDeviceUID());
        if (cursor.moveToFirst()) {
            do {
                deviceClass1 = new DeviceClass();
                deviceClass1.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                deviceClass1.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                deviceClass1.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_PROGRESS)));
                deviceClass1.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                deviceClass1.setMasterStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_MASTER_STATUS)));
                deviceClass1.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_STATUS)) == 1);

                // do what ever you want here
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (deviceClass1 != null) {
            if (deviceClass1.getMasterStatus() == 1) {
                enableAttributes();
//                showAlert(String.format("'%s' is master.", deviceClass.getDeviceName()));
            } else {
                showAlert(String.format("'%s' is not master.", deviceClass.getDeviceName()));
            }
        }
    }

    @OnClick({R.id.light_delete, R.id.light_save, R.id.light_edit, R.id.group_save, R.id.light_set_master, R.id.light_check_group, R.id.check_level, R.id.set_level, R.id.light_check_status, R.id.light_check_master,
            R.id.add_group, R.id.reset_node, R.id.unset_master, R.id.hold_time, R.id.lux_leve, R.id.enable_attribute})
    public void onViewClicked(View view) {
        final AdvertiseTask[] advertiseTask = new AdvertiseTask[1];
        final ByteQueue[] byteQueue = new ByteQueue[1];
        switch (view.getId()) {
            case R.id.light_delete:
                deleteDialog();

//                if (sqlHelper.deleteLight(deviceClass.getDeviceUID()) > 0) {
//                    Toast.makeText(activity, "Light deleted.", Toast.LENGTH_SHORT).show();
//                    activity.onBackPressed();
//                } else
//                    Toast.makeText(activity, "Some error to delete light", Toast.LENGTH_SHORT).show();
                break;
            case R.id.light_edit:
                if (editLightName.isEnabled()) {
                    editLightName.setEnabled(false);
                    lightEdit.setVisibility(View.VISIBLE);
                    editLightSave.setVisibility(View.GONE);
                    editLightDelete.setVisibility(View.GONE);
                } else {
                    editLightName.setEnabled(true);
                    lightEdit.setVisibility(View.GONE);
                    editLightSave.setVisibility(View.VISIBLE);
                    editLightDelete.setVisibility(View.VISIBLE);
                }
                break;
//            case R.id.light_check_group:
//
//                byteQueue[0] = new ByteQueue();
//                byteQueue[0].push(RxMethodType.GROUP);
//                byteQueue[0].pushU4B(deviceClass.getDeviceUID());
//                byteQueue[0].push(0x00);
//                advertiseTask[0] = new AdvertiseTask(this, activity);
//                advertiseTask[0].setByteQueue(byteQueue[0]);
//                advertiseTask[0].setSearchRequestCode(GROUP_RESPONSE);
//                advertiseTask[0].startAdvertising();
//
//                break;
//            case R.id.light_set_master:
//                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
//                dialogBuilder
//                        .withTitle("Master Light")
//                        .withEffect(Effectstype.Shake)
//                        .withMessage("Set light '" + deviceClass.getDeviceName() + "' as master light")
//                        .withButton1Text("OK")
//                        .setButton1Click(v -> {
//                            byteQueue[0] = new ByteQueue();
//                            byteQueue[0].push(RxMethodType.SELECT_MASTER);
//                            byteQueue[0].pushU4B(deviceClass.getDeviceUID());
//                            byteQueue[0].push(0x00);
//                            advertiseTask[0] = new AdvertiseTask(EditDeviceFragment.this, activity);
//                            advertiseTask[0].setByteQueue(byteQueue[0]);
//                            advertiseTask[0].setSearchRequestCode(SELECT_MASTER_RESPONSE);
//                            advertiseTask[0].startAdvertising();
//                            dialogBuilder.dismiss();
//                        }).withButton2Text("Cancel")
//                        .setButton2Click(v -> {
//                            dialogBuilder.dismiss();
//                        })
//                        .show();
//
//                break;
//            case R.id.light_check_status:
//                byteQueue[0] = new ByteQueue();
//                byteQueue[0].push(RxMethodType.LIGHT_STATE);
//                byteQueue[0].pushU4B(deviceClass.getDeviceUID());
//                byteQueue[0].push(0x00);
//                advertiseTask[0] = new AdvertiseTask(this, activity);
//                advertiseTask[0].setByteQueue(byteQueue[0]);
//                advertiseTask[0].setSearchRequestCode(LIGHT_STATE_RESPONSE);
//                advertiseTask[0].startAdvertising();
//
//                break;
            case R.id.light_check_group:
                if (deviceClass.getGroupId() == 0)
                    showAlert("No Group");
                else {
                    showAlert(String.format("Group of %s is %s.", deviceClass.getDeviceName(), sqlHelper.getGroupDetails(deviceClass.getGroupId()).getGroupName()));
                }

//                byteQueue[0] = new ByteQueue();
//                byteQueue[0].push(RxMethodType.GROUP);
//                byteQueue[0].pushU4B(deviceClass.getDeviceUID());
//                byteQueue[0].push(0x00);
//                advertiseTask[0] = new AdvertiseTask(this, activity);
//                advertiseTask[0].setByteQueue(byteQueue[0]);
//                advertiseTask[0].setSearchRequestCode(GROUP_RESPONSE);
//                advertiseTask[0].startAdvertising();

                break;
            case R.id.light_set_master:
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
                dialogBuilder
                        .withTitle("Master Light")
                        .withEffect(Effectstype.Shake)
                        .withMessage("Set light '" + deviceClass.getDeviceName() + "' as master light")
                        .withButton1Text("OK")
                        .setButton1Click(v -> {
                            byteQueue[0] = new ByteQueue();
                            byteQueue[0].push(RxMethodType.SELECT_MASTER);
                            byteQueue[0].push(0x01);
                            byteQueue[0].pushU4B(deviceClass.getDeviceUID());
                            byteQueue[0].push(0x01);
                            Log.e("Master", byteQueue[0].toString());
                            advertiseTask[0] = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
                            advertiseTask[0].setByteQueue(byteQueue[0]);
                            advertiseTask[0].setSearchRequestCode(SELECT_MASTER_RESPONSE);
                            advertiseTask[0].startAdvertising();
                            dialogBuilder.dismiss();
                        }).withButton2Text("Cancel")
                        .setButton2Click(v -> {
                            dialogBuilder.dismiss();
                        })
                        .show();

                break;

            case R.id.unset_master:
                NiftyDialogBuilder dialogBuilder1 = NiftyDialogBuilder.getInstance(activity);
                dialogBuilder1
                        .withTitle("Normal Light")
                        .withEffect(Effectstype.Shake)
                        .withMessage("Set light '" + deviceClass.getDeviceName() + "' as normal light")
                        .withButton1Text("OK")
                        .setButton1Click(v -> {
                            byteQueue[0] = new ByteQueue();
                            byteQueue[0].push(RxMethodType.SELECT_MASTER);
                            byteQueue[0].push(0x01);
                            byteQueue[0].pushU4B(deviceClass.getDeviceUID());
                            byteQueue[0].push(0x00);
                            Log.e("Master", byteQueue[0].toString());
                            advertiseTask[0] = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
                            advertiseTask[0].setByteQueue(byteQueue[0]);
                            advertiseTask[0].setSearchRequestCode(UNSELECT_MASTER_RESPONSE);
                            advertiseTask[0].startAdvertising();
                            dialogBuilder1.dismiss();
                        }).withButton2Text("Cancel")
                        .setButton2Click(v -> {
                            dialogBuilder1.dismiss();
                        })
                        .show();

                break;

            case R.id.reset_node:
                NiftyDialogBuilder dialogBuilder12 = NiftyDialogBuilder.getInstance(activity);
                dialogBuilder12
                        .withTitle("Reset Node")
                        .withEffect(Effectstype.Shake)
                        .withMessage("Are you sure to reset '" + deviceClass.getDeviceName() + "'This device loss all saving data")
                        .withButton1Text("OK")
                        .setButton1Click(v -> {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_ONE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_TWO, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_THREE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_FOUR, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_FIVE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_SIX, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_SEVEN, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_EIGET, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_ONE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_TWO, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_THREE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_FOUR, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_FIVE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_SIX, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_SEVEN, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_EIGET, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_ONE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_TWO, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_THREE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FOUR, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FIVE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_ONE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_TWO, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_THREE, "");
                            contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_Four, "");
                            contentValues.put(COLUMN_DEVICE_MASTER_STATUS, 0);
                            contentValues.put(DatabaseConstant.COLUMN_GROUP_SITE_ID, "0");
                            contentValues.put(DatabaseConstant.COLUMN_GROUP_BUILDINGID, "0");
                            contentValues.put(DatabaseConstant.COLUMN_GROUP_LEVELID, "0");
                            contentValues.put(DatabaseConstant.COLUMN_GROUP_ROOMID, "0");
                            contentValues.put(DatabaseConstant.COLUMN_GROUP_ID, "0");
                            if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues)) {
                            }
                            List<String> list = new ArrayList<>();
                            if (PreferencesManager.getInstance(getContext()).getList(Constants.ATTRIBUTE_TYPE) != null) {
                                list = PreferencesManager.getInstance(getContext()).getList(Constants.ATTRIBUTE_TYPE);
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).contains(name)) {
                                        list.remove(i);
                                    }
                                }
                            }
                            list.add("site" + "disable" + name);
                            list.add("building" + "disable" + name);
                            list.add("level" + "disable" + name);
                            list.add("room" + "disable" + name);
                            list.add("group" + "disable" + name);
                            PreferencesManager.getInstance(getContext()).setList(Constants.ATTRIBUTE_TYPE, list);
                            byteQueue[0] = new ByteQueue();
                            byteQueue[0].push(RxMethodType.RESET_NODE_INFO);
                            byteQueue[0].push(0x01);
                            byteQueue[0].pushU4B(deviceClass.getDeviceUID());
                            advertiseTask[0] = new AdvertiseTask(this, activity);
                            advertiseTask[0].setByteQueue(byteQueue[0]);
                            Log.e("RESET===>", byteQueue[0].toString());
                            advertiseTask[0].startAdvertising();

                            dialogBuilder12.dismiss();
                        }).withButton2Text("Cancel")
                        .setButton2Click(v -> {
                            dialogBuilder12.dismiss();
                        })
                        .show();
                break;
            case R.id.hold_time:
//                holdTimeDialog();
                DeviceClass deviceClass1 = null;
                Cursor cursor = sqlHelper.getLightDetails(deviceClass.getDeviceUID());
                if (cursor.moveToFirst()) {
                    do {
                        deviceClass1 = new DeviceClass();
                        deviceClass1.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                        deviceClass1.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                        deviceClass1.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_PROGRESS)));
                        deviceClass1.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                        deviceClass1.setMasterStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_MASTER_STATUS)));
                        deviceClass1.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_STATUS)) == 1);

                        // do what ever you want here
                    } while (cursor.moveToNext());
                }
                cursor.close();
                if (deviceClass1 != null) {
                    if (deviceClass1.getMasterStatus() == 1) {
                        holdTimeDialog();
//                showAlert(String.format("'%s' is master.", deviceClass.getDeviceName()));
                    } else {
                        showAlert(String.format("'%s' is not master.", deviceClass.getDeviceName()));
                    }
                }

                break;
            case R.id.light_check_status:
                showAlert(deviceClass.getStatus() ? "Light is on" : "Light is off");
//                byteQueue[0] = new ByteQueue();
//                byteQueue[0].push(RxMethodType.LIGHT_STATE);
//                byteQueue[0].pushU4B(deviceClass.getDeviceUID());
//                byteQueue[0].push(0x00);
//                advertiseTask[0] = new AdvertiseTask(this, activity);
//                advertiseTask[0].setByteQueue(byteQueue[0]);
//                advertiseTask[0].setSearchRequestCode(LIGHT_STATE_RESPONSE);
//                advertiseTask[0].startAdvertising();

                break;
            case R.id.light_check_master:
//                Toast.makeText(activity, "Will be soon.", Toast.LENGTH_SHORT).show();
                checkMaster();

                break;
            case R.id.check_level:

//                byteQueue[0] = new ByteQueue();
//                byteQueue[0].push(RxMethodType.LIGHT_LEVEL);
//                byteQueue[0].pushU4B(deviceClass.getDeviceUID());
//                byteQueue[0].push(0x00);
//                advertiseTask[0] = new AdvertiseTask(this, activity);
//                advertiseTask[0].setByteQueue(byteQueue[0]);
//                advertiseTask[0].setSearchRequestCode(LIGHT_LEVEL_RESPONSE);
//                advertiseTask[0].startAdvertising();
                showAlert("Light dimming level is " + deviceClass.getDeviceDimming());
                break;

            case R.id.set_level:
                showDialog();

                break;
            case R.id.light_save:
                saveData();
                break;
            case R.id.group_save:
                if (groupListSpinner.getSelectedItemPosition() != spinnerSelectedPosition) {
//                    Toast.makeText(activity, "Selected Group " + list.get(groupListSpinner.getSelectedItemPosition()).getGroupName(), Toast.LENGTH_SHORT).show();
                    byteQueue[0] = new ByteQueue();
                    byteQueue[0].push(RxMethodType.UPDATE_GROUP);
                    byteQueue[0].pushU4B(deviceClass.getDeviceUID());
                    byteQueue[0].push(list.get(groupListSpinner.getSelectedItemPosition()).getGroupId());


                    advertiseTask[0] = new AdvertiseTask(this, activity, 5 * 1000);
                    advertiseTask[0].setByteQueue(byteQueue[0]);
                    advertiseTask[0].setSearchRequestCode(UPDATE_GROUP_RESPONSE);
                    advertiseTask[0].startAdvertising();

                } else
                    saveData();
                break;

            case R.id.add_group:
                Intent intent = new Intent(activity, HelperActivity.class);
                intent.putExtra(Constants.MAIN_KEY, Constants.READ_DETAILS);
                intent.putExtra(Constants.LIGHT_DETAIL_KEY, deviceClass);
                activity.startActivity(intent);
//                addGroupDialog();
                break;
            case R.id.lux_leve:
//                luxlevelDialog();
                DeviceClass deviceClass2 = null;
                Cursor cursor1 = sqlHelper.getLightDetails(deviceClass.getDeviceUID());
                if (cursor1.moveToFirst()) {
                    do {
                        deviceClass2 = new DeviceClass();
                        deviceClass2.setDeviceName(cursor1.getString(cursor1.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                        deviceClass2.setDeviceUID(cursor1.getLong(cursor1.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                        deviceClass2.setDeviceDimming(cursor1.getInt(cursor1.getColumnIndex(COLUMN_DEVICE_PROGRESS)));
                        deviceClass2.setGroupId(cursor1.getInt(cursor1.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                        deviceClass2.setMasterStatus(cursor1.getInt(cursor1.getColumnIndex(COLUMN_DEVICE_MASTER_STATUS)));
                        deviceClass2.setStatus(cursor1.getInt(cursor1.getColumnIndex(COLUMN_DEVICE_STATUS)) == 1);

                        // do what ever you want here
                    } while (cursor1.moveToNext());
                }
                cursor1.close();
                if (deviceClass2 != null) {
                    if (deviceClass2.getMasterStatus() == 1) {
                        luxlevelDialog();
//                showAlert(String.format("'%s' is master.", deviceClass.getDeviceName()));
                    } else {
                        showAlert(String.format("'%s' is not master.", deviceClass.getDeviceName()));
                    }
                }
                break;

            case R.id.enable_attribute:
                checkMaster();
//                if (deviceClass.getMasterStatus()==1){
//                    enableAttributes();
//                }else {
//                    showAlert("Please make "+ deviceClass.getDeviceName() +" as a master");
//                }

                break;
        }
    }

    public void enableAttributes() {
//        if (deviceClass.getMasterStatus() == 1) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.attributes);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
            int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            if (PreferencesManager.getInstance(getContext()).getList(Constants.ATTRIBUTE_TYPE) != null) {
                List<String> list = PreferencesManager.getInstance(getContext()).getList(Constants.ATTRIBUTE_TYPE);
                Log.e(">>", list.toString());
                for (int i = 0; i < list.size(); i++) {
                    Log.e(">>>name", list.get(i).toString());
                    if (list.get(i).contains(name)) {
                        if (list.get(i).contains("site")) {
                            if (list.get(i).equalsIgnoreCase("siteenable" + name)) {
                                enableSite = "enable";
                            } else {
                                enableSite = "disable";
                            }
                        }
                        if (list.get(i).contains("building")) {
                            if (list.get(i).equalsIgnoreCase("buildingenable" + name)) {
                                enableBuilding = "enable";
                            } else {
                                enableBuilding = "disable";
                            }

                        }
                        if (list.get(i).contains("level")) {
                            if (list.get(i).equalsIgnoreCase("levelenable" + name)) {
                                enableLevel = "enable";
                            } else {
                                enableLevel = "disable";
                            }

                        }

                        if (list.get(i).contains("room")) {
                            if (list.get(i).equalsIgnoreCase("roomenable" + name)) {
                                enableRoom = "enable";
                            } else {
                                enableRoom = "disable";
                            }

                        }

                        if (list.get(i).contains("group")) {
                            if (list.get(i).equalsIgnoreCase("groupenable" + name)) {
                                enablegroup = "enable";
                            } else {
                                enablegroup = "disable";
                            }

                        }


                    }
                }
            }
            dialog.show();
            EditText deviceName = dialog.findViewById(R.id.deviceName);
            EditText site_attribute = dialog.findViewById(R.id.site_attribute);
            EditText building_attribute = dialog.findViewById(R.id.building_attribute);
            EditText level_attribute = dialog.findViewById(R.id.level_attribute);
            EditText room_attribute = dialog.findViewById(R.id.room_attribute);
            EditText group_attribute = dialog.findViewById(R.id.group_attribute);
            Button btn_submit = dialog.findViewById(R.id.btn_submit);
            deviceName.setText(deviceClass.getDeviceName());
            if (enableSite.equalsIgnoreCase("enable")) {
                site_attribute.setText("Enable");
            } else {
                site_attribute.setText("Disable");
            }
            if (enableBuilding.equalsIgnoreCase("enable")) {
                building_attribute.setText("Enable");
            } else {
                building_attribute.setText("Disable");
            }

            if (enableLevel.equalsIgnoreCase("enable")) {
                level_attribute.setText("Enable");
            } else {
                level_attribute.setText("Disable");
            }
            if (enableRoom.equalsIgnoreCase("enable")) {
                room_attribute.setText("Enable");
            } else {
                room_attribute.setText("Disable");
            }

            if (enablegroup.equalsIgnoreCase("enable")) {
                group_attribute.setText("Enable");
            } else {
                group_attribute.setText("Disable");
            }

            site_attribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu airPopUp2 = new PopupMenu(activity, site_attribute);
                    airPopUp2.getMenuInflater().inflate(R.menu.show_attributes, airPopUp2.getMenu());
                    airPopUp2.setOnMenuItemClickListener(item -> {
                        try {
                            site_attribute.setText(item.getTitle());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    });
                    airPopUp2.show();
                }
            });
            building_attribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu airPopUp2 = new PopupMenu(activity, building_attribute);
                    airPopUp2.getMenuInflater().inflate(R.menu.show_attributes, airPopUp2.getMenu());
                    airPopUp2.setOnMenuItemClickListener(item -> {
                        try {
                            building_attribute.setText(item.getTitle());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    });
                    airPopUp2.show();
                }
            });

            level_attribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu airPopUp2 = new PopupMenu(activity, level_attribute);
                    airPopUp2.getMenuInflater().inflate(R.menu.show_attributes, airPopUp2.getMenu());
                    airPopUp2.setOnMenuItemClickListener(item -> {
                        try {
                            level_attribute.setText(item.getTitle());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    });
                    airPopUp2.show();
                }
            });

            room_attribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu airPopUp2 = new PopupMenu(activity, room_attribute);
                    airPopUp2.getMenuInflater().inflate(R.menu.show_attributes, airPopUp2.getMenu());
                    airPopUp2.setOnMenuItemClickListener(item -> {
                        try {
                            room_attribute.setText(item.getTitle());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    });
                    airPopUp2.show();
                }
            });

            group_attribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu airPopUp2 = new PopupMenu(activity, group_attribute);
                    airPopUp2.getMenuInflater().inflate(R.menu.show_attributes, airPopUp2.getMenu());
                    airPopUp2.setOnMenuItemClickListener(item -> {
                        try {
                            group_attribute.setText(item.getTitle());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    });
                    airPopUp2.show();
                }
            });
            btn_submit.setOnClickListener(view -> {
                AdvertiseTask advertiseTask;
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push(RxMethodType.ATTRIBUTES_INFO);       ////State Command
                byteQueue.push(0x04);
                byteQueue.pushU4B(deviceClass.getDeviceUID());
                List<String> list = new ArrayList<>();
                if (PreferencesManager.getInstance(getContext()).getList(Constants.ATTRIBUTE_TYPE) != null) {
                    list = PreferencesManager.getInstance(getContext()).getList(Constants.ATTRIBUTE_TYPE);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).contains(name)) {
                            list.remove(i);
                        }
                    }
                }
                if (site_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    list.add("site" + "enable" + name);
                } else {
                    list.add("site" + "disable" + name);
                }
                if (building_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    list.add("building" + "enable" + name);
                } else {
                    list.add("building" + "disable" + name);
                }
                if (level_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    list.add("level" + "enable" + name);
                } else {
                    list.add("level" + "disable" + name);
                }
                if (room_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    list.add("room" + "enable" + name);
                } else {
                    list.add("room" + "disable" + name);
                }

                if (group_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    list.add("group" + "enable" + name);
                } else {
                    list.add("group" + "disable" + name);
                }
                PreferencesManager.getInstance(getContext()).setList(Constants.ATTRIBUTE_TYPE, list);


                if (site_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    byteQueue.push(0x01);
                } else if (site_attribute.getText().toString().equalsIgnoreCase("Disable")) {
                    byteQueue.push(0x00);
                }
                if (building_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    byteQueue.push(0x01);
                } else if (building_attribute.getText().toString().equalsIgnoreCase("Disable")) {
                    byteQueue.push(0x00);
                }
                if (level_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    byteQueue.push(0x01);
                } else if (level_attribute.getText().toString().equalsIgnoreCase("Disable")) {
                    byteQueue.push(0x00);
                }
                if (room_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    byteQueue.push(0x01);
                } else if (room_attribute.getText().toString().equalsIgnoreCase("Disable")) {
                    byteQueue.push(0x00);
                }
                if (group_attribute.getText().toString().equalsIgnoreCase("Enable")) {
                    byteQueue.push(0x01);
                } else if (group_attribute.getText().toString().equalsIgnoreCase("Disable")) {
                    byteQueue.push(0x00);
                }
                Log.e("Lux====>", byteQueue.toString());
                advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
                advertiseTask.setByteQueue(byteQueue);
                advertiseTask.startAdvertising();
                dialog.dismiss();
            });

//        } else {
//            Toast.makeText(activity, "Make Light as a master.", Toast.LENGTH_SHORT).show();
//        }


    }

    public void luxlevelDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.set_lux_level);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        lux_level_one = dialog.findViewById(R.id.lux_level_one);
        dimming_level1 = dialog.findViewById(R.id.dimming_level1);
        dimming_level1.setFilters(new InputFilter[]{new MinMaxFilter("1", "255")});
        Button btn_submit1 = dialog.findViewById(R.id.btn_submit1);
        lux_level_two = dialog.findViewById(R.id.lux_level_two);
        dimming_level2 = dialog.findViewById(R.id.dimming_level2);
        dimming_level2.setFilters(new InputFilter[]{new MinMaxFilter("1", "255")});
        Button btn_submit2 = dialog.findViewById(R.id.btn_submit2);
        lux_level_three = dialog.findViewById(R.id.lux_level_three);
        dimming_level3 = dialog.findViewById(R.id.dimming_level3);
        dimming_level3.setFilters(new InputFilter[]{new MinMaxFilter("1", "255")});
        Button btn_submit3 = dialog.findViewById(R.id.btn_submit3);
        lux_level_four = dialog.findViewById(R.id.lux_level_four);
        dimming_level4 = dialog.findViewById(R.id.dimming_level4);
        dimming_level4.setFilters(new InputFilter[]{new MinMaxFilter("1", "255")});
        Button btn_submit4 = dialog.findViewById(R.id.btn_submit4);
        lux_level_five = dialog.findViewById(R.id.lux_level_five);

        lux_level_one.setText(deviceClass.getLuxLevelOne());
        lux_level_two.setText(deviceClass.getLuxLevelTwo());
        lux_level_three.setText(deviceClass.getLuxLevelThree());
        lux_level_four.setText(deviceClass.getLuxLevelFour());
        lux_level_five.setText(deviceClass.getLuxLevelFive());
        dimming_level1.setText(deviceClass.getDimmingLevelOne());
        dimming_level2.setText(deviceClass.getDimmingLevelTwo());
        dimming_level3.setText(deviceClass.getDimmingLevelThre());
        dimming_level4.setText(deviceClass.getDimmingLevelFour());
        dialog.show();
        Log.e("Level======>", deviceClass.getLuxLevelThree());
        btn_submit1.setOnClickListener(view -> {
            if (lux_level_one.getText().toString().length() == 0) {
                showError("Lux level 1 can't empty", lux_level_one);
                return;
            } else if (lux_level_two.getText().toString().length() == 0) {
                showError("Lux level 2 can't empty", lux_level_two);
                return;
            } else if (dimming_level1.getText().toString().length() == 0) {
                showError("Lux level 1 can't empty", dimming_level1);
                return;
            }
            String room_dialog = dimming_level1.getText().toString().trim();
            int itemValue = Integer.parseInt(room_dialog);
//            dialog.dismiss();
            AdvertiseTask advertiseTask;
            ByteQueue byteQueue = new ByteQueue();
            byteQueue.push(LUX_LEVEL_ONE_INFO);       ////State Command
            byteQueue.push(0x04);
            byteQueue.pushU4B(deviceClass.getDeviceUID());
            byteQueue.pushU2B(Integer.parseInt(lux_level_one.getText().toString()));
            byteQueue.push(itemValue);    ////0x00-0x64
            byteQueue.pushU2B(Integer.parseInt(lux_level_two.getText().toString()));
            Log.e("Lux====>", byteQueue.toString());
            advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
            advertiseTask.setSearchRequestCode(LUX_LEVEL_ONE_INFO);
            advertiseTask.setByteQueue(byteQueue);
            advertiseTask.startAdvertising();

        });

        btn_submit2.setOnClickListener(view -> {
            if (lux_level_two.getText().toString().length() == 0) {
                showError("Lux level 2 can't empty", lux_level_two);
                return;
            } else if (lux_level_three.getText().toString().length() == 0) {
                showError("Lux level 3 can't empty", lux_level_three);
                return;
            } else if (dimming_level2.getText().toString().length() == 0) {
                showError("Lux level 2 can't empty", dimming_level2);
                return;
            }
            String room_dialog = dimming_level2.getText().toString().trim();
            int itemValue = Integer.parseInt(room_dialog);
//            dialog.dismiss();
            AdvertiseTask advertiseTask;
            ByteQueue byteQueue = new ByteQueue();
            byteQueue.push(LUX_LEVEL_TWO_INFO);       ////State Command
            byteQueue.push(0x04);
            byteQueue.pushU4B(deviceClass.getDeviceUID());
            byteQueue.pushU2B(Integer.parseInt(lux_level_two.getText().toString()));
            byteQueue.push(itemValue);    ////0x00-0x64
            byteQueue.pushU2B(Integer.parseInt(lux_level_three.getText().toString()));
            Log.e("Lux====>", byteQueue.toString());
            advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
            advertiseTask.setSearchRequestCode(LUX_LEVEL_TWO_INFO);
            advertiseTask.setByteQueue(byteQueue);
            advertiseTask.startAdvertising();
        });

        btn_submit3.setOnClickListener(view -> {
            if (lux_level_three.getText().toString().length() == 0) {
                showError("Lux level 3 can't empty", lux_level_three);
                return;
            } else if (lux_level_four.getText().toString().length() == 0) {
                showError("Lux level 4 can't empty", lux_level_four);
                return;
            } else if (dimming_level3.getText().toString().length() == 0) {
                showError("Lux level 3 can't empty", dimming_level3);
                return;
            }
            String room_dialog = dimming_level3.getText().toString().trim();
            int itemValue = Integer.parseInt(room_dialog);
//            dialog.dismiss();
            AdvertiseTask advertiseTask;
            ByteQueue byteQueue = new ByteQueue();
            byteQueue.push(LUX_LEVEL_THREE_INFO);       ////State Command
            byteQueue.push(0x04);
            byteQueue.pushU4B(deviceClass.getDeviceUID());
            byteQueue.pushU2B(Integer.parseInt(lux_level_three.getText().toString()));
            byteQueue.push(itemValue);    ////0x00-0x64
            byteQueue.pushU2B(Integer.parseInt(lux_level_four.getText().toString()));
            Log.e("Lux====>", byteQueue.toString());
            advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
            advertiseTask.setSearchRequestCode(LUX_LEVEL_THREE_INFO);
            advertiseTask.setByteQueue(byteQueue);
            advertiseTask.startAdvertising();
        });

        btn_submit4.setOnClickListener(view -> {
            if (lux_level_four.getText().toString().length() == 0) {
                showError("Lux level 4 can't empty", lux_level_four);
                return;
            } else if (lux_level_five.getText().toString().length() == 0) {
                showError("Lux level 5 can't empty", lux_level_five);
                return;
            } else if (dimming_level4.getText().toString().length() == 0) {
                showError("Lux level 4 can't empty", dimming_level4);
                return;
            }

            String room_dialog = dimming_level4.getText().toString().trim();
            int itemValue = Integer.parseInt(room_dialog);
//            dialog.dismiss();
            AdvertiseTask advertiseTask;
            ByteQueue byteQueue = new ByteQueue();
            byteQueue.push(RxMethodType.LUX_LEVEL_FOUR_INFO);       ////State Command
            byteQueue.push(0x04);
            byteQueue.pushU4B(deviceClass.getDeviceUID());
            byteQueue.pushU2B(Integer.parseInt(lux_level_four.getText().toString()));
            byteQueue.push(itemValue);    ////0x00-0x64
            byteQueue.pushU2B(Integer.parseInt(lux_level_five.getText().toString()));
            Log.e("Lux====>", byteQueue.toString());
            advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
            advertiseTask.setSearchRequestCode(LUX_LEVEL_FOUR_INFO);
            advertiseTask.setByteQueue(byteQueue);
            advertiseTask.startAdvertising();

        });
    }

    public void holdTimeDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_time_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
        TextInputEditText time = dialog.findViewById(R.id.et_time);
        time.setFilters(new InputFilter[]{new MinMaxFilter("1", "60")});
        TextView btn_holdTime = dialog.findViewById(R.id.btn_holdTime);
        btn_holdTime.setOnClickListener(view -> {
            String room_dialog = time.getText().toString().trim();
            int timeValue = Integer.parseInt(room_dialog);
            dialog.dismiss();
            AdvertiseTask advertiseTask;
            ByteQueue byteQueue = new ByteQueue();
            byteQueue.push(RxMethodType.TIME_HOLD_INFO);       ////State Command
            byteQueue.push(0x01);
            byteQueue.pushU4B(deviceClass.getDeviceUID());
            byteQueue.push(timeValue);
            Log.e("Time=====>", byteQueue.toString());
            advertiseTask = new AdvertiseTask(EditDeviceFragment.this, activity, 5 * 1000);
            advertiseTask.setByteQueue(byteQueue);
            advertiseTask.startAdvertising();

        });
    }

    public void addGroupDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        EditText deviceName = dialog.findViewById(R.id.deviceName);
        Spinner site_spinner = dialog.findViewById(R.id.site_spinner);
        Spinner building_spinner = dialog.findViewById(R.id.building_spinner);
        Spinner level_spinner = dialog.findViewById(R.id.level_spinner);
        Spinner room_spinner = dialog.findViewById(R.id.room_spinner);
        Spinner group_spinner_dialog = dialog.findViewById(R.id.group_spinner);
        Button addDevice = dialog.findViewById(R.id.btn_submit);
        dialog.show();
//        group_spinner_dialog.setSelection(spinnerSelectedPosition);
        group_spinner_dialog.setAdapter(adapter);
        site_spinner.setAdapter(adapterSite);
        building_spinner.setAdapter(adapterBuilding);
        level_spinner.setAdapter(adapterLevel);
        room_spinner.setAdapter(adapterRoom);
        addDevice.setOnClickListener(view -> {
            if (deviceName.getText().toString().length() < 1) {
                deviceName.setError("Enter device name");
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NAME, deviceName.getText().toString());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_SITE_ID, ((SiteGroupDetailsClass) site_spinner.getSelectedItem()).getGroupSiteId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_BUILDINGID, ((BuildingGroupDetailsClass) building_spinner.getSelectedItem()).getGroupBuildingId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_LEVELID, ((LevelGroupDetailsClass) level_spinner.getSelectedItem()).getGroupLevelId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_ROOMID, ((RoomGroupDetailsClass) room_spinner.getSelectedItem()).getRoomGroupId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_ID, ((GroupDetailsClass) group_spinner_dialog.getSelectedItem()).getGroupId());
            if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues)) {
                Log.e("UID===>", String.valueOf(deviceClass.getDeviceUID()));
            }

            ByteQueue byteQueue = new ByteQueue();
            if (site_spinner.getSelectedItem().equals("No Site Group")) {
            } else {
                byteQueue.push(ADD_SITE_GROUP);
                byteQueue.push(((SiteGroupDetailsClass) site_spinner.getSelectedItem()).getGroupSiteId());
            }
            if (building_spinner.getSelectedItem().equals("No Building Group")) {
            } else {
                byteQueue.push(ADD_BUILDING_GROUP);
                byteQueue.push(((BuildingGroupDetailsClass) building_spinner.getSelectedItem()).getGroupBuildingId());
            }
            if (level_spinner.getSelectedItem().equals("No Level Group")) {
            } else {
                byteQueue.push(ADD_LEVEL_GROUP);
                byteQueue.push(((LevelGroupDetailsClass) level_spinner.getSelectedItem()).getGroupLevelId());
            }
            if (group_spinner_dialog.getSelectedItem().equals("No Group")) {
            } else {
                byteQueue.push(ADD_GROUP);
                byteQueue.push(((GroupDetailsClass) group_spinner_dialog.getSelectedItem()).getGroupId());
            }
            if (room_spinner.getSelectedItem().equals("No Room Group")) {
            } else {
                byteQueue.push(ADD_ROOM_GROUP);
                byteQueue.push(((RoomGroupDetailsClass) room_spinner.getSelectedItem()).getRoomGroupId());
            }

            byteQueue.push(0x01);
            byteQueue.pushU4B(deviceClass.getDeviceUID());
            advertiseTask = new AdvertiseTask(this, activity, 5 * 1000);
            animatedProgress.setText("Advertising");
            advertiseTask.setByteQueue(byteQueue);
            Log.e("GGGGGGGGG=====>", byteQueue.toString());
            advertiseTask.startAdvertising();
            dialog.dismiss();

        });

    }


    void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure to delete Light  " + deviceClass.getDeviceName())
                .setTitle("Remove Light");
        builder.setPositiveButton("delete", (dialog1, id) -> {
            dialog1.dismiss();
            if (sqlHelper.deleteLight(deviceClass.getDeviceUID()) > 0) {
                Toast.makeText(activity, "Light deleted.", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            } else
                Toast.makeText(activity, "Some error to delete light", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveData() {
        if (editLightName.getText().toString().trim().length() < 1) {
            editLightName.setError("Light name can't empty");
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstant.COLUMN_DEVICE_NAME, editLightName.getText().toString());
        if (groupListSpinner.getSelectedItemPosition() != spinnerSelectedPosition)
            contentValues.put(DatabaseConstant.COLUMN_GROUP_ID, list.get(groupListSpinner.getSelectedItemPosition()).getGroupId());
        if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues)) {
            editLightName.setEnabled(false);
            lightEdit.setVisibility(View.VISIBLE);
            editLightSave.setVisibility(View.GONE);
            editLightDelete.setVisibility(View.GONE);
            activity.onBackPressed();
        } else
            Toast.makeText(activity, "Some error to edit group", Toast.LENGTH_SHORT).show();
    }

    public void setDeviceData(DeviceClass deviceData) {
        this.deviceClass = deviceData;
    }

    @Override
    public void onSuccess(String message) {
        animatedProgress.showProgress();
        Log.w(TAG, "Advertising start");
    }

    @Override
    public void onFailed(String errorMessage) {
        if (animatedProgress == null)
            return;
        Toast.makeText(activity, "Advertising failed.", Toast.LENGTH_SHORT).show();
        animatedProgress.hideProgress();
        Log.w(TAG, "onScanFailed " + errorMessage);

    }


    @Override
    public void onStop(String stopMessage, int resultCode) {
//        requestCode = resultCode;
//        scannerTask.setRequestCode(resultCode);
//        scannerTask.start();
//        Log.w(TAG, "Advertising stop" + resultCode);
        if (animatedProgress != null)
            animatedProgress.hideProgress();
        ContentValues contentValues = new ContentValues();


        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        Log.w(TAG, "Advertising stop" + resultCode);
        switch (resultCode) {


            case LIGHT_STATE_COMMAND_RESPONSE:

                boolean changedStatus = !deviceClass.getStatus();
                contentValues.put(COLUMN_DEVICE_STATUS, changedStatus);
                contentValues.put(COLUMN_DEVICE_PROGRESS, changedStatus ? 100 : 0);
                deviceClass.setDeviceDimming(changedStatus ? 100 : 0);
                deviceClass.setStatus(changedStatus);
                editLightStatus.setText(String.format("Light Status:%s", deviceClass.getStatus() ? "On" : "Off"));


//                    Toast.makeText(activity, "status"+status1, Toast.LENGTH_SHORT).show();
                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

                break;


            case SELECT_MASTER_RESPONSE:

                contentValues.put(COLUMN_DEVICE_MASTER_STATUS, 1);
                deviceClass.setMasterStatus(1);
                showAlert(String.format("Light '%s' is %s as master.", deviceClass.getDeviceName(), "set "));
                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
//
                break;

            case UNSELECT_MASTER_RESPONSE:
                contentValues.put(COLUMN_DEVICE_MASTER_STATUS, 0);
                deviceClass.setMasterStatus(0);
                showAlert(String.format("Light '%s' is %s as normal.", deviceClass.getDeviceName(), "set "));
                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");


                break;

            case LIGHT_LEVEL_COMMAND_RESPONSE:

//                    bytes1=byteQueue.pop4B();
//                    ArrayUtilities.reverse(bytes1);
//                    nodeUid=bytesToHex(bytes1);
//                    Log.w("ScanningBeacon",nodeUid);
//                    //                                String s = "4d0d08ada45f9dde1e99cad9";
//                    deviceUid = new BigInteger(nodeUid, 16).longValue();
//
//                    if(deviceUid!=deviceClass.getDeviceUID())
//                        return;


                deviceClass.setDeviceDimming(levelProgress);
                contentValues.put(COLUMN_DEVICE_PROGRESS, levelProgress);
                contentValues.put(COLUMN_DEVICE_STATUS, 1);
                deviceClass.setStatus(true);

                if (!this.lightStatus.isChecked())
                    this.lightStatus.setChecked(true);
                editLightStatus.setText(String.format("Light Status:%s", deviceClass.getStatus() ? "On" : "Off"));

                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();


                break;

            case UPDATE_GROUP_RESPONSE:

                saveData();
                showAlert("Group update successfully.");
//                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

//                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
//                Log.w("DashGroup",AppHelper.sqlHelper.updateDevice(arrayList.get(selectedPosition).getDeviceUID(),contentValues)+"");
                break;

            case LUX_LEVEL_ONE_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_ONE, lux_level_one.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_TWO, lux_level_two.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_ONE, dimming_level1.getText().toString());
                deviceClass.setLuxLevelOne(lux_level_one.getText().toString());
                deviceClass.setLuxLevelTwo(lux_level_two.getText().toString());
                deviceClass.setDimmingLevelOne(dimming_level1.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;
            case LUX_LEVEL_TWO_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_TWO, lux_level_two.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_THREE, lux_level_three.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_TWO, dimming_level2.getText().toString());
                deviceClass.setLuxLevelTwo(lux_level_two.getText().toString());
                deviceClass.setLuxLevelThree(lux_level_three.getText().toString());
                deviceClass.setDimmingLevelTwo(dimming_level2.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;
            case LUX_LEVEL_THREE_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_THREE, lux_level_three.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FOUR, lux_level_four.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_THREE, dimming_level3.getText().toString());
                deviceClass.setLuxLevelThree(lux_level_three.getText().toString());
                deviceClass.setLuxLevelFour(lux_level_four.getText().toString());
                deviceClass.setDimmingLevelThre(dimming_level3.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;
            case LUX_LEVEL_FOUR_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FOUR, lux_level_four.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FIVE, lux_level_five.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_Four, dimming_level4.getText().toString());
                deviceClass.setLuxLevelFour(lux_level_four.getText().toString());
                deviceClass.setLuxLevelFive(lux_level_five.getText().toString());
                deviceClass.setDimmingLevelFour(dimming_level4.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;

        }
    }

    @Override
    public void onScanSuccess(int successCode, ByteQueue byteQueue) {
        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();
        ContentValues contentValues = new ContentValues();

        Log.w("BYTEQUESIZE", byteQueue.size() + ",");
        Log.w("MethodType", (int) byteQueue.pop() + "");

        byte[] bytes1;
        String nodeUid;
        long deviceUid;
        int lightStatus;
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        switch (successCode) {
            case LIGHT_STATE_RESPONSE:
                bytes1 = byteQueue.pop4B();
                ArrayUtilities.reverse(bytes1);
                nodeUid = bytesToHex(bytes1);
                Log.w("ScanningBeacon", nodeUid);
                //                                String s = "4d0d08ada45f9dde1e99cad9";
                deviceUid = new BigInteger(nodeUid, 16).longValue();

                if (deviceUid != deviceClass.getDeviceUID())
                    return;

                lightStatus = byteQueue.pop();
                Log.w("Scann", deviceUid + "," + lightStatus);
                contentValues.put(COLUMN_DEVICE_STATUS, lightStatus == 1);
                if (deviceClass.getStatus() != (lightStatus == 1)) {
                    contentValues.put(COLUMN_DEVICE_PROGRESS, lightStatus == 1 ? 100 : 0);
                    deviceClass.setDeviceDimming(lightStatus == 1 ? 100 : 0);
                }
                deviceClass.setStatus(lightStatus == 1);
                this.lightStatus.setChecked(lightStatus == 1);
                if (lightStatus == 0) {
                    editLightStatus.setText("Light Status:Off");
                } else
                    editLightStatus.setText("Light Status:On");

                dialogBuilder
                        .withTitle("Light Status")
                        .withEffect(Effectstype.Newspager)
                        .withMessage("Light is " + (lightStatus == 0 ? "Off" : "On"))
                        .withButton1Text("OK")
                        .setButton1Click(v -> {
                            dialogBuilder.dismiss();
                        })
                        .show();

//                    Toast.makeText(activity, "status"+status1, Toast.LENGTH_SHORT).show();
                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
//
                break;

            case LIGHT_STATE_COMMAND_RESPONSE:
                bytes1 = byteQueue.pop4B();
                ArrayUtilities.reverse(bytes1);
                nodeUid = bytesToHex(bytes1);
                Log.w("ScanningBeacon", nodeUid);
                //                                String s = "4d0d08ada45f9dde1e99cad9";
                deviceUid = new BigInteger(nodeUid, 16).longValue();

                if (deviceUid != deviceClass.getDeviceUID())
                    return;

                lightStatus = byteQueue.pop();
                Log.w("Scann", deviceUid + "," + lightStatus);

                if (lightStatus == 0) {
                    boolean changedStatus = !deviceClass.getStatus();
                    contentValues.put(COLUMN_DEVICE_STATUS, changedStatus);
                    contentValues.put(COLUMN_DEVICE_PROGRESS, changedStatus ? 100 : 0);
                    deviceClass.setDeviceDimming(changedStatus ? 100 : 0);
                    deviceClass.setStatus(changedStatus);
                    editLightStatus.setText(String.format("Light Status:%s", deviceClass.getStatus() ? "On" : "Off"));


//                    Toast.makeText(activity, "status"+status1, Toast.LENGTH_SHORT).show();
                    Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    this.lightStatus.setChecked(deviceClass.getStatus());
                    Toast.makeText(activity, "Cannot change light status", Toast.LENGTH_SHORT).show();

                }
//
                break;


            case LIGHT_LEVEL_RESPONSE:

                bytes1 = byteQueue.pop4B();
                ArrayUtilities.reverse(bytes1);
                nodeUid = bytesToHex(bytes1);
                Log.w("ScanningBeacon", nodeUid);
                //                                String s = "4d0d08ada45f9dde1e99cad9";
                deviceUid = new BigInteger(nodeUid, 16).longValue();
                if (deviceUid != deviceClass.getDeviceUID())
                    return;
                lightStatus = byteQueue.pop();
                Log.w("Scann", deviceUid + "," + lightStatus);

//            arrayList.get(index).setDeviceDimming(seekBarProgress[0]);
                contentValues.put(COLUMN_DEVICE_PROGRESS, lightStatus);
                deviceClass.setDeviceDimming(lightStatus);
                contentValues.put(COLUMN_DEVICE_STATUS, lightStatus > 1 ? 1 : 0);
                deviceClass.setStatus(lightStatus > 1);

                if (deviceClass.getStatus()) {
                    if (!this.lightStatus.isChecked())
                        this.lightStatus.setChecked(true);
                } else {
                    if (this.lightStatus.isChecked())
                        this.lightStatus.setChecked(false);
                }
                showAlert(String.format("Light level of %s is %s.", deviceClass.getDeviceName(), lightStatus));
//                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(activity, "status"+status1, Toast.LENGTH_SHORT).show();
                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
//
                break;

            case SELECT_MASTER_RESPONSE:

                lightStatus = byteQueue.pop();


//            arrayList.get(index).setDeviceDimming(seekBarProgress[0]);
                contentValues.put(COLUMN_DEVICE_MASTER_STATUS, lightStatus == 0 ? 1 : 0);
                deviceClass.setMasterStatus(lightStatus == 0 ? 1 : 0);
//                deviceClass.(lightStatus);
                showAlert(String.format("Light '%s' is %s as master.", deviceClass.getDeviceName(), lightStatus == 0 ? "set " : "not set"));
//                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(activity, "status"+status1, Toast.LENGTH_SHORT).show();
                Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
//
                break;

            case LIGHT_LEVEL_COMMAND_RESPONSE:

//                    bytes1=byteQueue.pop4B();
//                    ArrayUtilities.reverse(bytes1);
//                    nodeUid=bytesToHex(bytes1);
//                    Log.w("ScanningBeacon",nodeUid);
//                    //                                String s = "4d0d08ada45f9dde1e99cad9";
//                    deviceUid = new BigInteger(nodeUid, 16).longValue();
//
//                    if(deviceUid!=deviceClass.getDeviceUID())
//                        return;

                lightStatus = byteQueue.pop();
                Log.w("Scann", "," + lightStatus);

//            arrayList.get(index).setDeviceDimming(seekBarProgress[0]);
                if (lightStatus == 0) {
                    deviceClass.setDeviceDimming(levelProgress);
                    contentValues.put(COLUMN_DEVICE_PROGRESS, levelProgress);
                    contentValues.put(COLUMN_DEVICE_STATUS, 1);
                    deviceClass.setStatus(true);

                    if (!this.lightStatus.isChecked())
                        this.lightStatus.setChecked(true);
                    editLightStatus.setText(String.format("Light Status:%s", deviceClass.getStatus() ? "On" : "Off"));

                    Log.w("DashGroup", sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues) + "");
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity, "Could not change the lighting level", Toast.LENGTH_SHORT).show();
                }
//
                break;

            case UPDATE_GROUP_RESPONSE:
                int requestStatus = byteQueue.pop();
                if (requestStatus == 0) {
                    saveData();
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(activity, "Cannot Update group", Toast.LENGTH_SHORT).show();
//                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
//                Log.w("DashGroup",AppHelper.sqlHelper.updateDevice(arrayList.get(selectedPosition).getDeviceUID(),contentValues)+"");
                break;

            case GROUP_RESPONSE:

                bytes1 = byteQueue.pop4B();
                ArrayUtilities.reverse(bytes1);
                String nodeUid2 = bytesToHex(bytes1);
                Log.w("ScanningBeacon", nodeUid2);
                //                                String s = "4d0d08ada45f9dde1e99cad9";
                long deviceUid2 = new BigInteger(nodeUid2, 16).longValue();
                int lightStatus2 = byteQueue.pop();
                Log.w("Scann", deviceUid2 + "," + lightStatus2);
                deviceClass.setGroupId(lightStatus2);

                if (lightStatus2 == 0)
                    showAlert("Light has no group");
                else
                    showAlert(String.format("Group of %s is %s.", deviceClass.getDeviceName(), getGroup(lightStatus2).getGroupName()));


//                    contentValues.put(COLUMN_DEVICE_STATUS, lightStatus2==1);
//                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
//                Log.w("DashGroup",AppHelper.sqlHelper.updateDevice(arrayList.get(selectedPosition).getDeviceUID(),contentValues)+"");
                break;
            case LUX_LEVEL_ONE_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_ONE, lux_level_one.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_TWO, lux_level_two.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_ONE, dimming_level1.getText().toString());
                deviceClass.setLuxLevelOne(lux_level_one.getText().toString());
                deviceClass.setLuxLevelTwo(lux_level_two.getText().toString());
                deviceClass.setDimmingLevelOne(dimming_level1.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;

            case LUX_LEVEL_TWO_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_TWO, lux_level_two.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_THREE, lux_level_three.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_TWO, dimming_level2.getText().toString());
                deviceClass.setLuxLevelTwo(lux_level_two.getText().toString());
                deviceClass.setLuxLevelThree(lux_level_three.getText().toString());
                deviceClass.setDimmingLevelTwo(dimming_level2.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;
            case LUX_LEVEL_THREE_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_THREE, lux_level_three.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FOUR, lux_level_four.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_THREE, dimming_level3.getText().toString());
                deviceClass.setLuxLevelThree(lux_level_three.getText().toString());
                deviceClass.setLuxLevelFour(lux_level_four.getText().toString());
                deviceClass.setDimmingLevelThre(dimming_level3.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;
            case LUX_LEVEL_FOUR_INFO:
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FOUR, lux_level_four.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_LUX_LEVEL_FIVE, lux_level_five.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_DIMMING_LEVEL_Four, dimming_level4.getText().toString());
                deviceClass.setLuxLevelFour(lux_level_four.getText().toString());
                deviceClass.setLuxLevelFive(lux_level_five.getText().toString());
                deviceClass.setDimmingLevelFour(dimming_level4.getText().toString());
                sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues);
                showAlert("Lux update successfully.");
                break;
        }

    }

    void showAlert(String message) {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        dialogBuilder

                .withEffect(Effectstype.Fall)
                .withMessage(message)
                .withButton1Text("OK")
                .setButton1Click(v -> {
                    dialogBuilder.dismiss();
                })
                .show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setMessage(message);
////                .setTitle("Remove Light");
//        builder.setPositiveButton("OK", (dialog1, id) -> {
//            // User clicked OK button
////            acceptRequest(2,position);
//            dialog1.dismiss();
//
//
////            Toast.makeText(activity, "Will be soon", Toast.LENGTH_SHORT).show();
//
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }

    @Override
    public void onScanFailed(int errorCode) {

        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();

        switch (requestCode) {

            case LIGHT_STATE_COMMAND_RESPONSE:

                this.lightStatus.setChecked(deviceClass.getStatus());

//
                break;


            case LIGHT_LEVEL_COMMAND_RESPONSE:


//                    deviceClass.setDeviceDimming(levelProgress);

//
                break;

            case UPDATE_GROUP_RESPONSE:

                Toast.makeText(activity, "Cannot Update group", Toast.LENGTH_SHORT).show();
//                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
//                Log.w("DashGroup",AppHelper.sqlHelper.updateDevice(arrayList.get(selectedPosition).getDeviceUID(),contentValues)+"");
                break;


        }

        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        dialogBuilder
                .withTitle("Timeout")
                .withEffect(Effectstype.Newspager)
                .withMessage("Timeout,Please check your beacon is in range")
                .withButton1Text("OK")
                .setButton1Click(v -> {
                    dialogBuilder.dismiss();
                })
                .show();
//        saveData();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
        getAllGroups();
        getAllSITEGroups();
        getAllBUILDINGGroups();
        getAllLEVELGroups();
        getAllROOMGroups();
    }

    private void showError(String error_st, EditText editText) {
        try {
            Dialog error_dialog = new Dialog(activity);
            error_dialog.setCanceledOnTouchOutside(false);
            error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            error_dialog.setContentView(R.layout.error_dialoge);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
            error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView error_text = error_dialog.findViewById(R.id.error_text);
            Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
            error_text.setText(error_st);
            error_dialog.show();
            ok_btn.setOnClickListener(view -> {
                error_dialog.dismiss();
                requestFocus(editText);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void hideKeyboard() {

        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
