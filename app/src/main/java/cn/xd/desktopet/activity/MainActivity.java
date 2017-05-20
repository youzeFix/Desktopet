package cn.xd.desktopet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import cn.xd.desktopet.BluetoothCombine.CombineActivity;
import cn.xd.desktopet.R;
import cn.xd.desktopet.control.MyWindowManager;
import cn.xd.desktopet.control.PetControl;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

/**
 * 主活动，包含所有功能开关和设置入口
 */


public class MainActivity extends AppCompatActivity{

    private String title="MainActivity";

    /**
     * 控件
     */
    private Switch displayPetSwitch;
    private Switch mychatMessageSwitch;
    private Button clockBtn;
    private Button bluetoothBtn;
    private Button petSetBtn;
    private Button aboutBtn;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取控件
        findView();
        //设置toolbar
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        //初始化状态
        initState();

        //设置监听器
        setListener();

        //权限申请
        checkPermission();



    }

    /**
     * 获取控件
     */
    private void findView(){
        displayPetSwitch=(Switch)findViewById(R.id.display_pet_switch);
        mychatMessageSwitch=(Switch)findViewById(R.id.mychat_message_switch);
        clockBtn=(Button)findViewById(R.id.clock_btn);
        bluetoothBtn=(Button)findViewById(R.id.bluetooth_btn);
        petSetBtn=(Button)findViewById(R.id.pet_set_btn);
        aboutBtn=(Button)findViewById(R.id.about_btn);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

    }


    /**
     * 设置各控件监听器
     */
    private void setListener(){
        /**
         * 宠物显示开关状态改变监听
         */
        displayPetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MyWindowManager.createPetSmallWindow(getApplicationContext());
                }
                else{
                    MyWindowManager.removePetSmallWindow(getApplicationContext());
                }

            }
        });
        /**
         * 接受微信消息开关状态改变监听器
         */
        mychatMessageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Toast.makeText(MainActivity.this, "打开了接受微信消息开关", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "关闭了接受微信消息开关", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 闹铃按钮点击事件
         */
        clockBtn.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(MainActivity.this, "点击蓝牙按钮", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,CombineActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 宠物设置按钮点击事件
         */
        petSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击宠物设置按钮", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 关于按钮点击事件
         */
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击关于按钮", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initState(){
        if(MyWindowManager.isPetWindowShowing()){
            displayPetSwitch.setChecked(true);
        }else{
            displayPetSwitch.setChecked(false);
        }
    }

    private void checkPermission(){
        /**
         * 检测安卓6.0以上动态申请悬浮窗权限
         */
        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)){
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "请打开悬浮窗权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
