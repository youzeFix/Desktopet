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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.xd.desktopet.BluetoothCombine.CombineActivity;
import cn.xd.desktopet.R;
import cn.xd.desktopet.activity.AlarmListActivity;
import cn.xd.desktopet.activity.PetSettingActivity;
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
        if(theme==Pet.themeDefault&&type==Pet.typeStill)resId= R.drawable.bluestill;
        else if(theme==Pet.themeDefault&&type==Pet.typeFree)resId=getPetFreeMotionRes(theme);
        else if(theme==Pet.theme1&&type==Pet.typeStill)resId=R.drawable.xinbastill;
        else if(theme==Pet.theme1&&type==Pet.typeFree)resId=getPetFreeMotionRes(theme);
        else if(theme==Pet.theme2&&type==Pet.typeStill)resId=R.drawable.alistill;
        else if(theme==Pet.theme2&&type==Pet.typeFree)resId=getPetFreeMotionRes(theme);
        return resId;
    }

    /**
     * 根据宠物主体随机选择宠物的闲时动作
     *
     * @param theme 宠物主体
     * @return  闲时动作gif资源id
     */
    private static int getPetFreeMotionRes(int theme){
        int resId=-1;
        Random random=new Random();
        int resNum=random.nextInt(4);
        switch (theme){
            case Pet.themeDefault:
                switch (resNum){
                    case 0:
                        resId=R.drawable.bluemotion1;
                        break;
                    case 1:
                        resId=R.drawable.bluemotion2;
                        break;
                    case 2:
                        resId=R.drawable.bluemotion3;
                        break;
                    case 3:
                        resId=R.drawable.bluemotion4;
                        break;
                    default:
                        break;
                }
                break;
            case Pet.theme1:
                switch (resNum){
                    case 0:
                        resId=R.drawable.xinbamotion1;
                        break;
                    case 1:
                        resId=R.drawable.xinbamotion2;
                        break;
                    case 2:
                        resId=R.drawable.xinbamotion3;
                        break;
                    case 3:
                        resId=R.drawable.xinbamotion4;
                        break;
                    default:
                        break;
                }
                break;
            case Pet.theme2:
                switch (resNum){
                    case 0:
                        resId=R.drawable.alimotion1;
                        break;
                    case 1:
                        resId=R.drawable.alimotion2;
                        break;
                    case 2:
                        resId=R.drawable.alimotion3;
                        break;
                    case 3:
                        resId=R.drawable.alimotion4;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
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
        Context context=MyApplication.getContext();
        Intent intent=new Intent(context,AlarmListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        MyWindowManager.removePetMenu(MyApplication.getContext());

    }

    public static void bluetoothBtnClick(){
        Context context=MyApplication.getContext();
        Intent intent=new Intent(context,CombineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        MyWindowManager.removePetMenu(MyApplication.getContext());
    }

    public static void settingBtnClick(){
        Context context=MyApplication.getContext();
        Intent intent=new Intent(context,PetSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        MyWindowManager.removePetMenu(MyApplication.getContext());
    }


}
