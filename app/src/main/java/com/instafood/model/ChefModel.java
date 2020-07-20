package com.instafood.model;

import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

public class ChefModel {
    public static final ChefModel instance = new ChefModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    private ChefModel() {
    }

    public void getChef(final Listener<Chef> getListener) {
        class AsyTask extends AsyncTask<String, String, String> {
            Chef data;

            @Override
            protected String doInBackground(String... strings) {
                data = AppLocalDb.db.ChefDao().getChef();
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

    public void addChef(final Chef chef, DishModel.Listener<Boolean> listener){
        class AsyTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                fillChefs();
                AppLocalDb.db.ChefDao().insertAll(chef);
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

    public void update(final Chef chef) {
        class AsyTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                fillChefs();
                AppLocalDb.db.ChefDao().insertAll(chef);
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

    public void fillChefs() {
        AppLocalDb.db.ChefDao().insertAll(new Chef("1","gas@on.com", "chef Gaston", "I'm the known chef Gaston and I'm happy to post my recipes here", ""));
    }
}
