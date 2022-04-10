package com.example.organicgrocery.userAccount.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.ApiClient;
import com.example.organicgrocery.api.response.RegisterResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment{
    EditText emailET, passwordET, confirmPassET, nameET;
    LinearLayout registerLL;
    ProgressBar circularProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailET = view.findViewById(R.id.emailSignup);
        passwordET = view.findViewById(R.id.signupPass);
        confirmPassET = view.findViewById(R.id.repasswordET);
        nameET = view.findViewById(R.id.nameET);
        circularProgress = view.findViewById(R.id.circularProgress);
        registerLL = view.findViewById(R.id.signinLL);
        registerLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    toggleProgress(true);
                    Call<RegisterResponse> registerCall = ApiClient.
                            getClient().register(nameET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString());

                    registerCall.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            RegisterResponse registerResponse = response.body();
                            toggleProgress(false);
                            if (response.isSuccessful()) {
                                System.out.println(registerResponse.getMessage());
                                Toast.makeText(getActivity(), registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (!registerResponse.getError()) {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.formContainerFL, new LoginFragment()).commit();

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            toggleProgress(false);
                            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }

    public void toggleProgress(Boolean toogle) {
        if (toogle)
            circularProgress.setVisibility(View.VISIBLE);
        else
            circularProgress.setVisibility(View.GONE);
    }

    public boolean validate() {
        boolean validated = true;

        if (emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty() || confirmPassET.getText().toString().isEmpty() || nameET.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Check the empty field", Toast.LENGTH_SHORT).show();
            validated = false;

        } else if (!confirmPassET.getText().toString().equals(passwordET.getText().toString())) {
            confirmPassET.setError("Password doesnot match with the above one");
            validated = false;

        }


        return validated;

    }

}