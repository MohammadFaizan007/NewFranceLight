package com.inferrix.lightsmart.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.inferrix.lightsmart.PogoClasses.DeviceClass;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.activity.HelperActivity;
import com.inferrix.lightsmart.constant.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AssociateListAdapter extends BaseAdapter {
    Dialog pinStatus_dialog;
    Dialog choose_dialog;
    Dialog attribute_dialog;
    Activity activity;
    ArrayList<DeviceClass> arrayList;
    DeviceClass deviceClass;
    String TAG = this.getClass().getSimpleName();
    int selectedPosition = -1;

    public AssociateListAdapter(@NonNull Activity context) {
        activity = context;
        arrayList = new ArrayList<>();
//        scannerTask=new ScannerTask(activity,this);
//        animatedProgress=new AnimatedProgress(activity);
//        animatedProgress.setCancelable(false);

    }

    public void setList(List<DeviceClass> arrayList1) {
        arrayList.clear();
        arrayList.addAll(arrayList1);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public DeviceClass getItem(int position) {
        if (arrayList.size() <= position)
            return null;
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        {
            convertView = LayoutInflater.from(activity).
                    inflate(R.layout.associate_list_adapter, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        DeviceClass deviceClass = arrayList.get(position);
        viewHolder.dashboard_deviceName.setText(deviceClass.getDeviceName());
        viewHolder.uid_no.setText(deviceClass.getDevicehexUid());
        viewHolder.light_details.setBackground(activity.getResources().getDrawable(deviceClass.getMasterStatus()==0?R.drawable.white_circle_border:R.drawable.yellow_circle));

//        viewHolder.icon_delete.setOnClickListener(view -> {
//            deleteDialog(position);

//        });
//        viewHolder.read.setOnClickListener(v -> {
//           Intent intent = new Intent(activity, HelperActivity.class);
//            intent.putExtra(Constants.MAIN_KEY, Constants.READ_REMOVE_DETAILS);
//            intent.putExtra(Constants.LIGHT_DETAIL_KEY, arrayList.get(position));
//            activity.startActivity(intent);
//
//        });
        if (deviceClass.getMasterStatus()==1){
            viewHolder.light_details.setOnClickListener(v -> {
                Intent intent = new Intent(activity, HelperActivity.class);
                intent.putExtra(Constants.MAIN_KEY, Constants.ADD_ASSOCIATE);
                intent.putExtra("name", deviceClass.getDeviceName());
                intent.putExtra("pos", position);
                intent.putExtra(Constants.LIGHT_DETAIL_KEY, arrayList.get(position));
                activity.startActivity(intent);

            });
        }else {
//            Toast.makeText(activity, "Make Light as a master.", Toast.LENGTH_SHORT).show();
        }



        return convertView;
    }





//    void deleteDialog(int position) {
//        selectedPosition = position;
//        DeviceClass deviceClass = arrayList.get(position);
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setMessage("Are you sure you want to delete " + deviceClass.getDeviceName())
//                .setTitle("Remove FDU");
//        builder.setPositiveButton("delete", (dialog1, id) -> {
//            dialog1.dismiss();
//            if (sqlHelper.deleteDevice(deviceClass.getDeviceUID()) > 0) {
//                Toast.makeText(activity, "FDU Deleted.", Toast.LENGTH_SHORT).show();
//                activity.onBackPressed();
//            } else
//                Toast.makeText(activity, "Some Error to Delete FDU", Toast.LENGTH_SHORT).show();
//
//        });
//        builder.setNegativeButton("Cancel", (dialog, which) -> {
//            dialog.dismiss();
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    static class ViewHolder {

        @BindView(R.id.dashboard_deviceName)
        TextView dashboard_deviceName;
        @BindView(R.id.uid_no)
        TextView uid_no;
//        @BindView(R.id.read)
//        Button read;
//        @BindView(R.id.add)
//        Button add;add
//        @BindView(R.id.icon_delete)
//        ImageView icon_delete;
        @BindView(R.id.light_details)
        ImageButton light_details;

        ViewHolder(View view) {

            ButterKnife.bind(this, view);

        }
    }


}


