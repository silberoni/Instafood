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
import android.view.MenuItem;

import com.instafood.model.Dish;

public class MainActivity extends AppCompatActivity implements DishListFragment.delegate {
    public static Context context;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        navController = Navigation.findNavController(this, R.id.navigation_graph);
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public void onItemSelected(Dish dish) {
        navController = Navigation.findNavController(this, R.id.navigation_graph);
        DishListFragmentDirections.ActionDishListFragmentToDishDetailsFragment dir = DishListFragmentDirections.actionDishListFragmentToDishDetailsFragment(dish);
        navController.navigate(dir);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            navController.navigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}