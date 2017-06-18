package cn.xd.desktopet.util;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 工具类
 */
public class Utilities {

    /**
     * 返回系统状态栏的高度
     *
     * @param context
     * @return 系统状态栏高度的像素值
     */
    public static int getStatusBarHeight(Context context) {

        int statusBarHeight = 0;

        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusBarHeight;
    }

    public static float getDistance(float startX, float startY, float endX, float endY) {
        float result = 0;
        result = (float) Math.sqrt((endY - startY) * (endY - startY) + (endX - startX) * (endX - startX));
        return result;
    }


    /**
     * 获取屏幕尺寸
     * @param context
     * @return  size[0]为宽，size[1]为高
     */
    public static int[] getScreenSize(Context context){
        int[] size=new int[2];
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        size[0]=windowManager.getDefaultDisplay().getWidth();
        size[1]=windowManager.getDefaultDisplay().getHeight();
        return size;
    }

    public static List<ActivityManager.RunningServiceInfo> getRunningServiceInfo(Context context){
        int defaultMaxNum=50;
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos=activityManager.getRunningServices(defaultMaxNum);
        for(ActivityManager.RunningServiceInfo runningServiceInfo:runningServiceInfos){
            ComponentName componentName=runningServiceInfo.service;
            Log.d("Utilities","服务包名："+componentName.getPackageName()+"服务类名："+componentName.getShortClassName());
        }
        return runningServiceInfos;
    }

    public static boolean isServiceRunning(Context context,String serviceName){
        String myPackageName=context.getPackageName();
        List<ActivityManager.RunningServiceInfo> runningServiceInfos=getRunningServiceInfo(context);

        ComponentName tempComponentName;
        String tempPackageName;
        String tempServiceName;
        for(ActivityManager.RunningServiceInfo runningServiceInfo:runningServiceInfos){
            tempComponentName=runningServiceInfo.service;
            tempPackageName=tempComponentName.getPackageName();
            tempServiceName=tempComponentName.getShortClassName();
            if(tempPackageName.equals(myPackageName)&&tempServiceName.equals(serviceName))return true;
        }
        return false;
    }

}
