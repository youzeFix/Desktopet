package cn.xd.desktopet.util;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import cn.xd.desktopet.R;
import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.service.PetFreeService;
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
        Log.d("PetControl","设置smallview图片。资源id为："+resId);
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
}
