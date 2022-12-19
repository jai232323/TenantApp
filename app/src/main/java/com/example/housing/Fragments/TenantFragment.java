package com.example.housing.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.housing.Activity.CreatePortionActivity;
import com.example.housing.Activity.CreateTenantActivity;
import com.example.housing.Adapter.PortionAdapter;
import com.example.housing.Adapter.TenantAdapter;
import com.example.housing.Model.PortionsData;
import com.example.housing.Model.TenantData;
import com.example.housing.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TenantFragment extends Fragment {

    FloatingActionButton fab;

    RecyclerView TenantRecyclerView;

    private ArrayList<TenantData> list;
    private TenantAdapter adapter;


    String U_MobileNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tenant, container, false);



        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs1.getString("U_MobileNumber","none");

        fab = view.findViewById(R.id.fab);
        TenantRecyclerView = view.findViewById(R.id.TenantRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        TenantRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TenantAdapter(getContext(),list);
        TenantRecyclerView.setAdapter(adapter);

        //getTenantData
        getTenantData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTenantActivity();
            }
        });

        return view;
    }

    private void getTenantData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tenants").child(U_MobileNumber);
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TenantData data = dataSnapshot.getValue(TenantData.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CreateTenantActivity() {
        Intent intent = new Intent(getContext(), CreateTenantActivity.class);
        startActivity(intent);
    }
}