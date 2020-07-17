package com.instafood.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Dish implements Serializable {
    @PrimaryKey
    @NonNull
    String id;
    String name;
    String desc;
    String imgUrl;
    String makerID;
    String basedOn;
    String ingredients;
    String instructions;
    long lastUpdated;
    int likes;
    boolean checked;
    boolean deleted;


// TODO decide how to create the connection between the dish and it's derivatives (optional)


    public Dish(@NonNull String id, String name,String desc, String imgUrl, String makerID, String basedOn, String ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.makerID = makerID;
        this.basedOn = basedOn;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.likes = 0;
        // TODO: can we find a nice way to make checked? if not, delete.
        this.checked = false;
        this.deleted = false;
        this.lastUpdated = 0L;
    }
    public Dish() {
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

        // TODO: should change to DishFirebase? If not, can copy methods from DishFirebase to ModelFirebase
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

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getMakerID() {
        return makerID;
    }

    public String getBasedOn() {
        return basedOn;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
