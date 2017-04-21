package cn.xd.desktopet.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.xd.desktopet.R;
import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.util.MyApplication;
import cn.xd.desktopet.util.MyWindowManager;
import cn.xd.desktopet.util.PetControl;
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
     * View的宽
     */
    public static int mVieWidth;
    /**
     * View的高
     */
    public static int mViewHeight;
    /**
     * 用与展示图片的imageview
     */
    private ImageView imageView;
    /**
     * 保存上下文环境对象
     */
    private Context context;

    /**
     * 宠物行为控制器
     */
    private PetControl petControl;

    /**
     *屏幕宽
     */
    private int screenWidth;
    /**
     * 屏幕高
     */
    private int screenHeight;




    public PetWindowSmallView(Context context) {
        super(context);
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.pet_window_small, this);
        imageView = (ImageView) view.findViewById(R.id.pet_imageview);
        //获取系统状态栏的高度
        statusBarHeight = Utilities.getStatusBarHeight(context);
        //获取屏幕宽高
        screenWidth=Utilities.getScreenSize(context)[0];
        screenHeight=Utilities.getScreenSize(context)[1];
        //获取view宽高
        mViewHeight = imageView.getLayoutParams().height;
        mVieWidth = imageView.getLayoutParams().width;

        setImageRes(PetControl.getPetImageRes(Pet.theme,Pet.typeStill));

        petControl=new PetControl(this);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //暂时关闭更换动画
                petControl.stopPetFreeServiceTimerTask();
                setImageRes(PetControl.getPetImageRes(Pet.theme,Pet.typeClick));
                xInView = event.getX();
                yInView = event.getY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - statusBarHeight;
                xDownInScreen = xInScreen;
                yDownInScreen = yInScreen;

                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - statusBarHeight;
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                setImageRes(PetControl.getPetImageRes(Pet.theme,Pet.typeStill));
                //重新开启更换动画
                petControl.startPetFreeServiceTimerTask();
                //贴边
                toTheSide();
                //检测是否触发单击事件
                if (Utilities.getDistance(xInScreen, yInScreen, xDownInScreen, yDownInScreen) < 5) {
                    MyWindowManager.createPetBigWindow(MyApplication.getContext());
                    Toast.makeText(getContext(), "单击事件", Toast.LENGTH_SHORT).show();
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
    private void updateViewPosition() {
        params.x = (int) (xInScreen - xInView);
        params.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, params);
    }
    /**
     * 贴边
     */
    private void toTheSide(){
        if(xInScreen<screenWidth/2)params.x=0;
        else params.x=screenWidth-mVieWidth;
        windowManager.updateViewLayout(this,params);
    }

    /**
     * 设置imageView要显示的图片，使用Glide库显示gif
     * @param resId    要使用的资源id
     */
    public void setImageRes(Integer resId) {
        Glide.with(context).load(resId).into(imageView);
    }


    public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }





}
