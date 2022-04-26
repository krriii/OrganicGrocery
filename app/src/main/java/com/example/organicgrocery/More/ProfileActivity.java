package com.example.organicgrocery.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.AddressResponse;
import com.example.organicgrocery.api.response.ProfileResponse;
import com.example.organicgrocery.checkout.address.AddAddressActivity;
import com.example.organicgrocery.checkout.address.AddressActivity;
import com.example.organicgrocery.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilebackIV;
    LinearLayout editLL, changePasswordLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        profilebackIV = findViewById(R.id.profilebackIV);
        editLL = findViewById(R.id.editLL);
        setContentView(R.layout.activity_profile);
//        setOnclickListeners();
        addProfileOnClick();
        getProfileOnline();
    }

    private void getProfileOnline() {
        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<ProfileResponse> profileResponseCall = ApiClient.getClient().getMyProfile(key);
        profileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void addProfileOnClick() {
        editLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileformActivity.class);
                startActivity(intent);

            }
        });

    }

//    private void setOnclickListeners() {
////        profilebackIV.setOnClickListener( v-> finish());
//
//    }

}