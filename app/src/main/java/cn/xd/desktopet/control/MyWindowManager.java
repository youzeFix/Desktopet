package cn.xd.desktopet.control;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import cn.xd.desktopet.R;
import cn.xd.desktopet.activity.MainActivity;
import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.util.MyApplication;
import cn.xd.desktopet.util.Utilities;
import cn.xd.desktopet.view.PetMessageWindow;
import cn.xd.desktopet.view.PetWindowSmallView;
import cn.xd.desktopet.view.SemiringLayout;

/**
 * 管理宠物悬浮窗
 */
public class MyWindowManager {

    private static WindowManager mWindowManager;
    private static PetWindowSmallView mPetWindowSmallView;
    private static PetMessageWindow petMessageWindow;
    private static SemiringLayout petMenu;

    public static boolean msgWindowShow=false;
    public static boolean petMenuShow=false;
    private static boolean isPetShow=true;
    /**
     * 小窗口布局参数
     */
    private static WindowManager.LayoutParams mSmallLayoutParams;


    /**
     * 信息窗口布局参数
     */
    private static WindowManager.LayoutParams petMsgWindowParams;

    private static WindowManager.LayoutParams petMenuParams;


    private static int screenWidth;
    private static int screenHeight;

    private static Map<String,Button> petMenuBtnMap;

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
        isPetShow=true;
        PetControl.displayPetMessage("hi~我是你的宠物"+ Pet.name);
    }

    public static void createPetMenu(final Context context){
        if(msgWindowShow==true)removePetMsgWindow(context);
        if(petMenu==null){
            petMenu= (SemiringLayout)LayoutInflater.from(context).inflate(R.layout.petmenu_layout,null);
            if(petMenuParams==null){
                petMenuParams=new WindowManager.LayoutParams();
                petMenuParams.type=WindowManager.LayoutParams.TYPE_PHONE;
                petMenuParams.format=PixelFormat.RGBA_8888;
                petMenuParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            }
            Log.d("MyWindowManager","当前宠物位置为："+PetWindowSmallView.params.x+"    "+PetWindowSmallView.params.y);
            Log.d("MyWindowManager","宠物大小为："+PetWindowSmallView.mVieWidth+"     "+PetWindowSmallView.mViewHeight);
            petMenu.setCenterY(PetWindowSmallView.params.y+PetWindowSmallView.mViewHeight/2);

            if(PetWindowSmallView.side==PetWindowSmallView.RIGHT){
                petMenu.setCenterX(PetWindowSmallView.params.x-PetWindowSmallView.mVieWidth/2);
                petMenu.setSide(SemiringLayout.SIDE_LEFT);
            }
            else {
                petMenu.setCenterX(PetWindowSmallView.params.x+PetWindowSmallView.mVieWidth/2);
                petMenu.setSide(SemiringLayout.SIDE_RIGHT);
            }
            if(petMenuBtnMap==null)petMenuBtnMap=new HashMap<>();
            petMenuBtnMap.put("alarmButton",(Button)petMenu.findViewById(R.id.petmenu_alarm_btn));
            petMenuBtnMap.put("settingButton",(Button)petMenu.findViewById(R.id.petmenu_setting_btn));
            petMenuBtnMap.put("bluetoothButton",(Button)petMenu.findViewById(R.id.petmenu_bluetooth_btn));
            petMenuBtnMap.put("closeButton",(Button)petMenu.findViewById(R.id.petmenu_close_btn));
            petMenuBtnMap.put("cancelButton",(Button)petMenu.findViewById(R.id.petmenu_cancel_btn));
            petMenuBtnMap.put("homeButton",(Button)petMenu.findViewById(R.id.petmenu_home_btn));


            setPetMenuCloseBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removePetMenu(context);
                }
            });
            setPetMenuAlarmBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PetControl.alarmBtnClick();
                }
            });
            setPetMenuBluetoothBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PetControl.bluetoothBtnClick();
                }
            });
            setPetMenuSettingBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PetControl.settingBtnClick();
                }
            });

            setPetMenuCancelBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removePetMenu(context);
                    removePetSmallWindow(context);
                }
            });

            setPetMenuHomeBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removePetMenu(context);
                    Intent intent=new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            mWindowManager.addView(petMenu,petMenuParams);
        }

        petMenuShow=true;
    }

    public static void removePetMenu(Context context){
        if(petMenu!=null){
            WindowManager windowManager=getWindowManager(context);
            windowManager.removeView(petMenu);
            petMenu=null;
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
        if(petMenuShow==true)removePetMenu(context);
        if(mPetWindowSmallView != null){
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mPetWindowSmallView);
            mPetWindowSmallView = null;
        }
        isPetShow=false;
        PetControl.doUnbindPetFreeService();
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
    public static boolean createPetMsgWindow(Context context,String msg){
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
                petMsgWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                petMsgWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

            }


            if(PetWindowSmallView.side==PetWindowSmallView.RIGHT){
                petMessageWindow.setBackground(R.drawable.msg_window_right);
            }else{
                petMessageWindow.setBackground(R.drawable.msg_window_left);
            }

            /**
             * 手动测量设置了背景和文字的宠物信息窗口
             */
            int widthMs= View.MeasureSpec.makeMeasureSpec((1<<30)-1, View.MeasureSpec.AT_MOST);
            int heightMs= View.MeasureSpec.makeMeasureSpec((1<<30)-1, View.MeasureSpec.AT_MOST);
            petMessageWindow.setLayoutParams(petMsgWindowParams);
            petMessageWindow.setMessage(msg);
            petMessageWindow.measure(widthMs,heightMs);
            int mw=petMessageWindow.getMeasuredWidth();
            int mh=petMessageWindow.getMeasuredHeight();
            Log.d("MyWindowManager","petMessageWindow测量，宽度为："+mw+"高度为："+mh);

            if(PetWindowSmallView.side==PetWindowSmallView.RIGHT){
                petMsgWindowParams.x=PetWindowSmallView.params.x-PetWindowSmallView.mVieWidth-mw;
            }else{
                petMsgWindowParams.x=PetWindowSmallView.params.x+PetWindowSmallView.mVieWidth;
            }
            petMsgWindowParams.y = mPetWindowSmallView.params.y-mh+PetWindowSmallView.mViewHeight;
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
        petMsgWindowParams.x=screenWidth/2-petMessageWindow.getMeasuredWidth()/2;
        petMsgWindowParams.y=mSmallLayoutParams.y;
        mWindowManager.updateViewLayout(petMessageWindow,petMsgWindowParams);
    }
    public static void updateMsgWindowPosition(){
        petMsgWindowParams.y=mSmallLayoutParams.y;
        mWindowManager.updateViewLayout(petMessageWindow,petMsgWindowParams);
    }

    public static void setPetMenuAlarmBtnListener(View.OnClickListener listener){
        if(petMenuBtnMap!=null){
            Button petMenuAlarmBtn=(Button)petMenuBtnMap.get("alarmButton");
            petMenuAlarmBtn.setOnClickListener(listener);
        }
    }

    public static void setPetMenuSettingBtnListener(View.OnClickListener listener){
        if(petMenuBtnMap!=null){
            Button petMenuSettingBtn=(Button)petMenuBtnMap.get("settingButton");
            petMenuSettingBtn.setOnClickListener(listener);
        }
    }

    public static void setPetMenuBluetoothBtnListener(View.OnClickListener listener){
        if(petMenuBtnMap!=null){
            Button petMenuBluetoothBtn=(Button)petMenuBtnMap.get("bluetoothButton");
            petMenuBluetoothBtn.setOnClickListener(listener);
        }
    }

    public static void setPetMenuCloseBtnListener(View.OnClickListener listener){
        if(petMenuBtnMap!=null){
            Button petMenuCloseBtn=(Button)petMenuBtnMap.get("closeButton");
            petMenuCloseBtn.setOnClickListener(listener);
        }
    }
    public static void setPetMenuCancelBtnListener(View.OnClickListener listener){
        if(petMenuBtnMap!=null){
            Button petMenuCancelBtn=(Button)petMenuBtnMap.get("cancelButton");
            petMenuCancelBtn.setOnClickListener(listener);
        }
    }
    public static void setPetMenuHomeBtnListener(View.OnClickListener listener){
        if(petMenuBtnMap!=null){
            Button petMenuHomeBtn=(Button)petMenuBtnMap.get("homeButton");
            petMenuHomeBtn.setOnClickListener(listener);
        }
    }


}
