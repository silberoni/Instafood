package com.instafood.model;

import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

public class ChefModel {
    public static final ChefModel instance = new ChefModel();

    public interface Listener<T> {
        void OnComplete(T data);
    }

    private ChefModel() {
    }

    public void getChef(final String cid, final Listener<Chef> getListener) {

        class AsyTask extends AsyncTask<String, String, String> {
            Chef cdata;
            @Override
            protected String doInBackground(String... strings) {
                cdata = AppLocalDb.db.ChefDao().getChef(cid);
                if (cdata==null){
                    // if does not exist in local db, go to firebase
                    ModelFirebase.getChef(cid, new Listener<Chef>() {
                        @Override
                        public void OnComplete(Chef data) {
                            cdata = data;
                        }
                    });
                }
                getListener.OnComplete(cdata);
                return null;
            }
        }
        AsyTask task = new AsyTask();
        task.execute();
    }

    // TODO: maybe delete
    public void addChef(final Chef chef, final ChefModel.Listener<Boolean> listener) {
        class AsyTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.ChefDao().insertAll(chef);
                ModelFirebase.addChef(chef, listener);
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }
        AsyTask task = new AsyTask();
        task.execute();
    }

    public void update(final Chef chef) {
        class AsyTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.ChefDao().insertAll(chef);
                ModelFirebase.addChef(chef, null);
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

    public void authUser(String email, String pwd, final ChefModel.Listener<Boolean> listener){
        ModelFirebase.AuthUser(email, pwd, new ModelFirebase.Listener<Boolean>() {
            @Override
            public void OnComplete(Boolean data) {
                listener.OnComplete(data);
            }
        });
    }
    public void createUser(final String email,final  String pwd, final String name, final ChefModel.Listener<Boolean> listener){
        ModelFirebase.CreateUser(email, pwd,name, new ModelFirebase.Listener<Boolean>() {
            @Override
            public void OnComplete(Boolean data) {
                if (data){
                    // if created in the firebase, create in the local DB
                    Chef cchef = new Chef(email, name);
                    AppLocalDb.db.ChefDao().insertAll(cchef);
                    ModelFirebase.addChef(cchef, listener);
                    Log.d("NOTIFY", "Finished adding chef to the DB");
                }
                listener.OnComplete(data);
            }
        });
    }

}
