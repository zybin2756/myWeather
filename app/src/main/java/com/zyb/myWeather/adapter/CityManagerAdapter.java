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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class CityManagerAdapter extends BaseAdapter {

    private List<County> cityList = new ArrayList<>();
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

    public List<County> getCityList() {
        return cityList;
    }

    public void setCityList(List<County> cityList) {
        this.cityList = null;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return this.cityList.size();
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

        vh.txt_cityName.setText(cityList.get(position).getCounty_name());

        //给删除按钮添加点击事件
        vh.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityList.remove(position);
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
