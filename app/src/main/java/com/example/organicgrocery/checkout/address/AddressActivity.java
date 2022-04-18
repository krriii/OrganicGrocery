package com.example.organicgrocery.checkout.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.Address;
import com.example.organicgrocery.api.response.AddressResponse;
import com.example.organicgrocery.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    RecyclerView addressRV;
    TextView AddAddressTV;
    public static String ADDRESS_SELECTED_KEY = "Dfa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addressRV = findViewById(R.id.addressRV);
        AddAddressTV = findViewById(R.id.AddAddressTV);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("Select Your Delivery Address");
        getAddressOnline();
        addAddressOnClick();
    }

    private void addAddressOnClick() {
        AddAddressTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getAddressOnline() {
        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<AddressResponse> addressResponseCall = ApiClient.getClient().getMyAddresses(key);
        addressResponseCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()){
                    listAddress(response.body().getAddresses());
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });

    }

    private void listAddress(List<Address> addresses) {

        addressRV.setHasFixedSize(true);
        addressRV.setLayoutManager(new LinearLayoutManager(this));
        AddressAdapter addressAdapter = new AddressAdapter(addresses, this);
        addressAdapter.setOnAddressItemClickListener(new AddressAdapter.OnAddressItemClickListener() {
            @Override
            public void onAddressClick(int position, Address address) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(ADDRESS_SELECTED_KEY, address);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
        addressRV.setAdapter(addressAdapter);

    }

      public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
              case android.R.id.home:
                  finish();
                  return true;
              default:
                  return super.onOptionsItemSelected(item);
          }

      }
    @Override
    protected void onResume() {
        super.onResume();
        getAddressOnline();
    }

      }


