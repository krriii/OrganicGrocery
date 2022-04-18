package com.example.organicgrocery.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.home.fragment.CartFragment;
import com.example.organicgrocery.home.fragment.CategoryFragment;
import com.example.organicgrocery.home.fragment.MoreFragment;
import com.example.organicgrocery.home.fragment.ProfileFragment;
import com.example.organicgrocery.home.fragment.WishListFragment;
import com.example.organicgrocery.home.fragment.home.HomeFragment;
import com.example.organicgrocery.utils.UserInterfaceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    CartFragment cartFragment;
    WishListFragment wishListFragment;
    Fragment currentFragment;
    MoreFragment moreFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.homeBottomNav);
        homeFragment = new HomeFragment();
        homeFragment.setBottomNavigationView(bottomNavigationView);
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.homeFL, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals(getString(R.string.home))) {
                    if (homeFragment == null)
                        homeFragment = new HomeFragment();
                    changeFragment(homeFragment);
                    return true;

                }
                if (item.getTitle().equals("Category")){
                    if (categoryFragment == null)
                        categoryFragment = new CategoryFragment();
                    changeFragment(categoryFragment);
                    return true;

                }

                if (item.getTitle().equals("Cart")) {
                    if (cartFragment == null)
                        cartFragment = new CartFragment();
                    changeFragment(cartFragment);
                    return true;
                }
                if (item.getTitle().equals(getString(R.string.wishlist))) {
                    if (wishListFragment == null)
                        wishListFragment = new WishListFragment();
                    changeFragment(wishListFragment);
                    return true;

                }
                if (item.getTitle().equals(getString(R.string.more))) {
                    if (moreFragment == null)
                        moreFragment = new MoreFragment();
                    changeFragment(moreFragment);
                    return true;
                }
                return false;
            }
        });
    }
    public  void  onSearchClicked(View v){
        Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
    }

    void changeFragment(Fragment fragment) {
        //fragment talni hide garney show garney
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();//hide the fragment

//        if (fragment == profileFragment)
//            UserInterfaceUtils.changeStatusBarColor(this, true);
//        else
//            UserInterfaceUtils.changeStatusBarColor(this, false);

        if (fragment.isAdded()){
            getSupportFragmentManager().beginTransaction().show(fragment).commit();

        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.homeFL, fragment).commit();
        }
        currentFragment = fragment;

    }

}

