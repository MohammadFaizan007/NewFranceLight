package com.inferrix.lightsmart.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
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
import com.niftymodaldialogeffects.NiftyDialogBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_ASSOCIATE;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.ADD_ASSOCIATE_ONE;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.REMOVE_ASSOCIATE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class LastDemoAddFragment extends Fragment implements AdvertiseResultInterface, ReceiverResultInterface {
    DeviceClass deviceClass;
    String TAG = this.getClass().getSimpleName();
    Activity activity;
    Unbinder unbinder;
    int requestCode;
    AnimatedProgress animatedProgress;
    ScannerTask scannerTask;

    String name = "";
    int position;
    String uid_One = "", sensor_One = "";

    ArrayList<DeviceClass> deviceList;
    ArrayList<DeviceClass> ListTwo;
    ArrayAdapter<DeviceClass> adapterSite;

    int spinnerSiteSelectedPosition = 0;
    @BindView(R.id.uid_no1)
    EditText uidNo1;
    ListPopupWindow listPopupWindow;
    ListPopupWindow listPopupWindowTwo;
    ListPopupWindow listPopupWindowThree;
    ListPopupWindow listPopupWindowFour;
    ListPopupWindow listPopupWindowFive;
    ListPopupWindow listPopupWindowSix;
    ListPopupWindow listPopupWindowSeven;
    ListPopupWindow listPopupWindowEight;
    @BindView(R.id.uid_no2)
    EditText uidNo2;
    @BindView(R.id.uid_no3)
    EditText uidNo3;
    @BindView(R.id.uid_no4)
    EditText uidNo4;
    @BindView(R.id.uid_no5)
    EditText uidNo5;
    @BindView(R.id.uid_no6)
    EditText uidNo6;
    @BindView(R.id.uid_no7)
    EditText uidNo7;
    @BindView(R.id.uid_no8)
    EditText uidNo8;
    @BindView(R.id.select_item1)
    EditText selectItem1;
    @BindView(R.id.type_one)
    EditText typeOne;
    @BindView(R.id.btn_submit1)
    Button btnSubmit1;
    @BindView(R.id.btn_remove1)
    Button btnRemove1;
    @BindView(R.id.select_item2)
    EditText selectItem2;
    @BindView(R.id.type_two)
    EditText typeTwo;
    @BindView(R.id.btn_submit2)
    Button btnSubmit2;
    @BindView(R.id.btn_remove2)
    Button btnRemove2;
    @BindView(R.id.select_item3)
    EditText selectItem3;
    @BindView(R.id.type_three)
    EditText typeThree;
    @BindView(R.id.btn_submit3)
    Button btnSubmit3;
    @BindView(R.id.btn_remove3)
    Button btnRemove3;
    @BindView(R.id.card_three)
    CardView cardThree;
    @BindView(R.id.select_item4)
    EditText selectItem4;
    @BindView(R.id.type_four)
    EditText typeFour;
    @BindView(R.id.btn_submit4)
    Button btnSubmit4;
    @BindView(R.id.btn_remove4)
    Button btnRemove4;
    @BindView(R.id.select_item5)
    EditText selectItem5;
    @BindView(R.id.type_five)
    EditText typeFive;
    @BindView(R.id.btn_submit5)
    Button btnSubmit5;
    @BindView(R.id.btn_remove5)
    Button btnRemove5;
    @BindView(R.id.select_item6)
    EditText selectItem6;
    @BindView(R.id.type_six)
    EditText typeSix;
    @BindView(R.id.btn_submit6)
    Button btnSubmit6;
    @BindView(R.id.btn_remove6)
    Button btnRemove6;
    @BindView(R.id.select_item7)
    EditText selectItem7;
    @BindView(R.id.type_seven)
    EditText typeSeven;
    @BindView(R.id.btn_submit7)
    Button btnSubmit7;
    @BindView(R.id.btn_remove7)
    Button btnRemove7;
    @BindView(R.id.select_item8)
    EditText selectItem8;
    @BindView(R.id.type_eight)
    EditText typeEight;
    @BindView(R.id.btn_submit8)
    Button btnSubmit8;
    @BindView(R.id.btn_remove8)
    Button btnRemove8;
    String valueOne = "", valueTwo = "", valueThree = "", valueFour = "", valueFive = "", valueSix = "", valueSeven = "", valueEight = "";

    public LastDemoAddFragment() {
        // Required empty public constructor
        deviceList = new ArrayList<>();
        ListTwo = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        getDevice();
        getDeviceTwo();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.last_one, container, false );
        unbinder = ButterKnife.bind( this, view );
        activity = getActivity();
        if (activity == null)
            return view;
        name = getActivity().getIntent().getStringExtra( "name" );
        position = getActivity().getIntent().getIntExtra( "pos", 0 );
        scannerTask = new ScannerTask( activity, this );
        animatedProgress = new AnimatedProgress( activity );
        animatedProgress.setCancelable( false );

        if (deviceClass.getNumberOne().equalsIgnoreCase( "" )) {
            uidNo1.setHint( "No address associated" );
        } else {
            uidNo1.setText( deviceClass.getNumberOne() );
        }

        if (deviceClass.getNumberTwo().equalsIgnoreCase( "" )) {
            uidNo2.setHint( "No address associated" );
        } else {
            uidNo2.setText( deviceClass.getNumberTwo() );
        }
        if (deviceClass.getNumberThree().equalsIgnoreCase( "" )) {
            uidNo3.setHint( "No address associated" );
        } else {
            uidNo3.setText( deviceClass.getNumberThree() );
        }

        if (deviceClass.getNumberFour().equalsIgnoreCase( "" )) {
            uidNo4.setHint( "No address associated" );
        } else {
            uidNo4.setText( deviceClass.getNumberFour() );
        }

        if (deviceClass.getNumberFive().equalsIgnoreCase( "" )) {
            uidNo5.setHint( "No address associated" );
        } else {
            uidNo5.setText( deviceClass.getNumberFive() );
        }

        if (deviceClass.getNumberSix().equalsIgnoreCase( "" )) {
            uidNo6.setHint( "No address associated" );
        } else {
            uidNo6.setText( deviceClass.getNumberSix() );
        }

        if (deviceClass.getNumberSeven().equalsIgnoreCase( "" )) {
            uidNo7.setHint( "No address associated" );
        } else {
            uidNo7.setText( deviceClass.getNumberSeven() );
        }

        if (deviceClass.getNumberEight().equalsIgnoreCase( "" )) {
            uidNo8.setHint( "No address associated" );
        } else {
            uidNo8.setText( deviceClass.getNumberEight() );
        }

        if (deviceClass.getItemOne().equalsIgnoreCase( "" )) {
            selectItem1.setHint( "Select" );
        } else if (deviceClass.getItemOne().equalsIgnoreCase( "Switch" )) {
            typeOne.setVisibility( View.VISIBLE );
            typeOne.setText( deviceClass.getGroupTypeOne() );
            selectItem1.setText( deviceClass.getItemOne() );

        } else {
            selectItem1.setText( deviceClass.getItemOne() );
        }


        if (deviceClass.getItemTwo().equalsIgnoreCase( "" )) {
            selectItem2.setHint( "Select" );
        } else if (deviceClass.getItemTwo().equalsIgnoreCase( "Switch" )) {
            typeTwo.setVisibility( View.VISIBLE );
            typeTwo.setText( deviceClass.getGroupTypeTwo() );
            selectItem2.setText( deviceClass.getItemTwo() );
        } else {
            selectItem2.setText( deviceClass.getItemTwo() );
        }

        if (deviceClass.getItemThree().equalsIgnoreCase( "" )) {
            selectItem3.setHint( "Select" );
        } else if (deviceClass.getItemThree().equalsIgnoreCase( "Switch" )) {
            typeThree.setVisibility( View.VISIBLE );
            typeThree.setText( deviceClass.getGroupTypeThree() );
            selectItem3.setText( deviceClass.getItemThree() );
        } else {
            selectItem3.setText( deviceClass.getItemThree() );
        }
        if (deviceClass.getItemFour().equalsIgnoreCase( "" )) {
            selectItem4.setHint( "Select" );
        } else if (deviceClass.getItemFour().equalsIgnoreCase( "Switch" )) {
            typeFour.setVisibility( View.VISIBLE );
            typeFour.setText( deviceClass.getGroupTypeFour() );
            selectItem4.setText( deviceClass.getItemFour() );
        } else {
            selectItem4.setText( deviceClass.getItemFour() );
        }

        if (deviceClass.getItemFive().equalsIgnoreCase( "" )) {
            selectItem5.setHint( "Select" );
        } else if (deviceClass.getItemFive().equalsIgnoreCase( "Switch" )) {
            typeFive.setVisibility( View.VISIBLE );
            typeFive.setText( deviceClass.getGroupTypeFive() );
            selectItem5.setText( deviceClass.getItemFive() );
        } else {
            selectItem5.setText( deviceClass.getItemFive() );
        }

        if (deviceClass.getItemSix().equalsIgnoreCase( "" )) {
            selectItem6.setHint( "Select" );
        } else if (deviceClass.getItemSix().equalsIgnoreCase( "Switch" )) {
            typeSix.setVisibility( View.VISIBLE );
            typeSix.setText( deviceClass.getGroupTypeSix() );
            selectItem6.setText( deviceClass.getItemSix() );
        } else {
            selectItem6.setText( deviceClass.getItemSix() );
        }
        if (deviceClass.getItemSeven().equalsIgnoreCase( "" )) {
            selectItem7.setHint( "Select" );
        } else if (deviceClass.getItemSeven().equalsIgnoreCase( "Switch" )) {
            typeSeven.setVisibility( View.VISIBLE );
            typeSeven.setText( deviceClass.getGroupTypeSeven() );
            selectItem7.setText( deviceClass.getItemSeven() );
        } else {
            selectItem7.setText( deviceClass.getItemSeven() );
        }

        if (deviceClass.getItemEight().equalsIgnoreCase( "" )) {
            selectItem8.setHint( "Select" );
        } else if (deviceClass.getItemEight().equalsIgnoreCase( "Switch" )) {
            typeEight.setVisibility( View.VISIBLE );
            typeEight.setText( deviceClass.getGroupTypeEight() );
            selectItem8.setText( deviceClass.getItemEight() );
        } else {
            selectItem8.setText( deviceClass.getItemEight() );
        }


        deviceList = new ArrayList<>();
        ListTwo = new ArrayList<>();

        listPopupWindow = new ListPopupWindow( activity );
        listPopupWindow.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindow.setAnchorView( uidNo1 );
        listPopupWindow.setWidth( 300 );
        listPopupWindow.setHeight( 400 );
        listPopupWindow.setModal( true );
        listPopupWindow.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo1.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_ONE>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                valueOne = String.valueOf( deviceList.get( position ).getDeviceUID() );
                listPopupWindow.dismiss();
            }
        } );
        listPopupWindowTwo = new ListPopupWindow( activity );
        listPopupWindowTwo.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, ListTwo ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( ListTwo.get( position ).getDeviceName() );
                String uid = String.valueOf( ListTwo.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowTwo.setAnchorView( uidNo2 );
        listPopupWindowTwo.setWidth( 300 );
        listPopupWindowTwo.setHeight( 400 );
        listPopupWindowTwo.setModal( true );
        listPopupWindowTwo.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo2.setText( ListTwo.get( position ).getDeviceName() );
                Log.e( "UID_TWO>>>", String.valueOf( ListTwo.get( position ).getDeviceUID() ) );
                valueTwo = String.valueOf( ListTwo.get( position ).getDeviceUID() );
                listPopupWindowTwo.dismiss();
            }
        } );

        listPopupWindowThree = new ListPopupWindow( activity );
        listPopupWindowThree.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowThree.setAnchorView( uidNo3 );
        listPopupWindowThree.setWidth( 300 );
        listPopupWindowThree.setHeight( 400 );
        listPopupWindowThree.setModal( true );
        listPopupWindowThree.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo3.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_THREE>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                listPopupWindowThree.dismiss();
                valueThree = String.valueOf( deviceList.get( position ).getDeviceUID() );
            }
        } );

        listPopupWindowFour = new ListPopupWindow( activity );
        listPopupWindowFour.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowFour.setAnchorView( uidNo4 );
        listPopupWindowFour.setWidth( 300 );
        listPopupWindowFour.setHeight( 400 );
        listPopupWindowFour.setModal( true );
        listPopupWindowFour.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo4.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_FOUR>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                valueFour = String.valueOf( deviceList.get( position ).getDeviceUID() );
                listPopupWindowFour.dismiss();
            }
        } );
        listPopupWindowFive = new ListPopupWindow( activity );
        listPopupWindowFive.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowFive.setAnchorView( uidNo5 );
        listPopupWindowFive.setWidth( 300 );
        listPopupWindowFive.setHeight( 400 );
        listPopupWindowFive.setModal( true );
        listPopupWindowFive.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo5.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_FIVE>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                valueFive = String.valueOf( deviceList.get( position ).getDeviceUID() );
                listPopupWindowFive.dismiss();
            }
        } );

        listPopupWindowSix = new ListPopupWindow( activity );
        listPopupWindowSix.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowSix.setAnchorView( uidNo6 );
        listPopupWindowSix.setWidth( 300 );
        listPopupWindowSix.setHeight( 400 );
        listPopupWindowSix.setModal( true );
        listPopupWindowSix.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo6.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_SIX>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                valueSix = String.valueOf( deviceList.get( position ).getDeviceUID() );
                listPopupWindowSix.dismiss();
            }
        } );

        listPopupWindowSeven = new ListPopupWindow( activity );
        listPopupWindowSeven.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowSeven.setAnchorView( uidNo7 );
        listPopupWindowSeven.setWidth( 300 );
        listPopupWindowSeven.setHeight( 400 );
        listPopupWindowSeven.setModal( true );
        listPopupWindowSeven.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo7.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_SEVEN>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                valueSeven = String.valueOf( deviceList.get( position ).getDeviceUID() );
                listPopupWindowSeven.dismiss();
            }
        } );

        listPopupWindowEight = new ListPopupWindow( activity );
        listPopupWindowEight.setAdapter( new ArrayAdapter<DeviceClass>( activity, R.layout.spinerlayout, deviceList ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView( position, convertView, parent );
                tv.setTextColor( Color.GRAY );
                tv.setText( deviceList.get( position ).getDeviceName() );
                String uid = String.valueOf( deviceList.get( position ).getDeviceUID() );
                return tv;
            }
        } );
        listPopupWindowEight.setAnchorView( uidNo8 );
        listPopupWindowEight.setWidth( 300 );
        listPopupWindowEight.setHeight( 400 );
        listPopupWindowEight.setModal( true );
        listPopupWindowEight.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uidNo8.setText( deviceList.get( position ).getDeviceName() );
                Log.e( "UID_SEVEN>>>", String.valueOf( deviceList.get( position ).getDeviceUID() ) );
                valueEight = String.valueOf( deviceList.get( position ).getDeviceUID() );
                listPopupWindowEight.dismiss();

            }
        } );


        return view;
    }

    @OnClick({R.id.uid_no1, R.id.uid_no2, R.id.uid_no3, R.id.uid_no4, R.id.uid_no5, R.id.uid_no6, R.id.uid_no7, R.id.uid_no8,
            R.id.select_item1, R.id.select_item2, R.id.select_item3, R.id.select_item4, R.id.select_item5, R.id.select_item6, R.id.select_item7, R.id.select_item8,
            R.id.type_one, R.id.type_two, R.id.type_three, R.id.type_four, R.id.type_five, R.id.type_six, R.id.type_seven, R.id.type_eight,
            R.id.btn_submit1, R.id.btn_submit2, R.id.btn_submit3, R.id.btn_submit4, R.id.btn_submit5, R.id.btn_submit6, R.id.btn_submit7, R.id.btn_submit8,
            R.id.btn_remove1, R.id.btn_remove2, R.id.btn_remove3, R.id.btn_remove4, R.id.btn_remove5, R.id.btn_remove6, R.id.btn_remove7, R.id.btn_remove8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.uid_no1:
                listPopupWindow.show();
                break;
            case R.id.uid_no2:
                listPopupWindowTwo.show();
                break;
            case R.id.uid_no3:
                listPopupWindowThree.show();
                break;
            case R.id.uid_no4:
                listPopupWindowFour.show();
                break;
            case R.id.uid_no5:
                listPopupWindowFive.show();
                break;
            case R.id.uid_no6:
                listPopupWindowSix.show();
                break;
            case R.id.uid_no7:
                listPopupWindowSeven.show();
                break;
            case R.id.uid_no8:
                listPopupWindowEight.show();
                break;

            case R.id.select_item1:
                PopupMenu airPopUp = new PopupMenu( activity, selectItem1 );
                airPopUp.getMenuInflater().inflate( R.menu.sensor_type, airPopUp.getMenu() );
                airPopUp.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem1.setText( item.getTitle() );
                        if (selectItem1.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeOne.setVisibility( View.VISIBLE );
                        } else {
                            typeOne.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                airPopUp.show();
                break;
            case R.id.select_item2:
                PopupMenu airPopUp2 = new PopupMenu( activity, selectItem2 );
                airPopUp2.getMenuInflater().inflate( R.menu.sensor_type, airPopUp2.getMenu() );
                airPopUp2.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem2.setText( item.getTitle() );
                        if (selectItem2.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeTwo.setVisibility( View.VISIBLE );
                        } else {
                            typeTwo.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                airPopUp2.show();
                break;

            case R.id.select_item3:
                PopupMenu PopUp3 = new PopupMenu( activity, selectItem3 );
                PopUp3.getMenuInflater().inflate( R.menu.sensor_type, PopUp3.getMenu() );
                PopUp3.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem3.setText( item.getTitle() );
                        if (selectItem3.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeThree.setVisibility( View.VISIBLE );
                        } else {
                            typeThree.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp3.show();
                break;

            case R.id.select_item4:
                PopupMenu PopUp4 = new PopupMenu( activity, selectItem4 );
                PopUp4.getMenuInflater().inflate( R.menu.sensor_type, PopUp4.getMenu() );
                PopUp4.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem4.setText( item.getTitle() );
                        if (selectItem4.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeFour.setVisibility( View.VISIBLE );
                        } else {
                            typeFour.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp4.show();
                break;
            case R.id.select_item5:
                PopupMenu PopUp5 = new PopupMenu( activity, selectItem5 );
                PopUp5.getMenuInflater().inflate( R.menu.sensor_type, PopUp5.getMenu() );
                PopUp5.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem5.setText( item.getTitle() );
                        if (selectItem5.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeFive.setVisibility( View.VISIBLE );
                        } else {
                            typeFive.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp5.show();
                break;
            case R.id.select_item6:
                PopupMenu PopUp6 = new PopupMenu( activity, selectItem6 );
                PopUp6.getMenuInflater().inflate( R.menu.sensor_type, PopUp6.getMenu() );
                PopUp6.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem6.setText( item.getTitle() );
                        if (selectItem6.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeSix.setVisibility( View.VISIBLE );
                        } else {
                            typeSix.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp6.show();
                break;
            case R.id.select_item7:
                PopupMenu PopUp7 = new PopupMenu( activity, selectItem7 );
                PopUp7.getMenuInflater().inflate( R.menu.sensor_type, PopUp7.getMenu() );
                PopUp7.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem7.setText( item.getTitle() );
                        if (selectItem7.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeSeven.setVisibility( View.VISIBLE );
                        } else {
                            typeSeven.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp7.show();
                break;
            case R.id.select_item8:
                PopupMenu PopUp8 = new PopupMenu( activity, selectItem8 );
                PopUp8.getMenuInflater().inflate( R.menu.sensor_type, PopUp8.getMenu() );
                PopUp8.setOnMenuItemClickListener( item -> {
                    try {
                        selectItem8.setText( item.getTitle() );
                        if (selectItem8.getText().toString().equalsIgnoreCase( "Switch" )) {
                            typeEight.setVisibility( View.VISIBLE );
                        } else {
                            typeEight.setVisibility( View.GONE );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp8.show();
                break;

            case R.id.type_one:
                PopupMenu PopUp = new PopupMenu( activity, typeOne );
                PopUp.getMenuInflater().inflate( R.menu.light_type, PopUp.getMenu() );
                PopUp.setOnMenuItemClickListener( item -> {
                    try {
                        typeOne.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUp.show();
                break;
            case R.id.type_two:
                PopupMenu PopUpTwo = new PopupMenu( activity, typeTwo );
                PopUpTwo.getMenuInflater().inflate( R.menu.light_type, PopUpTwo.getMenu() );
                PopUpTwo.setOnMenuItemClickListener( item -> {
                    try {
                        typeTwo.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUpTwo.show();
                break;

            case R.id.type_three:
                PopupMenu PopUpThree = new PopupMenu( activity, typeThree );
                PopUpThree.getMenuInflater().inflate( R.menu.light_type, PopUpThree.getMenu() );
                PopUpThree.setOnMenuItemClickListener( item -> {
                    try {
                        typeThree.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                PopUpThree.show();
                break;
            case R.id.type_four:
                PopupMenu popupMenuFour = new PopupMenu( activity, typeFour );
                popupMenuFour.getMenuInflater().inflate( R.menu.light_type, popupMenuFour.getMenu() );
                popupMenuFour.setOnMenuItemClickListener( item -> {
                    try {
                        typeFour.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                popupMenuFour.show();
                break;
            case R.id.type_five:
                PopupMenu popupMenuFive = new PopupMenu( activity, typeFive );
                popupMenuFive.getMenuInflater().inflate( R.menu.light_type, popupMenuFive.getMenu() );
                popupMenuFive.setOnMenuItemClickListener( item -> {
                    try {
                        typeFive.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                popupMenuFive.show();
                break;
            case R.id.type_six:
                PopupMenu popupMenuSix = new PopupMenu( activity, typeSix );
                popupMenuSix.getMenuInflater().inflate( R.menu.light_type, popupMenuSix.getMenu() );
                popupMenuSix.setOnMenuItemClickListener( item -> {
                    try {
                        typeSix.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                popupMenuSix.show();
                break;
            case R.id.type_seven:
                PopupMenu popupMenuSeven = new PopupMenu( activity, typeSeven );
                popupMenuSeven.getMenuInflater().inflate( R.menu.light_type, popupMenuSeven.getMenu() );
                popupMenuSeven.setOnMenuItemClickListener( item -> {
                    try {
                        typeSeven.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                popupMenuSeven.show();
                break;
            case R.id.type_eight:
                PopupMenu popupMenuEight = new PopupMenu( activity, typeEight );
                popupMenuEight.getMenuInflater().inflate( R.menu.light_type, popupMenuEight.getMenu() );
                popupMenuEight.setOnMenuItemClickListener( item -> {
                    try {
                        typeEight.setText( item.getTitle() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } );
                popupMenuEight.show();
                break;

            case R.id.btn_submit1:
                uid_One = uidNo1.getText().toString();
                sensor_One = selectItem1.getText().toString();
                if (uidNo1.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo1 );
                    return;
                } else if (selectItem1.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem1 );
                    return;
                } else if (selectItem1.getText().toString().equalsIgnoreCase( "Switch" ) && typeOne.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeOne );
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_ONE, uidNo1.getText().toString() );
                contentValues.put( DatabaseConstant.COLUMN_DEVICE_ITEM_ONE, selectItem1.getText().toString() );
                contentValues.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_ONE, typeOne.getText().toString() );

                AdvertiseTask advertiseTask = new AdvertiseTask( activity );
                ByteQueue byteQueue = new ByteQueue();
                byteQueue.push( ADD_ASSOCIATE );
                byteQueue.push( 0x03 );
                byteQueue.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem1.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue.push( 0x11 );
                } else if (selectItem1.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue.push( 0x12 );
                } else if (selectItem1.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue.push( 0x13 );
                }
//                byteQueue.pushU4B(Long.valueOf(valueOne));
                byteQueue.push( valueOne );
                if (selectItem1.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeOne.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue.push( 0x05 );
                    } else if (typeOne.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue.push( 0x04 );
                    } else if (typeOne.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue.push( 0x03 );
                    } else if (typeOne.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue.push( 0x02 );
                    } else if (typeOne.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue.push( 0x01 );
                    }
                }else {
                    contentValues.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_ONE, "");
                }

                advertiseTask = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask.setByteQueue( byteQueue );
                Log.e( "Check>>>>", byteQueue.toString() );
                advertiseTask.setSearchRequestCode( ADD_ASSOCIATE_ONE );
                advertiseTask.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }

                break;

            case R.id.btn_submit2:
                if (uidNo2.getText().toString().length() == 0) {
                    showError( "Address two can't empty ", uidNo2 );
                    return;
                } else if (selectItem2.getText().toString().length() == 0) {
                    showError( "Please select sensor type two", selectItem2 );
                    return;
                }else if (selectItem2.getText().toString().equalsIgnoreCase( "Switch" ) && typeTwo.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeTwo );
                    return;
                }
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_TWO, uidNo2.getText().toString() );
                contentValues2.put( DatabaseConstant.COLUMN_DEVICE_ITEM_TWO, selectItem2.getText().toString() );
                contentValues2.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_TWO, typeTwo.getText().toString() );

                AdvertiseTask advertiseTask2 = new AdvertiseTask( activity );
                ByteQueue byteQueue2 = new ByteQueue();
                byteQueue2.push( ADD_ASSOCIATE );
                byteQueue2.push( 0x03 );
                byteQueue2.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem2.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue2.push( 0x21 );
                } else if (selectItem2.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue2.push( 0x22 );
                } else if (selectItem2.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue2.push( 0x23 );
                }
//                byteQueue2.pushU4B(Long.valueOf(uid_no2.getText().toString().trim()));
                byteQueue2.push( valueTwo );
//                byteQueue2.pushU4B(Long.valueOf(valueTwo));
                if (selectItem2.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeTwo.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue2.push( 0x05 );
                    } else if (typeTwo.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue2.push( 0x04 );
                    } else if (typeTwo.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue2.push( 0x03 );
                    } else if (typeTwo.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue2.push( 0x02 );
                    } else if (typeTwo.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue2.push( 0x01 );
                    }
                }else {
                    contentValues2.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_TWO, "");
                }

                advertiseTask2 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask2.setByteQueue( byteQueue2 );
                Log.e( "Check>>>>", byteQueue2.toString() );
                advertiseTask2.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask2.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues2 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }
                break;
            case R.id.btn_submit3:
                if (uidNo3.getText().toString().length() == 0) {
                    showError( "Address three can't empty ", uidNo3 );
                    return;
                } else if (selectItem3.getText().toString().length() == 0) {
                    showError( "Please select sensor type three", selectItem3 );
                    return;
                }
                else if (selectItem3.getText().toString().equalsIgnoreCase( "Switch" ) && typeThree.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeThree );
                    return;
                }
                ContentValues contentValues3 = new ContentValues();
                contentValues3.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_THREE, uidNo3.getText().toString() );
                contentValues3.put( DatabaseConstant.COLUMN_DEVICE_ITEM_THREE, selectItem3.getText().toString() );
                contentValues3.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_THREE, typeThree.getText().toString() );

                AdvertiseTask advertiseTask3 = new AdvertiseTask( activity );
                ByteQueue byteQueue3 = new ByteQueue();
                byteQueue3.push( ADD_ASSOCIATE );
                byteQueue3.push( 0x03 );
                byteQueue3.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem3.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue3.push( 0x31 );
                } else if (selectItem3.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue3.push( 0x32 );
                } else if (selectItem3.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue3.push( 0x33 );
                }
//                byteQueue3.pushU4B(Long.valueOf(uid_no3.getText().toString().trim()));
                byteQueue3.push( valueThree );
//                byteQueue3.pushU4B(Long.valueOf(valueThree));
                if (selectItem3.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeThree.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue3.push( 0x05 );
                    } else if (typeThree.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue3.push( 0x04 );
                    } else if (typeThree.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue3.push( 0x03 );
                    } else if (typeThree.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue3.push( 0x02 );
                    } else if (typeThree.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue3.push( 0x01 );
                    }
                }else {
                    contentValues3.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_THREE, "");
                }

                advertiseTask3 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask3.setByteQueue( byteQueue3 );
                Log.e( "Check>>>>", byteQueue3.toString() );
                advertiseTask3.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask3.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues3 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }
                break;
            case R.id.btn_submit4:
                if (uidNo4.getText().toString().length() == 0) {
                    showError( "Address four can't empty ", uidNo4 );
                    return;
                } else if (selectItem4.getText().toString().length() == 0) {
                    showError( "Please select sensor type four", selectItem4 );
                    return;
                }else if (selectItem4.getText().toString().equalsIgnoreCase( "Switch" ) && typeFour.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeFour );
                    return;
                }

                ContentValues contentValues4 = new ContentValues();
                contentValues4.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_FOUR, uidNo4.getText().toString() );
                contentValues4.put( DatabaseConstant.COLUMN_DEVICE_ITEM_FOUR, selectItem4.getText().toString() );
                contentValues4.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FOUR, typeFour.getText().toString() );

                AdvertiseTask advertiseTask4 = new AdvertiseTask( activity );
                ByteQueue byteQueue4 = new ByteQueue();
                byteQueue4.push( ADD_ASSOCIATE );
                byteQueue4.push( 0x03 );
                byteQueue4.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem4.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue4.push( 0x41 );
                } else if (selectItem4.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue4.push( 0x42 );
                } else if (selectItem4.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue4.push( 0x43 );
                }
//                byteQueue4.pushU4B(Long.valueOf(uid_no4.getText().toString().trim()));
                byteQueue4.push( valueFour );
//                byteQueue4.pushU4B(Long.valueOf(valueFour));
                if (selectItem4.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeFour.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue4.push( 0x05 );
                    } else if (typeFour.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue4.push( 0x04 );
                    } else if (typeFour.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue4.push( 0x03 );
                    } else if (typeFour.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue4.push( 0x02 );
                    } else if (typeFour.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue4.push( 0x01 );
                    }
                }else {
                    contentValues4.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FOUR, "");
                }

                advertiseTask4 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask4.setByteQueue( byteQueue4 );
                Log.e( "Check>>>>", byteQueue4.toString() );
                advertiseTask4.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask4.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues4 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }
                break;
            case R.id.btn_submit5:
                if (uidNo5.getText().toString().length() == 0) {
                    showError( "Address five can't empty ", uidNo5 );
                    return;
                } else if (selectItem5.getText().toString().length() == 0) {
                    showError( "Please select sensor type five", selectItem5 );
                    return;
                }else if (selectItem5.getText().toString().equalsIgnoreCase( "Switch" ) && typeFive.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeFive );
                    return;
                }
                ContentValues contentValues5 = new ContentValues();
                contentValues5.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_FIVE, uidNo5.getText().toString() );
                contentValues5.put( DatabaseConstant.COLUMN_DEVICE_ITEM_FIVE, selectItem5.getText().toString() );
                contentValues5.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FIVE, typeFive.getText().toString() );


                AdvertiseTask advertiseTask5 = new AdvertiseTask( activity );
                ByteQueue byteQueue5 = new ByteQueue();
                byteQueue5.push( ADD_ASSOCIATE );
                byteQueue5.push( 0x03 );
                byteQueue5.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem5.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue5.push( 0x51 );
                } else if (selectItem5.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue5.push( 0x52 );
                } else if (selectItem5.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue5.push( 0x53 );
                }
//                byteQueue5.pushU4B(Long.valueOf(uid_no5.getText().toString().trim()));
                byteQueue5.push( valueFive );
//                byteQueue5.pushU4B(Long.valueOf(valueFive));
                if (selectItem5.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeFive.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue5.push( 0x05 );
                    } else if (typeFive.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue5.push( 0x04 );
                    } else if (typeFive.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue5.push( 0x03 );
                    } else if (typeFive.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue5.push( 0x02 );
                    } else if (typeFive.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue5.push( 0x01 );
                    }
                }else {
                    contentValues5.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FIVE, "");
                }

                advertiseTask5 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask5.setByteQueue( byteQueue5 );
                Log.e( "Check>>>>", byteQueue5.toString() );
                advertiseTask5.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask5.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues5 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }
                break;

            case R.id.btn_submit6:
                if (uidNo6.getText().toString().length() == 0) {
                    showError( "Address six can't empty ", uidNo6 );
                    return;
                } else if (selectItem6.getText().toString().length() == 0) {
                    showError( "Please select sensor type six", selectItem6 );
                    return;
                }else if (selectItem6.getText().toString().equalsIgnoreCase( "Switch" ) && typeSix.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeSix );
                    return;
                }
                ContentValues contentValues6 = new ContentValues();
                contentValues6.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_SIX, uidNo6.getText().toString() );
                contentValues6.put( DatabaseConstant.COLUMN_DEVICE_ITEM_SIX, selectItem6.getText().toString() );
                contentValues6.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SIX, typeSix.getText().toString() );

                AdvertiseTask advertiseTask6 = new AdvertiseTask( activity );
                ByteQueue byteQueue6 = new ByteQueue();
                byteQueue6.push( ADD_ASSOCIATE );
                byteQueue6.push( 0x03 );
                byteQueue6.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem6.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue6.push( 0x61 );
                } else if (selectItem6.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue6.push( 0x62 );
                } else if (selectItem6.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue6.push( 0x63 );
                }
//                byteQueue6.pushU4B(Long.valueOf(uid_no6.getText().toString().trim()));
                byteQueue6.push( valueSix );
//                byteQueue6.pushU4B(Long.valueOf(valueSix));
                if (selectItem6.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeSix.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue6.push( 0x05 );
                    } else if (typeSix.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue6.push( 0x04 );
                    } else if (typeSix.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue6.push( 0x03 );
                    } else if (typeSix.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue6.push( 0x02 );
                    } else if (typeSix.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue6.push( 0x01 );
                    }
                }else {
                    contentValues6.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SIX, "");
                }

                advertiseTask6 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask6.setByteQueue( byteQueue6 );
                Log.e( "Check>>>>", byteQueue6.toString() );
                advertiseTask6.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask6.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues6 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }
                break;

            case R.id.btn_submit7:
                if (uidNo7.getText().toString().length() == 0) {
                    showError( "Address seven can't empty ", uidNo7 );
                    return;
                } else if (selectItem7.getText().toString().length() == 0) {
                    showError( "Please select sensor type seven", selectItem7 );
                    return;
                }else if (selectItem7.getText().toString().equalsIgnoreCase( "Switch" ) && typeSeven.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeSeven );
                    return;
                }
                ContentValues contentValues7 = new ContentValues();
                contentValues7.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_SEVEN, uidNo7.getText().toString() );
                contentValues7.put( DatabaseConstant.COLUMN_DEVICE_ITEM_SEVEN, selectItem7.getText().toString() );
                contentValues7.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SEVEN, typeSeven.getText().toString() );

                AdvertiseTask advertiseTask7 = new AdvertiseTask( activity );
                ByteQueue byteQueue7 = new ByteQueue();
                byteQueue7.push( ADD_ASSOCIATE );
                byteQueue7.push( 0x03 );
                byteQueue7.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem7.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue7.push( 0x71 );
                } else if (selectItem7.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue7.push( 0x72 );
                } else if (selectItem7.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue7.push( 0x73 );
                }
//                byteQueue7.pushU4B(Long.valueOf(uid_no7.getText().toString().trim()));
                byteQueue7.push( valueSeven );
//                byteQueue7.pushU4B(Long.valueOf(valueSeven));
                if (selectItem7.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeSeven.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue7.push( 0x05 );
                    } else if (typeSeven.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue7.push( 0x04 );
                    } else if (typeSeven.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue7.push( 0x03 );
                    } else if (typeSeven.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue7.push( 0x02 );
                    } else if (typeSeven.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue7.push( 0x01 );
                    }
                }else {
                    contentValues7.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SEVEN, "");
                }

                advertiseTask7 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask7.setByteQueue( byteQueue7 );
                Log.e( "Check>>>>", byteQueue7.toString() );
                advertiseTask7.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask7.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues7 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }
                break;

            case R.id.btn_submit8:
                if (uidNo8.getText().toString().length() == 0) {
                    showError( "Address eight can't empty ", uidNo8 );
                    return;
                } else if (selectItem8.getText().toString().length() == 0) {
                    showError( "Please select sensor type eight", selectItem8 );
                    return;
                }
                else if (selectItem8.getText().toString().equalsIgnoreCase( "Switch" ) && typeEight.getText().toString().length() == 0) {
                    showError( "Please select add type ", typeEight );
                    return;
                }
                ContentValues contentValues8 = new ContentValues();
                contentValues8.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_EIGET, uidNo8.getText().toString() );
                contentValues8.put( DatabaseConstant.COLUMN_DEVICE_ITEM_EIGET, selectItem8.getText().toString() );
                contentValues8.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_EIGHT, typeEight.getText().toString() );

                AdvertiseTask advertiseTask8 = new AdvertiseTask( activity );
                ByteQueue byteQueue8 = new ByteQueue();
                byteQueue8.push( ADD_ASSOCIATE );
                byteQueue8.push( 0x03 );
                byteQueue8.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem8.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue8.push( 0x81 );
                } else if (selectItem8.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue8.push( 0x82 );
                } else if (selectItem8.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue8.push( 0x83 );
                }
//                byteQueue8.pushU4B(Long.valueOf(uid_no8.getText().toString().trim()));
                byteQueue8.push( valueEight );
//                byteQueue8.pushU4B(Long.valueOf(valueEight));
                if (selectItem8.getText().toString().equalsIgnoreCase( "Switch" )){
                    if (typeEight.getText().toString().equalsIgnoreCase( "Site" )) {
                        byteQueue8.push( 0x05 );
                    } else if (typeEight.getText().toString().equalsIgnoreCase( "Building" )) {
                        byteQueue8.push( 0x04 );
                    } else if (typeEight.getText().toString().equalsIgnoreCase( "Level" )) {
                        byteQueue8.push( 0x03 );
                    } else if (typeEight.getText().toString().equalsIgnoreCase( "Room" )) {
                        byteQueue8.push( 0x02 );
                    } else if (typeEight.getText().toString().equalsIgnoreCase( "Group" )) {
                        byteQueue8.push( 0x01 );
                    }
                }else {
                    contentValues8.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_EIGHT, "");
                }

                advertiseTask8 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask8.setByteQueue( byteQueue8 );
                Log.e( "Check>>>>", byteQueue8.toString() );
                advertiseTask8.setSearchRequestCode( ADD_ASSOCIATE );
                advertiseTask8.startAdvertising();
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValues8 )) {
                    Toast.makeText( activity, "Device added successfully.", Toast.LENGTH_SHORT ).show();
                }

                break;

            case R.id.btn_remove1:
                if (uidNo1.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo1 );
                    return;
                } else if (selectItem1.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem1 );
                    return;
                }
                ContentValues contentValuesRemove = new ContentValues();
                contentValuesRemove.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_ONE, "" );
                contentValuesRemove.put( DatabaseConstant.COLUMN_DEVICE_ITEM_ONE, "" );
                contentValuesRemove.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_ONE, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove )) {
                }
                AdvertiseTask advertiseTask1 = new AdvertiseTask( activity );
                ByteQueue byteQueue1 = new ByteQueue();
                byteQueue1.push( REMOVE_ASSOCIATE );
                byteQueue1.push( 0x03 );
                byteQueue1.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem1.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueue1.push( 0x11 );
                } else if (selectItem1.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueue1.push( 0x12 );
                } else if (selectItem1.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueue1.push( 0x13 );
                }
                if (deviceClass.getNumberOne().equalsIgnoreCase( "" )) {
//                    byteQueue1.pushU4B( Long.valueOf( uidNo1.getText().toString().trim() ) );
                    byteQueue1.pushU4B( Long.valueOf( valueOne ) );
                } else {
                    byteQueue1.pushU4B( Long.parseLong( deviceClass.getNumberOne() ) );
                }
                advertiseTask1 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTask1.setByteQueue( byteQueue1 );
                Log.e( "Check>>>>", byteQueue1.toString() );
                advertiseTask1.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTask1.startAdvertising();
                uidNo1.setText( "" );
                selectItem1.setText( "" );
                typeOne.setText( "" );
                break;

            case R.id.btn_remove2:
                if (uidNo2.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo2 );
                    return;
                } else if (selectItem2.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem2 );
                    return;
                }
                ContentValues contentValuesRemove2 = new ContentValues();
                contentValuesRemove2.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_TWO, "" );
                contentValuesRemove2.put( DatabaseConstant.COLUMN_DEVICE_ITEM_TWO, "" );
                contentValuesRemove2.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_TWO, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove2 )) {
                }
                AdvertiseTask advertiseTaskRemove2 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove2 = new ByteQueue();
                byteQueueRemove2.push( REMOVE_ASSOCIATE );
                byteQueueRemove2.push( 0x03 );
                byteQueueRemove2.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem2.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove2.push( 0x21 );
                } else if (selectItem2.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove2.push( 0x22 );
                } else if (selectItem2.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove2.push( 0x23 );
                }
                if (deviceClass.getNumberTwo().equalsIgnoreCase( "" )) {
//                    byteQueueRemove2.pushU4B( Long.valueOf( uidNo2.getText().toString().trim() ) );
                    byteQueueRemove2.pushU4B( Long.valueOf( valueTwo ) );
                } else {
                    byteQueueRemove2.pushU4B( Long.parseLong( deviceClass.getNumberTwo() ) );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove2.pushU4B(Long.parseLong(deviceClass.getNumberTwo()));
                advertiseTaskRemove2 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove2.setByteQueue( byteQueueRemove2 );
                Log.e( "Check>>>>", byteQueueRemove2.toString() );
                advertiseTaskRemove2.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove2.startAdvertising();
                uidNo2.setText( "" );
                selectItem2.setText( "" );
                typeTwo.setText( "" );
//                card_two.setVisibility(View.GONE);
//                viewDetail1.setBackgroundResource(R.drawable.plus_ic);
                break;

            case R.id.btn_remove3:
                if (uidNo3.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo3 );
                    return;
                } else if (selectItem3.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem3 );
                    return;
                }
                ContentValues contentValuesRemove3 = new ContentValues();
                contentValuesRemove3.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_THREE, "" );
                contentValuesRemove3.put( DatabaseConstant.COLUMN_DEVICE_ITEM_THREE, "" );
                contentValuesRemove3.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_THREE, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove3 )) {
                }
                AdvertiseTask advertiseTaskRemove3 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove3 = new ByteQueue();
                byteQueueRemove3.push( REMOVE_ASSOCIATE );
                byteQueueRemove3.push( 0x03 );
                byteQueueRemove3.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem3.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove3.push( 0x31 );
                } else if (selectItem3.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove3.push( 0x32 );
                } else if (selectItem3.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove3.push( 0x33 );
                }
                if (deviceClass.getNumberThree().equalsIgnoreCase( "" )) {
//                    byteQueueRemove3.pushU4B( Long.valueOf( uidNo3.getText().toString().trim() ) );
                    byteQueueRemove3.pushU4B( Long.valueOf( valueThree ) );
                } else {
                    byteQueueRemove3.pushU4B( Long.parseLong( deviceClass.getNumberThree() ) );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove3.pushU4B(Long.parseLong(deviceClass.getNumberThree()));
                advertiseTaskRemove3 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove3.setByteQueue( byteQueueRemove3 );
                Log.e( "Check>>>>", byteQueueRemove3.toString() );
                advertiseTaskRemove3.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove3.startAdvertising();
                uidNo3.setText( "" );
                selectItem3.setText( "" );
                typeThree.setText( "" );
                break;
            case R.id.btn_remove4:
                if (uidNo4.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo4 );
                    return;
                } else if (selectItem4.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem4 );
                    return;
                }
                ContentValues contentValuesRemove4 = new ContentValues();
                contentValuesRemove4.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_FOUR, "" );
                contentValuesRemove4.put( DatabaseConstant.COLUMN_DEVICE_ITEM_FOUR, "" );
                contentValuesRemove4.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FOUR, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove4 )) {
                }
                AdvertiseTask advertiseTaskRemove4 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove4 = new ByteQueue();
                byteQueueRemove4.push( REMOVE_ASSOCIATE );
                byteQueueRemove4.push( 0x03 );
                byteQueueRemove4.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem4.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove4.push( 0x41 );
                } else if (selectItem4.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove4.push( 0x42 );
                } else if (selectItem4.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove4.push( 0x43 );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove4.pushU4B(Long.parseLong(deviceClass.getNumberFour()));
                if (deviceClass.getNumberFour().equalsIgnoreCase( "" )) {
//                    byteQueueRemove4.pushU4B( Long.valueOf( uidNo4.getText().toString().trim() ) );
                    byteQueueRemove4.pushU4B( Long.valueOf( valueFour ) );
                } else {
                    byteQueueRemove4.pushU4B( Long.parseLong( deviceClass.getNumberFour() ) );
                }
                advertiseTaskRemove4 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove4.setByteQueue( byteQueueRemove4 );
                Log.e( "Check>>>>", byteQueueRemove4.toString() );
                advertiseTaskRemove4.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove4.startAdvertising();
                uidNo4.setText( "" );
                selectItem4.setText( "" );
                typeFour.setText( "" );
                break;
            case R.id.btn_remove5:
                if (uidNo5.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo5 );
                    return;
                } else if (selectItem5.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem5 );
                    return;
                }
                ContentValues contentValuesRemove5 = new ContentValues();
                contentValuesRemove5.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_FIVE, "" );
                contentValuesRemove5.put( DatabaseConstant.COLUMN_DEVICE_ITEM_FIVE, "" );
                contentValuesRemove5.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_FIVE, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove5 )) {
                }
                AdvertiseTask advertiseTaskRemove5 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove5 = new ByteQueue();
                byteQueueRemove5.push( REMOVE_ASSOCIATE );
                byteQueueRemove5.push( 0x03 );
                byteQueueRemove5.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem5.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove5.push( 0x51 );
                } else if (selectItem5.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove5.push( 0x52 );
                } else if (selectItem5.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove5.push( 0x53 );
                }
                if (deviceClass.getNumberFive().equalsIgnoreCase( "" )) {
                    byteQueueRemove5.pushU4B( Long.valueOf( valueFive ) );
//                    byteQueueRemove5.pushU4B( Long.valueOf( uidNo5.getText().toString().trim() ) );
                } else {
                    byteQueueRemove5.pushU4B( Long.parseLong( deviceClass.getNumberFive() ) );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove5.pushU4B(Long.parseLong(deviceClass.getNumberFive()));
                advertiseTaskRemove5 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove5.setByteQueue( byteQueueRemove5 );
                Log.e( "Check>>>>", byteQueueRemove5.toString() );
                advertiseTaskRemove5.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove5.startAdvertising();
                uidNo5.setText( "" );
                selectItem5.setText( "" );
                typeFive.setText( "" );
                break;

            case R.id.btn_remove6:
                if (uidNo6.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo6 );
                    return;
                } else if (selectItem6.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem6 );
                    return;
                }
                ContentValues contentValuesRemove6 = new ContentValues();
                contentValuesRemove6.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_SIX, "" );
                contentValuesRemove6.put( DatabaseConstant.COLUMN_DEVICE_ITEM_SIX, "" );
                contentValuesRemove6.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SIX, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove6 )) {
                }
                AdvertiseTask advertiseTaskRemove6 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove6 = new ByteQueue();
                byteQueueRemove6.push( REMOVE_ASSOCIATE );
                byteQueueRemove6.push( 0x03 );
                byteQueueRemove6.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem6.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove6.push( 0x61 );
                } else if (selectItem6.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove6.push( 0x62 );
                } else if (selectItem6.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove6.push( 0x63 );
                }
                if (deviceClass.getNumberSix().equalsIgnoreCase( "" )) {
                    byteQueueRemove6.pushU4B( Long.valueOf( valueSix ) );
//                    byteQueueRemove6.pushU4B( Long.valueOf( uidNo6.getText().toString().trim() ) );
                } else {
                    byteQueueRemove6.pushU4B( Long.parseLong( deviceClass.getNumberSix() ) );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove6.pushU4B(Long.parseLong(deviceClass.getNumberSix()));
                advertiseTaskRemove6 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove6.setByteQueue( byteQueueRemove6 );
                Log.e( "Check>>>>", byteQueueRemove6.toString() );
                advertiseTaskRemove6.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove6.startAdvertising();
                uidNo6.setText( "" );
                selectItem6.setText( "" );
                typeSix.setText( "" );
                break;

            case R.id.btn_remove7:
                if (uidNo7.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo7 );
                    return;
                } else if (selectItem7.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem7 );
                    return;
                }
                ContentValues contentValuesRemove7 = new ContentValues();
                contentValuesRemove7.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_SEVEN, "" );
                contentValuesRemove7.put( DatabaseConstant.COLUMN_DEVICE_ITEM_SEVEN, "" );
                contentValuesRemove7.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_SEVEN, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove7 )) {
                }
                AdvertiseTask advertiseTaskRemove7 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove7 = new ByteQueue();
                byteQueueRemove7.push( REMOVE_ASSOCIATE );
                byteQueueRemove7.push( 0x03 );
                byteQueueRemove7.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem7.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove7.push( 0x71 );
                } else if (selectItem7.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove7.push( 0x72 );
                } else if (selectItem7.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove7.push( 0x73 );
                }
                if (deviceClass.getNumberSeven().equalsIgnoreCase( "" )) {
                    byteQueueRemove7.pushU4B( Long.valueOf( valueSeven ) );
//                    byteQueueRemove7.pushU4B( Long.valueOf( uidNo8.getText().toString().trim() ) );
                } else {
                    byteQueueRemove7.pushU4B( Long.parseLong( deviceClass.getNumberSeven() ) );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove7.pushU4B(Long.parseLong(deviceClass.getNumberSeven()));
                advertiseTaskRemove7 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove7.setByteQueue( byteQueueRemove7 );
                Log.e( "Check>>>>", byteQueueRemove7.toString() );
                advertiseTaskRemove7.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove7.startAdvertising();
                uidNo7.setText( "" );
                selectItem7.setText( "" );
                typeSeven.setText( "" );
                break;

            case R.id.btn_remove8:
                if (uidNo8.getText().toString().length() == 0) {
                    showError( "Address one can't empty", uidNo8 );
                    return;
                } else if (selectItem8.getText().toString().length() == 0) {
                    showError( "Please select sensor type one", selectItem8 );
                    return;
                }
                ContentValues contentValuesRemove8 = new ContentValues();
                contentValuesRemove8.put( DatabaseConstant.COLUMN_DEVICE_NUMBER_EIGET, "" );
                contentValuesRemove8.put( DatabaseConstant.COLUMN_DEVICE_ITEM_EIGET, "" );
                contentValuesRemove8.put( DatabaseConstant.COLUMN_DEVICE_GROUP_TYPE_EIGHT, "");
                if (sqlHelper.updateDevice( deviceClass.getDeviceUID(), contentValuesRemove8 )) {
                }
                AdvertiseTask advertiseTaskRemove8 = new AdvertiseTask( activity );
                ByteQueue byteQueueRemove8 = new ByteQueue();
                byteQueueRemove8.push( REMOVE_ASSOCIATE );
                byteQueueRemove8.push( 0x03 );
                byteQueueRemove8.pushU4B( deviceClass.getDeviceUID() );
                if (selectItem8.getText().toString().equalsIgnoreCase( "PIR" )) {
                    byteQueueRemove8.push( 0x81 );
                } else if (selectItem8.getText().toString().equalsIgnoreCase( "Switch" )) {
                    byteQueueRemove8.push( 0x82 );
                } else if (selectItem8.getText().toString().equalsIgnoreCase( "Day Light" )) {
                    byteQueueRemove8.push( 0x83 );
                }
                if (deviceClass.getNumberEight().equalsIgnoreCase( "" )) {
                    byteQueueRemove8.pushU4B( Long.valueOf( valueFive ) );
//                    byteQueueRemove8.pushU4B( Long.valueOf( uidNo8.getText().toString().trim() ) );
                } else {
                    byteQueueRemove8.pushU4B( Long.parseLong( deviceClass.getNumberEight() ) );
                }
//                byteQueue1.push(PIR_INFO);
//                byteQueueRemove8.pushU4B(Long.parseLong(deviceClass.getNumberEight()));
                advertiseTaskRemove8 = new AdvertiseTask( LastDemoAddFragment.this, activity, 5 * 1000 );
                animatedProgress.setText( "Uploading" );
                advertiseTaskRemove8.setByteQueue( byteQueueRemove8 );
                Log.e( "Check>>>>", byteQueueRemove8.toString() );
                advertiseTaskRemove8.setSearchRequestCode( REMOVE_ASSOCIATE );
                advertiseTaskRemove8.startAdvertising();
                uidNo8.setText( "" );
                selectItem8.setText( "" );
                typeEight.setText( "" );
                break;


        }
    }

    public void getDevice() {
        deviceList.clear();
        DeviceClass noGroupData = new DeviceClass();
        noGroupData.setDeviceName( "No Associate" );
        deviceList.add( noGroupData );
        Cursor cursor = sqlHelper.getAllDevice( DatabaseConstant.ADD_DEVICE_TABLE );
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ).equalsIgnoreCase( "533" ) ||
                        (cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ).equalsIgnoreCase( "55811" ) && cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS ) ).startsWith( "E2:15" )) ||
                        (cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ).equalsIgnoreCase( "55811" ) && cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS ) ).startsWith( "E5:00" ))) {
                    DeviceClass deviceData = new DeviceClass();
                    deviceData.setDeviceName( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_NAME ) ) );
                    deviceData.setDeviceUID( cursor.getLong( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_UID ) ) );
                    deviceData.setTypeCode( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ) );
                    deviceData.setMacAddress( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS ) ) );
                    deviceList.add( deviceData );
                }

            }
            while (cursor.moveToNext());
        }
        cursor.close();
//        adapterSite.notifyDataSetChanged();

    }

    public void getDeviceTwo() {
        ListTwo.clear();
        DeviceClass noGroupData = new DeviceClass();
        noGroupData.setDeviceName( "No Associate" );
        ListTwo.add( noGroupData );
        Cursor cursor = sqlHelper.getAllDevice( DatabaseConstant.ADD_DEVICE_TABLE );
//        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ).equalsIgnoreCase( "533" ) ||
                        (cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ).equalsIgnoreCase( "55811" ) && cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS ) ).startsWith( "E2:15" )) ||
                        (cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ).equalsIgnoreCase( "55811" ) && cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS ) ).startsWith( "E5:00" ))) {

                    DeviceClass deviceData = new DeviceClass();
                    deviceData.setDeviceName( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_NAME ) ) );
                    deviceData.setDeviceUID( cursor.getLong( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_UID ) ) );
                    deviceData.setTypeCode( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_TYPE_CODE ) ) );
                    deviceData.setMacAddress( cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_MAC_ADDRESSS ) ) );
                    ListTwo.add( deviceData );
                }


            }
            while (cursor.moveToNext());
        }
        cursor.close();

    }


    public void setDeviceData(DeviceClass deviceData) {
        this.deviceClass = deviceData;
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
        activity.onBackPressed();
        Log.w( TAG, "onScanFailed " + errorMessage );

    }

    @Override
    public void onStop(String stopMessage, int resultCode) {
        if (animatedProgress != null)
            animatedProgress.hideProgress();
//        activity.onBackPressed();
        ContentValues contentValues = new ContentValues();
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance( activity );
        Log.w( TAG, "Advertising stop" + resultCode );
        switch (resultCode) {
        }


    }

    @Override
    public void onScanSuccess(int successCode, ByteQueue byteQueue) {
        if (animatedProgress == null)
            return;
        animatedProgress.hideProgress();
//        activity.onBackPressed();
        byte[] bytes1;
        String nodeUid;
        long deviceUid;
        int lightStatus;
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance( activity );
        switch (successCode) {
        }

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
        getDevice();
        getDeviceTwo();

    }

    private void showError(String error_st, EditText editText) {
        try {
            Dialog error_dialog = new Dialog( activity );
            error_dialog.setCanceledOnTouchOutside( false );
            error_dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
            error_dialog.setContentView( R.layout.error_dialoge );
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
            error_dialog.getWindow().setLayout( width, ViewGroup.LayoutParams.WRAP_CONTENT );
            error_dialog.getWindow().setBackgroundDrawableResource( android.R.color.transparent );
            TextView error_text = error_dialog.findViewById( R.id.error_text );
            Button ok_btn = error_dialog.findViewById( R.id.ok_btn );
            error_text.setText( error_st );
            error_dialog.show();
            ok_btn.setOnClickListener( view -> {
                error_dialog.dismiss();
                requestFocus( editText );
            } );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE );
        }
    }


}
