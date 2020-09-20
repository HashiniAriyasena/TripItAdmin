package com.example.tripitadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewGuidesActivity extends AppCompatActivity {

    private String  CategoryGuideName, GuideName ,GcontactNumber, Gage, Gexperience;
    private Button  AddNewGuideButton;
    private ImageView InputGuideImage;
    private EditText InputGuideName, InputGuideConNumber, InputGuideAge, InputExperience;
    private static final int GalleryGuidePick = 1 ;
    private Uri ImageGuideUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_guides);


        CategoryGuideName= getIntent().getExtras().get("categoryGuide").toString();

        AddNewGuideButton = (Button) findViewById(R.id.add_new_guide);
        InputGuideImage   = (ImageView) findViewById(R.id.select_guide_image);
        InputGuideName    = (EditText) findViewById(R.id.guide_name);
        InputGuideConNumber=(EditText) findViewById(R.id.guide_con_number);
        InputGuideAge = (EditText) findViewById(R.id.guide_age);
        InputExperience = (EditText) findViewById(R.id.guide_experiance);

        InputGuideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();

            }
        });

        AddNewGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateGuideData();

            }
        });


    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryGuidePick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryGuidePick && requestCode==RESULT_OK  &&  data!=null)
        {

            ImageGuideUri = data.getData();
            InputGuideImage.setImageURI(ImageGuideUri);

        }

    }


    private void ValidateGuideData()
    {

        GuideName = InputGuideName.getText().toString();
        GcontactNumber = InputGuideConNumber.getText().toString();
        Gage = InputGuideAge.getText().toString();
        Gexperience = InputExperience.getText().toString();

        if (ImageGuideUri == null)
        {
            Toast.makeText(this,"Guide image is mendatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(GuideName))
        {
            Toast.makeText(this,"Please enter guide's name...",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(GcontactNumber))
        {
            Toast.makeText(this,"Please enter guide's telephone number name...",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(Gage))
        {
            Toast.makeText(this,"Please enter guide's age...",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(Gexperience))
        {
            Toast.makeText(this,"Please enter guide's experiance...",Toast.LENGTH_SHORT).show();

        }
        else{

            StoreGuideInformation();

        }

    }

    private void StoreGuideInformation() {
    }


}