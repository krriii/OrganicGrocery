package com.example.organicgrocery.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.organicgrocery.R;

public class TermsandconditionActivity extends AppCompatActivity {
    ImageView termsandConbackIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsandcondition);
        termsandConbackIV = findViewById(R.id.termsandConbackIV);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        termsandConbackIV.setOnClickListener( v-> finish());
    }
}