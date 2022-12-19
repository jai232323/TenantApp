package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.housing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateTenantActivity extends AppCompatActivity {


    //Initialize Variables
    //Tenant
    EditText T_Name,T_MobileNummber,Members;
    Button T_AddMembers;
    String t_name,t_mobilenumber,t_Members;



    //AddMembers
    LinearLayout LL_AddMembers;
    EditText Member_Name,Member_Address,Member_MobileNumber;
    Button Tenant_Submit;
    String m_name,m_address,m_mobilenumber;

    String T_ID;

    //Initialize Database
    DatabaseReference reference;

    String U_MobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tenant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Tenants");
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

        //Assign Values to database
        reference = FirebaseDatabase.getInstance().getReference("Tenants");

        //Assign Values
        T_Name = findViewById(R.id.T_Name);
        T_MobileNummber = findViewById(R.id.T_MobileNummber);
        T_AddMembers = findViewById(R.id.T_AddMembers);

        Members = findViewById(R.id.Members);

        LL_AddMembers = findViewById(R.id.LL_AddMembers);
        Member_Name = findViewById(R.id.Member_Name);
        Member_Address = findViewById(R.id.Member_Address);
        Member_MobileNumber = findViewById(R.id.Member_MobileNumber);
        Tenant_Submit = findViewById(R.id.Tenant_Submit);

        T_AddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_name = T_Name.getText().toString();
                t_mobilenumber = T_MobileNummber.getText().toString();
                t_Members = Members.getText().toString();

//                //CheckValidations
//                //Tenant Name
//                if (t_name.isEmpty()){
//                    T_Name.setError("Required Tenant Name");
//                    T_Name.requestFocus();
//                }else if (t_name.length()<3){
//                    T_Name.setError("Tenant Name too short");
//                    T_Name.requestFocus();
//                }else if (t_name.length()>120){
//                    T_Name.setError("Tenant Name too long");
//                    T_Name.requestFocus();
//                }
//
//                //Tenant MobileNumber
//                else if (t_mobilenumber.isEmpty()){
//                    T_MobileNummber.setError("Required Tenant MobileNumber");
//                    T_MobileNummber.requestFocus();
//                }else if (t_mobilenumber.length()<10){
//                    T_MobileNummber.setError("Required 10Numbers Only");
//                    T_MobileNummber.requestFocus();
//                }else if (t_mobilenumber.length()>10){
//                    T_MobileNummber.setError("Required 10Numbers Only");
//                    T_MobileNummber.requestFocus();
//                }
//
//                //UploadData
//                else {
//                    UploadTenantData(t_name,t_mobilenumber);
//                }

                UploadTenantData(t_name,t_mobilenumber);
            }
        });
    }
    private void UploadTenantData(String t_name,String t_mobilenumber) {

        T_ID = reference.push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("T_ID",T_ID);
        hashMap.put("T_Name",t_name);
        hashMap.put("T_MobileNumber",t_mobilenumber);
        hashMap.put("U_MobileNumber",U_MobileNumber);

        reference.child(U_MobileNumber).child(T_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                T_AddMembers.setText("SAVED");


                SharedPreferences.Editor edito2 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                edito2.putString("U_MobileNumber", U_MobileNumber);
                edito2.apply();

                Toast.makeText(CreateTenantActivity.this, "SAVED\nADD MEMBERS", Toast.LENGTH_LONG).show();


                SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                editor.putString("T_ID",T_ID);
                editor.apply();

                LL_AddMembers.setVisibility(View.VISIBLE);

                Tenant_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GotoAddMembers();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateTenantActivity.this, "Something Issue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GotoAddMembers() {

        m_name = Member_Name.getText().toString();
        m_address = Member_Address.getText().toString();
        m_mobilenumber = Member_MobileNumber.getText().toString();


        //Check Validations
        //Name
//        if (m_name.isEmpty()){
//            Member_Name.setError("Required Name");
//            Member_Name.requestFocus();
//        }else if (m_name.length()<3){
//            Member_Name.setError("Name too short");
//            Member_Name.requestFocus();
//        }else if (m_name.length()>120){
//            Member_Name.setError("Name too long");
//            Member_Name.requestFocus();
//        }
//        //Address
//        else if (m_address.isEmpty()){
//            Member_Address.setError("Required Address");
//            Member_Address.requestFocus();
//        }
//        //MobileNumber
//        else if (m_mobilenumber.isEmpty()){
//            Member_MobileNumber.setError("Required MobileNumber");
//            Member_MobileNumber.requestFocus();
//        }else if (m_mobilenumber.length()<10){
//            Member_MobileNumber.setError("Required 10Numbers Only");
//            Member_MobileNumber.requestFocus();
//        }else if (m_mobilenumber.length()>10){
//            Member_MobileNumber.setError("Required 10Numbers Only");
//            Member_MobileNumber.requestFocus();
//        }

        //UploadMembersData
        UploadMembersData(m_name,m_address,m_mobilenumber);

    }

    private void UploadMembersData(String m_name, String m_address, String m_mobilenumber) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TenantMembers");
        String Member_ID = reference.push().getKey();


        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String T_ID1=prefs1.getString("T_ID","none");

        HashMap <String,Object> hashMap = new HashMap<>();
        hashMap.put("Member_ID",Member_ID);
        hashMap.put("Member_Name",m_name);
        hashMap.put("Member_Address",m_address);
        hashMap.put("Member_MobileNumber",m_mobilenumber);
        hashMap.put("T_Name",t_name);
        hashMap.put("T_ID",T_ID1);
        hashMap.put("U_MobileNumber",U_MobileNumber);


        reference.child(U_MobileNumber).child(T_ID).child(Member_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateTenantActivity.this, m_name+"\nAdded Successfully", Toast.LENGTH_SHORT).show();


                SharedPreferences.Editor edito2 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                edito2.putString("U_MobileNumber", U_MobileNumber);
                edito2.apply();

                Member_Name.setText("");
                Member_Address.setText("");
                Member_MobileNumber.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateTenantActivity.this, "Something Issues", Toast.LENGTH_SHORT).show();
            }
        });


    }
}