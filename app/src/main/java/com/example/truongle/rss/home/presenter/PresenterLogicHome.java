package com.example.truongle.rss.home.presenter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

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

/**
 * Created by TruongLe on 26/07/2017.
 */

public class PresenterLogicHome implements PresenterImplHome{
    ViewHome viewHome;
    RecyclerView recyclerView;
    Context context;
    private XmlPullParserFactory xmlFactoryObject;
    ArrayList<News> listNews = new ArrayList<>();

    public PresenterLogicHome(ViewHome viewHome,RecyclerView recyclerView, Context context) {
        this.viewHome = viewHome;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    public void onProcess(String url) {
        new AsyncTaskRss().execute(url);

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

    public class AsyncTaskRss extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected void onPreExecute() {
           viewHome.startDialog();
        }

        @Override
        protected ArrayList<News> doInBackground(String... params) {
            ArrayList<News> list = new ArrayList<>();
            String urlRss = params[0];
            URL url = null;
            try {
                url = new URL(urlRss);
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

                ParseRss parseRss = new ParseRss();
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
            HomeAdapter adapter = new HomeAdapter(newses, context);
            recyclerView.setAdapter(adapter);
           viewHome.stopDialog();
        }
    }
}
