package com.h520t.wangyinews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import com.h520t.wangyinews.splashScreen.TimeView;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    int now = 1;
    int total;
    int space = 200;
    TimeView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = findViewById(R.id.timeView);
        total = 2000/space;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (total>=now){
                            timeView.setProgress(total,now);
                        }else{
                            handler.removeCallbacks(refreshRunnable);
                        }
                        break;

                }
                return false;
            }
        });
        handler.post(refreshRunnable);
    }

    Runnable refreshRunnable  = new Runnable() {
        @Override
        public void run() {
            Message message = handler.obtainMessage(0);
            now++;
            handler.sendMessage(message);
            handler.postDelayed(this,space);
        }
    };
}
