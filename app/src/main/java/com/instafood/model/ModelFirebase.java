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

    // TODO: seperate
    public ModelFirebase()
    {
        db = FirebaseFirestore.getInstance();
//
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
//
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
        // get all dishes made by a chef
        // get a specific dish
        // save a dish
        // update a dish



        // db.collection("dishes")




        // TODO: chef handeling
        // get all chefs
        // get specific chef
        // update a chef
        // add a chef
    }



}
