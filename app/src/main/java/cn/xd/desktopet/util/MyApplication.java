package cn.xd.desktopet.util;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by songzhixin on 2017/4/19.
 */

public class MyApplication extends Application {
    public static Context context;
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getContext(){
        return context;
    }
}
