package com.example.organicgrocery.admin.addCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.Category;
import com.example.organicgrocery.api.response.CategoryResponse;
import com.example.organicgrocery.api.response.RegisterResponse;
import com.example.organicgrocery.home.fragment.home.adapters.CategoryAdapter;
import com.example.organicgrocery.utils.DataHolder;
import com.example.organicgrocery.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryActivity extends AppCompatActivity {
    RecyclerView fullCategoryRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All Categories");
        fullCategoryRV = findViewById(R.id.fullCategoryRV);
        getOnline();
    }

    private void getOnline() {
        //        swipeRefresh.setRefreshing(true);
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
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
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this, false, false, this);
        fullCategoryRV.setLayoutManager(new GridLayoutManager(this, 1));
        fullCategoryRV.setAdapter(categoryAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove item from backing list here
                if (swipeDir > 0) {
                    String key = SharedPrefUtils.getString(ListCategoryActivity.this, getString(R.string.api_key));
                    Call<RegisterResponse> responseCall = ApiClient.getClient().deleteCategory(key, categories.get(viewHolder.getAdapterPosition()).getCategoryId());
                    responseCall.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            categories.remove(viewHolder.getAdapterPosition());
                            categoryAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {

                        }
                    });

                }
            }
        });
        itemTouchHelper.attachToRecyclerView(fullCategoryRV);
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