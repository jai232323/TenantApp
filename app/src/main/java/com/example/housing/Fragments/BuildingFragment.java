package com.example.housing.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.housing.Activity.CreateBuildingActivity;
import com.example.housing.Adapter.BuildingAdapter;
import com.example.housing.Model.BuildingData;
import com.example.housing.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BuildingFragment extends Fragment {


    RecyclerView BuildingRecyclerView;
    FloatingActionButton fab;

    private ArrayList<BuildingData> list;
    private BuildingAdapter adapter;

    String U_MobileNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_building, container, false);


        fab = view.findViewById(R.id.fab);
        BuildingRecyclerView = view.findViewById(R.id.BuildingRecyclerView);

        GridLayoutManager linearLayoutManager =  new GridLayoutManager(getContext(),2);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);



        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs1.getString("U_MobileNumber","none");


        BuildingRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new BuildingAdapter(getContext(),list);
        BuildingRecyclerView.setAdapter(adapter);

        //getTenantData
        getTenantData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBuildingActivity();
            }
        });

        return view;
    }

    private void getTenantData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Buildings").child(U_MobileNumber);
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BuildingData data = dataSnapshot.getValue(BuildingData.class);
                    list.add(0, data);

                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CreateBuildingActivity() {
        Intent intent = new Intent(getContext(), CreateBuildingActivity.class);
        startActivity(intent);
    }
}