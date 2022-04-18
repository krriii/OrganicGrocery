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

import com.example.organicgrocery.More.AboutUsActivity;
import com.example.organicgrocery.More.ContactUsActivity;
import com.example.organicgrocery.More.ProfileActivity;
import com.example.organicgrocery.More.TermsandconditionActivity;
import com.example.organicgrocery.R;
import com.example.organicgrocery.admin.AdminActivity;
import com.example.organicgrocery.userAccount.UserAccountActivity;
import com.example.organicgrocery.utils.SharedPrefUtils;

public class MoreFragment extends Fragment {
    TextView logoutTV;
    TextView profileTV;
    TextView adminAreaTV;
    TextView policiesTV;
    TextView aboutusTV, contactusTV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutTV = view.findViewById(R.id.logoutTV);
        profileTV = view.findViewById(R.id.profileTV);
        adminAreaTV = view.findViewById(R.id.adminAreaTV);
        policiesTV = view.findViewById(R.id.policiesTV);
        aboutusTV = view.findViewById(R.id.aboutusTV);
        contactusTV = view.findViewById(R.id.contactusTV);
        contactusOnClick();
        checkAdmin();
        setClickListeners();
        ProfileOnClick();
        PoliciesOnClick();
    }

    private void contactusOnClick() {

            contactusTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                    startActivity(intent);
                }
            });
        }


        private void PoliciesOnClick() {
            policiesTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(getActivity(), TermsandconditionActivity.class);
                    startActivity(intent);
                }
            });
            aboutusTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                    startActivity(intent);
                }
            });
        }

        private void checkAdmin() {
            boolean is_staff = SharedPrefUtils.getBool(getActivity(), getString(R.string.staff_key), false);
            if (is_staff)
                adminAreaTV.setVisibility(View.VISIBLE);

        }

        private void ProfileOnClick() {
            profileTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intent);

                }
            });
        }
        private void setClickListeners() {
            logoutTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPrefUtils.clear(getContext());
                    Intent userAccount = new Intent(getContext(), UserAccountActivity     .class);
                    startActivity(userAccount);
                    getActivity().finish();
                }
            });
            adminAreaTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AdminActivity.class);
                    startActivity(intent);
                }
            });


        }


}