package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.housing.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class EditRentActivity extends AppCompatActivity {


     String Rent_id,Rent_Amount,Deposit_Amount,Maintenace_Charge,Building_Name,Portion_Name,
            Tenant_Name,Start_Date,End_Date,Due_Date,Document,Rental_Type;


    //Initialize Widgets Variables


    Button StartDate;
    Button EndDate;
    Button Submit;
    EditText RentAmount,DepositAmount,MaintenanceCharges;
    Spinner BuildingName,PortionName,TenantName,RentType,due_date;

    MaterialCardView MC_RentDocuImage;
    ImageView DocuImage;
    TextView DocText;


    //Initialize Database Variables
    DatabaseReference referenceRent = FirebaseDatabase.getInstance().getReference("Rent");
    DatabaseReference referenceDue = FirebaseDatabase.getInstance().getReference("Due");
    DatabaseReference referenceBuilding = FirebaseDatabase.getInstance().getReference();
    DatabaseReference referencePortion = FirebaseDatabase.getInstance().getReference();
    DatabaseReference referenceTenant = FirebaseDatabase.getInstance().getReference();

    ArrayList<String> arrayListBuilding = new ArrayList<>();
    ArrayList<String> arrayListPortion = new ArrayList<>();
    ArrayList<String> arrayListTenant = new ArrayList<>();

    //Initialize Predefined Variables
    DatePickerDialog.OnDateSetListener onDateSetListener1;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    DatePickerDialog.OnDateSetListener onDateSetListener3;



    String Portion_id,Tenant_id;
    Date start_date,end_date;
    String downloadUrl="",portion_name,tenant_name;
    Double rent_amount,deposita_amount,maintenace_charege,rent_type;

    Bitmap bitmap;
    private final int REQ = 1;

    ProgressDialog pd;


    Uri imageuri = null;
    String myurl;

    String U_MobileNumber;

     String messagePushID;

    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rent);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SharedPreferences prefs3 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs3.getString("U_MobileNumber","none");

        SharedPreferences prefs1 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Rent_id=prefs1.getString("Rent_id","none");

        //Assign Vaibles
        due_date = findViewById(R.id.due_date);
        StartDate = findViewById(R.id.StartDate);
        EndDate = findViewById(R.id.EndDate);
        Submit = findViewById(R.id.Submit);
        RentAmount = findViewById(R.id.RentAmount);
        DepositAmount = findViewById(R.id.DepositAmount);
        MaintenanceCharges = findViewById(R.id.MaintenanceCharges);
        BuildingName = findViewById(R.id.BuildingName);
        PortionName = findViewById(R.id.PortionName);
        TenantName = findViewById(R.id.TenantName);
        RentType = findViewById(R.id.RentType);
        MC_RentDocuImage = findViewById(R.id.MC_RentDocuImage);
        DocuImage = findViewById(R.id.DocuImage);
        DocText = findViewById(R.id.DocText);

        pd = new ProgressDialog(this);
        storageReference= FirebaseStorage.getInstance().getReference();
//
//        Rent_id = getIntent().getStringExtra("Rent_id");
        Rent_Amount = getIntent().getStringExtra("Rent_Amount");
        Deposit_Amount = getIntent().getStringExtra("Deposit_Amount");
        Maintenace_Charge = getIntent().getStringExtra("Maintenace_Charge");
        Building_Name = getIntent().getStringExtra("Building_Name");
        Portion_Name = getIntent().getStringExtra("Portion_Name");
        Tenant_Name = getIntent().getStringExtra("Tenant_Name");
        Start_Date = getIntent().getStringExtra("Start_Date");
        End_Date = getIntent().getStringExtra("End_Date");
        Due_Date = getIntent().getStringExtra("Due_Date");
        Document = getIntent().getStringExtra("Document");
        Rental_Type = getIntent().getStringExtra("Rental_Type");

        RentAmount.setText(Rent_Amount);
        DepositAmount.setText(Deposit_Amount);
        MaintenanceCharges.setText(Maintenace_Charge);



        //Due Date
//        DueDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateRentalActivity.this,
//                        onDateSetListener1,year,month,day);
//                datePickerDialog.show();
//            }
//        });
//        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month+1;
//                String date = dayOfMonth + "/" +month+"/"+year;
//                DueDate.setText(date);
//            }
//        };
        //Start Date

        MC_RentDocuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditRentActivity.this, PdfViewerActivity.class);
                intent.putExtra("pdfUrl",Document);
                startActivity(intent);
            }
        });
        StartDate.setText(Start_Date);
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRentActivity.this,
                        onDateSetListener2,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                StartDate.setText(date);
            }
        };
        //Start Date
        EndDate.setText(End_Date);
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRentActivity.this,
                        onDateSetListener3,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                EndDate.setText(date);
            }
        };

        //Get Building Names

        referenceBuilding.child("Buildings").child(U_MobileNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBuilding.clear();
                arrayListBuilding.add(Building_Name);

                arrayListBuilding.add("Select Building Name");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    //   String portionid = items.child("P_ID").getValue(String.class);
                    arrayListBuilding.add(items.child("B_Name").getValue(String.class));
                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditRentActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListBuilding);
                BuildingName.setAdapter(arrayAdapter);
                BuildingName.getSelectedItem().toString();

                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditRentActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });


        //Get Protion Names

        referencePortion.child("Portions").child(U_MobileNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListPortion.clear();
                arrayListPortion.add(Portion_Name);

                arrayListPortion.add("Select Portion Name");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    //   String portionid = items.child("P_ID").getValue(String.class);
                    arrayListPortion.add(items.child("P_Name").getValue(String.class));
                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditRentActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListPortion);
                PortionName.setAdapter(arrayAdapter);
                PortionName.getSelectedItem().toString();

                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditRentActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });

        //Get Tenant Names

        referenceTenant.child("Tenants").child(U_MobileNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListTenant.clear();

                arrayListTenant.add(Tenant_Name);
                arrayListTenant.add("Select Tenant Name");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListTenant.add(items.child("T_Name").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditRentActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayListTenant);
                TenantName.setAdapter(arrayAdapter);
                TenantName.getSelectedItem().toString();

                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditRentActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });


        //Rent Types

        String [] rent_item = new String[]{Rental_Type,"Select Rent Type","Monthly"};

        RentType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rent_item));

        RentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   RentType.getSelectedItem();
                RentType.getSelectedItem().toString();
                //  Double rent_type = Double.valueOf(RentType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String [] rent_date = new String[]{Due_Date,"Due Date","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
                "17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

        due_date.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rent_date));

        due_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   RentType.getSelectedItem();
                due_date.getSelectedItem().toString();
                //  Double rent_type = Double.valueOf(RentType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        MC_RentDocuImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                openGallery();
//                Intent galleryIntent = new Intent();
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//                // We will be redirected to choose pdf
//                galleryIntent.setType("application/pdf");
//                startActivityForResult(galleryIntent, 1);
//            }
//        });

        //Submit
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Double rent_amount,deposita_amount,maintenace_charege,rent_type;

                rent_amount = Double.valueOf(RentAmount.getText().toString());
                deposita_amount = Double.valueOf(DepositAmount.getText().toString());
                maintenace_charege = Double.valueOf(MaintenanceCharges.getText().toString());

             //   checkValidations();
                UploadData();

            }
        });

    }

//    private void checkValidations() {
//
//
//        if (imageuri == null) {
//            UploadData(Document);
//        }else {
//            UploadDocument();
//        }
//
//
////            UploadData(rent_amount,deposita_amount,maintenace_charege,portion_name,tenant_name,
////                    start_date,due_date,end_date,rent_type);
//
//
//
////        UploadData(rent_amount,deposita_amount,maintenace_charege,portion_name,tenant_name,
////                start_date,due_date,end_date,rent_type);
//
//    }
//
//    private void UploadDocument() {
//
//
//
//
//        final String timestamp = "" + System.currentTimeMillis();
//        storageReference = FirebaseStorage.getInstance().getReference();
//        messagePushID = timestamp;
//
//        final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
//        //  Toast.makeText(CreateRentalActivity.this, filepath.getName(), Toast.LENGTH_SHORT).show();
//        filepath.putFile(imageuri).continueWithTask(new Continuation() {
//            @Override
//            public Object then(@NonNull Task task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//                return filepath.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//
//                if (task.isSuccessful()) {
//                    // After uploading is done it progress
//                    // dialog box will be dismissed
//                    //  dialog.dismiss();
//                    Uri uri = task.getResult();
//
//                    myurl = uri.toString();
//
//
//                    UploadData(myurl);
//
//                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
//                            child("Rent").child(U_MobileNumber).child(Rent_id);
//
//                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("Document",""+myurl);
//                    reference1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            pd.dismiss();
//                            finish();
//                        }
//                    });
////
//
//
//                    //  DocuImage.setVisibility(View.INVISIBLE);
//
//                    //   DocText.setText(filepath.getName());
//                    DocText.setText("Success");
//                    Glide.with(EditRentActivity.this).load(R.drawable.pdf_icon).into(DocuImage);
//                    //   Toast.makeText(CreateRentalActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    //  dialog.dismiss();
//                    //  Toast.makeText(CreateRentalActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void UploadData() {

        String s_Date = StartDate.getText().toString();
        String e_Date = EndDate.getText().toString();

        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("Rent_id",Rent_id);
        hashMap.put("Rent_Amount",rent_amount);
        hashMap.put("Deposit_Amount",deposita_amount);
        hashMap.put("Maintenace_Charge",maintenace_charege);
        hashMap.put("P_ID","0");
        hashMap.put("T_ID","0");
        hashMap.put("Building_Name",BuildingName.getSelectedItem().toString());
        hashMap.put("Portion_Name",PortionName.getSelectedItem().toString());
        hashMap.put("Tenant_Name",TenantName.getSelectedItem().toString());
        hashMap.put("Start_Date",s_Date);
        hashMap.put("End_Date",e_Date);
        hashMap.put("Due_Date",due_date.getSelectedItem().toString());
        hashMap.put("Document",myurl);
        hashMap.put("Rental_Type",RentType.getSelectedItem().toString());
        hashMap.put("U_MobileNumber", U_MobileNumber);



        referenceRent.child(U_MobileNumber).child(Rent_id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Log.i("s",hashMap+"");
                Toast.makeText(EditRentActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();


                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditRentActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageuri = data.getData();


    }
}