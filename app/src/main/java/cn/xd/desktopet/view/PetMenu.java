package cn.xd.desktopet.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import cn.xd.desktopet.R;
import cn.xd.desktopet.control.PetControl;

/**
 * Created by Administrator on 2017/4/23 0023.
 */

public class PetMenu {
    private Button alarmBtn;
    private Button bluetoothBtn;
    private Button settingBtn;

    private WindowManager.LayoutParams alarmBtnLayoutParams;
    private WindowManager.LayoutParams bluetoothBtnLayoutParams;
    private WindowManager.LayoutParams settingBtnLayoutParams;

    private int centerX;
    private int centerY;
    private int radius;
    private double angleInterval;

    private Map<Button,WindowManager.LayoutParams> map;

    public PetMenu(Context context){
        alarmBtn=new Button(context);
        bluetoothBtn=new Button(context);
        settingBtn=new Button(context);

        alarmBtnLayoutParams=new WindowManager.LayoutParams();
        bluetoothBtnLayoutParams=new WindowManager.LayoutParams();
        settingBtnLayoutParams=new WindowManager.LayoutParams();

        centerX=PetWindowSmallView.params.x;
        centerY=PetWindowSmallView.params.y;
        radius=150;
        angleInterval=Math.PI/2;


        alarmBtnLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        alarmBtnLayoutParams.format = PixelFormat.RGBA_8888;
        alarmBtnLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        alarmBtnLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        alarmBtnLayoutParams.width = 120;
        alarmBtnLayoutParams.height = 120;

        bluetoothBtnLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        bluetoothBtnLayoutParams.format = PixelFormat.RGBA_8888;
        bluetoothBtnLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        bluetoothBtnLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        bluetoothBtnLayoutParams.width = 120;
        bluetoothBtnLayoutParams.height = 120;

        settingBtnLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        settingBtnLayoutParams.format = PixelFormat.RGBA_8888;
        settingBtnLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        settingBtnLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        settingBtnLayoutParams.width = 120;
        settingBtnLayoutParams.height = 120;


        updateParams();


        alarmBtn.setBackgroundResource(R.drawable.alarm_btn_bg);
        bluetoothBtn.setBackgroundResource(R.drawable.bluetooth_btn_bg);
        settingBtn.setBackgroundResource(R.drawable.setting_btn_bg);

        setListener();
    }
    public Map<Button,WindowManager.LayoutParams> getButton(){
        updateParams();
        if(map==null){
            map=new HashMap<>();
            map.put(settingBtn,settingBtnLayoutParams);
            map.put(bluetoothBtn,bluetoothBtnLayoutParams);
            map.put(alarmBtn,alarmBtnLayoutParams);
        }

        return map;
    }

    private void updateParams(){
        centerX=PetWindowSmallView.params.x;
        centerY=PetWindowSmallView.params.y;
        int smallViewWidth=PetWindowSmallView.mVieWidth;
        if(PetWindowSmallView.side==PetWindowSmallView.LEFT) {
            alarmBtnLayoutParams.x = centerX + 100;
            alarmBtnLayoutParams.y = centerY + radius;
            bluetoothBtnLayoutParams.x=centerX+radius;
            bluetoothBtnLayoutParams.y=centerY;
            settingBtnLayoutParams.x=centerX+100;
            settingBtnLayoutParams.y=centerY-radius;
        }else{
            alarmBtnLayoutParams.x=centerX-smallViewWidth-75;
            alarmBtnLayoutParams.y=centerY+radius;
            bluetoothBtnLayoutParams.x=centerX-PetWindowSmallView.mVieWidth-smallViewWidth;
            bluetoothBtnLayoutParams.y=centerY;
            settingBtnLayoutParams.x=centerX-smallViewWidth-75;
            settingBtnLayoutParams.y=centerY-radius;
        }
    }
    private void setListener(){
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetControl.alarmBtnClick();
            }
        });
        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetControl.bluetoothBtnClick();
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetControl.settingBtnClick();
            }
        });
    }
}
