package com.instafood.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dish {
    @PrimaryKey
    @NonNull
    String id;
    String name;
    String imgUrl;
    String makerID;
    String basedOn;
    boolean checked;
    int likes;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public int getLikes() {
        return likes;
    }
// TODO decide how to create the connection between the dish and it's derivatives (optional)

    public Dish(String id, String name, String imgUrl, String makerID, String basedOn) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.makerID = makerID;
        this.basedOn = basedOn;
        this.likes = 0;
        this.checked = false;
    }
}
