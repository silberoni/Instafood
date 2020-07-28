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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.instafood.model.Dish;

public class MainActivity extends AppCompatActivity{
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navController.navigateUp();
                break;
            case R.id.menu_add_dish:
                navController.navigate(DishAddFragmentDirections.actionGlobalDishAddFragment());
                break;
            case R.id.menu_home:
                navController.navigate(DishListFragmentDirections.actionGlobalDishListFragment());
                break;
            case R.id.menu_user:
                String Current =MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getString("CurrentUser", "");
                NavGraphDirections.ActionGlobalChefDetailsFragment action = ChefDetailsFragmentDirections.actionGlobalChefDetailsFragment();
                action.setChefId(Current);
                navController.navigate(action);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}