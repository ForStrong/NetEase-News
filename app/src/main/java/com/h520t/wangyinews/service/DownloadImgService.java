package com.h520t.wangyinews.service;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.h520t.wangyinews.splashScreen.SplashActivity;
import com.h520t.wangyinews.splashScreen.javaBean.Ads;
import com.h520t.wangyinews.splashScreen.javaBean.AdsBean;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.Md5Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadImgService extends IntentService {
    public static final String ADS_DATA = "ads";
    public DownloadImgService() {
        super("DownloadImgService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Ads ads = (Ads) intent.getSerializableExtra(ADS_DATA);
        List<AdsBean> adsBeans = ads.getAds();

        for (AdsBean adsBean  :adsBeans ) {
            List<String> res_url = adsBean.getRes_url();
            if (res_url!=null){
                //拿到图片的URL字符串
                String img_url = res_url.get(0);
                //把url字符串用MD5转换成一个唯一标识符
                String cache_name = Md5Helper.toMD5(img_url);
                if (img_url!=null){
                    //下载图片
                    downloadImg(img_url,cache_name);
                }
            }
        }
    }

    private void downloadImg(String img_url, String MD5_name) {
        try {
            //拿到url对象
            URL url = new URL(img_url);
            URLConnection urlConnection = url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            saveToSD(bitmap,MD5_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToSD(Bitmap bitmap, String md5_name) {
        if (bitmap == null) {
            return;
        }
        File directory = getCacheDir();
        File cacheFile = new File(directory, Contants.IMG_CACHE);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }

        File imgFile = new File(cacheFile, md5_name + ".jpg");
        if (imgFile.exists()) {
            return;
        }

        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream1);
            fileOutputStream1.flush();
            fileOutputStream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
