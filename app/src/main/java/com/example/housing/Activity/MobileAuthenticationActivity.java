package com.example.housing.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.housing.R;

public class MobileAuthenticationActivity extends AppCompatActivity {



    EditText MobileNumber,Password;
    Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);

        MobileNumber = findViewById(R.id.MobileNumber);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = MobileNumber.getText().toString();
                String password = Password.getText().toString();


                if (mobile.isEmpty()){
                    MobileNumber.setError("MobileNumber is Empty");
                    MobileNumber.requestFocus();
                }else if (mobile.length()<10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }else if (mobile.length()>10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }else if (password.isEmpty()){
                    Password.setError("Password is Empty");
                    Password.requestFocus();
                }else if (mobile.equals("1234567890") && password.equals("123456")){
                    Intent intent = new Intent(MobileAuthenticationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    MobileNumber.setError("Something Error");
                    MobileNumber.requestFocus();
                    Password.setError("Something Error");
                    Password.requestFocus();
                }
            }
        });


    }
}