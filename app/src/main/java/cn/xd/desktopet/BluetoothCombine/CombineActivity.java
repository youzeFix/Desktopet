package cn.xd.desktopet.BluetoothCombine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.xd.desktopet.R;

/**
 * Created by LeeHam on 2017/5/5.
 */

public class CombineActivity extends AppCompatActivity {

    public static final String TAG = "CombineActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"here is running      1111111111111111111111111111111111111111111111111111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combine_activity);

        Toolbar combine_toolbar = (Toolbar)findViewById(R.id.combine_activity_toolbar);
        combine_toolbar.setLogo(R.mipmap.ic_launcher);
        combine_toolbar.setTitle("Desktopet");
        combine_toolbar.setSubtitle("Pets-Combine");

        setSupportActionBar(combine_toolbar);
        combine_toolbar.setNavigationIcon(R.mipmap.bluetooth_combine);

        combine_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);

    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()){
                case R.id.insecure_connect_scan:
                    msg+="insecure scan";
                    break;
                case R.id.secure_connect_scan:
                    msg+="secure scan";
                    break;
                case R.id.discoverable:
                    msg+="make discoverable";
                    break;
            }
            if(!msg.equals("")){
                Toast.makeText(CombineActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.combine_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
