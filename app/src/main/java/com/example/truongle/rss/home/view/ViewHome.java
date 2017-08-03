package com.example.truongle.rss.home.view;

import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.home.model.News;

import java.util.ArrayList;

/**
 * Created by TruongLe on 26/07/2017.
 */

public interface ViewHome {
    void startDialog();
    void stopDialog();
    void onLoadMore(HomeAdapter adapter, ArrayList<News> list);
    void onSwipeRefreshLayout();
}
