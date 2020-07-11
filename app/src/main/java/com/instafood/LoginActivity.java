package com.instafood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.instafood.model.ModelFirebase;

public class LoginActivity extends AppCompatActivity {

    private Button btnSign, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ModelFirebase modelFirebase = new ModelFirebase();
        FragmentTransaction fragmenTtransaction =  getSupportFragmentManager().beginTransaction();
        fragmenTtransaction.add(R.id.Login_Container, new LoginFragment());
        fragmenTtransaction.commit();


        //LoginFragment loginFrag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.Loginfragment);
//
        //SignupFragment signFrag = new SignupFragment();
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.add(R.id.Login_Container, signFrag, "TAG");
        //transaction.commit();


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