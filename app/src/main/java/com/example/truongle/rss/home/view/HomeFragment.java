package com.example.truongle.rss.home.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.truongle.rss.R;
import com.example.truongle.rss.home.presenter.PresenterLogicHome;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class HomeFragment extends Fragment implements ViewHome{

    RecyclerView mRecyclerView;
    private String finalUrl="http://vnexpress.net/rss/tin-moi-nhat.rss";
    ProgressDialog progressDialog;
    View footer_view;
    boolean isLoading = false;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.trang_chu, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTrangChu);
        progressDialog = new ProgressDialog(getContext());

        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer_view = inflater1.inflate(R.layout.footer_view, null);


        PresenterLogicHome presenterLogicHome = new PresenterLogicHome(this, mRecyclerView, getContext());
        presenterLogicHome.onProcess(finalUrl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
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
    public void onLoadMore() {

    }

    @Override
    public void onSwipeRefreshLayout() {

    }


}
