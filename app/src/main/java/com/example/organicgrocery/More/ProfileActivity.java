package com.example.organicgrocery.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.AddressResponse;
import com.example.organicgrocery.api.response.Profile;
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
    TextView nameTV,emailTV,phoneTV,dobTV;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilebackIV = findViewById(R.id.profilebackIV);
        editLL = findViewById(R.id.editLL);
        nameTV = findViewById(R.id.nameTV);
        emailTV = findViewById(R.id.emailTV);
        phoneTV = findViewById(R.id.phoneTV);
        dobTV = findViewById(R.id.dobTV);
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
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        profile = response.body().getProfile();
                        setProfile(profile);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void setProfile(Profile profile){
        nameTV.setText(profile.getName());
        emailTV.setText(profile.getEmail());
        phoneTV.setText(profile.getPhone());
        dobTV.setText(profile.getDob());
    }

    private void addProfileOnClick() {
        editLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileformActivity.class);
                intent.putExtra("name",profile.getName());
                intent.putExtra("email",profile.getEmail());
                intent.putExtra("phone",profile.getPhone());
                intent.putExtra("dob",profile.getDob());
                intent.putExtra("id",profile.getId());
                startActivity(intent);

            }
        });

    }

//    private void setOnclickListeners() {
////        profilebackIV.setOnClickListener( v-> finish());
//
//    }

}