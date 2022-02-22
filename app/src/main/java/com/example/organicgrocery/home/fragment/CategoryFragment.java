package com.example.organicgrocery.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.response.Category;
import com.example.organicgrocery.home.fragment.home.adapters.CategoryAdapter;
import com.example.organicgrocery.utils.DataHolder;

import java.util.List;


public class CategoryFragment extends Fragment {
    RecyclerView fullcategoryRV;
    ProgressBar categoryProgress;




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
        categoryProgress = view.findViewById(R.id.categoryProgress);
        if (DataHolder.categories != null) {
            showCategoryView(DataHolder.categories);
        }
    }




    private void showCategoryView(List<Category> categories){
    fullcategoryRV.setHasFixedSize(true);
    fullcategoryRV.setLayoutManager(new GridLayoutManager(getContext(), 4));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getContext(), true);
        fullcategoryRV.setAdapter(categoryAdapter);
}
}
