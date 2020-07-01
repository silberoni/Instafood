package com.instafood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.instafood.model.Dish;

public class MainActivity extends AppCompatActivity implements DishListFragment.delegate, DishDetailsFragment.delegate {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        // main_frgmnt_container
        DishListFragment dlfrgmnt = new DishListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frgmnt_container, dlfrgmnt, "PUTMEIT");
        transaction.commit();
    }

    void openDishDetails(Dish dish) {
        DishDetailsFragment dfrgmnt = new DishDetailsFragment();
        dfrgmnt.setDish(dish);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                R.animator.slide_in_right, R.animator.slide_out_left);
        transaction.replace(R.id.main_frgmnt_container, dfrgmnt, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Dish dish) {
        openDishDetails(dish);
    }
    void openEditDetails(Dish dish) {
        DishDetailsFragment ddfrgmnt = new DishDetailsFragment();
        ddfrgmnt.setDish(dish);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                R.animator.slide_in_right, R.animator.slide_out_left);
        transaction.replace(R.id.main_frgmnt_container, ddfrgmnt, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }

    @Override
    public void onItemEdit(Dish dish) {
        openDishEdit(dish);
    }
    void openDishEdit(Dish dish) {
        DishEditFragment defrgmnt = new DishEditFragment();
        defrgmnt.setDish(dish);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                R.animator.slide_in_right, R.animator.slide_out_left);
        transaction.replace(R.id.main_frgmnt_container, defrgmnt, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }
}