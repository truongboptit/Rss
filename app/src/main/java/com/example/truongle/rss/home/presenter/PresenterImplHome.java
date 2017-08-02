package com.example.truongle.rss.home.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by TruongLe on 26/07/2017.
 */

public interface PresenterImplHome {
    void onProcess(String url);
    void  onRefresh(RecyclerView recyclerView, LinearLayoutManager layoutManager,SwipeRefreshLayout refreshLayout);
}

