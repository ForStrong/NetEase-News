package com.h520t.wangyinews.splashScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.h520t.wangyinews.util.HttpResponse;
import com.h520t.wangyinews.util.HttpUtil;
import com.h520t.wangyinews.util.JsonUtil;
import com.h520t.wangyinews.util.Md5Helper;
import com.h520t.wangyinews.util.SharedPreferenceUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ImageView ads_img;

    private static final String ADS_CONTENT = "ads_content";
    private static final String NEXT_REQ = "next_req";
    private static final String NOW_TIME = "now_time";
    private static final String INDEX_IMG = "index_img";

    private int index_img = 0;


    Handler handler;
    int now = 1;
    int total;
    int space = 200;
    TimeView timeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();


    }

    private void initView() {
        ads_img = findViewById(R.id.adsView);
        timeView = findViewById(R.id.timeView);
        handler = new myHandler(SplashActivity.this);
        getAds();
        showImg();

        timeView.setListener(new OnTimeClick() {
            @Override
            public void OnClickTime(View view) {
                handler.removeCallbacks(refreshRunnable);
                goToMain();
            }
        });
    }

    Runnable refreshRunnable  = new Runnable() {
        @Override
        public void run() {
            Message message = handler.obtainMessage(0);
            message.arg1 = now++;
            handler.sendMessage(message);
            handler.postDelayed(this,space);
        }
    };

    public Runnable noPhotoRunnable = new Runnable() {
        @Override
        public void run() {
            goToMain();
        }
    };

    private void goToMain() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

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
                                showTimeView();
                                ads_img.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        handler.removeCallbacks(refreshRunnable);
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
                                        finish();
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
            timeView.setVisibility(View.GONE);
            //没有图片，三秒跳到主页
            handler.postDelayed(noPhotoRunnable,3000);
        }
    }

    private void showTimeView() {
        //设置跳过空间可见
        timeView.setVisibility(View.VISIBLE);
        total = 2000/space;
        handler.post(refreshRunnable);
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

        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.getData(Contants.SPLASH_URL, new HttpResponse<String>(String.class) {
            @Override
            public void onError(String msg) {
                Log.i("h520it", "getHttp onError: ");
            }

            @Override
            public void onSuccess(String data) {

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

        });
    }

    static class myHandler extends Handler{
        WeakReference<Activity> mWeakReference;
        public myHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity act = (SplashActivity) mWeakReference.get();
            switch (msg.what) {
                case 0:
                    if (act.total>=act.now){
                        act.timeView.setProgress(act.total,act.now);
                    }else{
                        act.handler.removeCallbacks(act.refreshRunnable);
                        act.goToMain();
                    }
                    break;

            }
        }
    }





}
