package com.instafood.model;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.instafood.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class DishModel {
    public static final DishModel instance = new DishModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface LDListener{
        void onComplete();
    }

    private DishModel() {
        fillDishes();
    }
    public void addDish(Dish dsh, Listener<Boolean> listener){
        ModelFirebase.addDish(dsh, listener);
    }

    // testing list
    // List<Dish> dishes = new LinkedList<>();

//    public DishModel(List<Dish> dishes) {
//        this.dishes = dishes;
//    }

    public LiveData<List<Dish>> getAllDishes() {
        LiveData<List<Dish>> liveData = AppLocalDb.db.dishDao().getAll();
        refreshDishList(null);
        return liveData;
    }

    public void refreshDishList(final LDListener listener){
        long LastUpdate = MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getLong("DishLastUpdateTime", 0);
        // ModelFirebase.getAllDishesSince(LastUpdate, new Listener<List<Dish>>() {
        ModelFirebase.getAllDishes(new Listener<List<Dish>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Dish> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        // Updates the local db itself and therefor doesn't need to return data

                        long lastUpdated = 0;
                        for(Dish d:data){
                            AppLocalDb.db.dishDao().insertAll(d);
                           // if(d.lastUpdated>lastUpdated) lastUpdated=d.lastUpdated;
                        }
                        SharedPreferences.Editor edit = MainActivity.context.getSharedPreferences("NOTIFY", MODE_PRIVATE).edit();
                        edit.putLong("DishLastUpdateTime", lastUpdated);
                        edit.commit();
                        return "";
                    }
                    @Override
                    protected void onPostExecute(String d) {
                        super.onPostExecute(d);
                        if (listener!=null)  listener.onComplete();
                    }
                }.execute("");
            }
        });

    }

    public Dish getDish(String id) {
        return null;
    }

    public void update(final Dish dish) {
        class AsyTask extends AsyncTask<String, String, String> {

            // TODO: need to insert and override in the firebase too
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
            Dish dsh = new Dish("" + i, "dish " + i, "Yummy Yummy this dish is so good :) " + i + " dish is just delicious. I love making it so much yum yum", "", "1", "", "stuff 1 \n stuff 2 \n " + i, "stuff 1 \n stuff 2 \n " + i);
            addDish(dsh, null);
        }
    }
}
