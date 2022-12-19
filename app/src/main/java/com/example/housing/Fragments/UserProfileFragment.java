package com.example.housing.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.housing.Activity.MainActivity;
import com.example.housing.Activity.UserLoginActivity;
import com.example.housing.Model.UserData;
import com.example.housing.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfileFragment extends Fragment {


//    ImageView UserImage;
    TextView User_Name,UserMobileNumber;
    Button Logout;

    String U_Id;

    FirebaseAuth firebaseAuth;


    String U_MobileNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

//        UserImage = view.findViewById(R.id.UserImage);

        User_Name = view.findViewById(R.id.User_Name);
        UserMobileNumber =view.findViewById(R.id.UserMobileNumber);
        Logout = view.findViewById(R.id.Logout);

        firebaseAuth=FirebaseAuth.getInstance();

        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_Id=prefs1.getString("U_MobileNumber","none");


        SharedPreferences prefs2 =getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber=prefs2.getString("U_MobileNumber","none");

        getUserProfile();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users")
                        .child(U_MobileNumber);

                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        firebaseAuth.signOut();


                     //   reference1.removeValue();

                        Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), UserLoginActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);


                        getActivity().finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        return view;

    }

    private void getUserProfile() {

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users")
                .child(U_MobileNumber);



        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if ((getContext() == null)){
                    return;
                }

                UserData data = snapshot.getValue(UserData.class);

                assert data != null;
//                Glide.with(getContext()).load(data.getU_Image()).into(UserImage);

                User_Name.setText(data.getU_Name());
                UserMobileNumber.setText(data.getU_MobileNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}