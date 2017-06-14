package cn.xd.desktopet.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import cn.xd.desktopet.BluetoothCombine.CombineActivity;
import cn.xd.desktopet.R;
import cn.xd.desktopet.control.MyWindowManager;
import cn.xd.desktopet.control.PetControl;
import cn.xd.desktopet.service.MMListenService;
import cn.xd.desktopet.util.Utilities;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

/**
 * 主活动，包含所有功能开关和设置入口
 */


public class MainActivity extends AppCompatActivity{


    private boolean accessibilityIsOpen=false;

    private final int btnAnimDuration=500;

    private final String petOnBtnMsg="桌面宠物已开启";
    private final String petOffBtnMsg="桌面宠物已关闭";

    private final String mychatOnMsg="微信消息提醒已开启";
    private final String mychatOffMsg="微信消息提醒已关闭";

    /**
     * 控件
     */
    private Button petToggleBtn;
    private Button mychatMsgToggleBtn;
    private Button alarmBtn;
    private Button bluetoothBtn;
    private Button petSettingBtn;
    private Button aboutBtn;

    private boolean petToggleBtnState=false;
    private boolean mychatMsgToggleBtnState=false;


    private Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取控件
        findView();

        //初始化状态
        initState();

        //设置监听器
        setListener();


    }

    /**
     * 获取控件
     */
    private void findView(){
        petToggleBtn=(Button) findViewById(R.id.pet_toggle_btn);
        mychatMsgToggleBtn=(Button) findViewById(R.id.mychat_msg_toggle_btn);
        alarmBtn=(Button)findViewById(R.id.alarm_btn);
        bluetoothBtn=(Button)findViewById(R.id.bluetooth_btn);
        petSettingBtn=(Button)findViewById(R.id.pet_setting_btn);
        aboutBtn=(Button)findViewById(R.id.about_btn);
    }


    /**
     * 设置各控件监听器
     */
    private void setListener(){
        petToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!petToggleBtnState){
                    if(checkFloatWindowPermission()){
                        startBtnOnAnim(petToggleBtn, petOnBtnMsg, new Runnable() {
                            @Override
                            public void run() {
                                MyWindowManager.createPetSmallWindow(getApplicationContext());
                            }
                        });

                    }else{
                        requestFloatWindowPermission();
                    }
                }else {
                    startBtnOffAnim(petToggleBtn, petOffBtnMsg, new Runnable() {
                        @Override
                        public void run() {
                            MyWindowManager.removePetSmallWindow(getApplicationContext());
                        }
                    });
                }
                petToggleBtnState=!petToggleBtnState;


                }


        });

        mychatMsgToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mychatMsgToggleBtnState){
                    if(checkAccessibilityPermission()){
                        startBtnOnAnim(mychatMsgToggleBtn, mychatOnMsg, new Runnable() {
                            @Override
                            public void run() {
                                MMListenService.isRunning=true;
                                startService(new Intent(MainActivity.this,MMListenService.class));
                            }
                        });
                    }else{
                        requestAccessibilityPermission();
                    }
                }else{
                    startBtnOffAnim(mychatMsgToggleBtn, mychatOffMsg, new Runnable() {
                        @Override
                        public void run() {
                            MMListenService.isRunning=false;
                        }
                    });
                }
                mychatMsgToggleBtnState=!mychatMsgToggleBtnState;

            }
        });

        /**
         * 闹铃按钮点击事件
         */
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AlarmListActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 蓝牙按钮点击事件
         */
        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CombineActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 宠物设置按钮点击事件
         */
        petSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PetSettingActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 关于按钮点击事件
         */
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });


    }

    private void startBtnOnAnim(final Button button, final String btnTextNew, final Runnable animEndRun){
        AnimatorSet animatorSet=new AnimatorSet();
        Drawable bgNew=getResources().getDrawable(R.drawable.btn_bg_pressed);
        bgNew.setAlpha(30);
        button.setBackground(bgNew);
        ObjectAnimator btnAnim=ObjectAnimator.ofFloat(button,"rotationY",0,360);
        ObjectAnimator bgAnim=ObjectAnimator.ofInt(bgNew,"alpha",30,255);
        ObjectAnimator scaleXAnim=ObjectAnimator.ofFloat(button,"scaleX",1.0f,0.2f,1.0f);
        ObjectAnimator scaleYAnim=ObjectAnimator.ofFloat(button,"scaleY",1.0f,0.2f,1.0f);
        animatorSet.setDuration(btnAnimDuration);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(btnAnim,bgAnim,scaleXAnim,scaleYAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setText(btnTextNew);
                    }
                },btnAnimDuration/2);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animEndRun.run();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();

    }

    private void startBtnOffAnim(final Button button, final String btnTextNew, final Runnable animEndRun){
        AnimatorSet animatorSet=new AnimatorSet();
        Drawable bgOld=button.getBackground();
        final Drawable bgNew=getResources().getDrawable(R.drawable.btn_bg);

        ObjectAnimator btnAnim=ObjectAnimator.ofFloat(button,"rotationY",0,360);
        ObjectAnimator bgAnim=ObjectAnimator.ofInt(bgOld,"alpha",255,30);
        ObjectAnimator scaleXAnim=ObjectAnimator.ofFloat(button,"scaleX",1.0f,0.2f,1.0f);
        ObjectAnimator scaleYAnim=ObjectAnimator.ofFloat(button,"scaleY",1.0f,0.2f,1.0f);
        animatorSet.setDuration(btnAnimDuration);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(btnAnim,bgAnim,scaleXAnim,scaleYAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setText(btnTextNew);
                    }
                },btnAnimDuration/2);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animEndRun.run();
                button.setBackground(bgNew);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void initState(){
        if(MyWindowManager.isPetWindowShowing()){
            changeBtnState(petToggleBtn,true);
        }else{
            changeBtnState(petToggleBtn,false);
        }
        if(MMListenService.isRunning){
            changeBtnState(mychatMsgToggleBtn,true);
        }else{
            changeBtnState(mychatMsgToggleBtn,false);
        }
    }
    private void changeBtnState(Button button,boolean state){
        switch (button.getId()){
            case R.id.pet_toggle_btn:
                if(state){
                    petToggleBtn.setText(petOnBtnMsg);
                    petToggleBtn.setBackgroundResource(R.drawable.btn_bg_pressed);
                }else{
                    petToggleBtn.setText(petOffBtnMsg);
                    petToggleBtn.setBackgroundResource(R.drawable.btn_bg);
                }
                petToggleBtnState=state;
                break;
            case R.id.mychat_msg_toggle_btn:
                if(state){
                    mychatMsgToggleBtn.setText(mychatOnMsg);
                    mychatMsgToggleBtn.setBackgroundResource(R.drawable.btn_bg_pressed);
                }else{
                    mychatMsgToggleBtn.setText(mychatOffMsg);
                    mychatMsgToggleBtn.setBackgroundResource(R.drawable.btn_bg);
                }
                mychatMsgToggleBtnState=state;
                break;
            default:
                break;
        }
    }
    private boolean checkFloatWindowPermission(){
        /**
         * 检测安卓6.0以上动态申请悬浮窗权限
         */
        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)){
                return false;
            }
        }
        return true;
    }

    private void requestFloatWindowPermission(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(this, "请打开悬浮窗权限", Toast.LENGTH_SHORT).show();
        startActivityForResult(intent,2);

    }

    private boolean checkAccessibilityPermission(){
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for(AccessibilityServiceInfo info : accessibilityServices){
            Log.w("MainActivity", "onCreate: " + info.getId() );
            if(info.getId().equals("cn.xd.desktopet/.service.MMListenService"))
            {
                accessibilityIsOpen = true;
                return true;
            }
        }
        return false;

    }

    private void requestAccessibilityPermission(){
        if(!accessibilityIsOpen){
            Toast.makeText(this, "请打开ListenMessage服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
                List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
                for(AccessibilityServiceInfo info : accessibilityServices){
                    Log.w("MainActivity", "onCreate: " + info.getId() );
                    if(info.getId().equals("cn.xd.desktopet/.service.MMListenService"))
                    {
                        accessibilityIsOpen = true;
                        MMListenService.isRunning=true;
                        startService(new Intent(this, MMListenService.class));
                        Toast.makeText(this, "微信消息提醒服务已开启", Toast.LENGTH_SHORT).show();
                        changeBtnState(mychatMsgToggleBtn,true);
                        return;
                    }
                }
                Toast.makeText(this, "微信消息提醒服务未开启", Toast.LENGTH_SHORT).show();
                changeBtnState(mychatMsgToggleBtn,false);
                break;
            case 2:
                if(Build.VERSION.SDK_INT>=23){
                    if(Settings.canDrawOverlays(this))MyWindowManager.createPetSmallWindow(this);
                    else {
                        Toast.makeText(this, "悬浮窗权限未开启", Toast.LENGTH_SHORT).show();
                        changeBtnState(petToggleBtn,false);
                    }
                }
                break;
            default:
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
