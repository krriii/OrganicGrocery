package com.example.organicgrocery.More;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.organicgrocery.R;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilebackIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        profilebackIV = findViewById(R.id.profilebackIV);
        setContentView(R.layout.activity_profile);
        setOnclickListeners();
    }

    private void setOnclickListeners() {
        profilebackIV.setOnClickListener( v-> finish());
    }
}