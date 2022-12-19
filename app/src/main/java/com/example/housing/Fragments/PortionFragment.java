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
import com.example.housing.Adapter.PortionAdapter;
import com.example.housing.Model.PortionsData;
import com.example.housing.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PortionFragment extends Fragment {



    FloatingActionButton fab;

    RecyclerView PortionRecyclerView;

    private ArrayList<PortionsData> list;
    private PortionAdapter adapter;

    String U_MobileNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_portion, container, false);




        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs1.getString("U_MobileNumber","none");

        fab = view.findViewById(R.id.fab);
        PortionRecyclerView = view.findViewById(R.id.PortionRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        PortionRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new PortionAdapter(getContext(),list);
        PortionRecyclerView.setAdapter(adapter);

        getPortionData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePortionActivity();
            }
        });
        return view;
    }

    public void handleEvent(){

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.i("fff","PortionFrrr");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//         EventBus.getDefault().unregister(this);
//    }

    private void getPortionData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Portions").child(U_MobileNumber);
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PortionsData data = dataSnapshot.getValue(PortionsData.class);
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

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
       Log.i("updateData","dudoddo");
        getPortionData();
    }*/

    private void CreatePortionActivity() {

        Intent intent = new Intent(getContext(), CreatePortionActivity.class);
        startActivity(intent);

    }

    class SampleEvent{

    }
}