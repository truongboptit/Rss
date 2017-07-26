package com.example.truongle.rss.home.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class News extends RealmObject implements Serializable {
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title, link, image_link, description, date;
    private boolean isClickBookMart = false;

    public boolean isClickBookMart() {
        return isClickBookMart;
    }

    public void setClickBookMart(boolean clickBookMart) {
        isClickBookMart = clickBookMart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public News(String title, String link, String image_link, String description, String date) {

        this.title = title;
        this.link = link;
        this.image_link = image_link;
        this.description = description;
        this.date = date;
    }

    public News() {

    }
}
