package com.example.truongle.rss.home.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.truongle.rss.home.model.News;

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
 * Created by TruongLe on 08/08/2017.
 */

public class Provider implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    FragmentActivity activity;
    ArrayList<News> list = new ArrayList<>();
    ArrayList<News> list2 = new ArrayList<>();
    String finalUrl;

    public Provider(FragmentActivity activity, String url) {
        this.activity = activity;
        this.finalUrl = url;
    }

    private XmlPullParserFactory xmlFactoryObject;
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<News>>(activity) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public void deliverResult(ArrayList<News> data) {
                super.deliverResult(data);
            }

            @Override
            public ArrayList<News> loadInBackground() {
                URL url = null;
                try {
                    url = new URL(finalUrl);
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
                    ParseRss parseRss = new ParseRss(finalUrl);
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
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        Log.d("AAA", "onLoadFinished: "+data.size());

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }
    public void startLoader(){
        activity.getSupportLoaderManager().initLoader(1,null,this);

        Log.d("AAA", "startLoader: "+list2.size());
    }
}
