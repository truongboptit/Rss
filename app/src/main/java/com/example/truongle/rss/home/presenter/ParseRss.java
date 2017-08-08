package com.example.truongle.rss.home.presenter;

import android.util.Log;

import com.example.truongle.rss.home.model.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TruongLe on 25/07/2017.
 */

public class ParseRss {
    private String title ;
    private String link ;
    private String image;
    private String description ;
    private String date;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    private ArrayList<News> list;
    public boolean insideItem = false;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public ParseRss(String urlString) {
        this.urlString = urlString;
    }

    public ArrayList<News> getList() {
        return list;
    }

    public ArrayList<News> parseXMLAndStoreIt(XmlPullParser myParser) {
        list = new ArrayList<>();
        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {

                    if (myParser.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (myParser.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            title = myParser.nextText();
                    } else if (myParser.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            link = myParser.nextText();
                    }
                    else if (myParser.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem)
                            date = myParser.nextText();
                    }
                    else if (myParser.getName().equalsIgnoreCase("description")) {
                        if (insideItem)
                        {
                            String description_tag = myParser.nextText();
                            if(description_tag!= null) {
                                int i = description_tag.indexOf("src");
                                int j = description_tag.indexOf("</a>");
                                if(i==0||j==0)
                                Log.d("AAA", ""+i+"  "+j);
                                if(i>0 && j>0){
                                    String str = description_tag.substring(i+5, j-3);
                                    image = str;
                                }
                                int k= description_tag.indexOf("/br");
                                if(k>0)  description = description_tag.substring(k+4);
                            }
                        }
                    }


                } else if (event == XmlPullParser.END_TAG && myParser.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    News news = new News(title,link, image,description,date);
                    list.add(news);
                }
                event = myParser.next();
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }
return list;
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
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

                    parseXMLAndStoreIt(myparser);
                    stream.close();

                }

                catch (Exception e) {
                }
            }
        });
        thread.start();
    }
}
