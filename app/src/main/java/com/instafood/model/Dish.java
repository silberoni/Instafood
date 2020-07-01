package com.instafood.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Dish {
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
