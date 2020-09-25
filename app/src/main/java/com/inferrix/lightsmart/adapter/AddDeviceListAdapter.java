package com.inferrix.lightsmart.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType;
import com.inferrix.lightsmart.InterfaceModule.AdvertiseResultInterface;
import com.inferrix.lightsmart.InterfaceModule.ReceiverResultInterface;
import com.inferrix.lightsmart.PogoClasses.BeconDeviceClass;
import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.PogoClasses.GroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.RoomGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.SiteGroupDetailsClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.ServiceModule.AdvertiseTask;
import com.inferrix.lightsmart.ServiceModule.ScannerTask;
import com.inferrix.lightsmart.activity.AppHelper;
import com.inferrix.lightsmart.constant.Constants;
import com.inferrix.lightsmart.fragments.AddGroupFragment;
import com.niftymodaldialogeffects.Effectstype;
import com.niftymodaldialogeffects.NiftyDialogBuilder;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ALL_GROUP_INFO;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_STATE_COMMAND_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.SELECT_MASTER_RESPONSE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;

public class AddDeviceListAdapter extends BaseAdapter implements AdvertiseResultInterface, ReceiverResultInterface {
    Activity activity;
    ArrayList<BeconDeviceClass> arrayList;
    ArrayAdapter<GroupDetailsClass> adapter;
    ArrayAdapter<RoomGroupDetailsClass> adapterRoom;
    ArrayAdapter<SiteGroupDetailsClass> adapterSite;
    ArrayAdapter<BuildingGroupDetailsClass> adapterBuilding;
    ArrayAdapter<LevelGroupDetailsClass> adapterLevel;

    ArrayList<GroupDetailsClass> groupDetailsClasses;
    ArrayList<RoomGroupDetailsClass> groupRoomDetailsClasses;
    ArrayList<SiteGroupDetailsClass> groupSiteDetailsClasses;
    ArrayList<BuildingGroupDetailsClass> groupBuildingDetailsClasses;
    ArrayList<LevelGroupDetailsClass> groupLevelDetailsClasses;
    ArrayList<DeviceClass> deviceList;
    ScannerTask scannerTask;
    AnimatedProgress animatedProgress;
    int requestCode;
    String TAG = this.getClass().getSimpleName();
    int selectedPosition = -1;

    public AddDeviceListAdapter(@NonNull Activity context) {
        activity = context;
        arrayList = new ArrayList<>();
        groupDetailsClasses = new ArrayList<>();
        groupRoomDetailsClasses = new ArrayList<>();
        groupSiteDetailsClasses = new ArrayList<>();
        groupBuildingDetailsClasses = new ArrayList<>();
        groupLevelDetailsClasses = new ArrayList<>();
        deviceList = new ArrayList<>();
        scannerTask = new ScannerTask(activity, this);
        animatedProgress = new AnimatedProgress(activity);
        animatedProgress.setCancelable(false);

        adapter = new ArrayAdapter<GroupDetailsClass>(activity, R.layout.spinerlayout, groupDetailsClasses) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLACK);
                tv.setText(groupDetailsClasses.get(position).getGroupName());
                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);
                tv.setText(groupDetailsClasses.get(position).getGroupName());

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
                tv.setTextColor(activity.getResources().getColor(R.color.black));
                tv.setText(groupSiteDetailsClasses.get(position).getGroupSiteName());
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
                tv.setTextColor(activity.getResources().getColor(R.color.black));
                tv.setText(groupBuildingDetailsClasses.get(position).getGroupBuildingName());
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
                tv.setTextColor(activity.getResources().getColor(R.color.black));
                tv.setText(groupLevelDetailsClasses.get(position).getGroupLevelName());
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
                tv.setTextColor(activity.getResources().getColor(R.color.black));
                tv.setText(groupRoomDetailsClasses.get(position).getGroupRoomName());
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


        getAllGroups();
        getAllROOMGroups();
        getAllSITEGroups();
        getAllBUILDINGGroups();
        getAllLEVELGroups();
        if (AppHelper.IS_TESTING) {
            setArrayList();
        }
    }

    public void clearList() {
        if (this.arrayList == null)
            this.arrayList = new ArrayList<>();
        this.arrayList.clear();
        notifyDataSetChanged();

    }

    public void setArrayList(ArrayList<BeconDeviceClass> arrayList) {
//        if(this.arrayList==null)
//            this.arrayList=new ArrayList<>();
//        this.arrayList.clear();
        this.arrayList = arrayList;
        notifyDataSetChanged();

    }

    public void setArrayList() {
        for (int i = 0; i <= 20; i++) {
            BeconDeviceClass beconDeviceClass = new BeconDeviceClass();
            beconDeviceClass.setBeaconUID(i + 10);
            beconDeviceClass.setDeviceUid((i + 10) + "");
            beconDeviceClass.setDeriveType(0x01);
            arrayList.add(beconDeviceClass);
        }
        notifyDataSetChanged();
    }

    public void showDialog(int position) {
        selectedPosition = position;
        final Dialog dialog = new Dialog(activity);
        BeconDeviceClass beconDeviceClass = arrayList.get(position);
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
        Spinner group_spinner = dialog.findViewById(R.id.group_spinner);
//        TextView deviceUid=dialog.findViewById(R.id.add_device_uid);
        Button addDevice = dialog.findViewById(R.id.btn_submit);
//        EditText deviceName=dialog.findViewById(R.id.add_device_name);
//        deviceUid.setText(beconDeviceClass.getDeviceUid());
        group_spinner.setAdapter(adapter);
        site_spinner.setAdapter(adapterSite);
        building_spinner.setAdapter(adapterBuilding);
        level_spinner.setAdapter(adapterLevel);
        room_spinner.setAdapter(adapterRoom);
        GroupDetailsClass selected = (GroupDetailsClass) group_spinner.getSelectedItem();
        dialog.show();

//        addDevice.setText(beconDeviceClass.isAdded()?"Added":"Add");
//
        addDevice.setOnClickListener(view -> {

            if (deviceName.getText().toString().length() < 1) {
                deviceName.setError("Enter device name");
                return;
            }

            Log.e("TYPE_CODE=====>", beconDeviceClass.getTypeCode().toString());
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstant.COLUMN_DEVICE_UID, beconDeviceClass.getBeaconUID());
            contentValues.put(DatabaseConstant.COLUMN_DEVICE_NAME, deviceName.getText().toString());
            contentValues.put(DatabaseConstant.COLUMN_DERIVE_TYPE, beconDeviceClass.getDeriveType());
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
            contentValues.put(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE, beconDeviceClass.getTypeCode());
            contentValues.put(DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS, beconDeviceClass.getDeviceMacAddress());
            contentValues.put(DatabaseConstant.COLUMN_DEVICE_STATUS, beconDeviceClass.getDeriveType() == 0 ? 1 : 0);
            contentValues.put(DatabaseConstant.COLUMN_GROUP_SITE_ID, ((SiteGroupDetailsClass) site_spinner.getSelectedItem()).getGroupSiteId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_BUILDINGID, ((BuildingGroupDetailsClass) building_spinner.getSelectedItem()).getGroupBuildingId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_LEVELID, ((LevelGroupDetailsClass) level_spinner.getSelectedItem()).getGroupLevelId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_ROOMID, ((RoomGroupDetailsClass) room_spinner.getSelectedItem()).getRoomGroupId());
            contentValues.put(DatabaseConstant.COLUMN_GROUP_ID, ((GroupDetailsClass) group_spinner.getSelectedItem()).getGroupId());
//            Log.w("Selected group",((GroupDetailsClass) spinner.getSelectedItem()).getGroupId()+"");
            if (sqlHelper.insertData(DatabaseConstant.ADD_DEVICE_TABLE, contentValues) < 0) {

                arrayList.get(position).setAdded(true);
                Toast.makeText(activity, "Device Already added.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            } else {
                arrayList.get(position).setAdded(true);
                arrayList.get(position).setDeviceName(deviceName.getText().toString());
                Toast.makeText(activity, "Device  added successfully.", Toast.LENGTH_SHORT).show();
                AdvertiseTask advertiseTask;
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push(ALL_GROUP_INFO);
                byteQueue.push(0x01);
                byteQueue.pushU4B(beconDeviceClass.getBeaconUID());
                if (site_spinner.getSelectedItem().equals("No Site Group")) {
                } else {
                    byteQueue.push(((SiteGroupDetailsClass) site_spinner.getSelectedItem()).getGroupSiteId());
                }
                if (building_spinner.getSelectedItem().equals("No Building Group")) {
                } else {
                    byteQueue.push(((BuildingGroupDetailsClass) building_spinner.getSelectedItem()).getGroupBuildingId());
                }
                if (level_spinner.getSelectedItem().equals("No Level Group")) {
                } else {
                    byteQueue.push(((LevelGroupDetailsClass) level_spinner.getSelectedItem()).getGroupLevelId());
                }

                if (room_spinner.getSelectedItem().equals("No Room Group")) {
                } else {
                    byteQueue.push(((RoomGroupDetailsClass) room_spinner.getSelectedItem()).getRoomGroupId());
                }
//                if (group_spinner.getSelectedItem().equals("No Group")) {
//                    ArrayList<Integer> arr = new ArrayList<Integer>();
//                    for (int i = 1; i <= arr.size(); i++) {
//                        arr.add(groupDetailsClasses.get(i).getGroupId());
//                        Log.e("ARRAY", arr.toString());
//                    }
//                }

//                byteQueue.push((groupDetailsClasses.get().getGroupId()));
//                if (group_spinner.getSelectedItem().equals("No Group")) {
//                } else {
//                    byteQueue.push(((GroupDetailsClass) group_spinner.getSelectedItem()).getGroupId());
//                }
                advertiseTask = new AdvertiseTask(this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask.setByteQueue(byteQueue);
                Log.e("Check>>>>", byteQueue.toString());
                advertiseTask.startAdvertising();

                dialog.cancel();
//                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
//                dialogBuilder
//                        .withTitle("Master Light")
//                        .withEffect(Effectstype.Shake)
//                        .withMessage("Set light '" + deviceName.getText().toString() + "' as master light")
//                        .withButton1Text("OK")
//                        .setButton1Click(v -> {
//
//                            selectedPosition = position;
//                            AdvertiseTask advertiseTask;
//                            ByteQueue byteQueue;
//                            byteQueue = new ByteQueue();
//                            byteQueue.push(RxMethodType.SELECT_MASTER);
//                            byteQueue.pushU4B(beconDeviceClass.getBeaconUID());
//                            byteQueue.push(0x00);
//                            advertiseTask = new AdvertiseTask(this, activity);
//                            advertiseTask.setByteQueue(byteQueue);
//                            advertiseTask.setSearchRequestCode(SELECT_MASTER_RESPONSE);
//                            arrayList.get(selectedPosition).setMasterStatus(1);
//                            advertiseTask.startAdvertising();
//                            dialogBuilder.dismiss();
//                        }).withButton2Text("Cancel")
//                        .setButton2Click(v -> {
//                            dialogBuilder.dismiss();
//                        })
//                        .show();
//
            }
            notifyDataSetChanged();

        });
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public BeconDeviceClass getItem(int position) {
        if (arrayList.size() <= position)
            return null;
        return arrayList.get(position);
    }

    public void getAllGroups() {
        groupDetailsClasses.clear();
        GroupDetailsClass noGroupData = new GroupDetailsClass();
        noGroupData.setGroupId(0);
        noGroupData.setGroupName("No Group");
        noGroupData.setGroupStatus(true);
        groupDetailsClasses.add(noGroupData);
        Cursor cursor = sqlHelper.getAllGroup();
        if (cursor.moveToFirst()) {
            do {
                GroupDetailsClass groupData = new GroupDetailsClass();
                groupData.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                groupData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_PROGRESS)));
                groupData.setGroupName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_NAME)));
                groupData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_STATUS)) == 1);
                groupDetailsClasses.add(groupData);

                // do what ever you want here
//                for (int i = 1 ; i<=groupData.getGroupId();i++);

            }
            while (cursor.moveToNext());
        }

//
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    public void getAllROOMGroups() {
        groupRoomDetailsClasses.clear();
        RoomGroupDetailsClass noGroupData = new RoomGroupDetailsClass();
        noGroupData.setRoomGroupId(0);
        noGroupData.setGroupRoomName("No Room");
        noGroupData.setGroupStatus(true);
        groupRoomDetailsClasses.add(noGroupData);
        Cursor cursor = sqlHelper.getAllRoomGroup();
        if (cursor.moveToFirst()) {
            do {
                RoomGroupDetailsClass groupRoomData = new RoomGroupDetailsClass();
                groupRoomData.setRoomGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMID)));
                groupRoomData.setGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_ROOM_GROUP_PROGRESS)));
                groupRoomData.setGroupRoomName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME)));
                groupRoomData.setGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ROOMSTATUS)) == 1);
                groupRoomDetailsClasses.add(groupRoomData);
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
        noGroupData.setGroupSiteName("No Site");
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
        noGroupData.setGroupBuildingName("No Building");
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
        noGroupData.setGroupLevelName("No Level");
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
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).
                    inflate(R.layout.add_device_adapter, parent, false);

        }
        BeconDeviceClass beconDeviceClass = arrayList.get(position);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.addDevice.setText(beconDeviceClass.isAdded() ? "Added" : "Add");

//        String uid = beconDeviceClass.getDeviceUid();
//        Integer uid_int_dialog = Integer.parseInt(uid, 16);

//        Log.e("INTEGER=====>",uid_int_dialog.toString());

        if (beconDeviceClass.isAdded()) {
            viewHolder.addDevice.setVisibility(View.GONE);
            viewHolder.addDeviceUid.setText(beconDeviceClass.getDeviceName());
            viewHolder.statusSwitch.setVisibility(View.VISIBLE);
        } else {
            viewHolder.addDevice.setVisibility(View.VISIBLE);
            viewHolder.addDeviceUid.setText(beconDeviceClass.getDeviceUid());
            viewHolder.statusSwitch.setVisibility(View.GONE);
        }
        if (beconDeviceClass.getiBeaconUuid().equalsIgnoreCase("533")) {
            viewHolder.review1.setImageResource(R.mipmap.moko);
            viewHolder.statusSwitch.setVisibility(View.GONE);

        } else if (beconDeviceClass.getiBeaconUuid().equalsIgnoreCase("55811") && beconDeviceClass.getDeviceMacAddress().startsWith("E5:00")) {
            viewHolder.review1.setImageResource(R.mipmap.pir);
            viewHolder.statusSwitch.setVisibility(View.GONE);
        } else if (beconDeviceClass.getiBeaconUuid().equalsIgnoreCase("55811")) {
            viewHolder.review1.setImageResource(R.mipmap.switches);
            viewHolder.statusSwitch.setVisibility(View.GONE);

        } else {
            viewHolder.review1.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
        }
        if (beconDeviceClass.getTypeCode().equalsIgnoreCase("16")) {
            viewHolder.statusSwitch.setVisibility(View.VISIBLE);
        } else {
            viewHolder.statusSwitch.setVisibility(View.GONE);
        }
        viewHolder.statusSwitch.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                boolean switchStatus = state != State.LEFT;
                if (beconDeviceClass.getStatus() == switchStatus) {
//                    Log.w("Advertise","state is same");
                    return;
                }
                requestCode = RxMethodType.LIGHT_STATE_NEW_COMMAND;
                AdvertiseTask advertiseTask;
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push(requestCode);       ////State Command
                byteQueue.push(0x01);
                byteQueue.pushU4B(beconDeviceClass.getBeaconUID());      ////  12 is static vale for Node id
//                byteQueue.push(0x00);                                    ///0x00 – OFF    0x01 – ON
//                scannerTask.setRequestCode(TxMethodType.LIGHT_STATE_COMMAND_RESPONSE);
                Log.w(TAG, state + "");
                switch (state) {
                    case LEFT:
                        byteQueue.push(0x00);   //0x00 – OFF    0x01 – ON
                        arrayList.get(position).setStatus(false);

                        break;
                    case RIGHT:
                        byteQueue.push(0x01);   //0x00 – OFF    0x01 – ON
                        arrayList.get(position).setStatus(true);
                        break;
                    case LEFT_TO_RIGHT:

                        return;

                    case RIGHT_TO_LEFT:
                        return;

                }
                selectedPosition = position;
                advertiseTask = new AdvertiseTask(AddDeviceListAdapter.this, activity, 5 * 1000);
                advertiseTask.setByteQueue(byteQueue);
                Log.e("LIGHT====>", byteQueue.toString());
                advertiseTask.setSearchRequestCode(LIGHT_STATE_COMMAND_RESPONSE);
                advertiseTask.startAdvertising();
            }

        });

//        viewHolder.review1.setBackground(activity.getResources().getDrawable(beconDeviceClass.getMasterStatus()==0?R.drawable.white_circle_border:R.drawable.ic_lightbulb_outline_black_24dp));
        viewHolder.addDevice.setOnClickListener(view -> {
//            if(beconDeviceClass.isAdded())
//            {
//                NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(activity);
//                dialogBuilder
//                        .withTitle("ADD DEVICE")
//                        .withEffect(Effectstype.Slit)
//                        .withMessage("Device is already added")
//                        .withButton1Text("OK")
//                        .setButton1Click(v -> {
//                            dialogBuilder.dismiss();
//                        })
//                        .show();
//                return;
//            }
            showDialog(position);

        });
//        viewHolder.addDeviceUid.setText(beconDeviceClass.isAdded()?beconDeviceClass.getDeviceName():beconDeviceClass.getDeviceUid());

        return convertView;
    }

    void showAlert(int position, String message, String title) {
        if (title.length() < 1)
            title = "Alert";
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton("Block", (dialog1, id) -> {
            // User clicked OK button
//            acceptRequest(2,position);
            dialog1.dismiss();

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
    }

    @Override
    public void onStop(String stopMessage, int resultCode) {
        scannerTask = new ScannerTask(activity, this);
        scannerTask.setRequestCode(resultCode);
        scannerTask.start();
        Log.w(TAG, "Advertising stop" + resultCode);
    }


    @Override
    public void onScanSuccess(int successCode, ByteQueue byteQueue) {
        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();
        ContentValues contentValues = new ContentValues();

        Log.w("BYTEQUESIZE", byteQueue.size() + ",");
        Log.w("MethodType", (int) byteQueue.pop() + "");

        byte bytes1;
        String nodeUid;
        long deviceUid;
        int lightStatus;
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        switch (successCode) {
            case SELECT_MASTER_RESPONSE:


                BeconDeviceClass beconDeviceClass = arrayList.get(selectedPosition);
                arrayList.get(selectedPosition).setMasterStatus(1);
                lightStatus = byteQueue.pop();

                contentValues.put(COLUMN_DEVICE_MASTER_STATUS, lightStatus == 0 ? 1 : 0);


                dialogBuilder
                        .withTitle("Master Status")
                        .withEffect(Effectstype.RotateBottom)
                        .withMessage("Light is set as master")
                        .withButton1Text("OK")
                        .setButton1Click(v -> {
                            dialogBuilder.dismiss();
                        })
                        .show();
                Log.w("DashGroup", sqlHelper.updateDevice(beconDeviceClass.getBeaconUID(), contentValues) + "");

                break;


        }

    }

    @Override
    public void onScanFailed(int errorCode) {
        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();
//        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
//        dialogBuilder
//                .withTitle("Timeout")
//                .withEffect(Effectstype.Slit)
//                .withMessage("Timeout,Please check your beacon is in range")
//                .withButton1Text("OK")
//                .setButton1Click(v -> {
//                    dialogBuilder.dismiss();
//                })
//                .show();
    }

    static class ViewHolder {
        @BindView(R.id.review_1)
        ImageView review1;
        @BindView(R.id.status_switch)
        JellyToggleButton statusSwitch;
        @BindView(R.id.add_device)
        Button addDevice;
        //        @BindView(R.id.add_device_layout)
//        RelativeLayout addDeviceLayout;
        @BindView(R.id.add_device_uid)
        TextView addDeviceUid;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}







