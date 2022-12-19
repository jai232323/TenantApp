package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.housing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class EditBuildingsActivity extends AppCompatActivity {


    ImageView BuildingImage;
    EditText B_Name,B_Address,B_PostalCode;
    Button Edit,Delete;


    //Name getting Variables
    String b_ID,b_Name,b_Image,b_address,b_postalcode;


    //Edit String Variables
    String bb_ID,bb_Name,bb_Image,bb_address,bb_postalcode,downloadUrl="";


    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;

    ProgressDialog pd;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings");


    String U_MobileNumber,B_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buildings);

        pd=new ProgressDialog(this);

        b_ID = getIntent().getStringExtra("B_ID");
        b_Name = getIntent().getStringExtra("B_Name");
        b_Image = getIntent().getStringExtra("B_Image");
        b_address = getIntent().getStringExtra("B_Address");
        b_postalcode = getIntent().getStringExtra("B_PostalCode");

        SharedPreferences prefs3 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs3.getString("U_MobileNumber","none");


        SharedPreferences prefs4 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        B_ID=prefs4.getString("B_ID","none");

        B_Name = findViewById(R.id.B_Name);
        B_Address = findViewById(R.id.B_Address);
        B_PostalCode = findViewById(R.id.B_PostalCode);
        BuildingImage = findViewById(R.id.BuildingImage);
        Edit = findViewById(R.id.Edit);
        Delete = findViewById(R.id.Delete);



        B_Name.setText(b_Name);
        B_Address.setText(b_address);
        B_PostalCode.setText(b_postalcode);

        Glide.with(EditBuildingsActivity.this).load(b_Image).into(BuildingImage);

        storageReference= FirebaseStorage.getInstance().getReference();

        //Click Image
        BuildingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bb_Name = B_Name.getText().toString();
                bb_address = B_Address.getText().toString();
                bb_postalcode = B_PostalCode.getText().toString();

                checkValidations();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditBuildingsActivity.this);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                DatabaseReference referenceDelete = FirebaseDatabase.getInstance().getReference("Buildings");

                                referenceDelete.child(U_MobileNumber).child(B_ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EditBuildingsActivity.this, "Building Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditBuildingsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });

                             //   Toast.makeText(EditBuildingsActivity.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void checkValidations() {
        if (bitmap == null) {
            UploadBuildingData(bb_Image);
        }else {
            UploadImage();
        }
    }

    private void UploadBuildingData(String b_image) {

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("B_ID",B_ID);
        hashMap.put("B_Name",bb_Name);
        hashMap.put("B_Address",bb_address);
        hashMap.put("B_PostalCode",bb_postalcode);
//        hashMap.put("B_Image",""+downloadUrl);


        reference.child(U_MobileNumber).child(B_ID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(EditBuildingsActivity.this, "Building Edited Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditBuildingsActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditBuildingsActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadImage() {

        pd.setMessage("wait few seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Buildings").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(EditBuildingsActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    UploadBuildingData(downloadUrl);

                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                            child("Buildings").child(U_MobileNumber).child(B_ID);


                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("B_Image",""+downloadUrl);
                                    reference1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pd.dismiss();
                                            finish();
                                        }
                                    });
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            pd.dismiss();
//                                            Toast.makeText(EditBuildingsActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });

                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(EditBuildingsActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
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
        }
    }
}