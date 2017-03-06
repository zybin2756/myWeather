package com.zyb.myWeather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zyb.myWeather.R;
import com.zyb.myWeather.adapter.SearchCityAdapter;
import com.zyb.myWeather.db.myWeatherDB;
import com.zyb.myWeather.model.County;
import com.zyb.myWeather.model.UserCity;
import com.zyb.myWeather.utils.Constants;
import com.zyb.myWeather.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class SerachCityActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    ImageButton search_btn_return = null;
    EditText search_edt_key = null;
    LinearLayout ll_quickSearch = null;
    ListView lv_result = null;
    ProgressBar progressBar2 = null;
    SearchCityAdapter searchCityAdapter = null;
    SearchSyncTask task = null;
    MyApplication app = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_city_activity);

        app = (MyApplication) getApplication();

        search_edt_key = (EditText) findViewById(R.id.search_edit_key);
        search_btn_return = (ImageButton) findViewById(R.id.search_btn_return);
        ll_quickSearch = (LinearLayout) findViewById(R.id.ll_quickSearch);
        lv_result = (ListView) findViewById(R.id.lv_result);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        Drawable drawable = getResources().getDrawable(R.mipmap.search);
        drawable.setBounds(0,0,35,35);
        search_edt_key.setCompoundDrawables(drawable,null,null,null);
        search_edt_key.addTextChangedListener(textWatcher);
        searchCityAdapter = new SearchCityAdapter(this);
        lv_result.setAdapter(searchCityAdapter);
        lv_result.setOnItemClickListener(this);

        search_btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addSearchLines();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length() > 0) {
                if(task != null){
                    task.cancel(true);
                    task = null;
                }
                task = new SearchSyncTask();
                task.execute(s.toString());
            }else{
                ll_quickSearch.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.GONE);
                lv_result.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        County c = searchCityAdapter.getCountyList().get(position);

        if(app.getDb().saveUserCity(c)){
            app.refreshData();
            setResult(Constants.FINISH_ADD_CITY);
        }else{
            Intent intent = new Intent();
            List<UserCity> userCityList = app.getUserCityList();
            for(int i = 0; i < userCityList.size(); i++){
                if(c.getCounty_code().equalsIgnoreCase(userCityList.get(i).getCounty_code())){
                    intent.putExtra("index",i);
                    break;
                }
            }
            setResult(Constants.REFRESH_ADD_CITY,intent);
        }
        finish();
    }

    class SearchSyncTask extends AsyncTask<String,Void,List<County>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isCancelled()) return;
            ll_quickSearch.setVisibility(View.GONE);
            progressBar2.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<County> counties) {
            super.onPostExecute(counties);
            if(isCancelled()) return;
            if(counties != null){
                searchCityAdapter.setCountyList(counties);
                searchCityAdapter.notifyDataSetChanged();
                lv_result.setVisibility(View.VISIBLE);
                ll_quickSearch.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(SerachCityActivity.this,"无匹配城市",Toast.LENGTH_SHORT).show();
                lv_result.setVisibility(View.GONE);
            }
            progressBar2.setVisibility(View.GONE);
        }

        @Override
        protected List<County> doInBackground(String... params) {
            if(isCancelled()) return null;

            return app.getDb().searchCityByKey(params[0]);
        }
    }

    private  void addSearchLines(){
        String[][] cities = new String[5][];
        cities[0] =  new String[]{"广州","苏州","上海"};
        cities[1] =  new String[]{"佛山","河源","普宁"};
        cities[2] =  new String[]{"茂名","惠州","天津"};
        cities[3] =  new String[]{"中山","苏州","江门"};
        cities[4] =  new String[]{"北海","潮州","梅州"};
        for(int i = 0; i < 5 ; i++){
            SearchKeyLine s = new SearchKeyLine(this);
            s.setAttr(search_edt_key,cities[i]);
            ll_quickSearch.addView(s);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
