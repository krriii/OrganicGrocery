package com.example.organicgrocery.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.userAccount.UserAccountActivity;
import com.example.organicgrocery.utils.SharedPrefUtils;


public class ProfileFragment extends Fragment {
    TextView logoutTV, adminAreaTV, addressTV;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       logoutTV = view.findViewById(R.id.logOutTV);
        adminAreaTV = view.findViewById(R.id.adminAreaTV);
        addressTV = view.findViewById(R.id.addressTV);
//        ordersTV = view.findViewById(R.id.ordersTV);
//        checkAdmin();
        setClickListeners();
    }

    private void setClickListeners() {
        logoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefUtils.clear(getContext());
                Intent userAccount = new Intent(getContext(), UserAccountActivity.class);
                startActivity(userAccount);
                getActivity().finish();
            }
        });

        addressTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserAccountActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

//        ordersTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), OrderActivity.class);
//                startActivity(intent);
//            }
//        });
//        ordersTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), OrderActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}