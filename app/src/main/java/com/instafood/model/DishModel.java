package com.instafood.model;

import java.util.LinkedList;
import java.util.List;

public class DishModel {
    public static final DishModel instance = new DishModel();

    // When used will be used like this: DishModel.instance.METHOD

    // testing list
    List<Dish> dishes = new LinkedList<>();

    public DishModel(List<Dish> dishes) {
        this.dishes = dishes;
    }

    private DishModel(){
        for (int i=0; i<10; i++){
            Dish d = new Dish(""+i, "dish "+i, "", "1", "");
            dishes.add(d);
        }
    }
    public List<Dish> getAllDishes(){
        return dishes;
    }

    public List<Dish> getAllByCook(String cookID){
        return null;
    }
}
