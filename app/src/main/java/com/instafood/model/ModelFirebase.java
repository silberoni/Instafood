package com.instafood.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ModelFirebase {

    public static FirebaseFirestore db;

    public ModelFirebase()
    {
        db = FirebaseFirestore.getInstance();
//
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

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

        // Add a new document with a generated ID
        ModelFirebase.db.collection("dishes")
                .add(currDish)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w("TAG", "Error adding document", e);
                    }
                });
    }

}
