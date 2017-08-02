package com.example.truongle.rss.weather.presenter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TruongLe on 27/07/2017.
 */

public class BuildRetrofit {
    public static String baseUrl = "http://api.openweathermap.org";
    public static Retrofit onBuildRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static String geturlImageOpenWeatherMap(String iconCode){
        return "http://openweathermap.org/img/w/" + iconCode + ".png";
    }
}
