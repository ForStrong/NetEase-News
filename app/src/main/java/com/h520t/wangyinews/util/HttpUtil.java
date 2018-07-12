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
    OkHttpClient mClient;
    private HttpUtil(){
        mClient = new OkHttpClient();
    }

    public static HttpUtil getInstance(){
        return HttpUtilHolder.sHttpUtil;
    }

    private static class HttpUtilHolder{
        private static HttpUtil sHttpUtil = new HttpUtil();
    }

    public void getData(String aimUrl, final HttpResponse httpResponse){
        Request request = new Request.Builder().url(aimUrl).build();
        mClient.newCall(request).enqueue(new Callback() {
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
