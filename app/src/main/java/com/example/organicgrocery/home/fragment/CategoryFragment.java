package com.example.organicgrocery.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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


public class CategoryFragment extends Fragment {
    RecyclerView fullcategoryRV;
    SwipeRefreshLayout swipeRefresh;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullcategoryRV = view.findViewById(R.id.fullCategoryRV);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        if (DataHolder.categories != null) {
            showCategoryView(DataHolder.categories);
        }
     else {
        getOnline();
    }
     swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
         @Override
         public void onRefresh() {
             getOnline();
         }
     });
    }

    private void getOnline() {
        swipeRefresh.setRefreshing(true);
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategoryView(response.body().getCategories());
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }


    private void showCategoryView(List<Category> categories){
    fullcategoryRV.setHasFixedSize(true);
    fullcategoryRV.setLayoutManager(new GridLayoutManager(getContext(), 4));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getContext(), true, false, null);
        fullcategoryRV.setAdapter(categoryAdapter);
}
}
