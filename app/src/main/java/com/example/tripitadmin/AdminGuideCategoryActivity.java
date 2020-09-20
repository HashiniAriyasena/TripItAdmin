package com.example.tripitadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminGuideCategoryActivity extends AppCompatActivity {

    private ImageView withVehicals , withOutVehicals;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_guide_category);

        withVehicals = (ImageView) findViewById(R.id.withVehicals);
        withOutVehicals = (ImageView) findViewById(R.id.withOutVehicals);

        withVehicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminGuideCategoryActivity.this, AdminAddNewGuidesActivity.class);
                intent.putExtra("categoryGuide","withVehicals");
                startActivity(intent);


            }
        });

        withOutVehicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminGuideCategoryActivity.this, AdminAddNewGuidesActivity.class);
                intent.putExtra("categoryGuide","withOutVehicals");
                startActivity(intent);

            }
        });

    }
}