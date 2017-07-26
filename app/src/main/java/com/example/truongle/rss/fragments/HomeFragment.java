package com.example.truongle.rss.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.model.News;
import com.example.truongle.rss.ParseRss;
import com.example.truongle.rss.R;

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
 * Created by TruongLe on 23/07/2017.
 */

public class HomeFragment extends Fragment {

     ArrayList<News> list = new ArrayList<>();
    RecyclerView mRecyclerView;
    HomeAdapter adapter;
    private XmlPullParserFactory xmlFactoryObject;
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
        new AsyncTaskRss().execute(finalUrl);
//        PresenterLogicHome presenterLogicHome = new PresenterLogicHome(this,getContext());
//        presenterLogicHome.onProcess(mRecyclerView, finalUrl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public class AsyncTaskRss extends AsyncTask<String, Void, ArrayList<News>>{

        @Override
        protected void onPreExecute() {
           progressDialog.setMessage("Load...");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
            adapter = new HomeAdapter(newses, getContext());
            mRecyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

}
