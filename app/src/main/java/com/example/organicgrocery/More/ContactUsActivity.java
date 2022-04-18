package com.example.organicgrocery.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicgrocery.R;

public class ContactUsActivity extends AppCompatActivity {
    ImageView contactusbackIV;
    private static final int REQUEST_CALL = 1;
    private TextView phonenoTV;
    private TextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        contactusbackIV = findViewById(R.id.contactusbackIV);
        phonenoTV = findViewById(R.id.phonenoTV);
        phonenoTV.setPaintFlags(phonenoTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTV = findViewById(R.id.emailTV);
        setOnClickListeners();
        emailOnClickListener();
        contactbackOnClick();
        setOnClickListeners();
    }

    private void contactbackOnClick() {
        contactusbackIV.setOnClickListener(v -> finish());
    }

    private void emailOnClickListener() {
        emailTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:orgaingmart@gmail.com")));
    }

    private void setOnClickListeners() {
        phonenoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
    }

    private void makePhoneCall() {
        String num = phonenoTV.getText().toString();
        if (ContextCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactUsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + num;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }
}