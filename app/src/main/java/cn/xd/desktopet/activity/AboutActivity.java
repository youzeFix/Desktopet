package cn.xd.desktopet.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.xd.desktopet.R;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class AboutActivity extends AppCompatActivity {

    private String title="关于";

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findView();

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setListener();

    }

    private void findView(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
    }

    private void setListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
