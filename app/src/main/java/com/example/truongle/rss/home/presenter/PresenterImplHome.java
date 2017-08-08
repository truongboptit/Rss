package com.example.truongle.rss.home.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by TruongLe on 08/08/2017.
 */

public interface PresenterImplHome {
    void onProcess(String url);
    void  onRefresh(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout);
}
