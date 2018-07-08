package com.h520t.wangyinews.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class SharedPreferenceUtil {
    public static final String ADS_JSON= "ads_json_string";

    public static void setString(Context context,String title,String content){
        SharedPreferences preferences = context.getSharedPreferences(ADS_JSON, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(title,content);
        editor.apply();

    }

    public static String getString(Context context,String title){
        SharedPreferences preferences = context.getSharedPreferences(ADS_JSON, Context.MODE_PRIVATE);
        String content = preferences.getString(title, "");
        return content;
    }

    public static void setInt(Context context,String title,int content){
        SharedPreferences preferences = context.getSharedPreferences(ADS_JSON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(title,content);
        editor.apply();
    }

    public static int getInt(Context context,String title){
        SharedPreferences preferences = context.getSharedPreferences(ADS_JSON, Context.MODE_PRIVATE);
        int i = preferences.getInt(title, 0);
        return i;
    }

    public static void setLong(Context context,String title,Long content){
        SharedPreferences preferences = context.getSharedPreferences(ADS_JSON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(title,content);
        editor.apply();
    }

    public static Long getLong(Context context,String title){
        SharedPreferences preferences = context.getSharedPreferences(ADS_JSON, Context.MODE_PRIVATE);
        long l = preferences.getLong(title, 0);
        return l;
    }


}
