package com.zyb.myWeather.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyb.myWeather.R;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class SerachCityActivity extends BaseActivity {
    ImageButton search_btn_return = null;
    EditText search_edt_key = null;
    LinearLayout ll_quickSearch = null;


    String[][] cities = new String[4][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_city_activity);
        search_edt_key = (EditText) findViewById(R.id.search_edit_key);
        search_btn_return = (ImageButton) findViewById(R.id.search_btn_return);
        ll_quickSearch = (LinearLayout) findViewById(R.id.ll_quickSearch);
        Drawable drawable = getResources().getDrawable(R.mipmap.search);
        drawable.setBounds(0,0,35,35);
        search_edt_key.setCompoundDrawables(drawable,null,null,null);
        addSearchLines();
    }

    private  void addSearchLines(){
        for(int i = 0; i < 4 ; i++){
            SearchKeyLine s = new SearchKeyLine(this);
            ll_quickSearch.addView(s);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
