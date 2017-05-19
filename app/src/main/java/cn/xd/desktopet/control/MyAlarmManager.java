package cn.xd.desktopet.control;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog;
import android.util.Log;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import android.view.WindowManager;

import cn.xd.desktopet.activity.AlarmListActivity;
import cn.xd.desktopet.database.MyDatabaseHelper;
import cn.xd.desktopet.model.Alarm;
import cn.xd.desktopet.receiver.AlarmReceiver;
import cn.xd.desktopet.service.SoundService;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class MyAlarmManager {
    private List<Alarm> alarmList;

    private static MyAlarmManager myAlarmManager;

    private Context context;

    private Alarm currentRunningAlarm;

    private MyDatabaseHelper myDatabaseHelper;



    public static MyAlarmManager getInstance() {
        if (myAlarmManager == null) {
            myAlarmManager = new MyAlarmManager();
        }
        return myAlarmManager;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private MyAlarmManager() {
        alarmList = new ArrayList<>();
    }

    public void addAlarm(Alarm alarm) {
        for (Alarm a : alarmList) {
            if (a.getId() == alarm.getId()) return;
        }
        alarmList.add(alarm);
    }

    public void removeAlarm(Alarm alarm) {
        alarmList.remove(alarm);
        SQLiteDatabase database=getMyDatabaseHelper().getWritableDatabase();
        database.execSQL("delete from Alarm where id ="+alarm.getId());
    }


    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public void startAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void startNextDayAlarm(Alarm alarm){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarm(int alarmPosition) {
        Alarm alarm = alarmList.get(alarmPosition);
        if (alarm == currentRunningAlarm) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
        alarm.setStatus(Alarm.STOP);
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context, "Alarm.db", null, 1);
        SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
        database.execSQL("update Alarm set status=0 where id=" + alarm.getId());
        setNextAlarm();


    }

    public void setNextAlarm() {
        Alarm alarm = getNextAlarm();

        if (alarm != null) {
            startAlarm(alarm);
            currentRunningAlarm = alarm;
            Log.d("MyAlarmManager","设置下一个闹钟，"+alarm.getStringTime());
        }else{
            alarm=getNextDayAlarm();
            if(alarm!=null){
                startNextDayAlarm(alarm);
                currentRunningAlarm=alarm;
                Log.d("MyAlarmManager","设置下一天的闹钟，"+alarm.getStringTime());
            }
        }
    }

    private Alarm getNextAlarm() {
        Calendar calendar = Calendar.getInstance();
        int systemTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        Log.d("MyAlarmManager", "当前系统时间为" + systemTime);
        Alarm nextAlarm = null;
        int minTime = 2359;
        int tempTime;
        //找出下一个当天闹钟，距离系统时间最近的
        for (Alarm alarm : alarmList) {
            if(alarm.getStatus()==Alarm.RUN) {
                tempTime = alarm.getHour() * 100 + alarm.getMinute();
                if (tempTime - systemTime > 0 && tempTime - systemTime < minTime) {
                    minTime = tempTime-systemTime;
                    nextAlarm = alarm;
                }
            }
        }

        return nextAlarm;

    }

    private Alarm getNextDayAlarm(){
        Calendar calendar = Calendar.getInstance();
        int systemTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        Log.d("MyAlarmManager", "当前系统时间为" + systemTime);
        Alarm nextAlarm = null;
        int minTime = 2359;
        int tempTime;
        //不存在当前闹钟时找出下一天距离当前系统时间最近的闹钟
        if (nextAlarm == null) {
            minTime = 2359;
            for (Alarm alarm : alarmList) {
                if (alarm.getStatus() == Alarm.RUN) {
                    tempTime = alarm.getHour() * 100 + alarm.getMinute();
                    if (tempTime < minTime) {
                        minTime = tempTime;
                        nextAlarm = alarm;
                    }
                }
            }
        }
        return nextAlarm;
    }

    public void openAlarm(int alarmPosition){
        Alarm alarm=alarmList.get(alarmPosition);
        alarm.setStatus(Alarm.RUN);
        MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(context,"Alarm.db",null,1);
        SQLiteDatabase database=myDatabaseHelper.getWritableDatabase();
        database.execSQL("update Alarm set status=1 where id="+alarm.getId());
        setNextAlarm();
    }

    public Alarm getCurrentRunningAlarm(){
        return currentRunningAlarm;
    }

    public void triggerAlarm(Context receiverContext){
        Log.d("MyAlarmManager","触发当前闹钟："+currentRunningAlarm.getStringTime());


        Intent intent=new Intent(context, SoundService.class);
        intent.putExtra("soundPath",currentRunningAlarm.getSoundPath());

        /**
         * 须用广播接收器的context启动Service，否则service无法从intent中提取数据
         */
        receiverContext.startService(intent);

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("闹钟");
        builder.setMessage(currentRunningAlarm.getStringTime());
        builder.setPositiveButton("关闭闹钟", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.stopService(new Intent(context,SoundService.class));
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();


        if(currentRunningAlarm.getType()==Alarm.ONCE){
            int position=alarmList.indexOf(currentRunningAlarm);
            cancelAlarm(position);
            AlarmListActivity.alarmListAdapter.setTextColor(position,0x55000000);
            AlarmListActivity.alarmListAdapter.setSwitch(position,false);
        }
        setNextAlarm();


    }
    private MyDatabaseHelper getMyDatabaseHelper(){
        if(myDatabaseHelper==null){
            myDatabaseHelper=new MyDatabaseHelper(context,"Alarm.db",null,1);
        }
        return myDatabaseHelper;
    }
}
