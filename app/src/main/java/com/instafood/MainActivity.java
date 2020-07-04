package com.instafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Context;
import android.os.Bundle;

import com.instafood.model.Dish;

public class MainActivity extends AppCompatActivity implements DishListFragment.delegate {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        if (savedInstanceState == null) {
            // main_frgmnt_container
            DishListFragment dlfrgmnt = new DishListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_frgmnt_container, dlfrgmnt, "PUTMEIT");
            transaction.commit();
        }
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
        //NavController navCtrl = Navigation.findNavController(this, R.id.navigation_graph);
        //navCtrl.navigate(R.id.action_dishListFragment_to_dishDetailsFragment);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}