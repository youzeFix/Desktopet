package cn.xd.desktopet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.xd.desktopet.control.MyWindowManager;
import cn.xd.desktopet.util.MyApplication;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootCompleteReceiver","收到开机广播，启动宠物");
        MyWindowManager.createPetSmallWindow(MyApplication.getContext());
    }
}
