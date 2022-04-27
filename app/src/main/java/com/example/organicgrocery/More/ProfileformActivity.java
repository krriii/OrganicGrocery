package com.example.organicgrocery.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.ProfileResponse;
import com.example.organicgrocery.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileformActivity extends AppCompatActivity {

    EditText nameET,emailET,phoneET,dobET;
    TextView updateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileform);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        phoneET = findViewById(R.id.phonenoET);
        dobET = findViewById(R.id.dobET);
        updateTV = findViewById(R.id.updateProfileTV);
        initializeValues();

        updateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void initializeValues()
    {
        if(getIntent().getSerializableExtra("name") != null){
            nameET.setText((CharSequence) getIntent().getSerializableExtra("name"));
        }
        if(getIntent().getSerializableExtra("email") != null){
            emailET.setText((CharSequence) getIntent().getSerializableExtra("email"));
        }
        if(getIntent().getSerializableExtra("phone") != null){
            phoneET.setText((CharSequence) getIntent().getSerializableExtra("phone"));
        }
        if(getIntent().getSerializableExtra("dob") != null){
            dobET.setText((CharSequence) getIntent().getSerializableExtra("dob"));
        }
    }

    public  void updateProfile()
    {
        if(nameET.getText().toString().length() == 0 || emailET.getText().toString().length() == 0|| phoneET.getText().toString().length() == 0 || dobET.getText().toString().length() == 0)
        {
            Toast.makeText(ProfileformActivity.this, "Fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<ProfileResponse> profileResponseCall = ApiClient.getClient().addProfile(key,nameET.getText().toString(),emailET.getText().toString(),phoneET.getText().toString()+"",dobET.getText().toString());
        profileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()){
                    System.out.println(response.body().getError());
                    if (!response.body().getError()){
                        Toast.makeText(ProfileformActivity.this, "Sucessfully updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileformActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ProfileformActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }
}