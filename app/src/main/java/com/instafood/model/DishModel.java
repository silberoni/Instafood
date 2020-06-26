package com.instafood.model;

import java.util.List;

public class DishModel {
    public static final DishModel instance = new DishModel();

    private DishModel(){

    }
    public List<Dish> getAllDishes(){
        return null;
    }

    public List<Dish> getAllByCook(String cookID){
        return null;
    }
}
