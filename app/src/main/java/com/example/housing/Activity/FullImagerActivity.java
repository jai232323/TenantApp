package com.example.housing.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.housing.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImagerActivity extends AppCompatActivity {


    private PhotoView FullImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_imager);

        FullImageView=findViewById(R.id.FullImageView);

        String image = getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(FullImageView);
    }
}