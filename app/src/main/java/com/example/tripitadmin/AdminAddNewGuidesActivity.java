package com.example.tripitadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminAddNewGuidesActivity extends AppCompatActivity {

    private String  CategoryGuideName;
    private Button  AddNewGuideButton;

    private EditText InputGuideName, InputGuideConNumber, InputGuideAge, InputExperience;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_guides);


        CategoryGuideName= getIntent().getExtras().get("categoryGuide").toString();




    }
}