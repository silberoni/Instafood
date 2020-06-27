package com.instafood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.instafood.model.Dish;

public class MainActivity extends AppCompatActivity implements DishListFragment.delegate{
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        // main_frgmnt_container
        DishListFragment dlfrgmnt = new DishListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frgmnt_container, dlfrgmnt, "PUTMEIT");
        transaction.commit();

        // TODO: handle fragments with the fragments manager

    }

    void openDishDetails() {
//
//        DishDetailsFragment dfrgmnt = new DishDetailsFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.main_frgmnt_container, dfrgmnt, "TAG");
//        transaction.addToBackStack("TAG");
//        transaction.commit();
    }

    @Override
    public void onItemSelected(Dish dish) {

    }
}