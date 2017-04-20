package cn.xd.desktopet.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.xd.desktopet.R;
import cn.xd.desktopet.util.MyApplication;
import cn.xd.desktopet.util.MyWindowManager;

/**
 * Created by songzhixin on 2017/4/19.
 */

public class PetWindowBigView extends LinearLayout {

    private View mClock;
    private View mSetting;
    private View mShadow;
    private View mBackgroundView;
    private View mCancel;

    public PetWindowBigView(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pet_window_big, this);
        mClock = view.findViewById(R.id.linear_clock);
        mSetting = view.findViewById(R.id.linear_setting);
        mShadow = view.findViewById(R.id.linear_shadow);
        mBackgroundView = findViewById(R.id.pet_window_big_view);
        mCancel = findViewById(R.id.cancelBigEvent);
        initOnClick();
    }

    private void initOnClick() {

        mClock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "闹钟事件", Toast.LENGTH_SHORT).show();
                MyWindowManager.removePetBigWindow(MyApplication.getContext());
            }
        });
        mSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "设置事件", Toast.LENGTH_SHORT).show();
                MyWindowManager.removePetBigWindow(MyApplication.getContext());
            }
        });
        mShadow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "隐藏事件", Toast.LENGTH_SHORT).show();
                MyWindowManager.removePetBigWindow(MyApplication.getContext());
            }
        });
        mBackgroundView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWindowManager.removePetBigWindow(MyApplication.getContext());
            }
        });

        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
