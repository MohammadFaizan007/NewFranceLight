package com.inferrix.lightsmart.fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.InterfaceModule.AdvertiseResultInterface;
import com.inferrix.lightsmart.InterfaceModule.ReceiverResultInterface;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.ServiceModule.AdvertiseTask;
import com.inferrix.lightsmart.ServiceModule.ScannerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_ASSOCIATE;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.REMOVE_ASSOCIATE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class AssociateAddFragment extends Fragment implements AdvertiseResultInterface, ReceiverResultInterface {
    DeviceClass deviceClass;
    String TAG = this.getClass().getSimpleName();
    Activity activity;
    Unbinder unbinder;
    int requestCode;
    AnimatedProgress animatedProgress;
    ScannerTask scannerTask;

    @BindView(R.id.card_one)
    CardView card_one;
    @BindView(R.id.card_two)
    CardView card_two;
    @BindView(R.id.card_three)
    CardView card_three;
    @BindView(R.id.card_four)
    CardView card_four;
    @BindView(R.id.card_five)
    CardView card_five;
    @BindView(R.id.card_six)
    CardView card_six;
    @BindView(R.id.card_seven)
    CardView card_seven;
    @BindView(R.id.card_eight)
    CardView card_eight;

    @BindView(R.id.uid_no1)
    EditText uid_no1;
    @BindView(R.id.uid_no2)
    EditText uid_no2;
    @BindView(R.id.uid_no3)
    EditText uid_no3;
    @BindView(R.id.uid_no4)
    EditText uid_no4;
    @BindView(R.id.uid_no5)
    EditText uid_no5;
    @BindView(R.id.uid_no6)
    EditText uid_no6;
    @BindView(R.id.uid_no7)
    EditText uid_no7;
    @BindView(R.id.uid_no8)
    EditText uid_no8;

    @BindView(R.id.select_item1)
    EditText select_item1;
    @BindView(R.id.select_item2)
    EditText select_item2;
    @BindView(R.id.select_item3)
    EditText select_item3;
    @BindView(R.id.select_item4)
    EditText select_item4;
    @BindView(R.id.select_item5)
    EditText select_item5;
    @BindView(R.id.select_item6)
    EditText select_item6;
    @BindView(R.id.select_item7)
    EditText select_item7;
    @BindView(R.id.select_item8)
    EditText select_item8;


    @BindView(R.id.btn_submit1)
    Button btn_submit1;
    @BindView(R.id.btn_submit2)
    Button btn_submit2;
    @BindView(R.id.btn_submit3)
    Button btn_submit3;
    @BindView(R.id.btn_submit4)
    Button btn_submit4;
    @BindView(R.id.btn_submit5)
    Button btn_submit5;
    @BindView(R.id.btn_submit6)
    Button btn_submit6;
    @BindView(R.id.btn_submit7)
    Button btn_submit7;
    @BindView(R.id.btn_submit8)
    Button btn_submit8;

    @BindView(R.id.btn_remove1)
    Button btn_remove1;
    @BindView(R.id.btn_remove2)
    Button btn_remove2;
    @BindView(R.id.btn_remove3)
    Button btn_remove3;
    @BindView(R.id.btn_remove4)
    Button btn_remove4;
    @BindView(R.id.btn_remove5)
    Button btn_remove5;
    @BindView(R.id.btn_remove6)
    Button btn_remove6;
    @BindView(R.id.btn_remove7)
    Button btn_remove7;
    @BindView(R.id.btn_remove8)
    Button btn_remove8;

    @BindView(R.id.viewDetail1)
    ImageView viewDetail1;
    @BindView(R.id.viewDetail2)
    ImageView viewDetail2;
    @BindView(R.id.viewDetail3)
    ImageView viewDetail3;
    @BindView(R.id.viewDetail4)
    ImageView viewDetail4;
    @BindView(R.id.viewDetail5)
    ImageView viewDetail5;
    @BindView(R.id.viewDetail6)
    ImageView viewDetail6;
    @BindView(R.id.viewDetail7)
    ImageView viewDetail7;



    public AssociateAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_associate, container, false);
        activity = getActivity();
        unbinder = ButterKnife.bind(this, view);
        scannerTask = new ScannerTask(activity, this);
        animatedProgress = new AnimatedProgress(activity);
        animatedProgress.setCancelable(false);



        if (deviceClass.getNumberOne().equalsIgnoreCase("")){
            uid_no1.setHint("No address associated");
        }else {
            uid_no1.setHint(deviceClass.getNumberOne());
        }

        if (deviceClass.getNumberTwo().equalsIgnoreCase("")){
            uid_no2.setHint("No address associated");
            card_two.setVisibility(View.GONE);
        }else {
            uid_no2.setHint(deviceClass.getNumberTwo());
            card_two.setVisibility(View.VISIBLE);
            viewDetail1.setBackgroundResource(R.drawable.minus_ic);
        }

        if (deviceClass.getNumberThree().equalsIgnoreCase("")){
            uid_no3.setHint("No address associated");
            card_three.setVisibility(View.GONE);
        }else {
            uid_no3.setHint(deviceClass.getNumberThree());
            card_three.setVisibility(View.VISIBLE);
            viewDetail2.setBackgroundResource(R.drawable.minus_ic);
        }

        if (deviceClass.getNumberFour().equalsIgnoreCase("")){
            uid_no4.setHint("No address associated");
            card_four.setVisibility(View.GONE);
        }else {
            uid_no4.setHint(deviceClass.getNumberFour());
            card_four.setVisibility(View.VISIBLE);
            viewDetail3.setBackgroundResource(R.drawable.minus_ic);
        }

        if (deviceClass.getNumberFive().equalsIgnoreCase("")){
            uid_no5.setHint("No address associated");
            card_five.setVisibility(View.GONE);
        }else {
            uid_no5.setHint(deviceClass.getNumberFive());
            card_five.setVisibility(View.VISIBLE);
            viewDetail4.setBackgroundResource(R.drawable.minus_ic);
        }

        if (deviceClass.getNumberSix().equalsIgnoreCase("")){
            uid_no6.setHint("No address associated");
            card_six.setVisibility(View.GONE);
        }else {
            uid_no6.setHint(deviceClass.getNumberSix());
            card_six.setVisibility(View.VISIBLE);
            viewDetail5.setBackgroundResource(R.drawable.minus_ic);
        }

        if (deviceClass.getNumberSeven().equalsIgnoreCase("")){
            uid_no7.setHint("No address associated");
            card_seven.setVisibility(View.GONE);
        }else {
            uid_no7.setHint(deviceClass.getNumberSeven());
            card_seven.setVisibility(View.VISIBLE);
            viewDetail6.setBackgroundResource(R.drawable.minus_ic);
        }

        if (deviceClass.getNumberEight().equalsIgnoreCase("")){
            uid_no8.setHint("No address associated");
            card_eight.setVisibility(View.GONE);
        }else {
            uid_no8.setHint(deviceClass.getNumberEight());
            card_eight.setVisibility(View.VISIBLE);
            viewDetail7.setBackgroundResource(R.drawable.minus_ic);
        }







        if (deviceClass.getItemOne().equalsIgnoreCase("")){
            select_item1.setHint("Select");
        }else {
            select_item1.setText(deviceClass.getItemOne());
        }
        if (deviceClass.getItemTwo().equalsIgnoreCase("")){
            select_item2.setHint("Select");
        }else {
            select_item2.setText(deviceClass.getItemTwo());
        }
        if (deviceClass.getItemThree().equalsIgnoreCase("")){
            select_item3.setHint("Select");
        }else {
            select_item3.setText(deviceClass.getItemThree());
        }
        if (deviceClass.getItemFour().equalsIgnoreCase("")){
            select_item4.setHint("Select");
        }else {
            select_item4.setText(deviceClass.getItemFour());
        }

        if (deviceClass.getItemFive().equalsIgnoreCase("")){
            select_item5.setHint("Select");
        }else {
            select_item5.setText(deviceClass.getItemFive());
        }
        if (deviceClass.getItemSix().equalsIgnoreCase("")){
            select_item6.setHint("Select");
        }else {
            select_item6.setText(deviceClass.getItemSix());
        }
        if (deviceClass.getItemSeven().equalsIgnoreCase("")){
            select_item7.setHint("Select");
        }else {
            select_item7.setText(deviceClass.getItemSeven());
        }

        if (deviceClass.getItemEight().equalsIgnoreCase("")){
            select_item8.setHint("Select");
        }else {
            select_item8.setText(deviceClass.getItemEight());
        }




        return view;
    }

    public void setDeviceData(DeviceClass deviceData) {
        this.deviceClass = deviceData;
    }


    @OnClick({R.id.card_one, R.id.card_two, R.id.card_three, R.id.card_four, R.id.card_five, R.id.card_six, R.id.card_seven, R.id.card_eight,
            R.id.uid_no1, R.id.uid_no2, R.id.uid_no3, R.id.uid_no4, R.id.uid_no5, R.id.uid_no6, R.id.uid_no7, R.id.uid_no8,
            R.id.select_item1, R.id.select_item2, R.id.select_item3, R.id.select_item4, R.id.select_item5, R.id.select_item6, R.id.select_item7, R.id.select_item8,
            R.id.viewDetail1, R.id.viewDetail2, R.id.viewDetail3, R.id.viewDetail4, R.id.viewDetail5, R.id.viewDetail6, R.id.viewDetail7,
            R.id.btn_submit1, R.id.btn_submit2, R.id.btn_submit3, R.id.btn_submit4, R.id.btn_submit5, R.id.btn_submit6, R.id.btn_submit7, R.id.btn_submit8,
            R.id.btn_remove1,R.id.btn_remove2,R.id.btn_remove3,R.id.btn_remove4,R.id.btn_remove5,R.id.btn_remove6,R.id.btn_remove7,R.id.btn_remove8
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.viewDetail1:
                if (card_two.getVisibility() == View.VISIBLE) {
                    card_two.setVisibility(View.GONE);
                    viewDetail1.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_two.setVisibility(View.VISIBLE);
                    viewDetail1.setBackgroundResource(R.drawable.minus_ic);
                }
                break;
            case R.id.viewDetail2:
                if (card_three.getVisibility() == View.VISIBLE) {
                    card_three.setVisibility(View.GONE);
                    viewDetail2.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_three.setVisibility(View.VISIBLE);
                    viewDetail2.setBackgroundResource(R.drawable.minus_ic);

                }
                break;
            case R.id.viewDetail3:
                if (card_four.getVisibility() == View.VISIBLE) {
                    card_four.setVisibility(View.GONE);
                    viewDetail3.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_four.setVisibility(View.VISIBLE);
                    viewDetail3.setBackgroundResource(R.drawable.minus_ic);

                }
                break;

            case R.id.viewDetail4:
                if (card_five.getVisibility() == View.VISIBLE) {
                    card_five.setVisibility(View.GONE);
                    viewDetail4.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_five.setVisibility(View.VISIBLE);
                    viewDetail4.setBackgroundResource(R.drawable.minus_ic);

                }
                break;

            case R.id.viewDetail5:
                if (card_six.getVisibility() == View.VISIBLE) {
                    card_six.setVisibility(View.GONE);
                    viewDetail5.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_six.setVisibility(View.VISIBLE);
                    viewDetail5.setBackgroundResource(R.drawable.minus_ic);

                }
                break;
            case R.id.viewDetail6:
                if (card_seven.getVisibility() == View.VISIBLE) {
                    card_seven.setVisibility(View.GONE);
                    viewDetail6.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_seven.setVisibility(View.VISIBLE);
                    viewDetail6.setBackgroundResource(R.drawable.minus_ic);
                }
                break;

            case R.id.viewDetail7:
                if (card_eight.getVisibility() == View.VISIBLE) {
                    card_eight.setVisibility(View.GONE);
                    viewDetail7.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    card_eight.setVisibility(View.VISIBLE);
                    viewDetail7.setBackgroundResource(R.drawable.minus_ic);
                }
                break;
            case R.id.select_item1:
                PopupMenu airPopUp = new PopupMenu(activity, select_item1);
                airPopUp.getMenuInflater().inflate(R.menu.sensor_type, airPopUp.getMenu());
//                if (select_item1.getText().toString().equalsIgnoreCase(""))
                airPopUp.setOnMenuItemClickListener(item -> {
                    try {
                        select_item1.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                airPopUp.show();

                break;
            case R.id.select_item2:
                PopupMenu airPopUp2 = new PopupMenu(activity, select_item2);
                airPopUp2.getMenuInflater().inflate(R.menu.sensor_type, airPopUp2.getMenu());
                airPopUp2.setOnMenuItemClickListener(item -> {
                    try {
                        select_item2.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                airPopUp2.show();
                break;
            case R.id.select_item3:
                PopupMenu PopUp3 = new PopupMenu(activity, select_item3);
                PopUp3.getMenuInflater().inflate(R.menu.sensor_type, PopUp3.getMenu());
                PopUp3.setOnMenuItemClickListener(item -> {
                    try {
                        select_item3.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                PopUp3.show();
                break;
            case R.id.select_item4:
                PopupMenu PopUp4 = new PopupMenu(activity, select_item4);
                PopUp4.getMenuInflater().inflate(R.menu.sensor_type, PopUp4.getMenu());
                PopUp4.setOnMenuItemClickListener(item -> {
                    try {
                        select_item4.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                PopUp4.show();
                break;
            case R.id.select_item5:
                PopupMenu PopUp5 = new PopupMenu(activity, select_item5);
                PopUp5.getMenuInflater().inflate(R.menu.sensor_type, PopUp5.getMenu());
                PopUp5.setOnMenuItemClickListener(item -> {
                    try {
                        select_item5.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                PopUp5.show();
                break;
            case R.id.select_item6:
                PopupMenu PopUp6 = new PopupMenu(activity, select_item6);
                PopUp6.getMenuInflater().inflate(R.menu.sensor_type, PopUp6.getMenu());
                PopUp6.setOnMenuItemClickListener(item -> {
                    try {
                        select_item6.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                PopUp6.show();
                break;
            case R.id.select_item7:
                PopupMenu PopUp7 = new PopupMenu(activity, select_item7);
                PopUp7.getMenuInflater().inflate(R.menu.sensor_type, PopUp7.getMenu());
                PopUp7.setOnMenuItemClickListener(item -> {
                    try {
                        select_item7.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                PopUp7.show();
                break;
            case R.id.select_item8:
                PopupMenu PopUp8 = new PopupMenu(activity, select_item8);
                PopUp8.getMenuInflater().inflate(R.menu.sensor_type, PopUp8.getMenu());
                PopUp8.setOnMenuItemClickListener(item -> {
                    try {
                        select_item8.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                PopUp8.show();
                break;
            case R.id.btn_submit1:
                if (uid_no1.getText().toString().trim().length() < 1) {
                    uid_no1.setError("Address can't empty");
                    return;
                }
                else if (select_item1.getText().toString().length()<1){
                    select_item1.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues=new ContentValues();
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_ONE,uid_no1.getText().toString());
                contentValues.put(DatabaseConstant.COLUMN_DEVICE_ITEM_ONE,select_item1.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }

                AdvertiseTask advertiseTask = new AdvertiseTask(activity);
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push(ADD_ASSOCIATE);
                byteQueue.pushU4B(deviceClass.getDeviceUID());
                if (select_item1.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue.push(0x11);
                } else if (select_item1.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue.push(0x12);
                } else if (select_item1.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue.push(0x13);
                }
                byteQueue.pushU4B(Long.valueOf(uid_no1.getText().toString().trim()));
                advertiseTask = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask.setByteQueue(byteQueue);
                Log.e("Check>>>>", byteQueue.toString());
                advertiseTask.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask.startAdvertising();
                break;
            case R.id.btn_submit2:
                if (uid_no2.getText().toString().trim().length() < 1) {
                    uid_no2.setError("Address can't empty");
                    return;
                }else if (select_item2.getText().toString().length()<1){
                    select_item2.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues2=new ContentValues();
                contentValues2.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_TWO,uid_no2.getText().toString());
                contentValues2.put(DatabaseConstant.COLUMN_DEVICE_ITEM_TWO,select_item2.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues2)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }
                AdvertiseTask advertiseTask2 = new AdvertiseTask(activity);
                ByteQueue byteQueue2 = new ByteQueue();
                byteQueue2.push(ADD_ASSOCIATE);
                byteQueue2.pushU4B(deviceClass.getDeviceUID());
                if (select_item2.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue2.push(0x21);
                } else if (select_item2.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue2.push(0x22);
                } else if (select_item2.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue2.push(0x23);
                }
                byteQueue2.pushU4B(Long.valueOf(uid_no2.getText().toString().trim()));
                advertiseTask2 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask2.setByteQueue(byteQueue2);
                Log.e("Check>>>>", byteQueue2.toString());
                advertiseTask2.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask2.startAdvertising();
                break;
            case R.id.btn_submit3:
                if (uid_no3.getText().toString().trim().length() < 1) {
                    uid_no3.setError("Address can't empty");
                    return;
                }else if (select_item3.getText().toString().length()<1){
                    select_item3.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues3=new ContentValues();
                contentValues3.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_THREE,uid_no3.getText().toString());
                contentValues3.put(DatabaseConstant.COLUMN_DEVICE_ITEM_THREE,select_item3.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues3)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }
                AdvertiseTask advertiseTask3 = new AdvertiseTask(activity);
                ByteQueue byteQueue3 = new ByteQueue();
                byteQueue3.push(ADD_ASSOCIATE);
                byteQueue3.pushU4B(deviceClass.getDeviceUID());
                if (select_item3.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue3.push(0x31);
                } else if (select_item3.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue3.push(0x32);
                } else if (select_item3.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue3.push(0x33);
                }
                byteQueue3.pushU4B(Long.valueOf(uid_no3.getText().toString().trim()));
                advertiseTask3 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask3.setByteQueue(byteQueue3);
                Log.e("Check>>>>", byteQueue3.toString());
                advertiseTask3.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask3.startAdvertising();
                break;
            case R.id.btn_submit4:
                if (uid_no4.getText().toString().trim().length() < 1) {
                    uid_no4.setError("Address can't empty");
                    return;
                }else if (select_item4.getText().toString().length()<1){
                    select_item4.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues4=new ContentValues();
                contentValues4.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_FOUR,uid_no4.getText().toString());
                contentValues4.put(DatabaseConstant.COLUMN_DEVICE_ITEM_FOUR,select_item4.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues4)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }
                AdvertiseTask advertiseTask4 = new AdvertiseTask(activity);
                ByteQueue byteQueue4 = new ByteQueue();
                byteQueue4.push(ADD_ASSOCIATE);
                byteQueue4.pushU4B(deviceClass.getDeviceUID());
                if (select_item4.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue4.push(0x41);
                } else if (select_item4.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue4.push(0x42);
                } else if (select_item4.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue4.push(0x43);
                }
                byteQueue4.pushU4B(Long.valueOf(uid_no4.getText().toString().trim()));
                advertiseTask4 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask4.setByteQueue(byteQueue4);
                Log.e("Check>>>>", byteQueue4.toString());
                advertiseTask4.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask4.startAdvertising();
                break;
            case R.id.btn_submit5:
                if (uid_no5.getText().toString().trim().length() < 1) {
                    uid_no5.setError("Address can't empty");
                    return;
                }else if (select_item5.getText().toString().length()<1){
                    select_item5.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues5=new ContentValues();
                contentValues5.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_FIVE,uid_no5.getText().toString());
                contentValues5.put(DatabaseConstant.COLUMN_DEVICE_ITEM_FIVE,select_item5.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues5)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }
                AdvertiseTask advertiseTask5 = new AdvertiseTask(activity);
                ByteQueue byteQueue5 = new ByteQueue();
                byteQueue5.push(ADD_ASSOCIATE);
                byteQueue5.pushU4B(deviceClass.getDeviceUID());
                if (select_item5.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue5.push(0x51);
                } else if (select_item5.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue5.push(0x52);
                } else if (select_item5.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue5.push(0x53);
                }
                byteQueue5.pushU4B(Long.valueOf(uid_no5.getText().toString().trim()));
                advertiseTask5 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask5.setByteQueue(byteQueue5);
                Log.e("Check>>>>", byteQueue5.toString());
                advertiseTask5.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask5.startAdvertising();
                break;
            case R.id.btn_submit6:
                if (uid_no6.getText().toString().trim().length() < 1) {
                    uid_no6.setError("Address can't empty");
                    return;
                }else if (select_item6.getText().toString().length()<1){
                    select_item6.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues6=new ContentValues();
                contentValues6.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_SIX,uid_no6.getText().toString());
                contentValues6.put(DatabaseConstant.COLUMN_DEVICE_ITEM_SIX,select_item6.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues6)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }
                AdvertiseTask advertiseTask6 = new AdvertiseTask(activity);
                ByteQueue byteQueue6 = new ByteQueue();
                byteQueue6.push(ADD_ASSOCIATE);
                byteQueue6.pushU4B(deviceClass.getDeviceUID());
                if (select_item6.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue6.push(0x61);
                } else if (select_item6.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue6.push(0x62);
                } else if (select_item6.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue6.push(0x63);
                }
                byteQueue6.pushU4B(Long.valueOf(uid_no6.getText().toString().trim()));
                advertiseTask6 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask6.setByteQueue(byteQueue6);
                Log.e("Check>>>>", byteQueue6.toString());
                advertiseTask6.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask6.startAdvertising();
                break;
            case R.id.btn_submit7:
                if (uid_no7.getText().toString().trim().length() < 1) {
                    uid_no7.setError("Address can't empty");
                    return;
                }else if (select_item7.getText().toString().length()<1){
                    select_item7.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues7=new ContentValues();
                contentValues7.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_SEVEN,uid_no7.getText().toString());
                contentValues7.put(DatabaseConstant.COLUMN_DEVICE_ITEM_SEVEN,select_item7.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues7)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }
                AdvertiseTask advertiseTask7 = new AdvertiseTask(activity);
                ByteQueue byteQueue7 = new ByteQueue();
                byteQueue7.push(ADD_ASSOCIATE);
                byteQueue7.pushU4B(deviceClass.getDeviceUID());
                if (select_item7.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue7.push(0x71);
                } else if (select_item7.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue7.push(0x72);
                } else if (select_item7.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue7.push(0x73);
                }
                byteQueue7.pushU4B(Long.valueOf(uid_no7.getText().toString().trim()));
                advertiseTask7 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask7.setByteQueue(byteQueue7);
                Log.e("Check>>>>", byteQueue7.toString());
                advertiseTask7.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask7.startAdvertising();
                break;
            case R.id.btn_submit8:
                if (uid_no8.getText().toString().trim().length() < 1) {
                    uid_no8.setError("Address can't empty");
                    return;
                }else if (select_item8.getText().toString().length()<1){
                    select_item8.setError("Please select sensor type");
                    return;
                }
                ContentValues contentValues8=new ContentValues();
                contentValues8.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_EIGET,uid_no8.getText().toString());
                contentValues8.put(DatabaseConstant.COLUMN_DEVICE_ITEM_EIGET,select_item8.getText().toString());
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues8)) {
                    Toast.makeText(activity, "Device added successfully.", Toast.LENGTH_SHORT).show();
                }

                AdvertiseTask advertiseTask8 = new AdvertiseTask(activity);
                ByteQueue byteQueue8 = new ByteQueue();
                byteQueue8.push(ADD_ASSOCIATE);
                byteQueue8.pushU4B(deviceClass.getDeviceUID());
                if (select_item8.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue8.push(0x81);
                } else if (select_item8.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue8.push(0x82);
                } else if (select_item8.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue8.push(0x83);
                }
                byteQueue8.pushU4B(Long.valueOf(uid_no8.getText().toString().trim()));
                advertiseTask8 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask8.setByteQueue(byteQueue8);
                Log.e("Check>>>>", byteQueue8.toString());
                advertiseTask8.setSearchRequestCode(ADD_ASSOCIATE);
                advertiseTask8.startAdvertising();
                break;
            case R.id.btn_remove1:
                ContentValues contentValuesRemove=new ContentValues();
                contentValuesRemove.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_ONE,"");
                contentValuesRemove.put(DatabaseConstant.COLUMN_DEVICE_ITEM_ONE,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove)) {
                }
                AdvertiseTask advertiseTask1 = new AdvertiseTask(activity);
                ByteQueue byteQueue1 = new ByteQueue();
                byteQueue1.push(REMOVE_ASSOCIATE);
                byteQueue1.pushU4B(deviceClass.getDeviceUID());
                if (select_item1.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueue1.push(0x11);
                } else if (select_item1.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueue1.push(0x12);
                } else if (select_item1.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueue1.push(0x13);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueue1.pushU4B(Long.valueOf("12345678"));
                advertiseTask1 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTask1.setByteQueue(byteQueue1);
                Log.e("Check>>>>", byteQueue1.toString());
                advertiseTask1.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTask1.startAdvertising();
                break;
            case R.id.btn_remove2:
                ContentValues contentValuesRemove2=new ContentValues();
                contentValuesRemove2.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_TWO,"");
                contentValuesRemove2.put(DatabaseConstant.COLUMN_DEVICE_ITEM_TWO,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove2)) {
                }
                AdvertiseTask advertiseTaskRemove2 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove2 = new ByteQueue();
                byteQueueRemove2.push(REMOVE_ASSOCIATE);
                byteQueueRemove2.pushU4B(deviceClass.getDeviceUID());
                if (select_item2.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove2.push(0x21);
                } else if (select_item2.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove2.push(0x22);
                } else if (select_item2.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove2.push(0x23);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove2.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove2 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove2.setByteQueue(byteQueueRemove2);
                Log.e("Check>>>>", byteQueueRemove2.toString());
                advertiseTaskRemove2.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove2.startAdvertising();
                card_two.setVisibility(View.GONE);
                viewDetail1.setBackgroundResource(R.drawable.pluse_ic);
                break;
            case R.id.btn_remove3:
                ContentValues contentValuesRemove3=new ContentValues();
                contentValuesRemove3.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_THREE,"");
                contentValuesRemove3.put(DatabaseConstant.COLUMN_DEVICE_ITEM_THREE,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove3)) {
                }
                AdvertiseTask advertiseTaskRemove3 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove3 = new ByteQueue();
                byteQueueRemove3.push(REMOVE_ASSOCIATE);
                byteQueueRemove3.pushU4B(deviceClass.getDeviceUID());
                if (select_item3.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove3.push(0x31);
                } else if (select_item3.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove3.push(0x32);
                } else if (select_item3.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove3.push(0x33);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove3.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove3 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove3.setByteQueue(byteQueueRemove3);
                Log.e("Check>>>>", byteQueueRemove3.toString());
                advertiseTaskRemove3.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove3.startAdvertising();
                card_three.setVisibility(View.GONE);
                viewDetail2.setBackgroundResource(R.drawable.pluse_ic);
                break;
            case R.id.btn_remove4:
                ContentValues contentValuesRemove4=new ContentValues();
                contentValuesRemove4.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_FOUR,"");
                contentValuesRemove4.put(DatabaseConstant.COLUMN_DEVICE_ITEM_FOUR,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove4)) {
                }
                AdvertiseTask advertiseTaskRemove4 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove4 = new ByteQueue();
                byteQueueRemove4.push(REMOVE_ASSOCIATE);
                byteQueueRemove4.pushU4B(deviceClass.getDeviceUID());
                if (select_item4.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove4.push(0x41);
                } else if (select_item4.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove4.push(0x42);
                } else if (select_item4.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove4.push(0x43);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove4.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove4 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove4.setByteQueue(byteQueueRemove4);
                Log.e("Check>>>>", byteQueueRemove4.toString());
                advertiseTaskRemove4.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove4.startAdvertising();
                card_four.setVisibility(View.GONE);
                viewDetail3.setBackgroundResource(R.drawable.pluse_ic);
                break;
            case R.id.btn_remove5:
                ContentValues contentValuesRemove5=new ContentValues();
                contentValuesRemove5.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_FIVE,"");
                contentValuesRemove5.put(DatabaseConstant.COLUMN_DEVICE_ITEM_FIVE,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove5)) {
                }
                AdvertiseTask advertiseTaskRemove5 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove5 = new ByteQueue();
                byteQueueRemove5.push(REMOVE_ASSOCIATE);
                byteQueueRemove5.pushU4B(deviceClass.getDeviceUID());
                if (select_item5.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove5.push(0x51);
                } else if (select_item5.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove5.push(0x52);
                } else if (select_item5.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove5.push(0x53);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove5.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove5 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove5.setByteQueue(byteQueueRemove5);
                Log.e("Check>>>>", byteQueueRemove5.toString());
                advertiseTaskRemove5.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove5.startAdvertising();
                card_five.setVisibility(View.GONE);
                viewDetail4.setBackgroundResource(R.drawable.pluse_ic);
                break;
            case R.id.btn_remove6:
                ContentValues contentValuesRemove6=new ContentValues();
                contentValuesRemove6.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_SIX,"");
                contentValuesRemove6.put(DatabaseConstant.COLUMN_DEVICE_ITEM_SIX,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove6)) {
                }
                AdvertiseTask advertiseTaskRemove6 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove6 = new ByteQueue();
                byteQueueRemove6.push(REMOVE_ASSOCIATE);
                byteQueueRemove6.pushU4B(deviceClass.getDeviceUID());
                if (select_item6.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove6.push(0x61);
                } else if (select_item6.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove6.push(0x62);
                } else if (select_item6.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove6.push(0x63);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove6.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove6 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove6.setByteQueue(byteQueueRemove6);
                Log.e("Check>>>>", byteQueueRemove6.toString());
                advertiseTaskRemove6.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove6.startAdvertising();
                card_six.setVisibility(View.GONE);
                viewDetail5.setBackgroundResource(R.drawable.pluse_ic);
                break;
            case R.id.btn_remove7:
                ContentValues contentValuesRemove7=new ContentValues();
                contentValuesRemove7.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_SEVEN,"");
                contentValuesRemove7.put(DatabaseConstant.COLUMN_DEVICE_ITEM_SEVEN,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove7)) {
                }
                AdvertiseTask advertiseTaskRemove7 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove7 = new ByteQueue();
                byteQueueRemove7.push(REMOVE_ASSOCIATE);
                byteQueueRemove7.pushU4B(deviceClass.getDeviceUID());
                if (select_item7.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove7.push(0x71);
                } else if (select_item7.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove7.push(0x72);
                } else if (select_item7.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove7.push(0x73);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove7.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove7 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove7.setByteQueue(byteQueueRemove7);
                Log.e("Check>>>>", byteQueueRemove7.toString());
                advertiseTaskRemove7.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove7.startAdvertising();
                card_seven.setVisibility(View.GONE);
                viewDetail6.setBackgroundResource(R.drawable.pluse_ic);
                break;
            case R.id.btn_remove8:
                ContentValues contentValuesRemove8=new ContentValues();
                contentValuesRemove8.put(DatabaseConstant.COLUMN_DEVICE_NUMBER_EIGET,"");
                contentValuesRemove8.put(DatabaseConstant.COLUMN_DEVICE_ITEM_EIGET,"");
                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValuesRemove8)) {
                }
                AdvertiseTask advertiseTaskRemove8 = new AdvertiseTask(activity);
                ByteQueue byteQueueRemove8 = new ByteQueue();
                byteQueueRemove8.push(REMOVE_ASSOCIATE);
                byteQueueRemove8.pushU4B(deviceClass.getDeviceUID());
                if (select_item8.getText().toString().equalsIgnoreCase("PIR")) {
                    byteQueueRemove8.push(0x81);
                } else if (select_item8.getText().toString().equalsIgnoreCase("Switch")) {
                    byteQueueRemove8.push(0x82);
                } else if (select_item8.getText().toString().equalsIgnoreCase("Day Light")) {
                    byteQueueRemove8.push(0x83);
                }
//                byteQueue1.push(PIR_INFO);
                byteQueueRemove8.pushU4B(Long.valueOf("12345678"));
                advertiseTaskRemove8 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
                animatedProgress.setText("Uploading");
                advertiseTaskRemove8.setByteQueue(byteQueueRemove8);
                Log.e("Check>>>>", byteQueueRemove8.toString());
                advertiseTaskRemove8.setSearchRequestCode(REMOVE_ASSOCIATE);
                advertiseTaskRemove8.startAdvertising();
                card_eight.setVisibility(View.GONE);
                viewDetail7.setBackgroundResource(R.drawable.pluse_ic);
                break;

        }
    }
//                if (uid_no.getText().toString().trim().length()<1){
//                    uid_no.setError("Address can't empty");
//                    return;
//                }
//                ContentValues contentValues=new ContentValues();
//                contentValues.put(DatabaseConstant.COLUMN_DEVICE_PIR_NUMBER,uid_no.getText().toString());
//                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues)) {
//                    Toast.makeText(activity, "Device  added successfully.", Toast.LENGTH_SHORT).show();
//                }
//                AdvertiseTask advertiseTask = new AdvertiseTask(activity);
//                ByteQueue byteQueue = new ByteQueue();
//                byteQueue.push(ADD_ASSOCIATE);
//                byteQueue.pushU4B(deviceClass.getDeviceUID());
//                int selectedId = radio_group.getCheckedRadioButtonId();
//                if(selectedId == pir.getId()) {
//                    byteQueue.push(PIR_INFO);
//                } else if (selectedId==daylight.getId()){
//                    byteQueue.push(0x03);
//                }
//                byteQueue.pushU4B(Long.valueOf(uid_no.getText().toString().trim()));
//                advertiseTask = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
//                animatedProgress.setText("Uploading");
//                advertiseTask.setByteQueue(byteQueue);
//                Log.e("Check>>>>", byteQueue.toString());
//                advertiseTask.setSearchRequestCode(ADD_ASSOCIATE);
//                advertiseTask.startAdvertising();
//
//                break;
//
//            case R.id.remove_sensor:
//                ContentValues contentValues4=new ContentValues();
//                contentValues4.put(DatabaseConstant.COLUMN_DEVICE_PIR_NUMBER,"");
//                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues4)) {
//                }
//                AdvertiseTask advertiseTask1 = new AdvertiseTask(activity);
//                ByteQueue byteQueue1 = new ByteQueue();
//                byteQueue1.push(REMOVE_ASSOCIATE);
//                byteQueue1.pushU4B(deviceClass.getDeviceUID());
//                byteQueue1.push(PIR_INFO);
//                byteQueue1.pushU4B(Long.valueOf("12345678"));
//                advertiseTask1 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
//                animatedProgress.setText("Uploading");
//                advertiseTask1.setByteQueue(byteQueue1);
//                Log.e("Check>>>>", byteQueue1.toString());
//                advertiseTask1.setSearchRequestCode(REMOVE_ASSOCIATE);
//                advertiseTask1.startAdvertising();
//                break;
//
//            case R.id.read_sensor:
//                Toast.makeText(activity, "Under developing.", Toast.LENGTH_SHORT).show();
//
////                Intent sensor = new Intent(activity, HelperActivity.class);
////                sensor.putExtra(Constants.MAIN_KEY, Constants.READ_ASSOCIATE);
////                sensor.putExtra(Constants.LIGHT_DETAIL_KEY, deviceClass);
////                sensor.putExtra("Type","PIR");
////                startActivity(sensor);
//                break;
//
//            case R.id.add_tag:
//                if (uid2_no.getText().toString().trim().length()<1){
//                    uid2_no.setError("Address can't empty");
//                    return;
//                }
//                ContentValues contentValues2=new ContentValues();
//                contentValues2.put(DatabaseConstant.COLUMN_DEVICE_TAG_NUMBER,uid2_no.getText().toString());
//                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues2)) {
//                    Toast.makeText(activity, "Device  added successfully.", Toast.LENGTH_SHORT).show();
//                }
//                AdvertiseTask advertiseTask2 = new AdvertiseTask(activity);
//                ByteQueue byteQueue2 = new ByteQueue();
//                byteQueue2.push(ADD_ASSOCIATE);
//                byteQueue2.pushU4B(deviceClass.getDeviceUID());
//                byteQueue2.push(SWITCH_INFO);
//                byteQueue2.pushU4B(Long.valueOf(uid2_no.getText().toString().trim()));
//                advertiseTask2 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
//                animatedProgress.setText("Uploading");
//                advertiseTask2.setByteQueue(byteQueue2);
//                Log.e("Check>>>>", byteQueue2.toString());
//                advertiseTask2.setSearchRequestCode(ADD_ASSOCIATE);
//                advertiseTask2.startAdvertising();
//                break;
//
//            case R.id.remove_tag:
//                ContentValues contentValues3=new ContentValues();
//                contentValues3.put(DatabaseConstant.COLUMN_DEVICE_TAG_NUMBER,"");
//                if (sqlHelper.updateDevice(deviceClass.getDeviceUID(), contentValues3)) {
//                }
//                AdvertiseTask advertiseTask3 = new AdvertiseTask(activity);
//                ByteQueue byteQueue3 = new ByteQueue();
//                byteQueue3.push(REMOVE_ASSOCIATE);
//                byteQueue3.pushU4B(deviceClass.getDeviceUID());
//                byteQueue3.push(SWITCH_INFO);
//                byteQueue3.pushU4B(Long.valueOf("12345678"));
//                advertiseTask3 = new AdvertiseTask(AssociateAddFragment.this, activity, 5 * 1000);
//                animatedProgress.setText("Uploading");
//                advertiseTask3.setByteQueue(byteQueue3);
//                Log.e("Check>>>>", byteQueue3.toString());
//                advertiseTask3.setSearchRequestCode(REMOVE_ASSOCIATE);
//                advertiseTask3.startAdvertising();
//                break;
//
//            case R.id.read_tag:
//                Toast.makeText(activity, "Under developing......", Toast.LENGTH_SHORT).show();
////                Intent tag = new Intent(activity, HelperActivity.class);
////                tag.putExtra(Constants.MAIN_KEY, Constants.READ_ASSOCIATE);
////                tag.putExtra(Constants.LIGHT_DETAIL_KEY, deviceClass);
////                tag.putExtra("Type","TAG");
////                activity.startActivity(tag);
//                break;
//
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(String message) {
        animatedProgress.showProgress();
        Log.w(TAG, "Uploading");

    }

    @Override
    public void onFailed(String errorMessage) {
        if (animatedProgress == null)
            return;
        Toast.makeText(activity, "Uploading", Toast.LENGTH_SHORT).show();
        animatedProgress.hideProgress();
        activity.onBackPressed();
        Log.w(TAG, "onScanFailed " + errorMessage);

    }

    @Override
    public void onStop(String stopMessage, int resultCode) {
        if (animatedProgress != null)
            animatedProgress.hideProgress();
//        activity.onBackPressed();
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
//        hideKeyboard();
    }

}
