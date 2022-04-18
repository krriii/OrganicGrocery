package com.example.organicgrocery.admin.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.AllProductResponse;
import com.example.organicgrocery.api.response.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends AppCompatActivity {
 RecyclerView allProductRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product2);
        allProductRV = findViewById(R.id.allProductRV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Products");
        serverCall();
    }

    private void serverCall() {
        Call<AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                setProdctRecyclerView(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                Toast.makeText(ListProductActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setProdctRecyclerView(List<Product> products) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapterAdmin shopAdapter = new ShopAdapterAdmin(products, this);
        allProductRV.setAdapter(shopAdapter);
    }
}