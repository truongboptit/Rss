package com.example.truongle.rss.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by TruongLe on 17/08/2017.
 */

public class DataPreferences {

    public static String getPreferences(Context context,String name_file, String property) {
        SharedPreferences pre = context.getSharedPreferences(name_file, MODE_PRIVATE);
        String font = pre.getString(property,"");
        return font;
    }

    public static void savePreferences(Context context,String name_file,String property,String tyle) {
        SharedPreferences pre = context.getSharedPreferences(name_file, MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putString(property,tyle);
        edit.commit();

    }
}
