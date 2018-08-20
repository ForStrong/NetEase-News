package com.h520t.wangyinews.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class JsonUtil {
    private static Gson mGson;

    public static <T> T parseJson(String json, Class<T> t){
        if (mGson == null){
            mGson = new Gson();
        }
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return  mGson.fromJson(json, t);

    }
}
