package com.inferrix.lightsmart.fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType;
import com.inferrix.lightsmart.InterfaceModule.AdvertiseResultInterface;
import com.inferrix.lightsmart.InterfaceModule.ReceiverResultInterface;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
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

import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.ADD_GROUP_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_LEVEL_COMMAND_RESPONSE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class CreateGroup extends Fragment implements AdvertiseResultInterface, ReceiverResultInterface {
    @BindView(R.id.create_group_device_list)
    ListView createGroupDeviceList;
    Unbinder unbinder;
    Activity activity;
    long groupId=0;
    long groupSiteId=0;
    long deviceUid=0;
    long groupBuildingId=0;
    long groupLevelId=0;
    long groupRoomlId=0;
    ArrayList<DeviceClass> deviceList;
//    CreateGroupAdapter createGroupAdapter;
    @BindView(R.id.group_save)
    Button groupSave;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.create_group_name)
    EditText groupName;

    String TAG=this.getClass().getSimpleName();
    ScannerTask scannerTask;
    AnimatedProgress animatedProgress;
    String Type;

    public CreateGroup() {
        deviceList = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_group, container, false);
        unbinder = ButterKnife.bind(this, view);
        Type= getActivity().getIntent().getStringExtra("type");
        Log.e("TYPE====>",Type.trim());
        activity = getActivity();
        deviceList = new ArrayList<>();
        animatedProgress=new AnimatedProgress(activity);
//        createGroupAdapter = new CreateGroupAdapter(activity);
//        createGroupDeviceList.setAdapter(createGroupAdapter);
        scannerTask=new ScannerTask(activity,this);
        if (Type.equalsIgnoreCase("site")){
            textView4.setText("CREATE SITE GROUP");
        }else if (Type.equalsIgnoreCase("building")){
            textView4.setText("CREATE BUILDING GROUP");
        }else if (Type.equalsIgnoreCase("level")){
            textView4.setText("CREATE LEVEL GROUP");
        }else if (Type.equalsIgnoreCase("room")){
            textView4.setText("CREATE ROOM GROUP");
        }else if (Type.equalsIgnoreCase("group")){
            textView4.setText("CREATE GROUP");
        }
//        getDevice();
        return view;
    }

//    public void getDevice()
//    {
//        Cursor cursor = sqlHelper.getNonGroupDevice(DatabaseConstant.ADD_DEVICE_TABLE);
//        if (cursor.moveToFirst())
//        {
//            do {
//                DeviceClass deviceClass = new DeviceClass();
//                deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
//                deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
//                deviceClass.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_PROGRESS)));
//                deviceClass.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
//                deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS)) == 1);
//                deviceList.add(deviceClass);
//                // do what ever you want here
//            } while (cursor.moveToNext());
//        }
//        createGroupAdapter.setList(deviceList);
//        cursor.close();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.group_save)
    public void onViewClicked() {
        if (groupName.getText().toString().trim().length()<1) {
            groupName.setError("Group name is required.");
            return;
        }


        if (Type.equalsIgnoreCase("group")){
            ContentValues contentValues=new ContentValues();
            contentValues.put(DatabaseConstant.COLUMN_GROUP_NAME,groupName.getText().toString()+"");
            long groupId=sqlHelper.insertData(DatabaseConstant.GROUP_TABLE_NAME,contentValues);
            this.groupId=groupId;
            Log.w("CreateGroup",groupId+"");
            if(groupId>0) {
                Toast.makeText(activity, "create group successfully.", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
//          setGroupDevice(groupId);
            }
            else
            {
                Toast.makeText(activity, "cannot create group.", Toast.LENGTH_SHORT).show();
            }

        }else if (Type.equalsIgnoreCase("site")){
            ContentValues contentValues2=new ContentValues();
            contentValues2.put(DatabaseConstant.COLUMN_GROUP_DEVICE_SITENAME,groupName.getText().toString()+"");
            long groupId=sqlHelper.insertData(DatabaseConstant.GROUP_TABLE_SITE_NAME,contentValues2);
            this.groupSiteId=groupId;
            Log.w("CreateGroup",groupSiteId+"");
            if(groupId>0) {
                Toast.makeText(activity, "create site group successfully.", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
//          setGroupDevice(groupId);
            }
            else
            {
                Toast.makeText(activity, "cannot create group.", Toast.LENGTH_SHORT).show();
            }
        }else if (Type.equalsIgnoreCase("building")){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstant.COLUMN_GROUP_DEVICE_BUILDINGNAME,groupName.getText().toString());
            long groupId = sqlHelper.insertData(DatabaseConstant.GROUP_TABLE_BUILDING_NAME,contentValues);
            this.groupBuildingId= groupId;
            if (groupId>0){
                Toast.makeText(activity, " Add building group successfully", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            }else {
                Toast.makeText(activity, "cannot create Building group.", Toast.LENGTH_SHORT).show();
            }

        }else if (Type.equalsIgnoreCase("level")){
            ContentValues contentValues4 = new ContentValues();
            contentValues4.put(DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME,groupName.getText().toString());
            long groupId = sqlHelper.insertData(DatabaseConstant.GROUP_TABLE_LEVEL_NAME,contentValues4);
            this.groupLevelId= groupId;
            if (groupId>0){
                Toast.makeText(activity, " Add Level group successfully", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            }else {
                Toast.makeText(activity, "cannot create Level group.", Toast.LENGTH_SHORT).show();
            }
        }else if (Type.equalsIgnoreCase("room")){
            ContentValues contentValues5 = new ContentValues();
            contentValues5.put(DatabaseConstant.COLUMN_GROUP_DEVICE_ROOMNAME,groupName.getText().toString());
            long groupId = sqlHelper.insertData(DatabaseConstant.GROUP_TABLE_ROOM_NAME,contentValues5);
            this.groupRoomlId= groupId;
            if (groupId>0){
                Toast.makeText(activity, " Add Room group successfully", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            }else {
                Toast.makeText(activity, "cannot create Room group.", Toast.LENGTH_SHORT).show();
            }
        }


    }
//    public void setGroupDevice(long groupId) {
//        ArrayList<String> selectedLights=createGroupAdapter.getSelectedLight();
//        if (selectedLights.size()<1)
//        {
//            activity.onBackPressed();
//            return;
//        }
//        try {
//        int i=0;
//        for (String s:selectedLights)
//        {
//            DeviceClass deviceClass=deviceList.get(Integer.parseInt(s));
//
//                AdvertiseTask advertiseTask;
//                ByteQueue byteQueue=new ByteQueue();
//                byteQueue.push(RxMethodType.ADD_GROUP);
//                Log.w("GroupUid",deviceClass.getDeviceUID()+"");
//                byteQueue.pushU4B(deviceClass.getDeviceUID());
//                byteQueue.push(groupId);
//
//                deviceUid=deviceClass.getDeviceUID();
//                advertiseTask=new AdvertiseTask(this,activity,5*1000);
//                advertiseTask.setByteQueue(byteQueue);
//                advertiseTask.setSearchRequestCode(ADD_GROUP_RESPONSE);
//                advertiseTask.startAdvertising();
//
//        }
//
//        }catch (Exception e)
//        {
//            Log.w("Create Group",e.getMessage());
//        }
//    }

    @Override
    public void onScanSuccess(int successCode, ByteQueue byteQueue)
    {

        animatedProgress.hideProgress();
        Log.w("MethodType12",(int)byteQueue.pop()+","+successCode);


        int status=byteQueue.pop();
//                                String s = "4d0d08ada45f9dde1e99cad9";
        Log.w("Status",status+"");
        switch (successCode)
        {
            case ADD_GROUP_RESPONSE:
                if(status==0x00)
                {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseConstant.COLUMN_GROUP_ID, (int) groupId);
                    Log.w("Group123",sqlHelper.updateDevice(this.deviceUid, contentValues)+"");
                    activity.onBackPressed();
                }
                else
                    Log.w("Group","FAiled");
            break;

            case LIGHT_LEVEL_COMMAND_RESPONSE:
                break;
        }
    }

    @Override
    public void onScanFailed(int errorCode) {
        NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(activity);
        dialogBuilder
                .withTitle("Timeout")
                .withEffect(Effectstype.Newspager)
                .withMessage("Timeout,Please check your beacon is in range")
                .withButton1Text("OK")
                .setButton1Click(v -> {
                    dialogBuilder.dismiss();
                })
                .show();
        animatedProgress.hideProgress();
        Log.w(TAG,"onScanFailed "+errorCode);

    }

    @Override
    public void onSuccess(String message) {
        animatedProgress.showProgress();
    }

    @Override
    public void onFailed(String errorMessage) {

    }

    @Override
    public void onStop(String stopMessage, int resultCode) {

        scannerTask.setRequestCode(resultCode);
        scannerTask.start();
    }
}
