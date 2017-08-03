package com.example.truongle.rss.home.presenter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.home.model.News;
import com.example.truongle.rss.home.view.ViewHome;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by TruongLe on 26/07/2017.
 */

public class PresenterLogicHome implements PresenterImplHome{
    ViewHome viewHome;
    RecyclerView recyclerView;
    Context context;
    HomeAdapter adapter;
    private XmlPullParserFactory xmlFactoryObject;
    ArrayList<News> listNews = new ArrayList<>();
    ArrayList<News> temp = new ArrayList<>();
    public AsyncResponse delegate = null;

    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;
    private boolean isLoading;

    public PresenterLogicHome(ViewHome viewHome,RecyclerView recyclerView, Context context) {
        this.viewHome = viewHome;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    public void onProcess(String url) {

        new AsyncTaskRss().execute(url);

        //check load more

    }

    public void loadMore2(){
        viewHome.onLoadMore(adapter,temp);
    }
//    public void loadMore(RecyclerView mRecyclerView){
//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
//                if (!isLoading&&pastVisibleItems + visibleItemCount >= totalItemCount) {
//                    viewHome.onLoadMore(adapter,temp);
//                    isLoading = true;
//                }
//            }
//        });
//    }
    public void setLoaded() {
    isLoading = false;
}

    @Override
    public void onRefresh(RecyclerView mRecyclerView, final LinearLayoutManager layoutManager, final SwipeRefreshLayout refreshLayout) {

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewHome.onSwipeRefreshLayout();
              //  refreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                refreshLayout.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
    }

    public void getData(String url){
        try {
            listNews  = new AsyncTaskRss().execute(url).get();
            delegate.getDataFromAsync(listNews);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        delegate.getDataFromAsync(listNews);
    }



    public class AsyncTaskRss extends AsyncTask<String , Void , ArrayList<News>>{


        @Override
        protected ArrayList<News> doInBackground(String... params) {
            String urlString = params[0];
            ArrayList<News> list = new ArrayList<>();
            URL url = null;
            try {
                url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();
                InputStream stream = conn.getInputStream();

                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myparser = xmlFactoryObject.newPullParser();

                myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                myparser.setInput(stream, null);
                ParseRss parseRss = new ParseRss(urlString);
                list = parseRss.parseXMLAndStoreIt(myparser);
                stream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<News> newses) {

                temp.clear();
                for (int i = 0; i < 10; i++)
                    temp.add(newses.get(i));
                adapter = new HomeAdapter(recyclerView, temp, context);
                recyclerView.setAdapter(adapter);
                viewHome.stopDialog();
            }


    }
}
