package com.h520t.wangyinews.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.h520t.wangyinews.R;
import com.h520t.wangyinews.service.DownloadImgService;
import com.h520t.wangyinews.splashScreen.javaBean.Ads;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.JsonUtil;
import com.h520t.wangyinews.util.SharedPreferenceUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SplashActivity extends AppCompatActivity {
    private ImageView ads;
    private final OkHttpClient mClient = new OkHttpClient();
    private static final String ADS_CONTENT = "ads_content";
    private static final String NEXT_REQ = "next_req";
    private static final String NOW_TIME = "now_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ads = findViewById(R.id.adsView);
        getAds();
    }

    public void getAds(){
        if(getSharedPreferences(SharedPreferenceUtil.ADS_JSON,MODE_PRIVATE)==null) {
            getHttp();
        }else{
            Long lastTime = SharedPreferenceUtil.getLong(SplashActivity.this, NOW_TIME);
            Long nowTime = System.currentTimeMillis();
            int next_req = SharedPreferenceUtil.getInt(SplashActivity.this,NEXT_REQ);
            if((nowTime - lastTime)>next_req*60*1000){
                getHttp();
            }
        }

    }

    public void getHttp(){
        Log.i("h520it", "getAds: 11111");
        //请求
        Request request = new Request.Builder().url(Contants.SPLASH_URL).build();

        //异步回调
        mClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try (ResponseBody responseBody = response.body()) {

                    if (!response.isSuccessful()) {
                        //请求失败
                    }
                    //拿到json解析的字符串
                    String data = responseBody.string();
                    //                    Log.i("520it", "onResponse: "+data);
                    //把字符串用Gson解析成Ads对象
                    Ads ads = JsonUtil.parseJson(data, Ads.class);
                    if (ads!=null) {
                        SharedPreferenceUtil.setString(SplashActivity.this,ADS_CONTENT,data);
                        SharedPreferenceUtil.setInt(SplashActivity.this,NEXT_REQ,ads.getNext_req());
                        SharedPreferenceUtil.setLong(SplashActivity.this,NOW_TIME, System.currentTimeMillis());
                        //通过IntentService下载图片
                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, DownloadImgService.class);
                        intent.putExtra(DownloadImgService.ADS_DATA, ads);
                        startService(intent);
                    }

                }
            }
        });


    }

}
