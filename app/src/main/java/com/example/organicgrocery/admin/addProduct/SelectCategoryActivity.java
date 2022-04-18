package com.example.organicgrocery.admin.addProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.Category;
import com.example.organicgrocery.api.response.CategoryResponse;
import com.example.organicgrocery.home.fragment.home.adapters.CategoryAdapter;
import com.example.organicgrocery.utils.DataHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCategoryActivity extends AppCompatActivity {
    RecyclerView fullCategoryRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All Categories");
        setContentView(R.layout.activity_select_category);
        fullCategoryRV = findViewById(R.id.fullCatRV);
        getOnline();
    }

    private void getOnline() {
        //        swipeRefresh.setRefreshing(true);
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                //                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategoryView(response.body().getCategories());

                    }

        }
    }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }

    private void showCategoryView(List<Category> categories) {
        fullCategoryRV.setHasFixedSize(true);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this, false, true, this);
        fullCategoryRV.setLayoutManager(new GridLayoutManager(this, 1));
        fullCategoryRV.setAdapter(categoryAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
