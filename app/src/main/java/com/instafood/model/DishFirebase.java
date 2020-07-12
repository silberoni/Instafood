package com.instafood.model;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DishFirebase {
    final static String DISH_COLLECTION = "dishes";

    public static void getAllDishesSince(long since, final DishModel.Listener<List<Dish>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since,0);
        db.collection(DISH_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Dish> dshData = null;
                if (task.isSuccessful()){
                    dshData = new LinkedList<Dish>();
                    // Adds all of the current items in the db, maps them from json and adds an object to the linked list
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Map<String,Object> json = doc.getData();
                        Dish dish = factory(json);
                        dshData.add(dish);
                    }
                }
                listener.onComplete(dshData);
                Log.d("NOTIFY","refresh " + dshData.size());
            }
        });
    }
    public static void getAllDishes(final DishModel.Listener<List<Dish>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DISH_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Dish> dshData = null;
                if (task.isSuccessful()){
                    dshData = new LinkedList<Dish>();
                    // Adds all of the current items in the db, maps them from json and adds an object to the linked list
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Dish dish = doc.toObject(Dish.class);
                        dshData.add(dish);
                    }
                }
                listener.onComplete(dshData);
            }
        });
    }

    public static void addDish(Dish dish, final DishModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Adds the new student by ID. Does override for update?
        db.collection(DISH_COLLECTION).document(dish.getId()).set(toJson(dish)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    private static Dish factory(Map<String, Object> json){
        // TODO: fill with correct data
//        Dish dsh = new Dish();
//
//        dsh.id = (String)json.get("id");
//        dsh.name = (String)json.get("name");
//        dsh.imgUrl = (String)json.get("imgUrl");
//        dsh.isChecked = (boolean)json.get("isChecked");
//        Timestamp ts = (Timestamp)json.get("lastUpdated");
//        if (ts != null) dsh.lastUpdated = ts.getSeconds();
//        return dsh;
        return null;
    }
    private static Map<String, Object> toJson(Dish dsh){
        // TODO: fill with correct data
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("id", st.id);
//        result.put("name", st.name);
//        result.put("imgUrl", st.imgUrl);
//        result.put("isChecked", st.isChecked);
//        result.put("lastUpdated", FieldValue.serverTimestamp());
//        return result;
        return null;
    }

}
