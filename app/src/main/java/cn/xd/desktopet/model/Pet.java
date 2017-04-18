package cn.xd.desktopet.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

/**
 * 宠物模型类
 */
public class Pet {

    /**
     * 使用整形常量表示不同的主题
     */
    public static final int themeDefault=0;


    /**
     * 姓名
     */
    public static String name;
    /**
     * 性别
     */
    public static String sex;
    /**
     * 主题
     */
    public static int theme;
    /**
     * 口头禅
     */
    public static String petPhrase;


    /**
     * 将宠物当前的设置信息保存到preference文件中
     * @param context
     */
    public static void save(Context context){
        SharedPreferences sp=context.getSharedPreferences("pet",0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("name",name);
        editor.putString("sex",sex);
        editor.putInt("theme",theme);
        editor.putString("petPhrase",petPhrase);
        editor.commit();
    }
}
