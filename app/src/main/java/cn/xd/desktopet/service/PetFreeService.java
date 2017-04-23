package cn.xd.desktopet.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.control.PetControl;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

/**
 * 自动更换动画服务类
 */
public class PetFreeService extends Service {

    /**
     * 计时器，用于定时更换动画
     */
    private Timer timer;
    private Timer timer1;

    /**
     * 计时器要执行的任务对象
     */
    private PetFreeTask petFreeTask;
    private PetStillTask petStillTask;

    /**
     * 执行异步任务
     */
    private Handler handler;


    private final IBinder mBinder=new PetFreeBinder();


    public class PetFreeBinder extends Binder{
        public PetFreeService getService(){
            return PetFreeService.this;
        }
    }

    /**
     * 初始化
     */
    private void init(){
        if(timer==null)timer=new Timer();
        if(timer1==null)timer1=new Timer();
        handler=new Handler();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        init();
        startTimerTask();
        return mBinder;
    }

    /**
     * 关闭定时更换动画任务
     */
    public void stopTimerTask(){
        petFreeTask.cancel();
        petStillTask.cancel();
    }

    /**
     * 开启定时更换动画任务，两个计时器，两个任务
     */
    public void startTimerTask(){
        petFreeTask=new PetFreeTask();
        petStillTask=new PetStillTask();
        timer.scheduleAtFixedRate(petFreeTask,Pet.freeTime*1000,(Pet.freeTime+Pet.freeContinueTime)*1000);
        timer1.scheduleAtFixedRate(petStillTask,(Pet.freeTime+Pet.freeContinueTime)*1000,
                (Pet.freeTime+Pet.freeContinueTime)*1000);
    }

    /**
     * 更换闲时动画任务类
     */
    class PetFreeTask extends TimerTask{

        @Override
        public void run() {
            //获取资源
            final int freeResId=PetControl.getPetImageRes(Pet.theme,Pet.typeFree);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //更换图片
                    PetControl.setPetWindowSmallViewImage(freeResId);
                }
            });

        }
    }

    /**
     * 更换静止动画任务类
     */
    class PetStillTask extends TimerTask{

        @Override
        public void run() {
            //获取资源
            final int stillResId=PetControl.getPetImageRes(Pet.theme,Pet.typeStill);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //更换图片
                    PetControl.setPetWindowSmallViewImage(stillResId);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        if(timer1!=null){
            timer1.cancel();
            timer1=null;
        }
        super.onDestroy();
    }
}
