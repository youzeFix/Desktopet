package cn.xd.desktopet.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import cn.xd.desktopet.R;
import cn.xd.desktopet.database.MyDatabaseHelper;
import cn.xd.desktopet.model.Alarm;
import cn.xd.desktopet.model.Sound;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class AddAlarmActivity extends AppCompatActivity {

    private String title="AddAlarm";


    private Toolbar toolbar;

    private TimePicker timePicker;

    private Spinner typeSpinner;

    private RelativeLayout soundItem;

    private TextView soundNameTv;

    private String soundName;
    private String soundPath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addalarm);

        findViews();

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        setListener();

        initStatus();

    }

    private void findViews(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        timePicker=(TimePicker)findViewById(R.id.timePicker);
        typeSpinner=(Spinner)findViewById(R.id.type_spinner);
        soundItem=(RelativeLayout)findViewById(R.id.sound_item);
        soundNameTv=(TextView)findViewById(R.id.sound_name_tv);
    }

    private void initStatus(){
        soundName="默认铃声";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addalarm,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(this,"Alarm.db",null,1);
                SQLiteDatabase database=myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put("hour",timePicker.getCurrentHour());
                contentValues.put("minute",timePicker.getCurrentMinute());
                String typeStr=getResources().getStringArray(R.array.alarm_type)[(int)typeSpinner.getSelectedItemId()];
                if(typeStr.equals("一次"))contentValues.put("type", Alarm.ONCE);
                else if(typeStr.equals("每天"))contentValues.put("type",Alarm.EVERYDAY);
                contentValues.put("status",Alarm.RUN);
                contentValues.put("soundName",soundName);
                if(soundName.equals("默认铃声"))contentValues.put("soundPath", Sound.defaultSoundPath);
                else contentValues.put("soundPath",soundPath);
                database.insert("Alarm",null,contentValues);
                Log.d("AddAlarmActivity","点击confirmAction");
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    private void setListener(){
        soundItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddAlarmActivity.this,SoundBrowseActivity.class);
                intent.putExtra("currSoundName",soundNameTv.getText());
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    soundName=data.getStringExtra("soundName");
                    soundPath=data.getStringExtra("soundPath");
                    soundNameTv.setText(soundName);
                }
                break;
            default:
                break;
        }
    }
}
