package com.inferrix.lightsmart.activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.DatabaseModule.InputValidation;
import com.inferrix.lightsmart.DatabaseModule.SqlHelper;
import com.inferrix.lightsmart.MainActivity;
import com.inferrix.lightsmart.PogoClasses.Project;
import com.inferrix.lightsmart.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EnterProjectActivity extends AppCompatActivity {
    @BindView(R.id.textInputEditTextProjectName)
    TextInputEditText textInputEditTextProjectName;
    @BindView(R.id.textInputLayoutProjectName)
    TextInputLayout textInputLayoutProjectName;
    @BindView(R.id.btn_create_project)
    Button btnCreateProject;
    @BindView(R.id.card_one)
    CardView cardOne;
    @BindView(R.id.textInputEditTextProjectNameEnter)
    TextInputEditText textInputEditTextProjectNameEnter;
    @BindView(R.id.textInputLayoutProjectNameEnter)
    TextInputLayout textInputLayoutProjectNameEnter;
    @BindView(R.id.btn_enter_project)
    Button btnEnterProject;
    @BindView(R.id.card_two)
    CardView cardTwo;

    private InputValidation inputValidation;
    private Project project;
    private SqlHelper sqlHelper;
    private ArrayList<Project> projectArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.create_project );
        ButterKnife.bind ( this );
        getSupportActionBar().setTitle("Enter Projects");
        initObjects();
    }

    private void initObjects() {
        inputValidation = new InputValidation( EnterProjectActivity.this);
        sqlHelper = new SqlHelper( EnterProjectActivity.this);
        project = new Project ();

    }

    @OnClick({R.id.btn_create_project, R.id.btn_enter_project,R.id.available_project,R.id.create_project})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.btn_create_project:
                postDataToSQLite ();
                break;
            case R.id.btn_enter_project:
                verifyFromSQLite();
                break;
            case R.id.create_project:
                Intent projects = new Intent ( EnterProjectActivity.this, CreateProjectActivity.class );
                startActivity ( projects );
                break;

            case R.id.available_project:
                Intent projects_new = new Intent ( EnterProjectActivity.this, AvailableProject.class );
                startActivity ( projects_new );
                break;
        }
    }

    private void postDataToSQLite() {
        if (textInputEditTextProjectName.getText().toString().length() < 1) {
            textInputEditTextProjectName.setError( "Enter project name" );
            return;
        }
        ContentValues contentValues = new ContentValues (  );
        contentValues.put ( DatabaseConstant.COLUMN_PROJECT_NAME,textInputEditTextProjectName.getText ().toString () );
        if (sqlHelper.insertData ( DatabaseConstant.TABLE_PROJECT,contentValues ) >0){
            Intent accountsIntent = new Intent ( EnterProjectActivity.this, AvailableProject.class );
//            accountsIntent.putExtra("user", projectArrayList.get(position));
            emptyInputEditText ();
            startActivity ( accountsIntent );
            Toast.makeText ( EnterProjectActivity.this, "Create Project Successful", Toast.LENGTH_SHORT ).show ();


        } else {
            Toast.makeText ( EnterProjectActivity.this, "Project Already Exists", Toast.LENGTH_SHORT ).show ();
            project.setProjectNname ( textInputEditTextProjectName.getText ().toString ().trim () );
        }

    }

    private void verifyFromSQLite() {
        if (textInputEditTextProjectNameEnter.getText ().toString ().length () < 1) {
            textInputEditTextProjectNameEnter.setError ( "Enter project name" );
            return;

        }
        if (sqlHelper.checkProject ( textInputEditTextProjectNameEnter.getText ().toString ().trim () )) {
            Intent accountsIntent = new Intent ( EnterProjectActivity.this, MainActivity.class );
//            getUserId();
            emptyInputEditText ();
            startActivity ( accountsIntent );

        } else {
            Toast.makeText ( EnterProjectActivity.this, "Enter a valid project", Toast.LENGTH_SHORT ).show ();
        }

    }

//    public void getUserId(){
//        BeconDeviceClass beconDeviceClass = new BeconDeviceClass ();
//        beconDeviceClass.setFk_userID ();
//    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextProjectName.setText ( null );
        textInputEditTextProjectNameEnter.setText ( null );
    }
}
