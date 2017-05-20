package cn.xd.desktopet.util;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.lang.reflect.Field;

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
    public static void measure(View view) {
        int sizeWidth, sizeHeight, modeWidth, modeHeight;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            sizeWidth = 0;
            modeWidth = View.MeasureSpec.UNSPECIFIED;
        } else {
            sizeWidth = layoutParams.width;
            modeWidth = View.MeasureSpec.EXACTLY;
        }
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            sizeHeight = 0;
            modeHeight = View.MeasureSpec.UNSPECIFIED;
        } else {
            sizeHeight = layoutParams.height;
            modeHeight = View.MeasureSpec.EXACTLY;
        }
//        view.measure(View.MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth),
//                View.MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight)
//        );
        view.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
    }

}
