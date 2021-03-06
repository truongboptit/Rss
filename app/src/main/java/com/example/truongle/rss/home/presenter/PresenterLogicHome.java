package com.example.truongle.rss.home.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
 * Created by TruongLe on 08/08/2017.
 */

public class PresenterLogicHome implements PresenterImplHome{

    ViewHome viewHome;
    RecyclerView mRecyclerView;
    HomeAdapter adapter;
    Context context;
    ArrayList<News> list = new ArrayList<>();
    ArrayList<News> listNews = new ArrayList<>();
    ArrayList<News> listLoadMore = new ArrayList<>();
    private XmlPullParserFactory xmlFactoryObject;
    FragmentActivity activity;

    public PresenterLogicHome(ViewHome viewHome, RecyclerView mRecyclerView, Context context, FragmentActivity activity) {
        this.viewHome = viewHome;
        this.mRecyclerView = mRecyclerView;
        this.context = context;
        this.activity= activity;
    }

    @Override
    public void onProcess(String url) {
        new AsyncTaskRss().execute(url);

    }

    @Override
    public void onRefresh(RecyclerView recyclerView, final SwipeRefreshLayout refreshLayout) {
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
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

    public class AsyncTaskRss extends AsyncTask<String , Void , ArrayList<News>> {


        @Override
        protected void onPreExecute() {
            viewHome.startDialog();
        }

        @Override
        protected ArrayList<News> doInBackground(String... params) {
            String urlString = params[0];
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
            String font  = getFontNews();
                adapter = new HomeAdapter(mRecyclerView,newses,context, font);
                mRecyclerView.setAdapter(adapter);
                viewHome.stopDialog();


        }
    }

    private String getFontNews() {
        SharedPreferences pre = context.getSharedPreferences("fontNews", Context.MODE_PRIVATE);
        String font = pre.getString("font","");
        Log.d("AAA", "getFontNews: "+font);
        return font;
    }
}
