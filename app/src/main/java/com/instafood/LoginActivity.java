package com.instafood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button btnSign, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFrag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.Loginfragment);

        SignupFragment signFrag = new SignupFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.Login_Container, signFrag, "TAG");
        transaction.commit();


        //init();

       //btnSign.setOnClickListener(new View.OnClickListener() {
       //    @override

       //    )};

    }

    private void init()
    {
        //btnLogin = findViewById(R.id.btnLogin);
        //btnSign = findViewById((R.id.btnSign);
    }

}