package cn.xd.desktopet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.xd.desktopet.control.MyAlarmManager;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class SetAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int alarmPosition=intent.getIntExtra("alarmPosition",-1);
        switch (intent.getAction()){
            case "cn.xd.desktopet.openalarm":
                MyAlarmManager.getInstance().openAlarm(alarmPosition);
                break;
            case "cn.xd.desktopet.closealarm":
                MyAlarmManager.getInstance().cancelAlarm(alarmPosition);
                break;
            default:
                break;
        }
    }
}
