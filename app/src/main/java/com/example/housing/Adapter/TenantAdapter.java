package com.example.housing.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housing.Activity.MembersActivity;
import com.example.housing.Model.PortionsData;
import com.example.housing.Model.TenantData;
import com.example.housing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.ViewHolder> {


    private Context context;
    private ArrayList<TenantData> items;

    public TenantAdapter(Context context, ArrayList<TenantData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public TenantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenant_adapter,parent,false);

        return new TenantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TenantData data = items.get(position);
        holder.T_Name.setText(data.getT_Name());
        holder.T_MobileNumber.setText(data.getT_MobileNumber());


        String U_MobileNumber;

        SharedPreferences prefs3 =context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs3.getString("U_MobileNumber","none");

        //GotoMembersActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MembersActivity.class);


                String  T_ID = data.getT_ID();

                SharedPreferences.Editor editor =context.getSharedPreferences("PREFS",context.MODE_PRIVATE).edit();
                editor.putString("T_ID",T_ID);
                editor.apply();

                context.startActivity(intent);

            }
        });

        holder.T_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ll_edit.setVisibility(View.VISIBLE);

                String TNAME,TMOBILENUMBER;
                TNAME = data.getT_Name();
                TMOBILENUMBER = data.getT_MobileNumber();

                holder.T_NameET.setText(TNAME);
                holder.T_MobileNummberET.setText(TMOBILENUMBER);

                holder.T_Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String t_name = holder.T_NameET.getText().toString();
                        String t_mobilnumber = holder.T_MobileNummberET.getText().toString();


//                        if (t_name.isEmpty()){
//                            holder.T_NameET.setError("Required Tenant Name");
//                            holder.T_NameET.requestFocus();
//                        }else if (t_mobilnumber.isEmpty()){
//                            holder.T_MobileNummberET.setError("Required MobileNumber");
//                            holder.T_MobileNummberET.requestFocus();
//                        }else {
//                            UpdateData(t_name,t_mobilnumber);
//                        }
                        UpdateData(t_name,t_mobilnumber);
                    }
                });
            }

            private void UpdateData(String t_name, String t_mobilenumber) {

                String T_ID = data.getT_ID();
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Tenants");

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("T_ID",T_ID);
                hashMap.put("T_Name",t_name);
                hashMap.put("T_MobileNumber",t_mobilenumber);

                reference1.child(U_MobileNumber).child(T_ID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        holder.T_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                        child("Tenants").child(U_MobileNumber).child(data.getT_ID());

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


        TextView T_Name,T_MobileNumber;
        LinearLayout T_Edit,T_Delete;

        //Edit
        LinearLayout ll_edit;
        EditText T_NameET,T_MobileNummberET;
        Button T_Save;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            T_Name=itemView.findViewById(R.id.T_Name);
            T_MobileNumber=itemView.findViewById(R.id.T_MobileNummber);

            T_Edit = itemView.findViewById(R.id.T_Edit);
            T_Delete = itemView.findViewById(R.id.T_Delete);

            //Edit
            ll_edit = itemView.findViewById(R.id.ll_edit);
            T_NameET = itemView.findViewById(R.id.T_NameET);
            T_MobileNummberET = itemView.findViewById(R.id.T_MobileNummberET);
            T_Save = itemView.findViewById(R.id.T_Save);

        }
    }
}
