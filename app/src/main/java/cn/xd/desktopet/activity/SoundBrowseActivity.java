package cn.xd.desktopet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.xd.desktopet.R;
import cn.xd.desktopet.adapter.SoundListAdapter;
import cn.xd.desktopet.model.Sound;
import cn.xd.desktopet.util.FileUtil;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class SoundBrowseActivity extends AppCompatActivity {

    private String title="闹铃选择";

    private final int AUDIO_SCAN_FINISHED=0;

    private Toolbar toolbar;

    private ListView soundLv;

    private SoundListAdapter soundListAdapter;

    private List<Sound> soundListDatas;

    private ProgressDialog progressDialog;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUDIO_SCAN_FINISHED:
                    closeWaitingDialog();
                    initDatas();
                    initStatus();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundbrowse);

        findViews();

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showWaitingDialog();

        Thread scanAudioThread=new Thread(new Runnable() {
            @Override
            public void run() {
                soundListDatas=new ArrayList<>();
                List<File> soundFiles= FileUtil.getAudioFileInStorage();
                for(File file:soundFiles){
                    soundListDatas.add(new Sound(file.getName(),file.getAbsolutePath()));
                }
                Message msg=new Message();
                msg.what=AUDIO_SCAN_FINISHED;
                handler.sendMessage(msg);
            }
        });
        scanAudioThread.start();

        setListener();

    }

    private void findViews(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        soundLv=(ListView)findViewById(R.id.sound_listview);
    }

    private void initDatas(){
        soundListAdapter=new SoundListAdapter(this,soundListDatas);
        soundLv.setAdapter(soundListAdapter);
    }


    private void setListener(){
        soundLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                soundListAdapter.changeSelected(position);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initStatus(){
        Intent intent=getIntent();
        String currSoundName=intent.getStringExtra("currSoundName");
        if(!currSoundName.equals("默认铃声")){
            Sound tempSound;
            for(int i=0;i<soundListDatas.size();++i){
                tempSound=soundListDatas.get(i);
                if(tempSound.getName().equals(currSoundName)){
                    soundListAdapter.changeSelected(i);
                    break;
                }
            }
        }
    }

    private void showWaitingDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在扫描音频文件，请稍等...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void closeWaitingDialog(){
        progressDialog.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_soundbrowse,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_confirm:
                Sound selectedSound=null;
                int selectedPosition=soundListAdapter.getSelectPosition();
                if(selectedPosition!=-1){
                    selectedSound=soundListDatas.get(selectedPosition);
                }
                Intent intent=new Intent();
                if(selectedSound!=null){
                    intent.putExtra("soundName",selectedSound.getName());
                    intent.putExtra("soundPath",selectedSound.getPath());
                    setResult(RESULT_OK,intent);
                }else{
                    setResult(RESULT_CANCELED,intent);
                }
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
