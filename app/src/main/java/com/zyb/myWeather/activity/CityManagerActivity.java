package com.zyb.myWeather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.zyb.myWeather.R;
import com.zyb.myWeather.adapter.CityManagerAdapter;
import com.zyb.myWeather.utils.Constants;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27 0027.
 */


public class CityManagerActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton btn_return = null;
    private Button btn_edit = null;
    private Button btn_cancel = null;
    private ImageView img_addCity = null;
    private ListView lst_city = null;

    private CityManagerAdapter adapter = null;
    private MyApplication app = null;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.city_manager_activity);
        app = (MyApplication) getApplication();
        btn_return = (ImageButton) findViewById(R.id.btn_return);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        img_addCity = (ImageView) findViewById(R.id.img_add);
        lst_city = (ListView) findViewById(R.id.lst_city);
        adapter = new CityManagerAdapter(this);
        lst_city.setAdapter(adapter);
        btn_edit.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        img_addCity.setOnClickListener(this);

        changeData();
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
                if(!adapter.isEdit()){
                    btn_edit.setText("确定");
                    btn_return.setVisibility(View.GONE);
                    btn_cancel.setVisibility(View.VISIBLE);
                    adapter.setEdit(true);
                    isEdit = false;
                }else{
                    app.getDb().deleteUserCity(adapter.getCountyList());
                    btn_edit.setText("编辑");
                    btn_return.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.GONE);
                    adapter.setEdit(false);
                    isEdit = true;
                }

                changeData();
                break;
            }

            case R.id.btn_cancel:
            {
                btn_edit.setText("编辑");
                adapter.setEdit(false);
                btn_return.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.GONE);
                isEdit = false;
                changeData();
                break;
            }

            case R.id.btn_return:{
                setResult(Constants.FINISH_ADD_CITY);
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
       switch (resultCode){
           case Constants.FINISH_ADD_CITY:
               setResult(Constants.FINISH_ADD_CITY);
               break;
           case Constants.REFRESH_ADD_CITY:
               setResult(Constants.REFRESH_ADD_CITY,data);
               break;
       }
       finish();
    }

    private void changeData(){
        if(isEdit) {
            app.refreshData();
        }
        adapter.setCityList(app.getDb().loadUserCity());
        adapter.notifyDataSetChanged();
    }
}
