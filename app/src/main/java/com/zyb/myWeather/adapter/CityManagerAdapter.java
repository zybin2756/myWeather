package com.zyb.myWeather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyb.myWeather.R;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.UserCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class CityManagerAdapter extends BaseAdapter {

    private List<UserCity> countyList = new ArrayList<>();
    private Context ctx = null;

    private boolean isEdit = false;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public CityManagerAdapter(Context ctx) {
        super();
        this.ctx = ctx;
    }

    public List<UserCity> getCountyList() {
        return countyList;
    }

    public void setCityList(List<UserCity> countyList) {
        this.countyList.clear();
        this.countyList.addAll(countyList);
    }

    @Override
    public int getCount() {
        return this.countyList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = View.inflate(ctx,R.layout.city_item,null);
            vh.txt_cityName = (TextView) convertView.findViewById(R.id.txt_cityName);
            vh.txt_curCity = (TextView) convertView.findViewById(R.id.txt_curCity);
            vh.btn_del = (ImageButton) convertView.findViewById(R.id.btn_del);
            vh.img_location = (ImageView) convertView.findViewById(R.id.img_location);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        //当前定位
        if(position == 0){
            vh.img_location.setVisibility(View.VISIBLE);
        }else{
            vh.img_location.setVisibility(View.GONE);
        }

        //如果处于编辑状态
        if(isEdit){
            vh.btn_del.setVisibility(View.VISIBLE);
        }else{
            vh.btn_del.setVisibility(View.GONE);
        }

        vh.txt_cityName.setText(countyList.get(position).getCounty_name());

        //给删除按钮添加点击事件
        vh.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countyList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView txt_cityName;
        ImageButton btn_del;
        ImageView img_location;
        TextView txt_curCity;
    }
}
