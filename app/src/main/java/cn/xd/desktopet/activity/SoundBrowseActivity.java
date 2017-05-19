package cn.xd.desktopet.activity;

import android.content.Intent;
import android.os.Bundle;
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

    private String title="BrowseSound";

    private Toolbar toolbar;

    private ListView soundLv;

    private SoundListAdapter soundListAdapter;

    private List<Sound> soundListDatas;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundbrowse);

        findViews();

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        initDatas();

        setListener();

        initStatus();
    }

    private void findViews(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        soundLv=(ListView)findViewById(R.id.sound_listview);
    }

    private void initDatas(){
        soundListDatas=new ArrayList<>();
        List<File> soundFiles= FileUtil.getAudioFileInStorage();
        for(File file:soundFiles){
            soundListDatas.add(new Sound(file.getName(),file.getAbsolutePath()));
        }
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
                intent.putExtra("soundName",selectedSound.getName());
                intent.putExtra("soundPath",selectedSound.getPath());
                setResult(RESULT_OK,intent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
