package com.example.organicgrocery.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.organicgrocery.R;

public class AboutUsActivity extends AppCompatActivity {
    ImageView aboutusbackIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutusbackIV= findViewById(R.id.aboutusbackIV);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        aboutusbackIV.setOnClickListener(v -> finish());
    }
}