package com.h520t.wangyinews.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class HttpUtil {
    static HttpUtil util;
    static OkHttpClient client;

    private HttpUtil(){
        client = new OkHttpClient();
    }

    //单例的方法
    public static HttpUtil getInstance(){
        if(util==null){
            synchronized (HttpUtil.class){
                if(util==null){
                    util = new HttpUtil();
                }
            }
        }
        return  util;
    }

    public void getData(String aimUrl, final HttpResponse httpResponse){
        Request request = new Request.Builder().url(aimUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                httpResponse.onError("连接服务器失败");
            }

            @Override
            public void onResponse(Call call, @NonNull Response response) throws IOException {

                try (ResponseBody responseBody = response.body()) {

                    if (!response.isSuccessful()) {
                        //请求失败
                    }
                    //拿到json解析的字符串
                    String data = responseBody.string();
                    httpResponse.parse(data);
                }

            }
        });
    }

}
