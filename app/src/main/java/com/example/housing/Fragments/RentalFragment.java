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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.housing.Activity.CreateBuildingActivity;
import com.example.housing.Activity.CreatePortionActivity;
import com.example.housing.Activity.CreateRentalActivity;
import com.example.housing.Adapter.BuildingAdapter;
import com.example.housing.Adapter.PortionAdapter;
import com.example.housing.Adapter.RentalAdapter;
import com.example.housing.Model.BuildingData;
import com.example.housing.Model.PortionsData;
import com.example.housing.Model.RentalData;
import com.example.housing.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class RentalFragment extends Fragment {



    RecyclerView RentalRecyclerView;
    FloatingActionButton fab;

    private ArrayList<RentalData> list;
    private RentalAdapter adapter;


    EditText search_bar;

    String s,Rent_id;

    String U_MobileNumber;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rental, container, false);





        RentalRecyclerView = view.findViewById(R.id.RentalRecyclerView);

        SharedPreferences prefs3 =getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs3.getString("U_MobileNumber","none");

        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Rent_id=prefs1.getString("Rent_id","none");

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);




        RentalRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new RentalAdapter(getContext(),list);
        RentalRecyclerView.setAdapter(adapter);


      //  getRentData();


        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CreateRentalActivity.class);
                startActivity(intent);

            }
        });


        getRentData1();

        search_bar = view.findViewById(R.id.search_bar);


        return view;
    }
    private void getRentData1() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Rent").child(U_MobileNumber);
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RentalData data = dataSnapshot.getValue(RentalData.class);
                    list.add(0, data);
                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();


                search_bar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        filter(editable.toString());
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filter(String text) {

        ArrayList<RentalData> filterlist = new ArrayList<>();
        for (RentalData item : list){
            if (item.getTenant_Name().toLowerCase().contains(text.toLowerCase())){

                filterlist.add(item);
            }
        }

        adapter.Filteredlist(filterlist);
    }

//    private void getRentData() {
//        // dbRef = reference.child("O+");
//        Query query = FirebaseDatabase.getInstance().getReference("Rent").child(U_MobileNumber)
//                .orderByChild("Tenant_Name").startAt(s).endAt(s+"\uf8ff");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list = new ArrayList<>();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    RentalData data = snapshot.getValue(RentalData.class);
//                    list.add(0, data);
//                }
//                Collections.reverse(list);
//               // adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}