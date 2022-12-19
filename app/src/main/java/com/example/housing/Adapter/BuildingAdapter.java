package com.example.housing.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.housing.Activity.EditBuildingsActivity;
import com.example.housing.Activity.FullImagerActivity;
import com.example.housing.Activity.ShowPortionActivity;
import com.example.housing.Model.BuildingData;
import com.example.housing.Model.PortionsData;
import com.example.housing.Model.TenantData;
import com.example.housing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {


    private Context context;
    private ArrayList<BuildingData> items;

    private ArrayList<PortionsData> items1;

    public BuildingAdapter(Context context, ArrayList<BuildingData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.building_adapter,parent,false);

        return new BuildingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BuildingData data = items.get(position);
//        PortionsData data1 = items1.get(position);

        Glide.with(context)
                .load(data.getB_Image())
                .into(holder.BuildingImage);
        holder.B_Name.setText(data.getB_Name());


        holder.BuildingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImagerActivity.class);
                intent.putExtra("image",data.getB_Image());
                context.startActivity(intent);
            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bid = data.getB_ID();
                String bname = data.getB_Name();
                String bimage = data.getB_Image();
                String baddress = data.getB_Address();
                String bpostalcode = data.getB_PostalCode();

                Intent intent = new Intent(context, EditBuildingsActivity.class);

                intent.putExtra("B_ID",bid);
                intent.putExtra("B_Name",bname);
                intent.putExtra("B_Image",bimage);
                intent.putExtra("B_Address",baddress);
                intent.putExtra("B_PostalCode",bpostalcode);

                context.startActivity(intent);





            }
        });


//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.ll_visible.setVisibility(View.VISIBLE);
//
//
//                holder.EditBuildingsName.setText(data.getB_Name());
//
//                holder.OK.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//
//                        String e_buildings = holder.EditBuildingsName.getText().toString();
//                        String old_building_name = data.getB_Name();
//                        String b_id = data.getB_ID();
//
////                        if (e_buildings.isEmpty()){
////                            holder.EditBuildingsName.setError("Building Name Empty");
////                            holder.EditBuildingsName.requestFocus();
////                        }else {
////                            UpdateBuildingsName(e_buildings);
////                        }
//
//                        UpdateBuildingsName(e_buildings,old_building_name,b_id);
//
//                    }
//
//                });
//
//            }
//            private void UpdateBuildingsName(String e_buildings,String old_building_name,String b_id) {
//
//
//                DatabaseReference  reference = FirebaseDatabase.getInstance().getReference("Buildings");
//
//                HashMap<String,Object> hashMap = new HashMap<>();
//                hashMap.put("B_ID",b_id);
//                hashMap.put("B_Name",e_buildings);
//
//                reference.child(b_id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(context, e_buildings+"\nUpdated Successfully", Toast.LENGTH_SHORT).show();
//                        holder.ll_visible.setVisibility(View.GONE);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
////                Intent intent = new Intent(context, ShowPortionActivity.class);
////                context.startActivity(intent);
//
//              //  String P_ID= data1.getP_ID();
//
//                String pid = data.getP_ID();
//                Toast.makeText(context, pid, Toast.LENGTH_SHORT).show();
//
//                SharedPreferences.Editor editor = context.getSharedPreferences("PREFS",context.MODE_PRIVATE).edit();
//                editor.putString("P_ID",data.getP_ID());
//                editor.apply();
//
//
//
//            }
//        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String B_Name = data.getB_Name();
//                Toast.makeText(context, B_Name, Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView BuildingImage;
        TextView B_Name;
        ImageView edit;

        LinearLayout ll_visible;
        EditText EditBuildingsName;
        Button OK;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            BuildingImage = itemView.findViewById(R.id.BuildingImage);
            B_Name = itemView.findViewById(R.id.B_Name);

            edit = itemView.findViewById(R.id.edit);

            //Update Buildings Name
            ll_visible = itemView.findViewById(R.id.ll_visible);
            EditBuildingsName = itemView.findViewById(R.id.EditBuildingsName);
            OK = itemView.findViewById(R.id.OK);
        }
    }

}
