package cn.xd.desktopet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.xd.desktopet.R;
import cn.xd.desktopet.model.Sound;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class SoundListAdapter extends BaseAdapter {
    private int selectPosition=-1;

    private List<Sound> listDatas;

    private Context context;

    public SoundListAdapter(Context context,List<Sound> listDatas){
        this.context=context;
        this.listDatas=listDatas;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;

        if(convertView==null){

            convertView= LayoutInflater.from(context).inflate(R.layout.sound_list_item,null);

            viewHolder=new ViewHolder();
            viewHolder.nameTv=(TextView)convertView.findViewById(R.id.name_tv);
            viewHolder.selectedIv=(ImageView)convertView.findViewById(R.id.selected_iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Sound sound=listDatas.get(position);

        viewHolder.nameTv.setText(sound.getName());

        if(selectPosition>=0&&selectPosition==position){
            viewHolder.selectedIv.setVisibility(View.VISIBLE);
            viewHolder.nameTv.setTextColor(Color.BLACK);
        }else{
            viewHolder.selectedIv.setVisibility(View.GONE);
            viewHolder.nameTv.setTextColor(0x8A000000);
        }



        return convertView;
    }

    class ViewHolder{
        TextView nameTv;
        ImageView selectedIv;
    }

    public void changeSelected(int selectPosition){
        if(this.selectPosition!=selectPosition){
            this.selectPosition=selectPosition;
            notifyDataSetChanged();
        }

    }

    public int getSelectPosition(){
        return selectPosition;
    }
}
