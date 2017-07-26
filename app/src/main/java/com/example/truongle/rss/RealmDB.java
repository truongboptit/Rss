package com.example.truongle.rss;

import android.content.Context;

import com.example.truongle.rss.model.News;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TruongLe on 24/07/2017.
 */

public class RealmDB {
    Context context;
    Realm realmDB;

    public RealmDB(Context context) {
        this.context = context;

        Realm.init(context);
        realmDB= Realm.getDefaultInstance();
    }
    public  RealmResults<News> getAllNews(){
         RealmResults<News> list = realmDB.where(News.class).findAll();
        return list;
    }
    public int getNextKey() {
        try {
            Number number = realmDB.where(News.class).max("id");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }
    public void bookMart(final News newsRealmDB){
        realmDB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                 newsRealmDB.setId(getNextKey());
                newsRealmDB.setClickBookMart(true);
                realmDB.copyToRealmOrUpdate(newsRealmDB);

            }
        });
    }
}
