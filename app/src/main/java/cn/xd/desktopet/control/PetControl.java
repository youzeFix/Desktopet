package cn.xd.desktopet.control;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.xd.desktopet.R;
import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.service.PetFreeService;
import cn.xd.desktopet.util.MyApplication;
import cn.xd.desktopet.view.PetMessageWindow;
import cn.xd.desktopet.view.PetWindowSmallView;

/**
 * 宠物行为控制类，包括动画切换，资源获取等。
 */
public class PetControl {


    /**
     * 更换动画服务是否已绑定
     */
    private static boolean petFreeServiceIsBound=false;

    /**
     * 自动更换动画服务
     */
    private static PetFreeService petFreeService;

    private static PetWindowSmallView petWindowSmallView;

    private static Timer timer;

    public PetControl(PetWindowSmallView petWindowSmallView){

        this.petWindowSmallView=petWindowSmallView;
        //绑定服务
        doBindPetFreeService();

    }

    private static ServiceConnection mPetFreeConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            petFreeService=((PetFreeService.PetFreeBinder)service).getService();
            if(petFreeService==null) Log.d("PetControl","绑定服务失败！");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            petFreeService=null;

        }
    };

    /**
     * 绑定更换动画服务
     */
    private void doBindPetFreeService(){
        MyApplication.getContext().bindService(new Intent(MyApplication.context,PetFreeService.class),
                mPetFreeConnection,Context.BIND_AUTO_CREATE);
        petFreeServiceIsBound=true;
    }

    /**
     * 解绑更换动画服务
     */
    public static void doUnbindPetFreeService(){
        if(petFreeServiceIsBound){
            MyApplication.getContext().unbindService(mPetFreeConnection);
            petFreeServiceIsBound=false;
        }
    }


    /**
     * 根据宠物主题和动作类型获取图片
     *
     * @param theme 宠物主题
     * @param type  动作类型
     * @return  资源id
     */
    public static int getPetImageRes(int theme,int type){
        int resId=-1;
        if(theme==Pet.themeDefault&&type==Pet.typeStill)resId= R.drawable.beaver;
        else if(theme==Pet.themeDefault&&type==Pet.typeClick)resId=R.drawable.dance;
        else if(theme==Pet.themeDefault&&type==Pet.typeMove)resId=R.drawable.beaver;
        else if(theme==Pet.themeDefault&&type==Pet.typeFree)resId=R.drawable.circle;
        else if(theme==Pet.theme1&&type==Pet.typeStill)resId=R.drawable.beaver;
        else if(theme==Pet.theme1&&type==Pet.typeClick)resId=R.drawable.beaver;
        else if(theme==Pet.theme1&&type==Pet.typeMove)resId=R.drawable.beaver;
        else if(theme==Pet.theme1&&type==Pet.typeFree)resId=R.drawable.beaver;
        else if(theme==Pet.theme2&&type==Pet.typeStill)resId=R.drawable.beaver;
        else if(theme==Pet.theme2&&type==Pet.typeClick)resId=R.drawable.beaver;
        else if(theme==Pet.theme2&&type==Pet.typeMove)resId=R.drawable.beaver;
        else if(theme==Pet.theme2&&type==Pet.typeFree)resId=R.drawable.beaver;
        return resId;
    }

    /**
     * 设置smallView显示的图片
     * @param resId
     */
    public static void setPetWindowSmallViewImage(int resId){
        petWindowSmallView.setImageRes(resId);
    }


    /**
     * 关闭定时更换动画
     */
    public static void stopPetFreeServiceTimerTask(){
        petFreeService.stopTimerTask();
    }

    /**
     * 开启定时更换动画
     */
    public static void startPetFreeServiceTimerTask(){
        petFreeService.startTimerTask();
    }

    /**
     * 显示信息窗口
     * @param msg 要显示的信息
     */
    public static void displayPetMessage(String msg){
        if(MyWindowManager.petMenuShow==true)MyWindowManager.removePetMenu(MyApplication.getContext());

        if(MyWindowManager.createPetMsgWindow(MyApplication.getContext(),msg)==false)return;
        if(Pet.msgWindowAutoClose==true) {
            if (timer == null) timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MyWindowManager.removePetMsgWindow(MyApplication.getContext());
                }
            }, Pet.msgWindowContinueTime*1000);
        }
    }

    public static void petClickDown(){
        if(MyWindowManager.msgWindowShow==true){
            MyWindowManager.hangUpMsgWindow();
        }

    }

    public static void petClickUp(){
        if(MyWindowManager.msgWindowShow==true){
            MyWindowManager.removePetMsgWindow(MyApplication.getContext());
            MyWindowManager.createPetMsgWindow(MyApplication.getContext(), PetMessageWindow.textBuffer);
        }
    }
    public static void petMove(){
        if(MyWindowManager.msgWindowShow==true){
            MyWindowManager.updateMsgWindowPosition();
        }
        MyAlarmManager myAlarmManager=MyAlarmManager.getInstance();
        if(myAlarmManager.isAlarmRinging()){
            myAlarmManager.stopAlarmRing(MyApplication.getContext());
        }
    }
    public static void alarmBtnClick(){
        Toast.makeText(MyApplication.getContext(), "click alarm", Toast.LENGTH_SHORT).show();
    }

    public static void bluetoothBtnClick(){
        Toast.makeText(MyApplication.getContext(), "click bluetooth", Toast.LENGTH_SHORT).show();
    }

    public static void settingBtnClick(){
        Toast.makeText(MyApplication.getContext(), "click setting", Toast.LENGTH_SHORT).show();
    }


}
