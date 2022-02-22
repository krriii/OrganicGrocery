package com.example.organicgrocery.singleProductScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.response.Product;
import com.example.organicgrocery.api.response.Slider;
import com.example.organicgrocery.home.fragment.home.adapters.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
//import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class SingleProductActivity extends AppCompatActivity {
    public static String key = "pKey";
    Product product;
    SliderView imageSlider;
    ImageView backIV;
    TextView name, price, desc, oldPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_single_product);
        backIV = findViewById(R.id.backIV);
        imageSlider = findViewById(R.id.productImageSlider);
        name = findViewById(R.id.productNameTV);
        price = findViewById(R.id.productPriceTV);
        desc = findViewById(R.id.descTV);
        oldPrice = findViewById(R.id.productOldPriceTV);
        setOnclickListners();
        if (getIntent().getSerializableExtra(key) != null) {
            product = (Product) getIntent().getSerializableExtra(key);
            setProduct(product);
        }


    }

    private void setOnclickListners() {
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    private void setProduct(Product product) {
        setSliders(product.getImages());
        name.setText(product.getName());
        if (product.getDiscountPrice() == 0 || product.getDiscountPrice() == null) {
            price.setText("Rs. " + product.getPrice());
            oldPrice.setVisibility(View.INVISIBLE);
        } else {
            price.setText("Rs. " + product.getDiscountPrice());
            oldPrice.setText("Rs. " + product.getPrice());
        }
        desc.setText((product.getDescription()));
    }

    private void setSliders(List<String> images) {
        List<Slider> sliders = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            Slider slider = new Slider();
            slider.setImage(images.get(i));
            sliders.add(slider);

        }
        SliderAdapter sliderAdapter = new SliderAdapter(sliders, this);
        sliderAdapter.setClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onSliderClick(int position, Slider slider) {

            }
        });
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);

    }
}









