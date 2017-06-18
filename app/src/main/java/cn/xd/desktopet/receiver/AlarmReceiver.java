package cn.xd.desktopet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.xd.desktopet.control.MyAlarmManager;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver","收到闹钟广播");
        Toast.makeText(context, "收到闹钟广播", Toast.LENGTH_SHORT).show();
        MyAlarmManager.getInstance().triggerAlarm(context);

    }




}
