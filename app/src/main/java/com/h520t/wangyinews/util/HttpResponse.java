package com.h520t.wangyinews.util;

import android.text.TextUtils;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public abstract class HttpResponse<T> {
    Class<T> t;

    public HttpResponse(Class<T> tClass){
        t = tClass;
    }
    public abstract void onError(String msg);
    public abstract void onSuccess(T t);

    public void parse(String data){
        if (TextUtils.isEmpty(data)){
            onError("连接网络失败");
            return;
        }

        if (t==String.class){
            onSuccess((T) data);
            return;
        }

        T result = JsonUtil.parseJson(data, this.t);

        if (result!=null) {
            onSuccess(result);
        }else{
            onError("Json解析失败");
        }
    }

}
