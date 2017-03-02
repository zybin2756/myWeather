package com.zyb.myWeather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.zyb.myWeather.R;
import com.zyb.myWeather.adapter.CityManagerAdapter;
import com.zyb.myWeather.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27 0027.
 */


public class CityManagerActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton btn_return = null;
    private Button btn_edit = null;
    private ImageView img_addCity = null;
    private ListView lst_city = null;

    private CityManagerAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.city_manager_activity);
        btn_return = (ImageButton) findViewById(R.id.btn_return);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        img_addCity = (ImageView) findViewById(R.id.img_add);
        lst_city = (ListView) findViewById(R.id.lst_city);
        adapter = new CityManagerAdapter(this);
        lst_city.setAdapter(adapter);
        btn_edit.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        img_addCity.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit:
            {
                if(adapter.setEdit()){
                    btn_edit.setText("取消");
                }else{
                    btn_edit.setText("编辑");
                }
                adapter.notifyDataSetChanged();
                break;
            }

            case R.id.btn_return:{
                finish();
                break;
            }

            case R.id.img_add:{
                Intent intent = new Intent(this,SerachCityActivity.class);
                startActivityForResult(intent, Constants.START_ADD_CITY);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constants.START_ADD_CITY:

                break;
        }
    }
}
