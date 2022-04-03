package com.example.organicgrocery.checkout.orderComplete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.organicgrocery.R;

public class OrderCompleteActivity extends AppCompatActivity {

   TextView backTV, shopmoreTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        backTV = findViewById(R.id.backTV);
        shopmoreTV = findViewById(R.id.shopMoreTv);
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shopmoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}