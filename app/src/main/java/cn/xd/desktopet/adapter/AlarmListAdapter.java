package cn.xd.desktopet.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xd.desktopet.R;
import cn.xd.desktopet.model.Alarm;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class AlarmListAdapter extends BaseAdapter {

        private Map<Integer,View> viewBuffer=new HashMap<>();


        private List<Alarm> listDatas;
        private Context context;



        public AlarmListAdapter(Context context, List<Alarm> listDatas) {
            this.listDatas=listDatas;
            this.context=context;
        }

        @Override
        public int getCount() {
            return listDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return listDatas.get(position).getId();
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.alarm_list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.alarmTimeTv=(TextView)convertView.findViewById(R.id.alarm_time);
                viewHolder.alarmTypeTv=(TextView)convertView.findViewById(R.id.alarm_type);
                viewHolder.alarmSwitch=(Switch)convertView.findViewById(R.id.alarm_switch);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
                //先清除convertView中switch的OnCheckedChangeListener否则下面setChecked时会出错
                viewHolder.alarmSwitch.setOnCheckedChangeListener(null);
            }

            viewHolder.alarmTimeTv.setText(listDatas.get(position).getStringTime());
            switch (listDatas.get(position).getType()){
                case Alarm.ONCE:
                    viewHolder.alarmTypeTv.setText("一次");
                    break;
                case Alarm.EVERYDAY:
                    viewHolder.alarmTypeTv.setText("每天");
                    break;
                default:
                    break;
            }

            Alarm alarm=listDatas.get(position);
            if(alarm.getStatus()==Alarm.RUN){
                viewHolder.alarmSwitch.setChecked(true);
                viewHolder.alarmTimeTv.setTextColor(Color.BLACK);
                viewHolder.alarmTypeTv.setTextColor(Color.BLACK);
            }
            else if(listDatas.get(position).getStatus()==Alarm.STOP){
                viewHolder.alarmSwitch.setChecked(false);
                viewHolder.alarmTimeTv.setTextColor(0x55000000);
                viewHolder.alarmTypeTv.setTextColor(0x55000000);
            }

            viewHolder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked){
                        Intent intent=new Intent("cn.xd.desktopet.openalarm");
                        setTextColor(position,Color.BLACK);
                        intent.putExtra("alarmPosition",position);
                        context.sendBroadcast(intent);

                    }else{
                        Intent intent=new Intent("cn.xd.desktopet.closealarm");
                        setTextColor(position,0x55000000);
                        intent.putExtra("alarmPosition",position);
                        context.sendBroadcast(intent);
                    }
                }
            });



            viewBuffer.put(position,convertView);

            return convertView;
        }
        class ViewHolder{
            public TextView alarmTimeTv;
            public TextView alarmTypeTv;
            public Switch alarmSwitch;
        }
        public void setTextColor(int viewPosition,int color){
            TextView alarmTimeTv=(TextView)viewBuffer.get(viewPosition).findViewById(R.id.alarm_time);
            TextView alarmTypeTv=(TextView)viewBuffer.get(viewPosition).findViewById(R.id.alarm_type);
            alarmTimeTv.setTextColor(color);
            alarmTypeTv.setTextColor(color);
        }
        public void setSwitch(int viewPosition,boolean status){
            Switch mSwitch=(Switch)viewBuffer.get(viewPosition).findViewById(R.id.alarm_switch);
            mSwitch.setChecked(status);
        }

}


