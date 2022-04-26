package com.example.organicgrocery.checkout.address;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.Address;
import com.example.organicgrocery.api.response.AddressResponse;
import com.example.organicgrocery.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {

    EditText cityET, streetET, provinceET, districtET;
    LinearLayout addAddressLL, cancelAddressLL;
    static String ADDED_KEY = "ad";
    static String ADDED_DATA_KEY = "adk";
    TextView cancelTV;
    Window window;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.primary));
        setContentView(R.layout.activity_add_address);
        cityET = findViewById(R.id.cityET);
        streetET = findViewById(R.id.streetET);
        provinceET = findViewById(R.id.provinceET);
        districtET = findViewById(R.id.districtET);
        addAddressLL = findViewById(R.id.addAddressLL);
        cancelAddressLL = findViewById(R.id.cancelAddressLL);
        cancelTV = findViewById(R.id.cancelTV);
        cancelOnClickListener();

        setOnClickListener();
    }

    private void cancelOnClickListener() {
        cancelTV.setOnClickListener(v -> finish());
    }

    private void setOnClickListener() {
        addAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    String key = SharedPrefUtils.getString(AddAddressActivity.this, getString(R.string.api_key));
                    Call<AddressResponse> addressResponseCall = ApiClient.getClient().addAddress(key, cityET.getText().toString(), streetET.getText().toString(), provinceET.getText().toString(), districtET.getText().toString());

                    addressResponseCall.enqueue(new Callback<AddressResponse>() {
                        @Override
                        public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                            AddressResponse addressResponse = response.body();
                            if (response.isSuccessful()) {
                                if (!addressResponse.getError()) {
                                    Address address = new Address();
                                    address.setCity(cityET.getText().toString());
                                    address.setStreet(streetET.getText().toString());
                                    address.setProvince(provinceET.getText().toString());
                                    address.setDistrict(districtET.getText().toString());
                                    address.setId(address.getId());
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(ADDED_KEY, true);
                                    resultIntent.putExtra(ADDED_DATA_KEY, address);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddressResponse> call, Throwable t) {
                        }
                    });

                }
            }
        });

        cancelAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    private boolean validate() {

        boolean validate = true;
        if (cityET.getText().toString().isEmpty()
                || streetET.getText().toString().isEmpty()
                || provinceET.getText().toString().isEmpty()
                || districtET.getText().toString().isEmpty()) {
            Toast.makeText(AddAddressActivity.this, "None of the above fields can be empty", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;

    }
}