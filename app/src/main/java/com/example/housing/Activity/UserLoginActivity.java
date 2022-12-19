package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.housing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class UserLoginActivity extends AppCompatActivity {



    FirebaseUser firebaseUser;
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users");
//
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (!snapshot.exists()){
//                    startActivity(new Intent(UserLoginActivity.this,MainActivity.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//
//        reference1.addListenerForSingleValueEvent(eventListener);
//
//    }

     EditText U_MobileNumber,U_Password;
     Button LoginBtn;
    LinearLayout NewUserRegisterBtn;

    FirebaseAuth auth;
    String U_Id;


    DatabaseReference reference = FirebaseDatabase.getInstance().
            getReferenceFromUrl("https://housing-5d345-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        U_MobileNumber=findViewById(R.id.U_MobileNumber);
        U_Password=findViewById(R.id.U_Password);
        LoginBtn=findViewById(R.id.LoginBtn);
        NewUserRegisterBtn=findViewById(R.id.NewUserRegisterBtn);

        auth=FirebaseAuth.getInstance();


        SharedPreferences prefs1 = getSharedPreferences("PREFS", MODE_PRIVATE);
        U_Id=prefs1.getString("U_Id","none");


        NewUserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserLoginActivity.this,UserRegisterActivity.class));
                finish();


            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(UserLoginActivity.this);
                pd.setMessage("Wait a seconds...");
                pd.show();

                final String str_mobile=U_MobileNumber.getText().toString();
                final String str_password = U_Password.getText().toString();

//                if (TextUtils.isEmpty(str_mobile) || TextUtils.isEmpty(str_password)){
//                    Toast.makeText(UserLoginActivity.this,"All Fields are Required",Toast.LENGTH_SHORT).show();
//                }else {
//
//
//
//                }
                reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.hasChild(str_mobile)){
                            final String password = snapshot.child(str_mobile).
                                    child("U_Password").getValue(String.class);

                            if (password.equals(str_password)){
                                pd.dismiss();
                                Toast.makeText(UserLoginActivity.this,
                                        "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserLoginActivity.this,MainActivity.class));

                                Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);



                                SharedPreferences.Editor edito2 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                                edito2.putString("U_MobileNumber", str_mobile);
                                edito2.apply();

                                startActivity(intent);
                                finish();

                            }else {
                                pd.dismiss();
                                Toast.makeText(UserLoginActivity.this,
                                        "Wrong Password", Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            pd.dismiss();
                            Toast.makeText(UserLoginActivity.this,
                                    "Wrong Mobile Number", Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
//                .child(U_Id);
//
//        if (reference!=null){
//            startActivity(new Intent(UserLoginActivity.this,MainActivity.class));
//            finish();
//        }
//    }
}