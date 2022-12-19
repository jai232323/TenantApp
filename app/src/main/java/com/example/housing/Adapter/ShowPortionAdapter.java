package com.example.housing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housing.Model.BuildingData;
import com.example.housing.Model.PortionsData;
import com.example.housing.R;

import java.util.ArrayList;

public class ShowPortionAdapter extends RecyclerView.Adapter<ShowPortionAdapter.ViewHolder> {


    private Context context;
    private ArrayList<PortionsData> items;

    public ShowPortionAdapter(Context context, ArrayList<PortionsData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_portion_adapter,parent,false);

        return new ShowPortionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PortionsData data = items.get(position);
        holder.PortionsNames.setText(data.getP_Name());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView PortionsNames;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            PortionsNames =itemView.findViewById(R.id.PortionsNames);
        }
    }

}
