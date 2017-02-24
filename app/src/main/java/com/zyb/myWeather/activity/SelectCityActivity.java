package com.zyb.myWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyb.myWeather.R;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.City;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.Province;
import com.zyb.myWeather.utils.Constants;
import com.zyb.myWeather.utils.HttpRequestListener;
import com.zyb.myWeather.utils.HttpUtil;
import com.zyb.myWeather.utils.LogUtil;
import com.zyb.myWeather.utils.ParseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class SelectCityActivity extends Activity {


    private TextView info;
    private ListView cityListView;

    private myWeatherDB weatherDB;

    private List<Province> provinces;
    private List<City> cities;
    private List<County> counties;

    private List<String> list;
    private mAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_city_activity);
        info = (TextView) findViewById(R.id.info);
        cityListView = (ListView) findViewById(R.id.cityListView);
        list = new ArrayList<String>();
        adapter = new mAdapter(this,list);
        cityListView.setAdapter(adapter);
        weatherDB = myWeatherDB.getInstance(this);
        loadProvince();
    }

    public void loadProvince(){
        provinces = weatherDB.loadProvince();
        if(provinces != null){
            list.clear();
            for(Province p : provinces){
                list.add(p.getProvince_name());
                LogUtil.i(p.getProvince_name());
            }
            adapter.notifyDataSetChanged();
        }else{
            loadDataFromService(null, Constants.PROVINCE);
        }
    }

    public void loadDataFromService(final String code, final int type){
        String address = null;
        if(code != null){
            address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
        }else{
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }

        HttpUtil.sendHttpRequset(address, new HttpRequestListener() {
            @Override
            public void OnFinish(String response) {

                boolean result = false;
                switch (type){
                    case Constants.PROVINCE:
                        LogUtil.i("parseProvinceResponce");
                        result = ParseUtil.parseProvinceResponce(weatherDB,response);
                        break;
                }

                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (type){
                                case Constants.PROVINCE:
                                    loadProvince();
                                    break;
                            }
                        }
                    });
                }
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SelectCityActivity.this,"加载失败啦...",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public class mAdapter extends BaseAdapter{

        private List<String> list = null;
        private Context ctx;
        public mAdapter(Context ctx,List<String> list) {
            super();
            this.list = list;
            this.ctx = ctx;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return this.list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if(convertView == null){

                convertView = View.inflate(ctx,R.layout.city_item,null);
                vh = new ViewHolder();
                vh.info_name = (TextView) convertView.findViewById(R.id.info_name);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder) convertView.getTag();
            }

            vh.info_name.setText(list.get(position));
            return convertView;
        }


        class ViewHolder{
            TextView info_name;
        }
    }
}
