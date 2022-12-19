package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.housing.Adapter.MembersAdapter;
import com.example.housing.Adapter.TenantAdapter;
import com.example.housing.Model.TenantData;
import com.example.housing.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity {


    RecyclerView MembersRecyclerView;

    private ArrayList<TenantData> list;
    private MembersAdapter adapter;

    String U_MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);


        SharedPreferences prefs2 =getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs2.getString("U_MobileNumber","none");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Members");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MembersRecyclerView=findViewById(R.id.MembersRecyclerView);


        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String T_ID1=prefs1.getString("T_ID","none");

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(MembersActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        MembersRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new MembersAdapter(MembersActivity.this,list);
        MembersRecyclerView.setAdapter(adapter);

        getMembersData(T_ID1);

    }

    private void getMembersData( String T_ID1) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("TenantMembers")
                .child(U_MobileNumber).child(T_ID1);
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TenantData data = dataSnapshot.getValue(TenantData.class);
                    list.add(0, data);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MembersActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}