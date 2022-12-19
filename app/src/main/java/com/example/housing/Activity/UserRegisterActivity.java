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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.housing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UserRegisterActivity extends AppCompatActivity {


    private ImageView U_Image;
    private EditText U_Name,U_MobileNumber,U_Password;
    private Button RegisterBtn;
    private LinearLayout AlreadyUser;

    private FirebaseAuth auth;
    private StorageReference storageReference;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://housing-5d345-default-rtdb.firebaseio.com/");
    private ProgressDialog pd;

    String category;
    private final int REQ = 1;
    private Bitmap bitmap;
    private String Name,MobileNumber,Password,downloadUrl = "img";


    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);


   //     U_Image = findViewById(R.id.U_Image);
        U_Name = findViewById(R.id.U_Name);
        U_MobileNumber = findViewById(R.id.U_MobileNumber);
        U_Password = findViewById(R.id.U_Password);
        RegisterBtn = findViewById(R.id.RegisterBtn);
        AlreadyUser = findViewById(R.id.AlreadyUser);

        pd = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        storageReference = FirebaseStorage.getInstance().getReference();

//        U_Image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGallery();
////                if (RegisterStudentImage==null){
////                    Toast.makeText(StudentRegisterActivity.this,"please set yout image",Toast.LENGTH_SHORT).show();
////                }
//
//            }
//        });



        AlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegisterActivity.this,UserLoginActivity.class));
                finish();
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

//    private void openGallery() {
//        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(picImage,REQ);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQ && resultCode == RESULT_OK) {
//
//            Uri uri = data.getData();
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            U_Image.setImageBitmap(bitmap);
//        }
//    }

    private void checkValidation() {
        Name =U_Name.getText().toString();
        MobileNumber =U_MobileNumber.getText().toString();
        Password =U_Password.getText().toString();


        insertDate(Name,MobileNumber,Password);

//        if (bitmap==null){
//            Toast.makeText(UserRegisterActivity.this,"Set your Image",Toast.LENGTH_SHORT).show();
//        }
//        else  if (Name.isEmpty()){
//            U_Name.setError("Name Empty");
//            U_Name.requestFocus();
//        }else  if (MobileNumber.isEmpty()){
//            U_MobileNumber.setError("Email Empty");
//            U_MobileNumber.requestFocus();
//        }else if (MobileNumber.length()<10){
//            U_MobileNumber.setError("10 Numbers Only");
//            U_MobileNumber.requestFocus();
//        }else if (MobileNumber.length()>10){
//            U_MobileNumber.setError("10 Numbers Only");
//            U_MobileNumber.requestFocus();
//        }
//
//        //password
//        else   if (Password.isEmpty()){
//            U_Password.setError("Password Empty");
//            U_Password.requestFocus();
//        }  else if (Password.length() < 6 )
//        {
//            U_Password.setError("Password 6 Numbers Only");
//            U_Password.requestFocus();
//        }else if (U_Image==null){
//            Toast.makeText(UserRegisterActivity.this,"Please Set Your Image" , Toast.LENGTH_SHORT).show();
//        }
//        else{
//            uploadImage();
//        }

       // uploadImage();
    }

//    private void uploadImage() {
//        pd.setMessage("Wait a Seconds...");
//        pd.show();
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
//        byte[] finalimg = baos.toByteArray();
//
//        final StorageReference filePath;
//        filePath=storageReference.child("Users").child(finalimg+"jpg");
//        final UploadTask uploadTask = filePath.putBytes(finalimg);
//        uploadTask.addOnCompleteListener(UserRegisterActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()){
//                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    downloadUrl = String.valueOf(uri);
//                                  //  insertDate(Name,MobileNumber,Password);
//                                }
//                            });
//                        }
//                    });
//                }else {
//                    pd.dismiss();
//                    Toast.makeText(UserRegisterActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void insertDate(String name, String MobileNumber, String password) {


        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(MobileNumber)) {
                    Toast.makeText(UserRegisterActivity.this,
                            "Mobile Number Already Registered", Toast.LENGTH_SHORT).show();
                } else {
                    reference = FirebaseDatabase.getInstance().getReference().child("Users");

                    String userid = reference.push().getKey().toString();

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("U_Id", userid);
                    hashMap.put("U_Name", name);
                    hashMap.put("U_MobileNumber", MobileNumber);
                    hashMap.put("U_Password", password);

                    reference.child(MobileNumber).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            pd.dismiss();
                            Intent intent = new Intent(UserRegisterActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("U_MobileNumber", MobileNumber);
                            editor.apply();

                            startActivity(intent);

                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UserRegisterActivity.this, "You can't register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}