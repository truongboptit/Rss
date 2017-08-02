package com.example.truongle.rss.weather.model;

/**
 * Created by TruongLe on 01/08/2017.
 */

public class RecyclerViewWeatherModel {
    private String image, days,date,temp, status;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecyclerViewWeatherModel() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RecyclerViewWeatherModel(String image, String days, String date, String temp, String status) {

        this.image = image;
        this.days = days;
        this.date = date;
        this.temp = temp;
        this.status = status;
    }
}
