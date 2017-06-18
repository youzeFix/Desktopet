package cn.xd.desktopet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.xd.desktopet.R;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class WelcomeActivity extends AppCompatActivity {

    private Handler handler=new Handler();
    private final int welcomePeriod=1200;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_exit_anim);
                WelcomeActivity.this.finish();
            }
        },welcomePeriod);
    }

}
