package com.example.tripitadmin;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewGuidesActivity extends AppCompatActivity {

    private String  CategoryGuideName, GuideName ,GcontactNumber, Gage, Gexperience , Gamount, saveGuideCurrentDate, saveGuideCurrentTime;
    private Button  AddNewGuideButton;
    private ImageView InputGuideImage;
    private EditText InputGuideName, InputGuideConNumber, InputGuideAge, InputExperience ,InputAmount;
    private static final int GalleryGuidePick = 1 ;
    private Uri ImageGuideUri;
    private String GuidesRandomKey , downloadGuideImageURL;
    private StorageReference GuidesImagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_guides);


        CategoryGuideName= getIntent().getExtras().get("categoryGuide").toString();
        GuidesImagesRef = FirebaseStorage.getInstance().getReference().child("Guide Images");

        AddNewGuideButton = (Button) findViewById(R.id.add_new_guide);
        InputGuideImage   = (ImageView) findViewById(R.id.select_guide_image);
        InputGuideName    = (EditText) findViewById(R.id.guide_name);
        InputGuideConNumber=(EditText) findViewById(R.id.guide_con_number);
        InputGuideAge = (EditText) findViewById(R.id.guide_age);
        InputExperience = (EditText) findViewById(R.id.guide_experiance);
        InputAmount = (EditText) findViewById(R.id.guide_amount);


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
        Gamount = InputAmount.getText().toString();

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
        else if (TextUtils.isEmpty(Gamount))
        {
            Toast.makeText(this,"Please enter amount per-day",Toast.LENGTH_SHORT).show();

        }
        else{

            StoreGuideInformation();

        }

    }

    private void StoreGuideInformation() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveGuideCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTim = new SimpleDateFormat("HH:mm:ss a");
        saveGuideCurrentTime = currentDate.format(calendar.getTime());


        GuidesRandomKey = saveGuideCurrentDate + saveGuideCurrentTime;


        final StorageReference filePath = GuidesImagesRef.child(ImageGuideUri.getLastPathSegment() + GuidesRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageGuideUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {

                String massage = e.toString();
                Toast.makeText(AdminAddNewGuidesActivity.this,"Error: " + massage, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewGuidesActivity.this,"Guide's Image Uploadd Succssfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {

                            throw task.getException();

                        }

                            downloadGuideImageURL = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(AdminAddNewGuidesActivity.this,"got guide's Image Url Successfully..", Toast.LENGTH_SHORT).show();

                            SaveGuideInforToDatabase();
                        }

                    }
                });

            }
        });


    }

    private void SaveGuideInforToDatabase() {

        HashMap<String, Object> guideMap = new HashMap<>();
        guideMap.put("gid",GuidesRandomKey);
        guideMap.put("guidedate",saveGuideCurrentDate);
        guideMap.put("guidetime",saveGuideCurrentTime);
        guideMap.put("guidename",GuideName);
        guideMap.put("guideimage",downloadGuideImageURL);
        guideMap.put("guidecategory",CategoryGuideName);
        guideMap.put("guidecontactnum",GcontactNumber);
        guideMap.put("guideage",Gage);
        guideMap.put("guideexperiance",Gexperience);
        guideMap.put("guideamount",Gamount);

    }


}