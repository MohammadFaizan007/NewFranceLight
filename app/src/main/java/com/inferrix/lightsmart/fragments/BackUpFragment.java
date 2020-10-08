package com.inferrix.lightsmart.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.inferrix.lightsmart.DatabaseModule.SqlHelper;
import com.inferrix.lightsmart.MainActivity;
import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.constant.Permissions;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BackUpFragment extends Fragment {
    Unbinder unbinder;
    Activity activity;
    @BindView(R.id.back_up)
    AppCompatButton backUp;

    public BackUpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_backup, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = getActivity();
        return view;
    }


    @OnClick({R.id.back_up})
    public void onViewClicked(View view) {
        final SqlHelper db = new SqlHelper(activity);
        switch (view.getId()) {
            case R.id.back_up:
                Toast.makeText(activity, "Some error to edit group", Toast.LENGTH_SHORT).show();
//                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name) + File.separator;
//                performBackup(db, outFileName);
        }
    }



//    public void performBackup(final SqlHelper db, final String inferrix) {
//
//        Permissions.verifyStoragePermissions(activity);
//
//        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + activity.getResources().getString(R.string.app_name));
//
//        boolean success = true;
//        if (!folder.exists())
//            success = folder.mkdirs();
//        if (success) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//            builder.setTitle("Inferrix Backup");
//            final EditText input = new EditText(activity);
//            input.setInputType(InputType.TYPE_CLASS_TEXT);
//            builder.setView(input);
//            builder.setPositiveButton("Save", (dialog, which) -> {
//                String m_Text = input.getText().toString();
//                String out = inferrix + m_Text + ".db";
//                db.backup(out);
//            });
//            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//
//            builder.show();
//        } else
//            Toast.makeText(activity, "Unable to create directory. Retry", Toast.LENGTH_SHORT).show();
//    }

    //ask to the user what backup to restore
//    public void performRestore(final SqlHelper db) {
//
//        Permissions.verifyStoragePermissions(activity);
//
//        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + activity.getResources().getString(R.string.app_name));
//        if (folder.exists()) {
//
//            final File[] files = folder.listFiles();
//
//            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.select_dialog_item);
//            for (File file : files)
//                arrayAdapter.add(file.getName());
//
//            AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
//            builderSingle.setTitle("Restore:");
//            builderSingle.setNegativeButton(
//                    "cancel",
//                    (dialog, which) -> dialog.dismiss());
//            builderSingle.setAdapter(
//                    arrayAdapter,
//                    (dialog, which) -> {
//                        try {
//                            db.importDB(files[which].getPath());
//                        } catch (Exception e) {
//                            Toast.makeText(activity, "Unable to restore. Retry", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            builderSingle.show();
//        } else
//            Toast.makeText(activity, "Backup folder not present.\nDo a backup before a restore!", Toast.LENGTH_SHORT).show();
//    }

}

