package com.example.organicgrocery.checkout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.Address;
import com.example.organicgrocery.api.response.AllProductResponse;
import com.example.organicgrocery.api.response.Product;
import com.example.organicgrocery.api.response.RegisterResponse;
import com.example.organicgrocery.checkout.address.AddressActivity;
import com.example.organicgrocery.checkout.orderComplete.OrderCompleteActivity;
import com.example.organicgrocery.home.fragment.home.adapters.ShopAdapter;
import com.example.organicgrocery.utils.SharedPrefUtils;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    public static String CHECK_OUT_PRODUCTS = "sd";
    RecyclerView allProductRV;
    AllProductResponse allProductResponse;
    ImageView backIv, paymentkhaltiIV, cashodIV;
    RecyclerView allProductsRV;
    LinearLayout addressLL, checkOutLL;
    Address address;
    TextView emptyAddressTv, cityStreetTV, provinceTV, totalTV, subTotalTV, shippingTV, totalPriceTv, discountTV;
    List<Product> products;
    double subTotalPrice = 0;
    double shippingCharge = 100;
    int p_type = 1;
    String p_ref = "COD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        paymentkhaltiIV = findViewById(R.id.paymentkhaltiIV);
        cashodIV = findViewById(R.id.cashodIV);
        backIv = findViewById(R.id.backIv);
        emptyAddressTv = findViewById(R.id.emptyAddressTv);
        addressLL = findViewById(R.id.addressLL);
        checkOutLL = findViewById(R.id.checkOutLL);
        cityStreetTV = findViewById(R.id.cityStreetTV);
        provinceTV = findViewById(R.id.provinceTV);
        allProductsRV = findViewById(R.id.allProductsRV);
        totalTV = findViewById(R.id.totalTV);
        subTotalTV = findViewById(R.id.subTotalTV);
        shippingTV = findViewById(R.id.shippingTV);
        totalPriceTv = findViewById(R.id.totalPriceTv);
        discountTV = findViewById(R.id.discountTV);
        setClickListner();
        allProductResponse = (AllProductResponse) getIntent().getSerializableExtra(CHECK_OUT_PRODUCTS);
        products = allProductResponse.getProducts();
        loadCartList();


    }

    private void setClickListner() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, AddressActivity.class);
                startActivityForResult(intent, 1);

            }
        });
        emptyAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, AddressActivity.class);
                startActivityForResult(intent, 1);

            }
        });
        checkOutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address != null){
                    if (p_type == 1) {
                        checkOut();
                    }  else {
                        khaltiCheckOut();
                    }
                }
                else{
                    Toast.makeText(CheckOutActivity.this, "Please select valid address", Toast.LENGTH_SHORT).show();
                }
            }
        });

        paymentkhaltiIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p_type = 2;
                paymentkhaltiIV.setBackground(getResources().getDrawable(R.drawable.box_shape));
                cashodIV.setBackground(getResources().getDrawable(R.drawable.box));
            }
        });

    }

    private void checkOut() {
        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<RegisterResponse> orderCall = ApiClient.getClient().order(key, p_type, address.getId(), p_ref);
        orderCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(CheckOutActivity.this, OrderCompleteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    private void khaltiCheckOut() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchant_extra", "This is extra data");

        Config.Builder builder = new Config.Builder("test_public_key_849f4df6b17c443488feec333d045b8f", "" + products.get(0).getId(), products.get(0).getName(), (long) (subTotalPrice + shippingCharge) * 100, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                Toast.makeText(CheckOutActivity.this, errorMap.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                p_type = 2;
                p_ref = data.toString();
                checkOut();

            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.KHALTI);
                    add(PaymentPreference.EBANKING);
                    add(PaymentPreference.MOBILE_BANKING);
                    add(PaymentPreference.CONNECT_IPS);
                    add(PaymentPreference.SCT);
                }})
                .additionalData(map)
                .productUrl("https://bazarhub.com.np/router-ups")
                .mobile("9802778788");
        Config config = builder.build();
        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(this, config);
        khaltiCheckOut.show();

}


    private void loadCartList() {

        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ShopAdapter shopAdapter = new ShopAdapter(products, this, true);
        shopAdapter.setRemoveEnabled(false);
        allProductsRV.setAdapter(shopAdapter);
        setPrice();
    }

    private void setPrice() {
        double discount = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getDiscountPrice() != 0 || products.get(i).getDiscountPrice() != null) {
                subTotalPrice = subTotalPrice + products.get(i).getDiscountPrice();
                discount = discount + products.get(i).getPrice() - products.get(i).getDiscountPrice();
            } else
                subTotalPrice = subTotalPrice + products.get(i).getPrice();
        }
        subTotalTV.setText("Rs. " + (subTotalPrice));
        totalTV.setText("Rs. " + (subTotalPrice + shippingCharge));
        totalPriceTv.setText("( Rs. " + subTotalPrice + " )");
        shippingTV.setText("Rs. " + shippingCharge);
        discountTV.setText("-" + "Rs. " + discount);
    }

//    private void checkOut(double subTotalPrice) {
//        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
//        Call<RegisterResponse> orderCall = ApiClient.getClient().order(key, address.getId(),p_type, p_ref);
//        orderCall.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                if (response.isSuccessful()){
//                    Intent intent = new Intent(CheckOutActivity.this, OrderCompleteActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            if (data.getSerializableExtra(AddressActivity.ADDRESS_SELECTED_KEY) != null) {
                showSelectedAddress((Address) data.getSerializableExtra(AddressActivity.ADDRESS_SELECTED_KEY));

            }
        }
    }


    private void showSelectedAddress(Address address) {

        this.address = address;
        emptyAddressTv.setVisibility(View.GONE);
        cityStreetTV.setText(address.getCity() + " " + address.getStreet());
        provinceTV.setText(address.getProvince());
        addressLL.setVisibility(View.VISIBLE);
    }

//    private void checkOut(double finalPrice) {
//        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
//        Call<RegisterResponse> orderCall = ApiClient.getClient().order(key, p_type, address.getId(), p_ref);
//        orderCall.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                if (response.isSuccessful()) {
//                    Intent intent = new Intent(CheckOutActivity.this, OrderCompleteActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//
//            }
//        });
//    }
}
