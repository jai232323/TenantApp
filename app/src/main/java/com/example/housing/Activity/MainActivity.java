package com.example.housing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.housing.Fragments.BuildingFragment;
import com.example.housing.Fragments.PortionFragment;
import com.example.housing.Fragments.RentalFragment;
import com.example.housing.Fragments.TenantFragment;
import com.example.housing.Fragments.UserProfileFragment;
import com.example.housing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_building);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

    BuildingFragment buildingFragment = new BuildingFragment();
    PortionFragment portionFragment = new PortionFragment();
    TenantFragment tenantFragment = new TenantFragment();
    RentalFragment rentalFragment = new RentalFragment();
    UserProfileFragment userProfileFragment = new UserProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_building:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,buildingFragment).commit();
                return true;
            case R.id.navigation_portion:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, portionFragment).commit();
                return true;
            case R.id.navigation_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, tenantFragment).commit();
                return true;
            case R.id.navigation_rent:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, rentalFragment).commit();
                return true;
            case R.id.navigation_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, userProfileFragment).commit();
                return true;
        }
        return false;
    }
}