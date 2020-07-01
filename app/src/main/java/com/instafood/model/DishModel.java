package com.instafood.model;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

public class DishModel {
    public static final DishModel instance = new DishModel();

    public interface Listener<T>{
        void onComplete(T data);
    }
    private DishModel(){

    }

    // testing list
    List<Dish> dishes = new LinkedList<>();

    public DishModel(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void getAllDishes(final Listener<List<Dish>> Listener)
    {
        class AsyTask extends AsyncTask<String, String, String>{
            List<Dish> data;
            @Override
            protected String doInBackground(String... strings) {
                // fillDishes();
                data = AppLocalDb.db.dishDao().getAll();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Listener.onComplete(data);

            }
        }
        AsyTask task = new AsyTask();
        task.execute();

    }

//    public void getAllByCook(String cookID){
//        return null;
//    }

    public Dish getDish(String id){return null;}

    public void update(Dish dish){
        AppLocalDb.db.dishDao().insertAll(dish);
    }
    public boolean delete(Dish dish){
        return true;
    }

    public void fillDishes(){
        for(int i = 1; i<=10; i++){
            AppLocalDb.db.dishDao().insertAll(new Dish(""+i, "dish "+i,"Yummy Yummy this dish is so good :) "+i+" dish is just delicious. I love making it so much yum yum", "", "1", "", "stuff 1 \n stuff 2 \n "+i, "stuff 1 \n stuff 2 \n "+i));
        }
    }
}
