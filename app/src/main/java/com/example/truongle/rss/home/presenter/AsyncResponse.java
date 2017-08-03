package com.example.truongle.rss.home.presenter;

import com.example.truongle.rss.home.model.News;

import java.util.ArrayList;

/**
 * Created by TruongLe on 02/08/2017.
 */

public interface AsyncResponse {
    void getDataFromAsync(ArrayList<News> news);
}
