package com.instafood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;

import java.util.List;

public class DishListViewModel extends ViewModel {
    LiveData<List<Dish>> liveDishData;
    LiveData<List<Dish>> ChefData;

    public LiveData<List<Dish>> getAllData() {
        if (liveDishData == null) {
            liveDishData = DishModel.instance.getAllDishes();
        }
        return liveDishData;
    }
    public LiveData<List<Dish>> getChefData(String id) {
        // static list, but uses live data
        ChefData = DishModel.instance.getAllDishes();
        return ChefData;
    }
    public void refresh(DishModel.LDListener listener) {
        DishModel.instance.refreshDishList(listener);
    }
}
