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

    public interface LDListener {
        void onComplete();
    }

    private DishModel() {
        //fillDishes();
    }

    public void addDish(final Dish dsh, final Listener<Boolean> listener) {
        class AsyTask extends AsyncTask<String, String, String> {
            // TODO: need to insert and override in the firebase too
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.dishDao().insertAll(dsh);
                ModelFirebase.addDish(dsh, listener);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // TODO: check if possible to return true/false as success..
                Log.d("NOTIFY", "Added new dish to the DB");
            }
        }
        AsyTask task = new AsyTask();
        task.execute();
    }

    public LiveData<List<Dish>> getAllDishes() {
        refreshDishList(null);
        LiveData<List<Dish>> liveData = AppLocalDb.db.dishDao().getAll();
        return liveData;
    }

    public LiveData<List<Dish>> getDishesBy(String id) {
        refreshByList(null, id);
        LiveData<List<Dish>> liveData = AppLocalDb.db.dishDao().getByChef(id);
        return liveData;
    }

    public void refreshDishList(final LDListener listener) {
        long LastUpdate = MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getLong("DishLastUpdateTime", 0);
        ModelFirebase.getAllDishesSince(LastUpdate, new Listener<List<Dish>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Dish> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        // Updates the local db itself and therefore doesn't need to return data
                        long lastUpdated = 0;
                        for (Dish d : data) {
                            AppLocalDb.db.dishDao().insertAll(d);
                            if (d.lastUpdated > lastUpdated) {
                                Log.d("NOTIFY", String.valueOf("last updated changed to: " + lastUpdated));
                                lastUpdated = d.lastUpdated;
                            }
                        }
                        SharedPreferences.Editor edit = MainActivity.context.getSharedPreferences("NOTIFY", MODE_PRIVATE).edit();
                        edit.putLong("DishLastUpdateTime", lastUpdated);
                        edit.commit();
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String d) {
                        super.onPostExecute(d);
                        if (listener != null) listener.onComplete();
                    }
                }.execute("");
            }
        });

    }

    public void getEverything(final LDListener listener) {
        long LastUpdate = MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getLong("DishLastUpdateTime", 0);
        ModelFirebase.getAllDishes(new Listener<List<Dish>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Dish> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        // Updates the local db itself and therefore doesn't need to return data
                        for (Dish d : data) {
                            AppLocalDb.db.dishDao().insertAll(d);
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String d) {
                        super.onPostExecute(d);
                        if (listener != null) listener.onComplete();
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
                ModelFirebase.addDish(dish, null);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // TODO: check if possible to return true/false as success.
                Log.d("NOTIFY", "finished saving");
            }
        }
        AsyTask task = new AsyTask();
        task.execute();
    }

    public void refreshByList(final LDListener LDlistener, final String id) {
        ModelFirebase.getDishesBy(id, new Listener<List<Dish>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Dish> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (Dish d : data) {
                            AppLocalDb.db.dishDao().insertAll(d);

                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String d) {
                        super.onPostExecute(d);
                        if (LDlistener != null) LDlistener.onComplete();
                    }
                }. execute("");
            }
        });
    }
}
