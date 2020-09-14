package com.inferrix.lightsmart.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.CustomProgress.CustomDialog.AnimatedProgress;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;
import com.inferrix.lightsmart.InterfaceModule.AdvertiseResultInterface;
import com.inferrix.lightsmart.InterfaceModule.ReceiverResultInterface;
import com.inferrix.lightsmart.PogoClasses.BuildingGroupDetailsClass;
import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.PogoClasses.LevelGroupDetailsClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.ServiceModule.AdvertiseTask;
import com.inferrix.lightsmart.ServiceModule.ScannerTask;
import com.inferrix.lightsmart.activity.AppHelper;
import com.inferrix.lightsmart.activity.HelperActivity;
import com.inferrix.lightsmart.constant.Constants;
import com.niftymodaldialogeffects.Effectstype;
import com.niftymodaldialogeffects.NiftyDialogBuilder;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_BUILDING_GROUP_PROGRESS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_PROGRESS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_DEVICE_STATUS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_PROGRESS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_GROUP_STATUS;
import static com.inferrix.lightsmart.DatabaseModule.DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.GROUP_STATE_COMMAND;
import static com.inferrix.lightsmart.EncodeDecodeModule.RxMethodType.LIGHT_LEVEL_GROUP_COMMAND;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.GROUP_STATE_COMMAND_RESPONSE;
import static com.inferrix.lightsmart.EncodeDecodeModule.TxMethodType.LIGHT_LEVEL_GROUP_COMMAND_RESPONSE;
import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class LevelAdapter extends BaseAdapter implements Filterable, AdvertiseResultInterface, ReceiverResultInterface {
    Activity activity;
    ArrayList<LevelGroupDetailsClass> arrayList;
    ArrayList<String> filterarrayList;
    int requestCode;
    ScannerTask scannerTask;
    AnimatedProgress animatedProgress;
    int seekBarProgress=0;
    int selectedPosition=0;
    String TAG=this.getClass().getSimpleName();
    private LevelAdapter.ItemFilter mFilter = new LevelAdapter.ItemFilter();
    public LevelAdapter(@NonNull Activity context) {
        activity = context;
        arrayList = new ArrayList<>();
        filterarrayList = new ArrayList<>();
        animatedProgress = new AnimatedProgress(activity);

    }

    public void setList(List<LevelGroupDetailsClass> arrayList1) {
        arrayList.clear();
//        filterarrayList.clear();
        arrayList.addAll(arrayList1);
//        filterarrayList.addAll(arrayList1);
        notifyDataSetChanged();
    }

    public void getAllGroups() {
        arrayList.clear();
        Cursor cursor = sqlHelper.getAllGroup();
        if (cursor.moveToFirst())
        {
            do {
                LevelGroupDetailsClass groupData = new LevelGroupDetailsClass();
                groupData.setGroupLevelId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELID)));
                groupData.setLevelGroupDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_LEVEL_GROUP_PROGRESS)));
                groupData.setGroupLevelName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_DEVICE_LEVELNAME)));
                groupData.setLevelGroupStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_LEVELSTATUS)) == 1);
                arrayList.add(groupData);
                // do what ever you want here
            } while (cursor.moveToNext());
        }
//        setList(arrayList);
        notifyDataSetChanged();
        cursor.close();
    }

    public ArrayList<DeviceClass> getGroupLight(int groupId)
    {
        ArrayList<DeviceClass> deviceClasses=new ArrayList<>();
        Cursor cursor = sqlHelper.getLightInGroup(groupId);
        if (cursor.moveToFirst()) {
            do {
                DeviceClass deviceClass = new DeviceClass();
                deviceClass.setDeviceName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_NAME)));
                deviceClass.setDeviceUID(cursor.getLong(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_UID)));
                deviceClass.setDeriveType(cursor.getString(cursor.getColumnIndex(DatabaseConstant.COLUMN_DERIVE_TYPE)));
                deviceClass.setDeviceDimming(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_PROGRESS)));
                deviceClass.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_GROUP_ID)));
                deviceClass.setStatus(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.COLUMN_DEVICE_STATUS))==1);
                // do what ever you want here
            } while (cursor.moveToNext());
        }

        cursor.close();
//        setList(arrayList);
        return deviceClasses;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public LevelGroupDetailsClass getItem(int position) {
        if (arrayList.size() <= position)
            return null;
        return arrayList.get(position);
    }

    public void showDialog(int index)
    {
        final Dialog dialog = new Dialog(activity);
        LevelGroupDetailsClass groupDetails =arrayList.get(index);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customize_group);
        LinearLayout linearLayout=dialog.findViewById(R.id.group_device_list);
        TextView groupName=dialog.findViewById(R.id.customize_group_name);
        SeekBar seekBar=dialog.findViewById(R.id.customizeGroupSeekBar);
        Button button=dialog.findViewById(R.id.customiseGroupSave);

        seekBarProgress=groupDetails.getLevelGroupDimming();
        TextView levelPercentage=dialog.findViewById(R.id.level_percentage);
        levelPercentage.setText(seekBarProgress+" %");
        seekBar.setProgress(groupDetails.getLevelGroupDimming());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {

                seekBarProgress =i;
                levelPercentage.setText(seekBarProgress+" %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                selectedPosition=index;
                Log.w(TAG,seekBarProgress+"");
                ContentValues contentValues=new ContentValues();
                arrayList.get(index).setLevelGroupDimming(seekBarProgress);
                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
                String hex = Integer.toHexString(seekBarProgress);
                Log.w(TAG,hex+" "+String.format("%02X", seekBarProgress));
                requestCode=LIGHT_LEVEL_GROUP_COMMAND;
                AdvertiseTask advertiseTask;

                ByteQueue byteQueue=new ByteQueue();
                byteQueue.push(requestCode);   //// Light Level Group Command method type
                byteQueue.push(groupDetails.getGroupLevelId());
                byteQueue.push(hex);    ////0x00-0x64
                advertiseTask=new AdvertiseTask(LevelAdapter.this,activity,5*1000);
                advertiseTask.setByteQueue(byteQueue);
                advertiseTask.setSearchRequestCode(LIGHT_LEVEL_GROUP_COMMAND_RESPONSE);
                advertiseTask.startAdvertising();

                Log.w(TAG, AppHelper.sqlHelper.updateGroup(groupDetails.getGroupLevelId(),contentValues)+"");

            }
        });
//        button.setOnClickListener(view -> {
//            selectedPosition=index;
//            Log.w(TAG,seekBarProgress+"");
//            ContentValues contentValues=new ContentValues();
//            arrayList.get(index).setGroupDimming(seekBarProgress);
//            contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
//            String hex = Integer.toHexString(seekBarProgress);
//            Log.w(TAG,hex+" "+String.format("%02X", seekBarProgress));
//            requestCode=LIGHT_LEVEL_GROUP_COMMAND;
//            AdvertiseTask advertiseTask;
//
//            ByteQueue byteQueue=new ByteQueue();
//            byteQueue.push(requestCode);   //// Light Level Group Command method type
//            byteQueue.push(groupDetails.getGroupId());
//            byteQueue.push(hex);    ////0x00-0x64
//            advertiseTask=new AdvertiseTask(this,activity);
//            advertiseTask.setByteQueue(byteQueue);
//            advertiseTask.setSearchRequestCode(LIGHT_LEVEL_GROUP_COMMAND_RESPONSE);
//            advertiseTask.startAdvertising();
//
//            Log.w(TAG, AppHelper.sqlHelper.updateGroup(groupDetails.getGroupId(),contentValues)+"");
//            dialog.dismiss();
//        });
        groupName.setText(groupDetails.getGroupLevelName());
//        for(DeviceClass deviceClass:getGroupLight(groupDetails.getGroupId())) {
//            View layout2 = LayoutInflater.from(activity).inflate(R.layout.group_device_text, null, false);
//
//            NormalText textView =  layout2.findViewById(R.id.custom_device_text);
//            textView.setText(deviceClass.getDeviceName());
//            linearLayout.addView(layout2);
//        }
        dialog.show();

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
                    inflate(R.layout.group_list_adapter, parent, false);
        }
        SiteAdapter.ViewHolder viewHolder=new SiteAdapter.ViewHolder(convertView);
        LevelGroupDetailsClass groupDetails=arrayList.get(position);
        viewHolder.groupCustomize.setOnClickListener(view -> showDialog(position));
        viewHolder.editGroup.setOnClickListener(view -> {
            Intent intent = new Intent(activity, HelperActivity.class);
            intent.putExtra(Constants.MAIN_KEY, Constants.EDIT_LEVEL_GROUP);
            intent.putExtra(Constants.BUILDIN_GROUP_DETAIL_KEY,groupDetails);
            activity.startActivity(intent);
        });
        viewHolder.groupName.setText(groupDetails.getGroupLevelName());
        return convertView;
    }



    @Override
    public Filter getFilter() {
//        arrayList=filterarrayList;
        return mFilter;
    }

    @Override
    public void onSuccess(String message) {
//        animatedProgress.showProgress();
        Log.w(TAG,"Advertising start");
    }

    @Override
    public void onFailed(String errorMessage) {
        notifyDataSetChanged();
        animatedProgress.hideProgress();
        Toast.makeText(activity, "cannot advertise ,", Toast.LENGTH_SHORT).show();
        Log.w(TAG,"Advertising failed");
    }

    @Override
    public void onStop(String stopMessage, int resultCode) {
//        scannerTask.setRequestCode(resultCode);
//        scannerTask.start();
//        Log.w(TAG,"Advertising stop");
//        Log.w(TAG,"Advertising stop");
        ContentValues contentValues=new ContentValues();
        switch (resultCode)
        {
            case LIGHT_LEVEL_GROUP_COMMAND_RESPONSE:
                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
                Log.w(TAG,AppHelper.sqlHelper.updateGroup(arrayList.get(selectedPosition).getGroupLevelId(),contentValues)+"");
                break;
        }
        animatedProgress.hideProgress();
    }

    @Override
    public void onScanSuccess(int successCode, ByteQueue byteQueue) {

        ContentValues contentValues=new ContentValues();
        switch (successCode)
        {
            case LIGHT_LEVEL_GROUP_COMMAND_RESPONSE:
                contentValues.put(COLUMN_GROUP_PROGRESS,seekBarProgress);
                Log.w(TAG,AppHelper.sqlHelper.updateGroup(arrayList.get(selectedPosition).getGroupLevelId(),contentValues)+"");
                break;
        }
        animatedProgress.hideProgress();
        Log.w(TAG,"onScanSuccess "+successCode);
    }

    @Override
    public void onScanFailed(int errorCode) {
        NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(activity);
        dialogBuilder
                .withTitle("Timeout")
                .withEffect(Effectstype.Newspager)
                .withMessage("Timeout,Please check your beacon is in range")
                .withButton1Text("OK")
                .setButton1Click(v ->
                {
                    dialogBuilder.dismiss();
                })
                .show();
        animatedProgress.hideProgress();
        Log.w(TAG,"onScanFailed "+errorCode);
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

           /* final ArrayList<FriendsRequestData.FRequestDetails> list = filterarrayList;
            int count = list.size();
            final ArrayList<FriendsRequestData.FRequestDetails> nlist = new ArrayList<>(count);

            FriendsRequestData.FRequestDetails filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getUserName().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }*/
    /*        results.values = nlist;
            results.count = nlist.size();*/

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // arrayList = (ArrayList<FriendsRequestData.FRequestDetails>) results.values;
            // notifyDataSetChanged();
        }

    }

    static class ViewHolder {
        @BindView(R.id.group_customize)
        Button groupCustomize;

        @BindView(R.id.edit_group)
        Button editGroup;

        @BindView(R.id.group_name)
        TextView groupName;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}