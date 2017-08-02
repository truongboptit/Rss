package com.example.truongle.rss.weather.view;

import com.example.truongle.rss.weather.model.WeatherOnWeekModel.Example;
import com.example.truongle.rss.weather.model.WeatherOnWeekModel.List;
import com.example.truongle.rss.weather.model.current_model.CurrentDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TruongLe on 27/07/2017.
 */

public interface apiWeather {
    @GET("/data/2.5/weather?mode=json&units=metric&appid=3218b80b6d9928dc446e7630550c19b6")
    Call<CurrentDataResponse> getWeather(@Query("q") String location);

    @GET("/data/2.5/forecast/daily?mode=json&units=metric&appid=3218b80b6d9928dc446e7630550c19b6")
    Call<Example> getData7Day(@Query("q") String location);


}
