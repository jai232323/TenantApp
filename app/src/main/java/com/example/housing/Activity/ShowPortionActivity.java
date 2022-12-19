package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.housing.Adapter.ShowPortionAdapter;
import com.example.housing.Model.PortionsData;
import com.example.housing.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowPortionActivity extends AppCompatActivity {


    RecyclerView ShowPortionRecyclerView;
    LinearLayout NoPortions;

    private ArrayList<PortionsData> list;
    private ShowPortionAdapter adapter;

    String P_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_portion);

        ShowPortionRecyclerView=findViewById(R.id.ShowPortionRecyclerView);
        NoPortions=findViewById(R.id.NoPortions);


        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        SharedPreferences prefs3 = getSharedPreferences("PREFS1", Context.MODE_PRIVATE);
        P_ID=prefs3.getString("P_ID","null");



        ShowPortionRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new ShowPortionAdapter(ShowPortionActivity.this,list);
        ShowPortionRecyclerView.setAdapter(adapter);


        getPortionsData();

    }

    private void getPortionsData() {

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Portions").child(P_ID);
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                if (!snapshot.exists()){
                    NoPortions.setVisibility(View.VISIBLE);
                    ShowPortionRecyclerView.setVisibility(View.GONE);
                }else {
                    NoPortions.setVisibility(View.GONE);
                    ShowPortionRecyclerView.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PortionsData data = dataSnapshot.getValue(PortionsData.class);
                        list.add(0,data);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}