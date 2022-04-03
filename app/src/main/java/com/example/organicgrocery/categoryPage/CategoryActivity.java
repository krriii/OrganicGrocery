package com.example.organicgrocery.categoryPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.AllProductResponse;
import com.example.organicgrocery.api.response.Category;
import com.example.organicgrocery.api.response.Product;
import com.example.organicgrocery.home.fragment.home.adapters.ShopAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    public static final String CATEGORY_DATA_KEY = "cdk";
    public static String CAT_KEY = "ctk";
    Category category;
    RecyclerView allProductRV;
    ProgressBar loadingProgress;
    ImageView emptyIV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        if (getIntent().getSerializableExtra(CATEGORY_DATA_KEY) == null)
            finish();
        allProductRV = findViewById(R.id.allProductsRV);
        loadingProgress = findViewById(R.id.loadingProgress);
        emptyIV = findViewById(R.id.emptyIV);

        category = (Category) getIntent().getSerializableExtra(CATEGORY_DATA_KEY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(category.getName());
        getCategoryOnline();
    }

    private void toggleLoading(Boolean toogle) {
        if (toogle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }

    private void getCategoryOnline() {
        toggleLoading(true);
        Call<AllProductResponse> getProductsByCategory = ApiClient.getClient().getProductsByCategory(category.getCategoryId());
        getProductsByCategory.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                if (response.isSuccessful()){
                    toggleLoading(false);
                    if (!response.body().getError()){
                        if (response.body().getProducts().size() == 0)
                            showEmptyMessage();
                        else
                            showCategoriesProducts(response.body().getProducts());
                    }
                    else{
                    }
                }
            }


            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleLoading(false);
                Toast.makeText(CategoryActivity.this, "Unknown Error Ocurred !", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showCategoriesProducts(List<Product> products) {
        allProductRV.setHasFixedSize(true);
        allProductRV.setLayoutManager(new GridLayoutManager(this, 2));
        ShopAdapter shopAdapter = new ShopAdapter(products, this);
        allProductRV.setAdapter(shopAdapter);
    }

    private void showEmptyMessage() {emptyIV.setVisibility(View.VISIBLE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        }

    }

