package com.inferrix.lightsmart.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.adapter.AssociateListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class AssociateFragment extends Fragment {
    Activity activity;
    Unbinder unbinder;
    @BindView(R.id.allDevice_list)
    ListView allDeviceList;
    AssociateListAdapter associateListAdapter;
    ArrayList<DeviceClass> deviceList;

    public AssociateFragment() {
        // Required empty public constructor
//        list = new ArrayList<>();
        deviceList = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.all_device_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity=getActivity();
        if (activity==null)
            return view;
        associateListAdapter =new AssociateListAdapter(activity);
        allDeviceList.setAdapter(associateListAdapter);

        return view;
    }

    public void getDevice() {
        deviceList=new ArrayList<>();
        Cursor cursor=sqlHelper.getAllDevice(DatabaseConstant.ADD_DEVICE_TABLE);
        if (cursor.moveToFirst()) {
            do{
                if (cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)).equalsIgnoreCase("16")&& cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS))==1)  {
                    DeviceClass deviceClass = new DeviceClass();
                    deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                    deviceClass.setDevicehexUid(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_HEXUID)));
                    deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                    deviceClass.setNumberOne(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_ONE)));
                    deviceClass.setNumberTwo(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_TWO)));
                    deviceClass.setNumberThree(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_THREE)));
                    deviceClass.setNumberFour(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_FOUR)));
                    deviceClass.setNumberFive(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_FIVE)));
                    deviceClass.setNumberSix(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_SIX)));
                    deviceClass.setNumberSeven(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_SEVEN)));
                    deviceClass.setNumberEight(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NUMBER_EIGET)));
                    deviceClass.setItemOne(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_ONE)));
                    deviceClass.setItemTwo(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_TWO)));
                    deviceClass.setItemThree(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_THREE)));
                    deviceClass.setItemFour(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_FOUR)));
                    deviceClass.setItemFive(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_FIVE)));
                    deviceClass.setItemSix(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_SIX)));
                    deviceClass.setItemSeven(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_SEVEN)));
                    deviceClass.setItemEight(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_ITEM_EIGET)));

                    deviceClass.setGroupTypeOne(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_ONE)));
                    deviceClass.setGroupTypeTwo(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_TWO)));
                    deviceClass.setGroupTypeThree(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_THREE)));
                    deviceClass.setGroupTypeFour(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FOUR)));
                    deviceClass.setGroupTypeFive(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FIVE)));
                    deviceClass.setGroupTypeSix(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SIX)));
                    deviceClass.setGroupTypeSeven(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SEVEN)));
                    deviceClass.setGroupTypeEight(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_EIGHT)));

                    deviceClass.setTypeCode(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_TYPE_CODE)));
                    deviceClass.setMasterStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_MASTER_STATUS)));
                    deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS)) == 1);
                    deviceList.add(deviceClass);
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        associateListAdapter.setList(deviceList);
    }

    @Override
    public void onResume() {
        getDevice();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
