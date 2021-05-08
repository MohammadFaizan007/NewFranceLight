package com.inferrix.lightsmart.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.inferrix.lightsmart.R;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.PogoClasses.Project;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.inferrix.lightsmart.activity.AppHelper.sqlHelper;


public class EditEnterGroupActivity extends Activity  {
    Unbinder unbinder;
    Activity activity;
    @BindView(R.id.group_name_text)
    TextView groupNameText;
    @BindView(R.id.customize)
    TextView customize;
    @BindView(R.id.edit_light_status)
    TextView editLightStatus;
    @BindView(R.id.edit_light_deriveType)
    TextView editLightDeriveType;
    @BindView(R.id.edit_light_name)
    EditText editLightName;
    @BindView(R.id.light_edit)
    ImageView lightEdit;
    @BindView(R.id.light_save)
    ImageView lightSave;
    @BindView(R.id.light_delete)
    ImageView lightDelete;
    @BindView(R.id.edit_layout)
    RelativeLayout editLayout;
    @BindView(R.id.light_check_status)
    Button lightCheckStatus;
    @BindView(R.id.light_check_group)
    Button lightCheckGroup;
    @BindView(R.id.check_status)
    LinearLayout checkStatus;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.main_lo)
    ConstraintLayout mainLo;
    Project project ;
    private String projectID,projectName;
    @BindView(R.id.title)
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_enter_group_edit);
        ButterKnife.bind(this);
        title.setText("Edit Group");
        Intent intent = getIntent();
        project = (Project) intent.getSerializableExtra("project");

        Log.e ("Project_id=====>", String.valueOf (project.getProjectId ()));
        groupNameText.setText (project.getProjectNname ());
        editLightName.setText (project.getProjectNname ());


    }

    @OnClick({R.id.edit_light_name, R.id.light_edit, R.id.light_save, R.id.light_delete})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.edit_light_name:
                break;
            case R.id.light_edit:
                if (editLightName.isEnabled ()) {
                    editLightName.setEnabled (false);
                    lightEdit.setVisibility (View.VISIBLE);
                    lightSave.setVisibility (View.GONE);
                    lightDelete.setVisibility (View.GONE);
                } else {
                    editLightName.setEnabled (true);
                    lightEdit.setVisibility (View.GONE);
                    lightSave.setVisibility (View.VISIBLE);
                    lightDelete.setVisibility (View.VISIBLE);
                }
                break;
            case R.id.light_save:
                saveData ();
                break;
            case R.id.light_delete:
                deleteDialog ();
                break;
        }
    }

    public void saveData() {
        if (editLightName.getText ().toString ().trim ().length () < 1) {
            editLightName.setError ("Light name can't empty");
            return;
        }
        ContentValues contentValues = new ContentValues ();
        contentValues.put (DatabaseConstant.COLUMN_PROJECT_NAME, editLightName.getText ().toString ());
        if (sqlHelper.updateProject (String.valueOf (project.getProjectId ()), contentValues)) {
            editLightName.setEnabled (false);
            lightEdit.setVisibility (View.VISIBLE);
            lightSave.setVisibility (View.GONE);
            lightDelete.setVisibility (View.GONE);
            Intent intent = new Intent(EditEnterGroupActivity.this, AvailableProject.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else
            Toast.makeText (activity, "Some error to edit group", Toast.LENGTH_SHORT).show ();
    }

     private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder (EditEnterGroupActivity.this);
        builder.setMessage ("Are you sure to delete Project  " + project.getProjectNname ())
                .setTitle ("Remove Light");
        builder.setPositiveButton ("delete", (dialog1, id) -> {
            dialog1.dismiss ();
            sqlHelper.deleteEnterGroup (project.getProjectId ());
            Toast.makeText (EditEnterGroupActivity.this, "Project deleted.", Toast.LENGTH_SHORT).show ();
            Intent intent = new Intent(EditEnterGroupActivity.this,AvailableProject.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        builder.setNegativeButton ("Cancel", (dialog, which) -> {
            dialog.dismiss ();
        });
        AlertDialog dialog = builder.create ();
        dialog.show ();
    }

}

