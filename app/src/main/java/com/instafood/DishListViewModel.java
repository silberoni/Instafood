package com.instafood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.instafood.model.Dish;
import com.instafood.model.DishModel;

import java.util.List;

public class DishListViewModel extends ViewModel {
    LiveData<List<Dish>> liveDishData;
//
//    public LiveData<List<Dish>> getData() {
//        if (liveDishData == null) {
//            liveDishData = DishModel.instance.getAllDishes();
//        }
//        return liveDishData;
//    }
}
