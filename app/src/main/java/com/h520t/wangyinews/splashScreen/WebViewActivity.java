package com.h520t.wangyinews.splashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.h520t.wangyinews.MainActivity;
import com.h520t.wangyinews.R;
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends AppCompatActivity {
    public static String LINK_URL = "link_url";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.web_view);
        String link_url = getIntent().getStringExtra(LINK_URL);
        webView.loadUrl(link_url);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.i("it520","onReceivedError");
                super.onReceivedError(view, request, error);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("it520","onReceivedError2 ");

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
            return;
        }else {
            Intent intent = new Intent();
            intent.setClass(WebViewActivity.this, MainActivity.class);
            startActivity(intent);
            super.onBackPressed();
        }
    }
}
