package cn.xd.desktopet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xd.desktopet.R;
import cn.xd.desktopet.control.MyWindowManager;
import cn.xd.desktopet.util.Utilities;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

public class PetMessageWindow extends RelativeLayout {

    /**
     * 当前手指在屏幕上的横坐标值
     */
    private float xInScreen;
    /**
     * 当前手指在屏幕上的纵坐标值
     */
    private float yInScreen;
    /**
     * 记录手指按下时在屏幕上的横坐标值
     */
    private float xDownInScreen;
    /**
     * 记录手指按下时在屏幕上的纵坐标值
     */
    private float yDownInScreen;
    /**
     * 系统状态栏高度
     */
    private int statusBarHeight;

    /**
     *显示信息内容的textview
     */
    private TextView msgTextView;

    /**
     * view的宽
     */
    public static int viewWidth;
    /**
     * view的高
     */
    public static int viewHeight;

    /**
     * messageWindow布局的最外层layout
     */
    private RelativeLayout msgWindowBg;

    /**
     * 信息缓存，信息显示期间重建窗口用
     */
    public static String textBuffer;
    /**
     * 是否有讯息缓存
     */
    public static boolean textBuffered;
    /**
     * 保存上下文环境对象
     */
    private Context context;

    public PetMessageWindow(Context context) {
        super(context);
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.pet_message_window,this);
        msgWindowBg=(RelativeLayout)view.findViewById(R.id.pet_msg_window_bg);
        msgTextView=(TextView)view.findViewById(R.id.msg_textview);
        //获取系统状态栏高度
        statusBarHeight= Utilities.getStatusBarHeight(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDownInScreen=event.getRawX();
                yDownInScreen=event.getRawY()-statusBarHeight;
                xInScreen=xDownInScreen;
                yInScreen=yDownInScreen;
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-statusBarHeight;
                break;
            case MotionEvent.ACTION_UP:
                //单击时关闭PetMessageWindow
                if(xInScreen==xDownInScreen&&yInScreen==yDownInScreen){
                    MyWindowManager.removePetMsgWindow(context);
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置msgTextView要显示的信息
     * @param msg 要显示的信息
     */
    public void setMessage(String msg){
        Log.d("PetMessageWindow","setMessage");
        msgTextView.setText(msg);
        textBuffer=msg;
        textBuffered=true;
    }

    /**
     * 设置背景
     * @param resId 背景图片资源id
     */
    public void setBackground(int resId){
        msgWindowBg.setBackgroundResource(resId);
    }


}
