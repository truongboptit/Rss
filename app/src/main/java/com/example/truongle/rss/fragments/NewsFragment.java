package com.example.truongle.rss.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.truongle.rss.R;
import com.example.truongle.rss.home.model.News;
import com.example.truongle.rss.home.presenter.PresenterLogicHome;
import com.example.truongle.rss.home.view.ViewHome;

import java.util.ArrayList;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class NewsFragment extends Fragment implements ViewHome{

    RecyclerView mRecyclerView;
    private String finalUrl="http://vnexpress.net/rss/thoi-su.rss";
    ProgressDialog progressDialog;
    View footer_view;
    LinearLayoutManager layoutManager;
    boolean isLoading = false;
    private boolean loading = true;
    ArrayList<News> listData= new ArrayList<>();
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    PresenterLogicHome presenterLogicHome;
    private SwipeRefreshLayout refreshLayout;
    ArrayList<News> temp= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.news, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewNews);
        refreshLayout = (SwipeRefreshLayout)rootView. findViewById(R.id.swipeRefresh);
        progressDialog = new ProgressDialog(getContext());

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        presenterLogicHome = new PresenterLogicHome(this, mRecyclerView, getContext(),getActivity());
        //swipeRefreshLayout


        presenterLogicHome.onProcess(finalUrl);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //check top screen
                refreshLayout.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                presenterLogicHome.onRefresh(mRecyclerView,refreshLayout);

                //check bottom screen

            }
        });
        return rootView;
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void startDialog() {
        progressDialog.setMessage("Load...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void stopDialog() {
        progressDialog.dismiss();
    }


    @Override
    public void onSwipeRefreshLayout() {
        presenterLogicHome.onProcess(finalUrl);
        refreshLayout.setRefreshing(false);
    }

}
