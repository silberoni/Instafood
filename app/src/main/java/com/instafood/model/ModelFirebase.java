package com.instafood.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {

    public static FirebaseFirestore db;
    final static String DISH_COLLECTION = "dishes";

    // TODO: seperate
    public ModelFirebase() {
        db = FirebaseFirestore.getInstance();
//
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
//
    }

    // Add a new document with a generated ID
    //db.collection("users")
    //        .add(user)
    //        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
    //            @Override
    //            public void onSuccess(DocumentReference documentReference) {
    //                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
    //            }
    //        })
    //        .addOnFailureListener(new OnFailureListener() {
    //            @Override
    //            public void onFailure(@NonNull Exception e) {
    //                Log.w("TAG", "Error adding document", e);
    //            }
    //        });
//
    // TODO: dish handeling
    // get all dishes/ all recent dishes
    public static void getAllDishesSince(long since, final DishModel.Listener<List<Dish>> listener) {
        Timestamp ts = new Timestamp(since, 0);
        db.collection(DISH_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Dish> dshData = null;
                if (task.isSuccessful()) {
                    dshData = new LinkedList<Dish>();
                    // Adds all of the current items in the db, maps them from json and adds an object to the linked list
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Map<String, Object> json = doc.getData();
                        Dish dish = dishFactory(json);
                        dshData.add(dish);
                    }
                }
                listener.onComplete(dshData);
                Log.d("NOTIFY", "refresh " + dshData.size());
            }
        });
    }

    public static void getAllDishes(final DishModel.Listener<List<Dish>> listener) {
        db.collection(DISH_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Dish> dshData = null;
                if (task.isSuccessful()) {
                    dshData = new LinkedList<Dish>();
                    // Adds all of the current items in the db, maps them from json and adds an object to the linked list
                    for (QueryDocumentSnapshot doc : task.getResult()) {
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
                if (listener != null) {
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    private static Dish dishFactory(Map<String, Object> json) {
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

    private static Map<String, Object> toJson(Dish dsh) {
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


    // get all dishes made by a chef
    // get a specific dish
    // save a dish
    // update a dish


    // db.collection("dishes")


    // TODO: chef handeling

    private static Chef chefFactory(Map<String, Object> json) {
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

    private static Map<String, Object> toJson(Chef chef) {
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
    // get all chefs
    // get specific chef
    // update a chef
    // add a chef
}




