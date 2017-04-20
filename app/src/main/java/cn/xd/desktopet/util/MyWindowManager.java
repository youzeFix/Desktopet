package cn.xd.desktopet.util;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.xd.desktopet.view.PetWindowBigView;
import cn.xd.desktopet.view.PetWindowSmallView;

/**
 * 管理宠物悬浮窗
 */
public class MyWindowManager {

    private static WindowManager mWindowManager;
    private static PetWindowSmallView mPetWindowSmallView;
    private static PetWindowBigView mPetWindowBigView;
    /**
     * 小窗口布局参数
     */
    private static WindowManager.LayoutParams mSmallLayoutParams;
    /**
     * 大窗口布局参数
     */
    private static WindowManager.LayoutParams mBigLayoutParams;

    private MyWindowManager() {
    }

    /**
     * 创建宠物小悬浮窗
     *
     * @param context
     */
    public static void createPetSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screamWidth = windowManager.getDefaultDisplay().getWidth();
        int screamHeight = windowManager.getDefaultDisplay().getHeight();
        if(mPetWindowSmallView == null){
            mPetWindowSmallView = new PetWindowSmallView(context);
            if(mSmallLayoutParams == null){
                mSmallLayoutParams = new WindowManager.LayoutParams();
                mSmallLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                mSmallLayoutParams.format = PixelFormat.RGBA_8888;
                mSmallLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                mSmallLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                mSmallLayoutParams.width = mPetWindowSmallView.mVieWidth;
                mSmallLayoutParams.height = mPetWindowSmallView.mViewHeight;

                mSmallLayoutParams.x = screamWidth;
                mSmallLayoutParams.y = screamHeight / 2;
            }
            mPetWindowSmallView.setParams(mSmallLayoutParams);
            mWindowManager.addView(mPetWindowSmallView, mSmallLayoutParams);
        }
    }

    /**
     * 创建宠物大悬浮窗
     */
    public static void createPetBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screamX = windowManager.getDefaultDisplay().getWidth();
        int screamY = windowManager.getDefaultDisplay().getHeight();
        if(mPetWindowBigView == null){
            mPetWindowBigView = new PetWindowBigView(context);
            if(mBigLayoutParams == null){
                mBigLayoutParams = new WindowManager.LayoutParams();
                mBigLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                mBigLayoutParams.format = PixelFormat.RGBA_8888;
                mBigLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                mBigLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                mBigLayoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                mBigLayoutParams.x = screamX / 2;
                mBigLayoutParams.y = screamY / 2;
            }
            mWindowManager.addView(mPetWindowBigView, mBigLayoutParams);
        }
    }

    /**
     * 当前是否有悬浮窗显示
     *
     * @return
     */
    public static boolean isPetWindowShowing() {
        return mPetWindowSmallView != null || mPetWindowBigView != null;
    }

    /**
     * 移除宠物小悬浮窗
     *
     * @param context
     */
    public static void removePetSmallWindow(Context context) {
        if(mPetWindowSmallView != null){
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mPetWindowSmallView);
            mPetWindowSmallView = null;
        }
    }

    /**
     * 移除宠物大悬浮窗
     *
     * @param context
     */
    public static void removePetBigWindow(Context context) {
        Log.w("remove", "removePetBigWindow: " + 1 );
        if(mPetWindowBigView != null){
            Log.w("remove", "removePetBigWindow: " + 2 );
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mPetWindowBigView);
            mPetWindowBigView = null;
        }
    }

    /**
     * 获取WindowManager
     * @param context
     * @return
     */
    public static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null)
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager;
    }

    /**
     * 接收到微信消息
     */
    public static void onReceiveWeChatMessage(){
    }

    /**
     * 宠物属性改变
     */
    public static void onPetStatusChanged(){
    }
    /**
     * 蓝牙接入
     */
    public static void onBluetoothIn(){
    }

    /**创建信息悬浮窗
     * @param context
     */
    public static void createPetMsgWindow(Context context){

    }

    /**移除信息悬浮窗
     * @param context
     */
    public static void removePetMsgWindow(Context context){

    }
}
