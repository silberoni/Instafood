package com.instafood.model;

import androidx.room.Entity;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

@Entity
public class Chef {
    @PrimaryKey
    @NonNull
    String id;
    String name;
    String desc;
    String imgUrl;


    public Chef(@NonNull String id, String name, String desc, String imgUrl) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imgUrl = imgUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // TODO decide how to create the connection between the cook and the dish (one to many relation)
    // TODO decide how to save dishes (for local storage)
}
