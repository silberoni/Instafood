package com.instafood.model;

import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

public class DishModel {
    public static final DishModel instance = new DishModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    private DishModel() {
    }

    // testing list
    List<Dish> dishes = new LinkedList<>();

    public DishModel(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void getAllDishes(final Listener<List<Dish>> getListener) {
        class AsyTask extends AsyncTask<String, String, String> {
            List<Dish> data;

            @Override
            protected String doInBackground(String... strings) {
                fillDishes();
                data = AppLocalDb.db.dishDao().getAll();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getListener.onComplete(data);
            }
        }
        AsyTask task = new AsyTask();
        task.execute();
    }

//    public void getAllByCook(String cookID){
//        return null;
//    }

    public Dish getDish(String id) {
        return null;
    }

    public void update(final Dish dish) {
        class AsyTask extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.dishDao().insertAll(dish);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // TODO: check if possible to return true/false as success.
                Log.d("TAG", "finished saving");
            }
        }
        AsyTask task = new AsyTask();
        task.execute();
    }

    public boolean delete(Dish dish) {
        return true;
    }

    public void fillDishes() {
        for (int i = 1; i <= 10; i++) {
            AppLocalDb.db.dishDao().insertAll(new Dish("" + i, "dish " + i, "Yummy Yummy this dish is so good :) " + i + " dish is just delicious. I love making it so much yum yum", "", "1", "", "stuff 1 \n stuff 2 \n " + i, "stuff 1 \n stuff 2 \n " + i));
        }
    }
}
