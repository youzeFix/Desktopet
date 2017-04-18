package cn.xd.desktopet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import cn.xd.desktopet.R;
import cn.xd.desktopet.util.MyWindowManager;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

/**
 * 主活动，包含所有功能开关和设置入口
 */


public class MainActivity extends Activity{


    /**
     * 控件
     */
    private Switch displayPetSwitch;
    private Switch mychatMessageSwitch;
    private Button clockBtn;
    private Button bluetoothBtn;
    private Button petSetBtn;
    private Button aboutBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        findView();
        //设置监听器
        setListener();






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
                if(isChecked)
                    MyWindowManager.createPetSmallWindow(MainActivity.this);
                else
                    MyWindowManager.removePetSmallWindow(MainActivity.this);
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

        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击闹钟按钮", Toast.LENGTH_SHORT).show();
            }
        });

        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击蓝牙按钮", Toast.LENGTH_SHORT).show();
            }
        });

        petSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击宠物设置按钮", Toast.LENGTH_SHORT).show();
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击关于按钮", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
