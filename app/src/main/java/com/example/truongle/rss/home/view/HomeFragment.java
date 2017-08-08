package com.example.truongle.rss.home.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.R;
import com.example.truongle.rss.home.model.News;
import com.example.truongle.rss.home.presenter.AsyncResponse;
import com.example.truongle.rss.home.presenter.PresenterLogicHome;

import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

/**
 * Created by TruongLe on 08/08/2017.
 */

public class HomeFragment extends Fragment implements ViewHome,AsyncResponse {
    private String finalUrl="http://vnexpress.net/rss/tin-moi-nhat.rss";
    ArrayList<News> list = new ArrayList<>();
    ArrayList<News> listData = new ArrayList<>();
    private XmlPullParserFactory xmlFactoryObject;
    private SwipeRefreshLayout refreshLayout;
    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    LinearLayoutManager layoutManager;
    public static final String TAG = "AAA";
    HomeAdapter adapter;
    boolean isLoading = false;
    PresenterLogicHome presenterLogicHome;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trang_chu, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTrangChu);
        refreshLayout = (SwipeRefreshLayout)rootView. findViewById(R.id.swipeRefresh);
        progressDialog = new ProgressDialog(getContext());

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        presenterLogicHome = new PresenterLogicHome(this, mRecyclerView,getContext());
        presenterLogicHome.onProcess(finalUrl);

        //get data
        presenterLogicHome.delegate= this;
        presenterLogicHome.getData(finalUrl);

        checkPositionRecyclerView();

        Log.d(TAG, "onCreateView: "+listData.size());
        return rootView;
    }

    public void checkPositionRecyclerView(){
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
                    // presenterLogicHome.loadMore();
                    presenterLogicHome.loadMore();
                    isLoading = true;
                }
            }
        });
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
    public void onLoadMore(final HomeAdapter adapter, final ArrayList<News> listLoadMore) {
        if(listLoadMore.size()<listData.size()){
            // if (temp.size() <= 20) {
            listLoadMore.add(null);
            adapter.notifyItemInserted(listLoadMore.size() - 1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listLoadMore.remove(listLoadMore.size() - 1);
                    adapter.notifyItemRemoved(listLoadMore.size());

                    //Generating more data
                    int index = listLoadMore.size();
                    int end = ((index + 10)<listData.size())?(index+10):listData.size();
                    for (int i = index; i < end; i++) {
                        listLoadMore.add(listData.get(i));
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
        checkPositionRecyclerView();
        presenterLogicHome.getData(finalUrl);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void getDataFromAsync(ArrayList<News> news) {
        if(listData.size()<25)
            listData.addAll(news);
    }
}
