package com.h520t.wangyinews.splashScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.h520t.wangyinews.MainActivity;
import com.h520t.wangyinews.R;
import com.h520t.wangyinews.service.DownloadImgService;
import com.h520t.wangyinews.splashScreen.javaBean.Action;
import com.h520t.wangyinews.splashScreen.javaBean.Ads;
import com.h520t.wangyinews.splashScreen.javaBean.AdsBean;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.FileUtil;
import com.h520t.wangyinews.util.JsonUtil;
import com.h520t.wangyinews.util.Md5Helper;
import com.h520t.wangyinews.util.SharedPreferenceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SplashActivity extends AppCompatActivity {
    private ImageView ads_img;
    private final OkHttpClient mClient = new OkHttpClient();
    private static final String ADS_CONTENT = "ads_content";
    private static final String NEXT_REQ = "next_req";
    private static final String NOW_TIME = "now_time";
    private static final String INDEX_IMG = "index_img";

    private int index_img = 0;

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ads_img = findViewById(R.id.adsView);

        mHandler = new Handler();

        getAds();
        showImg();

    }

    public Runnable noPhotoRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    public void showImg(){
        String s = SharedPreferenceUtil.getString(SplashActivity.this, ADS_CONTENT);
        if (!TextUtils.isEmpty(s)) {
            Ads ads = JsonUtil.parseJson(s, Ads.class);
            if (ads!=null) {
                List<AdsBean> adsBeans = ads.getAds();
                index_img = SharedPreferenceUtil.getInt(SplashActivity.this, INDEX_IMG);
                if (adsBeans != null&&adsBeans.size()>0){
                    final AdsBean adsBean = adsBeans.get(index_img%adsBeans.size());
                    if (adsBean!=null) {
                        List<String> res_url = adsBean.getRes_url();
                        String img_url = res_url.get(0);
                        if (img_url!=null && !TextUtils.isEmpty(img_url)) {
                            String md5_name = Md5Helper.toMD5(img_url);
                            File imgFile = FileUtil.getImgFile(SplashActivity.this,md5_name);
                            if (imgFile.exists()) {
                                Log.i("h520it", "showImg: "+imgFile.getAbsolutePath());
                                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                ads_img.setImageBitmap(bitmap);
                                ads_img.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Action action_params = adsBean.getAction_params();
                                        if (action_params!=null){
                                            String link_url = action_params.getLink_url();
                                            if (link_url!=null){
                                                Intent intent = new Intent();
                                                intent.putExtra(WebViewActivity.LINK_URL,link_url);
                                                intent.setClass(SplashActivity.this,WebViewActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                    index_img++;
                    SharedPreferenceUtil.setInt(SplashActivity.this,INDEX_IMG,index_img);
                }
            }
        }else{
            //没有图片，三秒跳到主页
            mHandler.postDelayed(noPhotoRunnable,3000);
        }
    }

    public void getAds(){
        String cache = SharedPreferenceUtil.getString(SplashActivity.this, ADS_CONTENT);
        if(TextUtils.isEmpty(cache)) {
            getHttp();
        }else{
            Long lastTime = SharedPreferenceUtil.getLong(SplashActivity.this, NOW_TIME);
            Long nowTime = System.currentTimeMillis();
            int next_req = SharedPreferenceUtil.getInt(SplashActivity.this,NEXT_REQ);
            if((nowTime - lastTime)>next_req*1000*60){
                getHttp();
            }
        }

    }

    public void getHttp(){
        Log.i("h520it", "getHttp: 11111");
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
