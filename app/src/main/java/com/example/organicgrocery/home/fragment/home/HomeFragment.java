package com.example.organicgrocery.home.fragment.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.AllProductResponse;
import com.example.organicgrocery.api.response.Category;
import com.example.organicgrocery.api.response.CategoryResponse;
import com.example.organicgrocery.api.response.Product;
import com.example.organicgrocery.api.response.Slider;
import com.example.organicgrocery.api.response.SliderResponse;
import com.example.organicgrocery.home.fragment.home.adapters.CategoryAdapter;
import com.example.organicgrocery.home.fragment.home.adapters.ShopAdapter;
import com.example.organicgrocery.home.fragment.home.adapters.SliderAdapter;
import com.example.organicgrocery.search.SearchActivity;
import com.example.organicgrocery.utils.DataHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView allProductRV, categoryRV;
    ProgressBar loadingProgress;
    SliderView imageSlider;
    TextView viewAllTV, searchIt;
    LinearLayout searchLL;
    BottomNavigationView bottomNavigationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void setBottomNavigationView(BottomNavigationView bottomNavigationView){
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductRV = view.findViewById(R.id.allProductRV);
        categoryRV = view.findViewById(R.id.categoryRV);
        loadingProgress = view.findViewById(R.id.loadingProgress);
        imageSlider = view.findViewById(R.id.imageSlider);
        viewAllTV = view.findViewById(R.id.viewAllTV);
        searchLL = view.findViewById(R.id.searchLL);
        searchIt = view.findViewById(R.id.searchIt);
        serverCall();
        getCategoriesOnline();
//        getProductOnline();
        getSliders();
        setClickListeners();
        searchClickListeners();


    }

    private void searchClickListeners() {
        searchLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setClickListeners() {
        viewAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.categoryMenu);

            }
        });
        searchLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getSliders() {
        Call<SliderResponse> sliderResponseCall = ApiClient.getClient().getSliders();
        sliderResponseCall.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if (response.isSuccessful()){
                    if  (!response.body().getError()){
                        setSliders(response.body().getSliders());
                    }
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {

            }
        });
      

    }

    private void setSliders(List<Slider> sliders) {
        SliderAdapter sliderAdapter = new SliderAdapter(sliders, getContext());
        sliderAdapter.setClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onSliderClick(int position, Slider slider) {
                Toast.makeText(getContext(), "from This is item in position " + position, Toast.LENGTH_SHORT).show();

            }
        });
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

    }



    private void getCategoriesOnline() {
        Call<CategoryResponse> categoriesResponse = ApiClient.getClient().getCategories();
        categoriesResponse.enqueue(new Callback<CategoryResponse>() {

            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategories(response.body().getCategories());
                    }
                }
            }


            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }
    private void showCategories(List<Category> categories) {
        List<Category> temp;
        if (categories.size() > 8 ){
            temp = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                temp.add(categories.get(categories.size() - i - 1));
        }
        } else
            temp = categories;

        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(temp, getContext(), true, false, null);
        categoryRV.setAdapter(categoryAdapter);
    }
     public void toggleProgress(Boolean toogle){
         if (toogle)
             loadingProgress.setVisibility(View.VISIBLE);
         else
             loadingProgress.setVisibility(View.GONE);
     }





    private void serverCall() {
    toggleProgress(true);
       Call<AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();

       allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
           @Override
           public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
               toggleProgress(false);
               if (response.isSuccessful()) {
                   setProductRecyclerView(response.body().getProducts());
                  System.out.println(response.body().getProducts().toString());
               }
           }

           @Override
           public void onFailure(Call<AllProductResponse> call, Throwable t) {
               toggleProgress(false);
               Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

           }
       });

    }

    private void setProductRecyclerView(List<Product> products) {
        allProductRV.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(), false);
        allProductRV.setAdapter(shopAdapter);
    }


}