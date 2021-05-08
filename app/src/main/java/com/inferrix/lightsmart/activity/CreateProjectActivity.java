package com.inferrix.lightsmart.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.inferrix.lightsmart.DatabaseModule.DatabaseConstant;
import com.inferrix.lightsmart.DatabaseModule.InputValidation;
import com.inferrix.lightsmart.DatabaseModule.SqlHelper;
import com.inferrix.lightsmart.PogoClasses.Project;
import com.inferrix.lightsmart.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateProjectActivity extends AppCompatActivity {
    @BindView(R.id.textInputEditTextProjectName)
    TextInputEditText textInputEditTextProjectName;
    @BindView(R.id.textInputLayoutProjectName)
    TextInputLayout textInputLayoutProjectName;
    @BindView(R.id.btn_create_project)
    Button btnCreateProject;
    @BindView(R.id.card_one)
    CardView cardOne;
//    @BindView(R.id.Enter_project)
//    TextView EnterProject;

    private InputValidation inputValidation;
    private Project project;
    private SqlHelper sqlHelper;
    private String formattedDate, dateStr, currentTimeDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.enter_project );
        ButterKnife.bind ( this );
        SimpleDateFormat curFormater = new SimpleDateFormat( "yyyy/MM/dd" );
        dateStr = curFormater.format( new Date () );
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" );
        formattedDate = sdf.format( cal.getTime() );
        currentTimeDate = (dateStr + " " + formattedDate);
        getSupportActionBar ().setTitle ( "Create Projects" );
        initObjects();
    }

    private void initObjects() {
        inputValidation = new InputValidation ( CreateProjectActivity.this);
        sqlHelper = new SqlHelper ( CreateProjectActivity.this);
        project = new Project ();

    }

    @OnClick({R.id.btn_create_project, R.id.Enter_project})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.btn_create_project:
                postDataToSQLite ();
                break;
//            case R.id.Enter_project:
//                Intent projects = new Intent ( CreateProjectActivity.this, EnterProjectActivity.class );
//                startActivity ( projects );
//                break;
        }
    }

    private void postDataToSQLite() {
        if (textInputEditTextProjectName.getText().toString().length() < 1) {
            textInputEditTextProjectName.setError( "Enter project name" );
            return;
        }
        ContentValues contentValues = new ContentValues (  );
        contentValues.put ( DatabaseConstant.COLUMN_PROJECT_NAME,textInputEditTextProjectName.getText ().toString () );
//        contentValues.put (DatabaseConstant.COLUMN_PROJECT_CREATED_AT,);
        if (sqlHelper.insertData ( DatabaseConstant.TABLE_PROJECT,contentValues ) >0){
            Intent accountsIntent = new Intent ( CreateProjectActivity.this,  AvailableProject.class );
            emptyInputEditText ();
            startActivity ( accountsIntent );
            Toast.makeText ( CreateProjectActivity.this, "Create Project Successful", Toast.LENGTH_SHORT ).show ();


        } else {
            Toast.makeText ( CreateProjectActivity.this, "Project Already Exists", Toast.LENGTH_SHORT ).show ();
            project.setProjectNname ( textInputEditTextProjectName.getText ().toString ().trim () );
        }

    }

    private void emptyInputEditText() {
        textInputEditTextProjectName.setText ( null );
    }
}
