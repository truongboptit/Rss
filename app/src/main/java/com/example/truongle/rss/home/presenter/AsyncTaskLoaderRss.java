package com.example.truongle.rss.home.presenter;

import android.content.AsyncTaskLoader;
import android.content.Context;

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

public class AsyncTaskLoaderRss extends AsyncTaskLoader<ArrayList<News>> {
    ArrayList<News> list = new ArrayList<>();
    private XmlPullParserFactory xmlFactoryObject;
    public AsyncTaskLoaderRss(Context context) {
        super(context);
    }

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
        String urlString = "http://vnexpress.net/rss/tin-moi-nhat.rss";
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
}
