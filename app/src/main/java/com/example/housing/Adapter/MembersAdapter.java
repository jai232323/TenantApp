package com.example.housing.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housing.Model.PortionsData;
import com.example.housing.Model.TenantData;
import com.example.housing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {


    private Context context;
    private ArrayList<TenantData> items;

    public MembersAdapter(Context context, ArrayList<TenantData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.members_adapter,parent,false);

        return new MembersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TenantData data = items.get(position);
        holder.Member_Name.setText(data.getMember_Name());
        holder.Member_Address.setText(data.getMember_Address());
        holder.Member_MobileNumber.setText(data.getMember_MobileNumber());




        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.LL_AddMembers.setVisibility(View.VISIBLE);


                holder.Member_NameEdit.setText(data.getMember_Name());
                holder.Member_AddressEdit.setText(data.getMember_Address());
                holder.Member_MobileNumberEdit.setText(data.getMember_MobileNumber());

                holder.EditTenantData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String m_n = holder.Member_NameEdit.getText().toString();
                        String m_ad = holder.Member_AddressEdit.getText().toString();
                        String m_mo = holder.Member_MobileNumberEdit.getText().toString();

//                        if (m_n.isEmpty()){
//                            holder.Member_NameEdit.setError("Required Name");
//                            holder.Member_NameEdit.requestFocus();
//                        }else if (m_ad.isEmpty()){
//                            holder.Member_AddressEdit.setError("Required Address");
//                            holder.Member_AddressEdit.requestFocus();
//                        }else if (m_mo.isEmpty()){
//                            holder.Member_MobileNumberEdit.setError("Required Mobile Number");
//                            holder.Member_MobileNumberEdit.requestFocus();
//                        }else {
//                            UpdateTenantMembersData(m_n,m_ad,m_mo);
//                        }
                        UpdateTenantMembersData(m_n,m_ad,m_mo);

                    }

                    private void UpdateTenantMembersData(String m_n, String m_ad, String m_mo) {

                        DatabaseReference referenceUpdate = FirebaseDatabase.getInstance().getReference("TenantMembers");


                        HashMap <String,Object> hashMap = new HashMap<>();
                        hashMap.put("Member_Name",m_n);
                        hashMap.put("Member_Address",m_ad);
                        hashMap.put("Member_MobileNumber",m_mo);


                        String tid = data.getT_ID();
                        String mid = data.getMember_ID();


                        String U_MobileNumber;
                        SharedPreferences prefs3 =context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                        U_MobileNumber=prefs3.getString("U_MobileNumber","none");

                        referenceUpdate.child(U_MobileNumber).child(tid).
                                child(mid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                holder.LL_AddMembers.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                DatabaseReference referenceDelete = FirebaseDatabase.getInstance().getReference("TenantMembers");

                                String tid = data.getT_ID();
                                String mid = data.getMember_ID();


                                String U_MobileNumber;
                                SharedPreferences prefs3 =context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                                U_MobileNumber=prefs3.getString("U_MobileNumber","none");

                                referenceDelete.child(U_MobileNumber).child(tid).
                                        child(mid).removeValue();

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

        TextView Member_Name,Member_Address,Member_MobileNumber;

        ImageView edit,delete;
        LinearLayout LL_AddMembers;
        Button EditTenantData;

        EditText Member_NameEdit,Member_AddressEdit,Member_MobileNumberEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Member_Name = itemView.findViewById(R.id.Member_Name);
            Member_Address = itemView.findViewById(R.id.Member_Address);
            Member_MobileNumber = itemView.findViewById(R.id.Member_MobileNumber);

            //edit/delete
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            LL_AddMembers = itemView.findViewById(R.id.LL_AddMembers);
            EditTenantData = itemView.findViewById(R.id.EditTenantData);


            Member_NameEdit = itemView.findViewById(R.id.Member_NameEdit);
            Member_AddressEdit = itemView.findViewById(R.id.Member_AddressEdit);
            Member_MobileNumberEdit = itemView.findViewById(R.id.Member_MobileNumberEdit);

        }
    }

}
