package cn.xd.desktopet.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import cn.xd.desktopet.R;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class SoundService extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String soundPath=intent.getStringExtra("soundPath");

        mediaPlayer=new MediaPlayer();
        try {
            if(soundPath.equals("")){
                String uriPath="android.resource://"+getPackageName()+"/"+R.raw.defaultalarmringtone;
                mediaPlayer.setDataSource(this, Uri.parse(uriPath));
            }else{
                mediaPlayer.setDataSource(soundPath);
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
}
