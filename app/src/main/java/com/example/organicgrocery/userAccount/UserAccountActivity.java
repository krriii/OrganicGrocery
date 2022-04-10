package com.example.organicgrocery.userAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.userAccount.fragment.LoginFragment;
import com.example.organicgrocery.userAccount.fragment.RegisterFragment;

public class UserAccountActivity extends AppCompatActivity  implements View.OnClickListener {

    TextView registerTV, signInRegisterTV;
    boolean isRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        registerTV = findViewById(R.id.registerTV);
        signInRegisterTV = findViewById(R.id.signInRegisterTV);
        registerTV.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.formContainerFL, new LoginFragment()).commit();
    }

    @Override
    public void onClick(View view) {

        if (view == registerTV) {
            if (!isRegister) {

                getSupportFragmentManager().beginTransaction().replace(R.id.formContainerFL, new RegisterFragment()).commit();

            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.formContainerFL, new LoginFragment()).commit();
            }
            changeTexts();
            isRegister = !isRegister;
        }
    }



    void changeTexts() {
        if (!isRegister) {
            registerTV.setText("Login");
            signInRegisterTV.setText("Already Have an Account?");
        } else {
            registerTV.setText("Create an account");
            signInRegisterTV.setText("New User?");
        }
    }



}

