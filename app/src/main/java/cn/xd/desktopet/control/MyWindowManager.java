package cn.xd.desktopet.control;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Map;
import java.util.Set;

import cn.xd.desktopet.R;
import cn.xd.desktopet.util.MyApplication;
import cn.xd.desktopet.util.Utilities;
import cn.xd.desktopet.view.PetMenu;
import cn.xd.desktopet.view.PetMessageWindow;
import cn.xd.desktopet.view.PetWindowSmallView;

/**
 * 管理宠物悬浮窗
 */
public class MyWindowManager {

    private static WindowManager mWindowManager;
    private static PetWindowSmallView mPetWindowSmallView;
    private static PetMessageWindow petMessageWindow;
    private static PetMenu petMenu;

    public static boolean msgWindowShow=false;
    public static boolean petMenuShow=false;
    public static boolean isPetShow=true;
    /**
     * 小窗口布局参数
     */
    private static WindowManager.LayoutParams mSmallLayoutParams;


    /**
     * 信息窗口布局参数
     */
    private static WindowManager.LayoutParams petMsgWindowParams;


    private static int screenWidth;
    private static int screenHeight;

    static {
        int[] a= Utilities.getScreenSize(MyApplication.getContext());
        screenWidth=a[0];
        screenHeight=a[1];
    }

    private MyWindowManager() {
    }

    /**
     * 创建宠物小悬浮窗
     *
     * @param context
     */
    public static void createPetSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
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
                mSmallLayoutParams.x = screenWidth;
                mSmallLayoutParams.y = screenHeight / 2;
            }
            mPetWindowSmallView.setParams(mSmallLayoutParams);
            mWindowManager.addView(mPetWindowSmallView, mSmallLayoutParams);
        }
    }

    public static void createPetMenu(Context context){
        if(msgWindowShow==true)removePetMsgWindow(context);
        if(petMenu==null)petMenu=new PetMenu(context);
        Map<Button,WindowManager.LayoutParams> map=petMenu.getButton();
        Set<Button> set=map.keySet();
        for(Button button:set){
            mWindowManager.addView(button,map.get(button));
        }
        petMenuShow=true;
    }

    public static void removePetMenu(){
        Map<Button,WindowManager.LayoutParams> map=petMenu.getButton();
        Set<Button> set=map.keySet();
        for(Button button:set){
            mWindowManager.removeView(button);
        }
        petMenuShow=false;
    }


    /**
     * 当前是否有悬浮窗显示
     *
     * @return
     */
    public static boolean isPetWindowShowing() {
        return mPetWindowSmallView != null || petMenu != null;
    }

    /**
     * 移除宠物小悬浮窗
     *
     * @param context
     */
    public static void removePetSmallWindow(Context context) {
        if(msgWindowShow==true)removePetMsgWindow(context);
        if(petMenuShow==true)removePetMenu();
        if(mPetWindowSmallView != null){
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mPetWindowSmallView);
            mPetWindowSmallView = null;
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
    public static boolean createPetMsgWindow(Context context){
        if(isPetShow==false)return false;
        if(petMessageWindow == null){
            petMessageWindow = new PetMessageWindow(context);
            if(petMsgWindowParams == null){
                petMsgWindowParams = new WindowManager.LayoutParams();
                petMsgWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                petMsgWindowParams.format = PixelFormat.RGBA_8888;
                petMsgWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                petMsgWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                petMsgWindowParams.width = PetMessageWindow.viewWidth;
                petMsgWindowParams.height = PetMessageWindow.viewHeight;

            }
            if(PetWindowSmallView.side==PetWindowSmallView.RIGHT){
                petMsgWindowParams.x=mPetWindowSmallView.params.x-PetMessageWindow.viewWidth-
                        PetWindowSmallView.mVieWidth;
                petMessageWindow.setBackground(R.drawable.msg_window_bg_right);
            }else{
                petMsgWindowParams.x=mPetWindowSmallView.params.x+PetWindowSmallView.mVieWidth;
                petMessageWindow.setBackground(R.drawable.msg_window_bg_left);
            }
            petMsgWindowParams.y = mPetWindowSmallView.params.y;
            petMessageWindow.setLayoutParams(petMsgWindowParams);
            if(PetMessageWindow.textBuffered==true)petMessageWindow.setMessage(PetMessageWindow.textBuffer);
            mWindowManager.addView(petMessageWindow, petMsgWindowParams);
            msgWindowShow=true;
        }
        return true;

    }

    /**移除信息悬浮窗
     * @param context
     */
    public static void removePetMsgWindow(Context context){
        if(petMessageWindow!=null){
            getWindowManager(context).removeView(petMessageWindow);
            petMessageWindow=null;
        }
        msgWindowShow=false;

    }
    public static PetMessageWindow getPetMessageWindow(){
        return petMessageWindow;
    }

    public static void hangUpMsgWindow(){
        petMsgWindowParams.x=screenWidth/2-PetMessageWindow.viewWidth/2;
        petMsgWindowParams.y=mSmallLayoutParams.y;
        mWindowManager.updateViewLayout(petMessageWindow,petMsgWindowParams);
    }
    public static void updateMsgWindowPosition(){
        petMsgWindowParams.y=mSmallLayoutParams.y;
        mWindowManager.updateViewLayout(petMessageWindow,petMsgWindowParams);
    }



}
