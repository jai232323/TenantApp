package com.example.housing.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housing.Activity.AssignTenantsActivity;
import com.example.housing.Activity.CreatePortionActivity;
import com.example.housing.Activity.MainActivity;
import com.example.housing.Fragments.PortionFragment;
import com.example.housing.Model.PortionsData;
import com.example.housing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PortionAdapter extends RecyclerView.Adapter<PortionAdapter.ViewHolder> {


    private Context context;
    private ArrayList<PortionsData> items;

    public PortionAdapter(Context context, ArrayList<PortionsData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.portion_adapter,parent,false);

        return new PortionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PortionsData data = items.get(position);

        holder.P_Name.setText(data.getP_Name());
        holder.P_Floor.setText(data.getP_Floor());
        holder.P_EBCard.setText(data.getP_EBCard());
        holder.B_Name.setText(data.getB_Name());


        String U_MobileNumber;

        SharedPreferences prefs3 =context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs3.getString("U_MobileNumber","none");

        ArrayList<String> arrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Tenants").child(U_MobileNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                arrayList.add("Assign to Tenants");
//                arrayList.add("Create Buildings");

                for (DataSnapshot items : snapshot.getChildren()){
                    arrayList.add(items.child("T_Name").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrayList);
                holder.AssignToTenants.setAdapter(arrayAdapter);
                String Spinner1 = holder.AssignToTenants.getSelectedItem().toString();


                //Log.i("s",BuildingSpinner+"");

                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });



        //Edit
        holder.P_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.ll_edit.setVisibility(View.VISIBLE);

                String PNAME , PFLOOR , PEBCARD;
                PNAME = data.getP_Name();
                PFLOOR = data.getP_Floor();
                PEBCARD = data.getP_EBCard();


                holder.P_NameET.setText(PNAME);
                holder.P_FloorET.setText(PFLOOR);
                holder.P_EBCardET.setText(PEBCARD);

                holder.P_Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pname1,pfloor1,pebcard1;

                        pname1 = holder.P_NameET.getText().toString();
                        pfloor1 = holder.P_FloorET.getText().toString();
                        pebcard1 = holder.P_EBCardET.getText().toString();

                        if (pname1.isEmpty()){
                            holder.P_NameET.setError("Required Portion Name");
                            holder.P_NameET.requestFocus();
                        }else if (pfloor1.isEmpty()){
                            holder.P_FloorET.setError("Required FloorName");
                            holder.P_FloorET.requestFocus();
                        }else if (pebcard1.isEmpty()){
                            holder.P_EBCardET.setError("Required EB Card");
                            holder.P_EBCardET.requestFocus();
                        }else {
                            UpdatePortionData(pname1,pfloor1,pebcard1);
                        }

                    }
                });

            }

            private void UpdatePortionData(String pname1, String pfloor1, String pebcard1) {
                String P_ID = data.getP_ID();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Portions");

                HashMap<String ,Object> hashMap = new HashMap<>();
                hashMap.put("P_ID",P_ID);
                hashMap.put("P_Name",pname1);
                hashMap.put("P_Floor",pfloor1);
                hashMap.put("P_EBCard",pebcard1);

                reference.child(U_MobileNumber).child(P_ID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        holder.ll_edit.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Something Issues", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Delete
        holder.P_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                String P_ID = data.getP_ID();
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                        child("Portions").child(U_MobileNumber).child(P_ID);

                                reference1.removeValue();

                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView P_Name,P_Floor,P_EBCard,B_Name;
        LinearLayout P_Edit,P_Delete;



        Spinner AssignToTenants;;

        String assign_tenants;

        //Update
        LinearLayout ll_edit;
        EditText P_NameET,P_FloorET,P_EBCardET;
        Button P_Save;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            P_Name = itemView.findViewById(R.id.P_Name);
            P_EBCard = itemView.findViewById(R.id.P_EBCard);
            P_Floor = itemView.findViewById(R.id.P_Floor);
            B_Name = itemView.findViewById(R.id.B_Name);



            AssignToTenants = itemView.findViewById(R.id.AssignToTenants);

            P_Edit = itemView.findViewById(R.id.P_Edit);
            P_Delete = itemView.findViewById(R.id.P_Delete);

            //Update
            ll_edit = itemView.findViewById(R.id.ll_edit);
            P_NameET = itemView.findViewById(R.id.P_NameET);
            P_FloorET = itemView.findViewById(R.id.P_FloorET);
            P_EBCardET = itemView.findViewById(R.id.P_EBCardET);
            P_Save = itemView.findViewById(R.id.P_Save);
        }
    }
}
