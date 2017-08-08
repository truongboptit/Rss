package com.example.truongle.rss.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by TruongLe on 03/08/2017.
 */

public class CheckInternet {
    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
