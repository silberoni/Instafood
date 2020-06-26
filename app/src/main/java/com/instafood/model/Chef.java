package com.instafood.model;

public class Chef {
    String id;
    String name;
    String imgUrl;


    public Chef(String id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }


    // TODO decide how to create the connection between the cook and the dish (one to many relation)
    // TODO decide how to save dishes (for local storage)
}
