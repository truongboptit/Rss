package com.example.truongle.rss.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.truongle.rss.R;
import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.home.model.News;
import com.example.truongle.rss.home.presenter.AsyncResponse;
import com.example.truongle.rss.home.presenter.PresenterLogicHome;
import com.example.truongle.rss.home.view.ViewHome;

import java.util.ArrayList;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class NewsFragment extends Fragment implements ViewHome, AsyncResponse{

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
        presenterLogicHome = new PresenterLogicHome(this, mRecyclerView, getContext());
        //get data
        presenterLogicHome.delegate= this;
        presenterLogicHome.getData(finalUrl);
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
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading&&pastVisibleItems + visibleItemCount >= totalItemCount) {
                    presenterLogicHome.loadMore();
                    isLoading = true;
                }
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
    public void onLoadMore(final HomeAdapter adapter, final ArrayList<News> temp) {
        if(temp.size()<listData.size()){
            // if (temp.size() <= 20) {
            temp.add(null);
            adapter.notifyItemInserted(temp.size() - 1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    temp.remove(temp.size() - 1);
                    adapter.notifyItemRemoved(temp.size());

                    //Generating more data
                    int index = temp.size();
                    int end = ((index + 10)<listData.size())?(index+10):listData.size();
                    for (int i = index; i < end; i++) {
                        temp.add(listData.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    isLoading= false;

                }
            }, 5000);
        } else {
            Toast.makeText(getContext(), "Loading data completed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSwipeRefreshLayout() {
        presenterLogicHome.onProcess(finalUrl);
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void getDataFromAsync(ArrayList<News> news) {
        if(listData.size()<25)
            listData.addAll(news);
    }
}
