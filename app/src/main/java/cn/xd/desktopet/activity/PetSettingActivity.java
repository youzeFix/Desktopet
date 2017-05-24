package cn.xd.desktopet.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cn.xd.desktopet.R;
import cn.xd.desktopet.control.MyWindowManager;
import cn.xd.desktopet.model.Pet;
import cn.xd.desktopet.util.MyApplication;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class PetSettingActivity extends AppCompatActivity {

    private final String title="宠物设置";

    private Toolbar toolbar;
    private Spinner petThemeSpinner;
    private EditText petNameEt;
    private Spinner petSexSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petsetting);

        findView();

        toolbar.setTitle(title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initStatus();

        setListener();

    }

    private void findView(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        petThemeSpinner=(Spinner)findViewById(R.id.pet_theme_spinner);
        petNameEt=(EditText)findViewById(R.id.pet_name_et);
        petSexSpinner=(Spinner)findViewById(R.id.pet_sex_spinner);
    }

    private void initStatus(){
        petThemeSpinner.setSelection(Pet.theme);
        petNameEt.setText(Pet.name);
        if(Pet.sex.equals("boy")) petSexSpinner.setSelection(0);
        else if(Pet.sex.equals("girl"))petSexSpinner.setSelection(1);
        else petSexSpinner.setSelection(0);
    }

    private void setListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_petsetting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_confirm:
                String petTheme=getResources().getStringArray(R.array.pet_theme)
                        [(int)petThemeSpinner.getSelectedItemId()];
                String petName=petNameEt.getText().toString();
                String petSex=getResources().getStringArray(R.array.pet_sex)
                        [(int)petSexSpinner.getSelectedItemId()];

                Pet.theme=(int)petThemeSpinner.getSelectedItemId();
                Pet.name=petNameEt.getText().toString();
                Pet.sex=getResources().getStringArray(R.array.pet_sex)
                        [(int)petSexSpinner.getSelectedItemId()];

                Pet.saveSetting(MyApplication.getContext());

                Log.d("PetSettingActivity","设置信息：[宠物主题："+petTheme+"宠物姓名:"+petName+"宠物性别:"+petSex+"]");

                if(MyWindowManager.isPetWindowShowing()){
                    MyWindowManager.removePetSmallWindow(MyApplication.getContext());
                    MyWindowManager.createPetSmallWindow(MyApplication.getContext());
                }

                Toast.makeText(this, "宠物设置已更改", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
