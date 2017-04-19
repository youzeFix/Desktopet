package cn.xd.desktopet.util;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 工具类
 */
public class Utilities {

    /**返回系统状态栏的高度
     * @param context
     * @return 系统状态栏高度的像素值
     */
    public static int getStatusBarHeight(Context context) {

        int statusBarHeight=0;

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

    public static float getInstance(){
        return 0;
    }

}
