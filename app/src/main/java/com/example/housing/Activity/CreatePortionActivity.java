package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.housing.Fragments.PortionFragment;
import com.example.housing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CreatePortionActivity extends AppCompatActivity {


    //Initialize Variables
    EditText P_Name,P_Floor,P_EBCard;
    Button AddPortion;

    //Spinnner
    Spinner BuildingSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();

    String p_name,p_floor,p_ebcard,building1;
    String B_Name,B_ID;
    String B_Name1;
    DatabaseReference reference;
    DatabaseReference SpinnerReference = FirebaseDatabase.getInstance().getReference();

    LinearLayout coordinatorLayout;

    String Spinner1,U_MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_portion);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Portion");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SharedPreferences prefs3 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        B_ID=prefs3.getString("B_ID","null");

        SharedPreferences prefs2 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs2.getString("U_MobileNumber","none");

        //Assign Variables
        P_Name =findViewById(R.id.P_Name);
        P_Floor =findViewById(R.id.P_Floor);
        P_EBCard =findViewById(R.id.P_EBCard);

        BuildingSpinner = findViewById(R.id.Building);

        AddPortion =findViewById(R.id.AddPortion);

        reference = FirebaseDatabase.getInstance().getReference("Portions");

        //Spinner
        //getValueFromDatabaseForSpinner();
        SpinnerReference.child("Buildings").child(U_MobileNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                arrayList.add("Select Buildings");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    arrayList.add(items.child("B_Name").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreatePortionActivity.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayList);
                BuildingSpinner.setAdapter(arrayAdapter);
                Spinner1 = BuildingSpinner.getSelectedItem().toString();


                Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreatePortionActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });

//        SharedPreferences prefs1 = getSharedPreferences("PREFS", MODE_PRIVATE);
//        B_Name=prefs1.getString("B_Name","none");
//
//        SharedPreferences prefs2 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
//        B_ID=prefs2.getString("B_ID","none");
//
//
//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Buildings").child(B_ID);
//
//        reference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    SharedPreferences prefs1 = getSharedPreferences("PREFS", MODE_PRIVATE);
//                    B_Name=prefs1.getString("B_Name","none");
//
//                    Toast.makeText(CreatePortionActivity.this, B_Name, Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        String[] itemsGender1 = new String[]{"Select Building",B_Name1};
//        Building.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsGender1));
//
////        Building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                building1 = Building.getSelectedItem().toString();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });

        AddPortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p_name = P_Name.getText().toString();
                p_floor = P_Floor.getText().toString();
                p_ebcard = P_EBCard.getText().toString();


//                if (Spinner1.equals("Select Buildings")){
//
////                    Snackbar snackbar = Snackbar
////                            .make(coordinatorLayout, "Select Buildings", Snackbar.LENGTH_LONG);
////                    snackbar.show();
//                   Toast.makeText(CreatePortionActivity.this, "Select Buildings", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    UploadData(p_name,p_floor,p_ebcard);
//                }
                UploadData(p_name,p_floor,p_ebcard);
//                else if (BuildingSpinner.equals("Create Buildings")){
//                    startActivity(new Intent(CreatePortionActivity.this,CreateBuildingActivity.class));
//                }

//                //Check Validations
//                //Portion Name
//                if (p_name.isEmpty()){
//                    P_Name.setError("Required Portion Name");
//                    P_Name.requestFocus();
//                }else if (p_name.length()<3){
//                    P_Name.setError("Portion Name too short");
//                    P_Name.requestFocus();
//                }else if (p_name.length()>120){
//                    P_Name.setError("Portion Name too long");
//                    P_Name.requestFocus();
//                }
//
//                //Portion Floor
//                else if (p_floor.isEmpty()){
//                    P_Floor.setError("Required Portion Floor");
//                    P_Floor.requestFocus();
//                }
//
//                //Portion EBCard
//                else if (p_ebcard.isEmpty()){
//                    P_EBCard.setError("Required Portion EBCard");
//                    P_EBCard.requestFocus();
//                }

                //UploadToFirebaseDatabase
//                else {
//
//                }


            }
        });
    }

//    private void getValueFromDatabaseForSpinner() {
//
//        //        String[] itemsGender1 = new String[]{"Select Building",B_Name1};
////        Building.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsGender1));
////
//////        Building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//////            @Override
//////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//////                building1 = Building.getSelectedItem().toString();
//////            }
//////
//////            @Override
//////            public void onNothingSelected(AdapterView<?> parent) {
//////
//////            }
//////        });
//
//        SpinnerReference.child("Buildings").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arrayList.clear();
//                for (DataSnapshot items : snapshot.getChildren()){
//                    arrayList.add(items.child("B_Name").getValue(String.class));
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreatePortionActivity.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayList);
//                BuildingSpinner.setAdapter(arrayAdapter);
//                String Spinner = BuildingSpinner.getSelectedItem().toString();
//                Toast.makeText(CreatePortionActivity.this, Spinner, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(CreatePortionActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        //EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//      //  EventBus.getDefault().unregister(this);
//    }

    private void UploadData(String p_name, String p_floor, String p_ebcard) {

        String P_ID = reference.push().getKey();

        HashMap<String , Object> hashMap = new HashMap<>();
      //  hashMap.put("B_Name",BuildingSpinner);
        hashMap.put("P_ID",P_ID);
        hashMap.put("P_Name",p_name);
        hashMap.put("P_Floor",p_floor);
        hashMap.put("P_EBCard",p_ebcard);
        hashMap.put("B_Name",BuildingSpinner.getSelectedItem().toString());
        hashMap.put("B_ID",B_ID);
        hashMap.put("U_MobileNumber",U_MobileNumber);


    //    Log.i("s",hashMap+"");

        reference.child(U_MobileNumber).child(P_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(CreatePortionActivity.this, "Portion Added Successfully", Toast.LENGTH_SHORT).show();

           //     EventBus.getDefault().post(new MessageEvent());

                SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                editor.putString("P_ID",P_ID);
                editor.apply();


                SharedPreferences.Editor edito2 = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                edito2.putString("U_MobileNumber", U_MobileNumber);
                edito2.apply();


                finish();

                // Update Push
              // GoToPortionFragment(P_ID);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreatePortionActivity.this, "Something issue", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GoToPortionFragment(String p_id) {
        Intent intent = new Intent(getBaseContext(), PortionFragment.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
        editor.putString("P_ID",p_id);
        editor.apply();

        startActivity(intent);

        finish();
    }
}