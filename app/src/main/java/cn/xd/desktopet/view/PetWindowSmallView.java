package cn.xd.desktopet.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import cn.xd.desktopet.R;
import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.util.Utilities;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class PetWindowSmallView extends LinearLayout {

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 记录系统状态栏的高度
     */
    private int statusBarHeight;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams params;
    /**
     * 记录手指按下时在小悬浮窗上的横坐标值
     */
    private float xInView;
    /**
     * 记录手指按下时在小悬浮窗上的纵坐标值
     */
    private float yInView;
    /**
     * 记录手指按下时在屏幕上的横坐标值
     */
    private float xDownInScreen;
    /**
     * 记录手指按下时在屏幕上的纵坐标值
     */
    private float yDownInScreen;
    /**
     * 记录当前手指在屏幕上的横坐标值
     */
    private float xInScreen;
    /**
     * 记录当前手指在屏幕上的纵坐标值
     */
    private float yInScreen;
    /**
     * 用与展示图片的imageview
     */
    private ImageView imageView;
    /**
     * 保存上下文环境对象
     */
    private Context context;

    public PetWindowSmallView(Context context) {
        super(context);
        this.context=context;
        windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        View view=LayoutInflater.from(context).inflate(R.layout.pet_window_small,this);
        imageView=(ImageView)view.findViewById(R.id.pet_imageview);
        //获取系统状态栏的高度
        statusBarHeight= Utilities.getStatusBarHeight(context);
        //设置imageView的资源
        setImageRes();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInView=event.getX();
                yInView=event.getY();
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-statusBarHeight;
                xDownInScreen=xInScreen;
                yDownInScreen=yInScreen;

                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-statusBarHeight;
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if(xInScreen==xDownInScreen&&yInScreen==yDownInScreen){
                    //单击事件
                }
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 更新小悬浮窗在屏幕上的显示位置
     */
    private void updateViewPosition(){
        params.x=(int)(xInScreen-xInView);
        params.y=(int)(yInScreen-yInView);
        windowManager.updateViewLayout(this,params);
    }

    /**
     * 设置imageView要显示的图片，根据pet的属性从资源中获取相应的图片，使用Glide库显示gif
     */
    public void setImageRes(){
        //当前要使用的资源id
        Integer resId=null;
        switch (Pet.theme){
            case Pet.themeDefault:
                resId=R.drawable.dance;
                break;
            default:
                break;
        }
        Glide.with(context).load(resId).into(imageView);
    }
}
