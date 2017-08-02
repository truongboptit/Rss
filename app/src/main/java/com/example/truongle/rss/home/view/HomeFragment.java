package com.example.truongle.rss.home.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.truongle.rss.home.presenter.PresenterLogicHome;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class HomeFragment extends Fragment implements ViewHome{

    RecyclerView mRecyclerView;
    private String finalUrl="http://vnexpress.net/rss/tin-moi-nhat.rss";
    ProgressDialog progressDialog;
    View footer_view;
    LinearLayoutManager layoutManager;
    boolean isLoading = false;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    PresenterLogicHome presenterLogicHome;
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.trang_chu, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTrangChu);
        refreshLayout = (SwipeRefreshLayout)rootView. findViewById(R.id.swipeRefresh);
        progressDialog = new ProgressDialog(getContext());

        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer_view = inflater1.inflate(R.layout.footer_view, null);

        presenterLogicHome = new PresenterLogicHome(this, mRecyclerView, getContext());
        presenterLogicHome.onProcess(finalUrl);

        //swipeRefreshLayout
        //onRefreshLayout(rootView);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                refreshLayout.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                presenterLogicHome.onRefresh(mRecyclerView,layoutManager,refreshLayout);
            }
        });



//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//            {
//                if(dy > 0) //check for scroll down
//                {
//                    visibleItemCount = layoutManager.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
//
//                    if (loading)
//                    {
//                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
//                        {
//                            loading = false;
//
//                        }
//                    }
//                }
//            }
//        });
        return rootView;
    }

//    public void onRefreshLayout(View view){
//        refreshLayout = (SwipeRefreshLayout)view. findViewById(R.id.swipeRefresh);
//        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                presenterLogicHome.onProcess(finalUrl);
//                refreshLayout.setRefreshing(false);
//            }
//        });
//        //check refresh layout
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                refreshLayout.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
//            }
//        });
//
//    }
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
        presenterLogicHome.onProcess(finalUrl);
        refreshLayout.setRefreshing(false);
    }


}
