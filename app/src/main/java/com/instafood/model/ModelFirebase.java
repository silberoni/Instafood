package com.instafood.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
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

    public ModelFirebase()
    {
        db = FirebaseFirestore.getInstance();
    }

    public void SaveDishInDb(String id, String name, String desc, String imgUrl, String makerID, String basedOn,
                             String ingredients, String instructions, int likes, boolean checked, boolean deleted)
    {
        // If ModelFirebase hasn't been initiated yet
        ModelFirebase modelFirebase = new ModelFirebase();

        // Create a new user with a first and last name
        Map<String, Object> currDish = new HashMap<>();
        currDish.put("id", id);
        currDish.put("name", name);
        currDish.put("desc", desc);
        currDish.put("imgUrl", imgUrl);
        currDish.put("makerID", makerID);
        currDish.put("basedOn", basedOn);
        currDish.put("ingredients", ingredients);
        currDish.put("instructions", instructions);
        currDish.put("likes", likes);
        currDish.put("checked", checked);
        currDish.put("deleted", deleted);
        currDish.put("lastUpdated", FieldValue.serverTimestamp());

        // Add a new document with a generated ID
        ModelFirebase.db.collection("dishes")
                .add(currDish)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public static void getAllDishesSince(long since, final DishModel.Listener<List<Dish>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DISH_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Dish> dshData = null;
                if (task.isSuccessful()) {
                    dshData = new LinkedList<Dish>();
                    // Adds all of the current items in the db, maps them from json and adds an object to the linked list
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Dish dish = doc.toObject(Dish.class);
                        if (dish.id != null && !dish.deleted) {
                            dshData.add(dish);
                        }
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
        Dish dsh = new Dish();
        dsh.id = (String)json.get("id");
        dsh.name = (String)json.get("name");
        dsh.imgUrl = (String)json.get("imgUrl");
        dsh.desc = (String)json.get("desc");
        dsh.makerID = (String)json.get("makerID");
        dsh.basedOn = (String)json.get("basedOn");
        dsh.ingredients = (String)json.get("ingredients");
        dsh.instructions = (String)json.get("instructions");
        dsh.checked = (boolean)json.get("checked");
        dsh.deleted = (boolean)json.get("deleted");
        Timestamp ts = (Timestamp)json.get("lastUpdated");
        if (ts != null) dsh.lastUpdated = ts.getSeconds();
        return dsh;
    }

    private static Map<String, Object> toJson(Dish dsh) {
        // TODO: fill with correct data
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", dsh.id);
        result.put("name", dsh.name);
        result.put("imgUrl", dsh.imgUrl);
        result.put("basedOn", dsh.basedOn);
        result.put("makerID", dsh.makerID);
        result.put("ingredients", dsh.ingredients);
        result.put("instructions", dsh.instructions);
        result.put("desc", dsh.desc);
        result.put("checked", dsh.checked);
        result.put("deleted", dsh.deleted);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }
}
