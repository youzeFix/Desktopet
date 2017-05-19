package cn.xd.desktopet.model;

import android.util.Log;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class Alarm {

    public static final int ONCE=0;
    public static final int EVERYDAY=1;

    public static final int RUN=1;
    public static final int STOP=0;

    private int id;
    private int hour;
    private int minute;
    private int type;
    private int status;
    private String stringTime;

    private String soundName;
    private String soundPath;


    public Alarm(int id,int hour,int minute,int type,int status,String soundName,String soundPath){
        this.id=id;
        this.hour=hour;
        this.minute=minute;
        this.type=type;
        this.status=status;
        this.soundName=soundName;
        this.soundPath=soundPath;
        stringTime=getStringNumber(hour)+":"+getStringNumber(minute);
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {

        this.type = type;
    }

    private String getStringNumber(int n){
        if(n<10)return "0"+n;
        else return String.valueOf(n);
    }

    public String getStringTime() {
        return stringTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "闹钟:[id:"+id+"时间:"+getStringTime()+"类型："+type+"状态："+status+"]";
    }
}
