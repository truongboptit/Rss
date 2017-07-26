package com.example.truongle.rss.home.presenter;

import com.example.truongle.rss.home.model.News;

import org.xmlpull.v1.XmlPullParser;

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

    private ArrayList<News> list;
    public boolean insideItem = false;

    public ParseRss() {

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
                                int j = description_tag.indexOf("jpg");
                                if(i>0 && j>0){
                                    String str = description_tag.substring(i+5, j+3);
                                    image = str;
                                }

                                int m= description_tag.indexOf("/br>");
                                if(m>0) {
                                    String str_desc  = description_tag.substring(m+4);
                                    description = str_desc;
                                }
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
}
