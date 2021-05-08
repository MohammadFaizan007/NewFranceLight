package com.inferrix.lightsmart.ServiceModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ArrayUtilities;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.EncodeDecodeModule.MyBase64;
import com.inferrix.lightsmart.InterfaceModule.MyBeaconScanner;
import com.inferrix.lightsmart.PogoClasses.BeconDeviceClass;
import com.inferrix.lightsmart.activity.AppHelper;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class ScanningBeacon implements BeaconConsumer {
    MyBeaconScanner myBeaconScanner;
    boolean mAllowRebind;
    ArrayList<BeconDeviceClass> arrayList;
    public static final int SCAN_SUCCESS_CODE = 200;
    public static final int SCAN_FAIL_CODE = 201;
    public static final int SCANNING_TIMEOUT = 15 * 1000;
    public static final String EDDYSTONE_URL_LAYOUT = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-21v";
    public static final String IBEACON_LAYOUT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
    public static final String SWICTES_LAYOUT = "x,m:0-1=da03,i:0-1,d:2-3,d:4-5,d:6-7,d:8-9,d:9-10";
    private BeaconManager mBeaconManager;
    Activity activity;
    String TAG = "ScanningBeacon";
    int scanPeriod = 500;
    int request = 0x4f;
    int resultCode = SCAN_FAIL_CODE;
    String url = "rx";
    Handler handler;
    ByteQueue byteQueue;
    AnimatedProgress animatedProgress;
    Region region;
    private Runnable runnable = this::stop;
    int deriveType;

    public ScanningBeacon(Activity activity) {
        BeaconManager.setDebug( true );
        BeaconManager.setAndroidLScanningDisabled( true );
        mBeaconManager = BeaconManager.getInstanceForApplication( activity );
        this.activity = activity;
        animatedProgress = new AnimatedProgress( activity );
        mBeaconManager.getBeaconParsers().add( new BeaconParser().
                setBeaconLayout( EDDYSTONE_URL_LAYOUT ) );
        mBeaconManager.getBeaconParsers().add( new BeaconParser().
                setBeaconLayout( IBEACON_LAYOUT ) );
        mBeaconManager.getBeaconParsers().add( new BeaconParser().
                setBeaconLayout( SWICTES_LAYOUT ) );

        mBeaconManager.setBackgroundBetweenScanPeriod( scanPeriod );
        mBeaconManager.setForegroundBetweenScanPeriod( scanPeriod );
        mBeaconManager.setBackgroundScanPeriod( scanPeriod );
        mBeaconManager.setForegroundScanPeriod( scanPeriod );
        mBeaconManager.setBackgroundMode( false );
        try {
            mBeaconManager.updateScanPeriods();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        arrayList = new ArrayList<>();
//        ArrayList<BeconDeviceClass> arrayList= new ArrayList<BeconDeviceClass>();

    }

    public void setMyBeaconScanner(MyBeaconScanner myBeaconScanner) {
        this.myBeaconScanner = myBeaconScanner;
    }

    public MyBeaconScanner getMyBeaconScanner() {
        return myBeaconScanner;
    }

    public void start() {

        region = new Region( "all-beacons-region", null, null, null );
        try {
//                Log.w(TAG, "onBeaconServiceConnect try");
            mBeaconManager.startRangingBeaconsInRegion( region );
        } catch (RemoteException e) {
            Log.w( TAG, "onBeaconServiceConnect catch" + e.getMessage() );
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier( new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.e( TAG, "BeaconsReceive" + beacons.size() );
                for (Beacon beacon : beacons) {
                    if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x10) {
                        BeconDeviceClass beconDeviceClass = new BeconDeviceClass();
                        String TypeCode = String.valueOf( beacon.getBeaconTypeCode() );
                        beconDeviceClass.setTypeCode( TypeCode.toString() );
                        Log.e( "TYPE==>", TypeCode.toString() );
                        byte[] bytes = beacon.getId1().toByteArray();
                        byte ONE = bytes[0];
                        Log.w( "Byte", ONE + "" );
                        String receivedString = null;
                        receivedString = new String( bytes, 0, bytes.length, StandardCharsets.US_ASCII );
                        Log.w( TAG, "I just received: " + receivedString );
                        if (receivedString.toLowerCase().contains( "tx" )) {
                            String[] splitUrl = receivedString.split( "tx" );
                            if (splitUrl.length > 1) {
                                byte[] encodeId1 = MyBase64.decode( splitUrl[1] );
                                ByteQueue byteQueue1 = new ByteQueue( encodeId1 );
                                byteQueue1.push( encodeId1 );
                                ByteQueue byteQueue2 = new ByteQueue( encodeId1 );
                                byteQueue2.push( encodeId1 );
                                int methodType = byteQueue1.pop();
                                Log.w( "MethodType", methodType + "" );
                                if (methodType == 0x4f) {
                                    byte[] bytes1 = byteQueue1.pop4B();
                                    ArrayUtilities.reverse( bytes1 );
                                    String nodeUid = bytesToHex( bytes1 );
                                    BigInteger bi = new BigInteger( nodeUid, 16 );
                                    Log.w( "Scann", bi + "" );
                                    int deriveType = byteQueue1.pop();
                                    Log.w( "ScanningBeacon", nodeUid + "," + deriveType );
//                                    BeconDeviceClass beconDeviceClass=new BeconDeviceClass();
                                    beconDeviceClass.setBeaconUID( bi.longValue() );
                                    beconDeviceClass.setDeviceUid( nodeUid );
                                    beconDeviceClass.setDeriveType( deriveType );
                                    if (!hasBeacon( beconDeviceClass )) {
//                                        Cursor cursor = AppHelper.sqlHelper.getLightDetails( beconDeviceClass.getBeaconUID() );
//                                        if (cursor != null && cursor.getCount() > 0) {
//                                            cursor.moveToFirst();
//                                            String beconName = cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_NAME ) );
//                                            Log.w( "BeaconName", beconName + "," );
//                                            beconDeviceClass.setDeviceName( beconName );
//                                            beconDeviceClass.setAdded( true );
//
//                                        }
                                        arrayList.add( beconDeviceClass );
                                    } else {
                                        Log.w( "Has", "Not add" );
                                    }
                                }
                            }
                        }
                    }


//                    E5:00 Pir
                    if (beacon.getBeaconTypeCode() == 533 || beacon.getBeaconTypeCode() == 55811) {
                        String mac_address = String.valueOf( beacon.getBluetoothAddress() );
                        String Type_Code = String.valueOf( beacon.getBeaconTypeCode() );
                        String packet = String.valueOf( beacon.getBluetoothAddress() );
                        String uuid = String.valueOf( beacon.getBluetoothAddress() );
                        Log.e( "BluetoothAddress", uuid + "" );
                        String first = "", second = "", third = "", four = "", total;
                        first = packet.substring( 6, 8 );
                        Log.e( "First", first + "" );
                        second = packet.substring( 9, 11 );
                        Log.e( "Second", second );
                        third = packet.substring( 12, 14 );
                        Log.e( "Third", third );
                        four = packet.substring( 15, 17 );
                        Log.e( "Four", four );
                        total = first + second + third + four;
                        Log.e( "total", total );
                        BigInteger bi = new BigInteger( total, 16 );
                        Log.e( "Scann", bi + "" );
                        BeconDeviceClass beconDeviceClass2 = new BeconDeviceClass();
                        beconDeviceClass2.setTypeCode( Type_Code );
                        beconDeviceClass2.setDeviceMacAddress( mac_address );
                        beconDeviceClass2.setDeviceUid( total );
                        beconDeviceClass2.setBeaconUID( bi.longValue() );
                        beconDeviceClass2.setiBeaconUuid( Type_Code );
                        if (!hasBeacon( beconDeviceClass2 )) {
//                            Cursor cursor = AppHelper.sqlHelper.getLightDetails( beconDeviceClass2.getBeaconUID() );
//                            if (cursor != null && cursor.getCount() > 0) {
//                            if (cursor.moveToFirst()) {
//                                String Name = cursor.getString( cursor.getColumnIndex( DatabaseConstant.COLUMN_DEVICE_NAME ) );
//                                Log.w( "BeaconName", Name + "," );
//                                beconDeviceClass2.setDeviceName( Name );
//                                beconDeviceClass2.setAdded( true );
//                            }
                            arrayList.add( beconDeviceClass2 );
//
                        } else {
                            Log.e( "Has2", "Not add" );
                        }
                    }

                }


                if (myBeaconScanner == null)
                    return;
                if (arrayList.size() < 1) {
                    myBeaconScanner.noBeaconFound();
                    return;
                }
                myBeaconScanner.onBeaconFound( arrayList );

            }
        } );
        handler();
    }

    public void startWithHandler() {
//    animatedProgress.showProgress();
        region = new Region( "all-beacons-region", null, null, null );
        try {
//                Log.w(TAG, "onBeaconServiceConnect try");
            mBeaconManager.startRangingBeaconsInRegion( region );
        } catch (RemoteException e) {
            Log.w( TAG, "onBeaconServiceConnect catch" + e.getMessage() );
            e.printStackTrace();
        }
//        mBeaconManager.setRangeNotifier(this);
        handler();
    }

    public void stop() {
//            arrayList.clear();
        if (region != null) {
            try {
                mBeaconManager.stopRangingBeaconsInRegion( region );
                mBeaconManager.stopMonitoringBeaconsInRegion( region );
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.w( TAG, "stopping error" + e.toString() );
            }
        }
        mBeaconManager.removeAllRangeNotifiers();
        if (handler != null)
            handler.removeCallbacks( runnable );
//    animatedProgress.hideProgress();


    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String( hexChars );
    }

    private void handler() {
        handler = new Handler();

        handler.postDelayed( runnable, SCANNING_TIMEOUT );
    }

    boolean hasBeacon(BeconDeviceClass beconDeviceClass) {
        int i = 0;
        for (BeconDeviceClass beconDeviceClass1 : arrayList) {
            if (beconDeviceClass1.getBeaconUID() == beconDeviceClass.getBeaconUID()) {
                Log.w( "Has", "hash" );
                return true;
            } else
                i++;

        }
        return i != arrayList.size();

    }


    @Override
    public void onBeaconServiceConnect() {
//        // Encapsulates a beacon identifier of arbitrary byte length
//        ArrayList<Identifier> identifiers = new ArrayList<>();
//
//        // Set null to indicate that we want to match beacons with any value
//        identifiers.add(null);
//        // Represents a criteria of fields used to match beacon
//        Region region = new Region("AllBeaconsRegion"), identifiers);
//        try {
//            // Tells the BeaconService to start looking for beacons that match the passed Region object
//            mBeaconManager.startRangingBeaconsInRegion(region);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        // Specifies a class that should be called each time the BeaconService gets ranging data, once per second by default
//        mBeaconManager.addRangeNotifier(this);
    }


    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }
}

