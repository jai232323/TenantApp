package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.housing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class CreateBuildingActivity extends AppCompatActivity {



    //Initialize Variables
    EditText B_Name,B_Address,B_PostalCode;
    Button Building_Submit;
    LinearLayout Next;

    String downloadUrl,b_name,b_address,b_postalcode;

    DatabaseReference reference;

    MaterialCardView MC_BuildingImage;
    ImageView BuildingImage;
    TextView BuildingText;

    Bitmap bitmap=null;
    private final int REQ = 1;
    StorageReference storageReference;

    ProgressDialog pd;

    String U_MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Buildings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        SharedPreferences prefs1 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs1.getString("U_MobileNumber","none");

        pd=new ProgressDialog(this);

        //Assign Variables
        reference = FirebaseDatabase.getInstance().getReference("Buildings");
        storageReference= FirebaseStorage.getInstance().getReference();

        B_Name = findViewById(R.id.B_Name);
        B_Address = findViewById(R.id.B_Address);
        B_PostalCode = findViewById(R.id.B_PostalCode);
        Building_Submit = findViewById(R.id.Building_Submit);
        Next = findViewById(R.id.Next);

        MC_BuildingImage = findViewById(R.id.MC_BuildingImage);
        BuildingImage = findViewById(R.id.BuildingImage);
        BuildingText = findViewById(R.id.BuildingText);


        MC_BuildingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateBuildingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Building_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_name = B_Name.getText().toString();
                b_address = B_Address.getText().toString();
                b_postalcode = B_PostalCode.getText().toString();


//                if (bitmap==null){
//                    Toast.makeText(CreateBuildingActivity.this, "Upload Building Image", Toast.LENGTH_SHORT).show();
//                }
//                //Building Name
//                if (b_name.isEmpty()){
//                    B_Name.setError("Required Building Name");
//                    B_Name.requestFocus();
//                }else if (b_name.length()<3){
//                    B_Name.setError("Building Name too short");
//                    B_Name.requestFocus();
//                }else if (b_name.length()>100){
//                    B_Name.setError("Building Name too long");
//                    B_Name.requestFocus();
//                }
//                //Building Address
//                else if (b_address.isEmpty()){
//                    B_Address.setError("Building Address Empty");
//                    B_Address.requestFocus();
//                }
//                //Building Postal Code
//                else if (b_postalcode.isEmpty()){
//                    B_PostalCode.setError("Building PostalCode Empty");
//                    B_PostalCode.requestFocus();
//                }
//                //UploadBuildingData
//                else {
//                    UploadBuildingData(b_name,b_address,b_postalcode);
//                }


                if (bitmap==null){
                   // UploadImage();
                    Toast.makeText(CreateBuildingActivity.this, "Set Building Image", Toast.LENGTH_SHORT).show();
                }else {
                    UploadImage();
                }

            }
        });


    }

    private void UploadImage() {

        pd.setMessage("Please Wait");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Buildings").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(CreateBuildingActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    //insertData(email, password);
                                    UploadBuildingData(b_name,b_address,b_postalcode);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(CreateBuildingActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
//        registerForActivityResult(picImage,REQ);
    }

    private void UploadBuildingData(String b_name, String b_address, String b_postalcode) {

        String B_ID = reference.push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("B_ID",B_ID);
        hashMap.put("B_Name",b_name);
        hashMap.put("B_Address",b_address);
        hashMap.put("B_PostalCode",b_postalcode);
        hashMap.put("B_Image",downloadUrl);
        hashMap.put("U_MobileNumber",U_MobileNumber);


        reference.child(U_MobileNumber).child(B_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(CreateBuildingActivity.this, "Building Added Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateBuildingActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                editor.putString("B_ID",B_ID);
                editor.apply();

                SharedPreferences.Editor editor1 = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                editor1.putString("B_Name",b_name);
                editor1.apply();

                SharedPreferences.Editor edito2 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                edito2.putString("U_MobileNumber", U_MobileNumber);
                edito2.apply();


                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateBuildingActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {

            Uri uri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BuildingImage.setImageBitmap(bitmap);
            BuildingText.setVisibility(View.INVISIBLE);
        }
    }
}