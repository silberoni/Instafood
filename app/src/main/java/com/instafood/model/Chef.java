package com.instafood.model;

import androidx.room.Entity;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Chef implements Serializable {
    @PrimaryKey
    @NonNull
    String id;
    String email;
    String name;
    String desc;
    String imgUrl;


    public Chef(){
    }

    public Chef(@NonNull String email, String name) {
        this.id = email;
        this.email=email;
        this.name = name;
        this.desc = "";
        this.imgUrl = "";
    }
    public Chef(@NonNull String email, String name, String desc) {
        this.id = email;
        this.email=email;
        this.name = name;
        this.desc = desc;
        this.imgUrl = "";
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
