package com.example.truongle.rss.home.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.R;
import com.example.truongle.rss.home.model.News;
import com.example.truongle.rss.home.presenter.PresenterLogicHome;
import com.example.truongle.rss.home.presenter.Provider;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by TruongLe on 08/08/2017.
 */

public class HomeFragment extends Fragment implements ViewHome {
    private String finalUrl="http://vnexpress.net/rss/tin-moi-nhat.rss";
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
        presenterLogicHome = new PresenterLogicHome(this, mRecyclerView,getContext(),getActivity());
        presenterLogicHome.onProcess(finalUrl);

        checkPositionRecyclerView();

        Provider provider = new Provider(getActivity(),finalUrl);
        provider.startLoader();

        String font = getFontNews();
        Log.d(TAG, "onCreateView: "+font);
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
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//                if (!isLoading&&pastVisibleItems + visibleItemCount >= totalItemCount) {
//                    // presenterLogicHome.loadMore();
//                    presenterLogicHome.loadMore();
//                    isLoading = true;
//                }
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
    public void onSwipeRefreshLayout() {
        presenterLogicHome.onProcess(finalUrl);
        checkPositionRecyclerView();
        refreshLayout.setRefreshing(false);
    }

    private String getFontNews() {
        SharedPreferences pre = getContext().getSharedPreferences("fontNews", Context.MODE_PRIVATE);
        String font = pre.getString("font","");
        Log.d("AAA", "getFontNews: "+font);
        return font;
    }
}
