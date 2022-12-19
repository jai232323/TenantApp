package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.example.housing.Fragments.UserProfileFragment;
import com.example.housing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {


    ImageView UserImage;
    EditText User_Name;
    Button Submit;

    Bitmap bitmap;
    private final int REQ = 1;

    DatabaseReference reference;
    StorageReference storageReference;


    ProgressDialog pd;
    String downloadUrl="",u_name;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (firebaseUser != null) {
//            startActivity(new Intent(getBaseContext(), MainActivity.class));
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        UserImage = findViewById(R.id.UserImage);
        User_Name = findViewById(R.id.User_Name);
        Submit = findViewById(R.id.Submit);



        pd=new ProgressDialog(this);

        //Assign Variables
        reference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference= FirebaseStorage.getInstance().getReference();

        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                u_name = User_Name.getText().toString();

//                if (bitmap==null){
//                    Toast.makeText(UserProfileActivity.this, "Set Your Image", Toast.LENGTH_SHORT).show();
//                }else if (u_name.isEmpty()){
//                    User_Name.setError("Fill Your Name");
//                    User_Name.requestFocus();
//                }else {
//                    UploadImage();
//                }

                UploadImage();
            }
        });

    }

    private void UploadImage() {

        pd.setMessage("Wait a Seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Users").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UserProfileActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    UploadUserData(u_name,downloadUrl);
                                    pd.dismiss();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UserProfileActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void UploadUserData(String u_name, String downloadUrl) {


        Intent intent = new Intent(UserProfileActivity.this,PhoneAuthenticationActivity.class);

        intent.putExtra("U_Name",u_name);
        intent.putExtra("U_Image",downloadUrl);

//        SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
//        editor.putString("U_ID",U_ID);
//        editor.apply();

        startActivity(intent);
        finish();

//        String U_ID = reference.push().getKey();
//
//        HashMap<String,Object> hashMap = new HashMap<>();
//
//        hashMap.put("U_ID",U_ID);
//        hashMap.put("U_Name",u_name);
//        hashMap.put("U_Image",downloadUrl);
//
//        reference.child(U_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                pd.dismiss();
//
//                Intent intent = new Intent(UserProfileActivity.this,PhoneAuthenticationActivity.class);
//                intent.putExtra("U_Name",u_name);
//                intent.putExtra("U_Image",downloadUrl);
//
//                SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
//                editor.putString("U_ID",U_ID);
//                editor.apply();
//
//                startActivity(intent);
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(UserProfileActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
//            }
//        });


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
            UserImage.setImageBitmap(bitmap);
        }
    }
}