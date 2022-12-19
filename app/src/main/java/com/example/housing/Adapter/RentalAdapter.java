package com.example.housing.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housing.Activity.DueActivity;
import com.example.housing.Activity.EditRentActivity;
import com.example.housing.Activity.PdfViewerActivity;
import com.example.housing.Model.RentalData;
import com.example.housing.R;

import java.util.ArrayList;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {



    private Context context;
    private ArrayList<RentalData> items;

    public RentalAdapter(Context context, ArrayList<RentalData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rent_adapter,parent,false);

        return new RentalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        RentalData data = items.get(position);
        holder.BuildingName.setText(data.getBuilding_Name());
        holder.PortionName.setText(data.getPortion_Name());
        holder.TenantName.setText(data.getTenant_Name());


        String rent_amount = String.valueOf(data.getRent_Amount());
        holder.RentAmount.setText(rent_amount);

        String d_amount = String.valueOf(data.getDeposit_Amount());
        holder.DepositAmount.setText(d_amount);

        String m_charge = String.valueOf(data.getMaintenace_Charge());
        holder.MaintenanceCharges.setText(m_charge);


        holder.viewpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfViewerActivity.class);
                intent.putExtra("pdfUrl",items.get(position).getDocument());
                context.startActivity(intent);
                // Toast.makeText(context,list.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.downloadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(items.get(position).getDocument()));
                context.startActivity(intent);
                //Toast.makeText(context,"Pdf Download",Toast.LENGTH_SHORT).show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DueActivity.class);
                context.startActivity(intent);

            }
        });

        holder.edit_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditRentActivity.class);


                String Rentid =data.getRent_id();
                String s_d = data.getStart_Date();
                String r_a = data.getRent_Amount().toString();
                String d_a = data.getDeposit_Amount().toString();
                String m_c = data.getMaintenace_Charge().toString();
                String b_n = data.getBuilding_Name();
                String p_n = data.getPortion_Name();
                String r_t = data.getRental_Type();
                String t_n = data.getTenant_Name();

                intent.putExtra("Rent_id",Rentid);
                intent.putExtra("Rent_Amount",r_a);
                intent.putExtra("Deposit_Amount",d_a);
                intent.putExtra("Maintenace_Charge",m_c);
                intent.putExtra("Building_Name",b_n);
                intent.putExtra("Portion_Name",p_n);
                intent.putExtra("Tenant_Name",t_n);
                intent.putExtra("Start_Date",s_d);
                intent.putExtra("End_Date",data.getEnd_Date());
                intent.putExtra("Due_Date",data.getDue_Date());
                intent.putExtra("Document",data.getDocument());
                intent.putExtra("Rental_Type",r_t);


//                Log.i("s",intent.toString()+"");
//                Toast.makeText(context, intent.toString(), Toast.LENGTH_SHORT).show();

                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void Filteredlist(ArrayList<RentalData> filterlist) {

        items = filterlist;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView BuildingName,PortionName,TenantName,RentAmount,DepositAmount,MaintenanceCharges,RentStatus;
        ImageView edit_dot;
        LinearLayout viewpdf;
        ImageView downloadpdf;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            BuildingName = itemView.findViewById(R.id.BuildingName);
            PortionName = itemView.findViewById(R.id.PortionName);
            TenantName = itemView.findViewById(R.id.TenantName);
            RentAmount = itemView.findViewById(R.id.RentAmount);
            DepositAmount = itemView.findViewById(R.id.DepositAmount);
            MaintenanceCharges = itemView.findViewById(R.id.MaintenanceCharges);
            RentStatus = itemView.findViewById(R.id.RentStatus);

            edit_dot = itemView.findViewById(R.id.edit_dot);

            //pdf
            viewpdf = itemView.findViewById(R.id.viewpdf);
            downloadpdf = itemView.findViewById(R.id.downloadpdf);

        }
    }
}
