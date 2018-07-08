package com.h520t.wangyinews.util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class JsonUtil {
    static Gson mgson;

    public static <T> T parseJson(String json,Class<T> tclass){
        if (mgson == null){
            mgson = new Gson();
        }
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return mgson.fromJson(json, tclass);

    }
}
