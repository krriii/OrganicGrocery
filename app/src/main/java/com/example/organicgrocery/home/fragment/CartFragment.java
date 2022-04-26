package com.example.organicgrocery.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.AllProductResponse;
import com.example.organicgrocery.api.response.Product;
import com.example.organicgrocery.api.response.RegisterResponse;
import com.example.organicgrocery.checkout.CheckOutActivity;
import com.example.organicgrocery.home.fragment.home.adapters.ShopAdapter;
import com.example.organicgrocery.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    RecyclerView allProductRV;
    List<Product> products;
    TextView totalPriceTv;
    SwipeRefreshLayout swipefreshlayout;
    LinearLayout addToCartLL;
    ImageView minusIV, plusIV;
    AllProductResponse allProductResponse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductRV = view.findViewById(R.id.allProductRV);
        totalPriceTv = view.findViewById(R.id.totalPriceTv);
        swipefreshlayout = view.findViewById(R.id.swipefreshlayout);
        addToCartLL = view.findViewById(R.id.addToCartLL);
        minusIV = view.findViewById(R.id.minusIV);
        plusIV = view.findViewById(R.id.plusIV);
        addToCartLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allProductResponse != null && allProductResponse.getProducts().size() > 0) {
                    Intent intent = new Intent(getContext(), CheckOutActivity.class);
                    intent.putExtra(CheckOutActivity.CHECK_OUT_PRODUCTS, allProductResponse);
                    getContext().startActivity(intent);

                }
            }
        });
        swipefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipefreshlayout.setRefreshing(true);
                getCartItems();
            }
        });

        getCartItems();
    }

    private void getCartItems() {
        String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
        Call<AllProductResponse> cartItemCall = ApiClient.getClient().getMyCart(key);
        cartItemCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                swipefreshlayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        allProductResponse = response.body();
                        products = response.body().getProducts();
                        loadCartList();
                        setPrice(products);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                swipefreshlayout.setRefreshing(false);
            }
        });
    }

    private void setPrice(List<Product> data) {
        List<Product> newData = data;
        double totalPrice = 0;
        for (int i = 0; i < newData.size(); i++){
            Product product = newData.get(i);
            int price = product.getDiscountPrice();
            int q = product.getCartQuantity();
            if(products.get(i).getDiscountPrice() != 0 || products.get(i).getDiscountPrice() != null)
                totalPrice = totalPrice + price * q;
            else
                totalPrice = totalPrice + price * q;
        }
        totalPriceTv.setText("Rs. " + totalPrice);
    }


//    private void setPrice() {
//        double totalPrice = 0;
//        for (int i = 0; i < products.size(); i++) {
//            if (products.get(i).getDiscountPrice() != 0 || products.get(i).getDiscountPrice() != null)
//                totalPrice = totalPrice + products.get(i).getDiscountPrice();
//            else
//                totalPrice = totalPrice + products.get(i).getPrice();
//        }
//        totalPriceTv.setText("( Rs. " + totalPrice + " )");
//    }


    private void loadCartList() {
        allProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(), true);
        shopAdapter.setCartItemClick(new ShopAdapter.CartItemClick() {
            @Override
            public void onRemoveCart(int position) {
                String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
                Call<RegisterResponse> removeCartCall = ApiClient.getClient().deleteFromCart(key, products.get(position).getCartID());
                removeCartCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            products.remove(products.get(position));
                            shopAdapter.notifyItemRemoved(position);
                            setPrice(products);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });

            }

            public void onCartAdd(int position) {
                String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
                Call<RegisterResponse> removeCartCall = ApiClient.getClient().deleteFromCart(key, products.get(position).getCartID());
            }
            });
        allProductRV.setAdapter(shopAdapter);
    }

    public void onResume() {
        super.onResume();
        getCartItems();
    }
}