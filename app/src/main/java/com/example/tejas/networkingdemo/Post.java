package com.example.tejas.networkingdemo;

/**
 * Created by charoo on 01-Oct-17.
 */

public class Post {

    int id;
    String title;
    String body;

    public Post(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
