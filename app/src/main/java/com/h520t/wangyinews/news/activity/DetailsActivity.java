package com.h520t.wangyinews.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.h520t.wangyinews.R;
import com.h520t.wangyinews.news.bean.Details;
import com.h520t.wangyinews.news.bean.DetailsImg;
import com.h520t.wangyinews.splashScreen.WebViewActivity;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.HttpResponse;
import com.h520t.wangyinews.util.HttpUtil;
import com.h520t.wangyinews.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    MyHandler mMyHandler;
    WebView mWebView;
    TextView replyCount;

    String id;
    ArrayList<DetailsImg> images;
    Details web;
    int replayCount;
    String body;
    final int INIT_SUCCESS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        //拿到url
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.i("h520it", "DetailsActivity: "+id);
        String detailYUrl = Contants.getDetailYUrl(id);
        Log.i("h520it", "DetailsActivity: "+detailYUrl);
        HttpUtil.getInstance().getData(detailYUrl, new HttpResponse<String>(String.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject js = new JSONObject(json);
                    String need_json = js.optJSONObject(id).toString();
                    web = JsonUtil.parseJson(need_json, Details.class);
                    Log.i("h520it", "DetailsActivity: "+web.toString());
                    if (web != null) {
                        body = web.getBody();
                        images = (ArrayList<DetailsImg>) web.getImg();
                        for (int i = 0; i < images.size(); i++) {
                            String src = images.get(i).getSrc();
                            String imageTag = "<img src='" + src + "'onclick=\"show()\"/>";
                            String tag = "<!--IMG#" + i + "-->";
                            body = body.replace(tag, imageTag);
                        }
                        //p 标签代表一个段落
                        String titleHTML = "<p><span style='font-size:18px;'><strong>" + web.getTitle() + "</strong></span></p>";// 标题

                        titleHTML = titleHTML+ "<p><span style='color:#666666;'>"+web.getSource()+"&nbsp&nbsp"+web.getPtime()+"</span></p>";//来源与时间

                        body = titleHTML + body;
                        body = "<html><head><style>img{width:100%}</style><script type='text/javascript'>function show(){window.demo.javaShow()} </script></head><body>" + body + "</body></html>";
                        replayCount = web.getReplyCount();
                        mMyHandler.sendEmptyMessage(INIT_SUCCESS);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void initView() {
        mMyHandler = new MyHandler(this);
        mWebView = findViewById(R.id.webView);
        replyCount = findViewById(R.id.reply_count);
    }

    static class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        MyHandler(Activity activity){
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DetailsActivity detailsActivity = (DetailsActivity) mWeakReference.get();
            if (detailsActivity==null){
                return;
            }
            detailsActivity.initWebView();
        }
    }

    private void initWebView() {
        mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
        replyCount.setText(web.getReplyCount()+"");
    }
}
