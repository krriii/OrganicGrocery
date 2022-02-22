package com.example.organicgrocery.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.organicgrocery.R;
import com.example.organicgrocery.userAccount.UserAccountActivity;
import com.example.organicgrocery.utils.SharedPrefUtils;

public class SplashScreenActivity extends AppCompatActivity {
    boolean isLoggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, UserAccountActivity.class );
                startActivity(intent);
                finish();

            }
        }, 3000);{

        }
    }
    public void getIsLoggedInOrNot() {
        isLoggedIn = SharedPrefUtils.getBool(this, "isl", false);
    }
}