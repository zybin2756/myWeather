package com.zyb.myWeather.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zyb.myWeather.R;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class SearchKeyLine extends LinearLayout implements View.OnClickListener{
    private EditText edit_search = null;
    public SearchKeyLine(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_key_line,this);
    }

    public SearchKeyLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SearchKeyLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAttr(EditText edit_search,String[] cities){
        this.edit_search = edit_search;
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn1.setText(cities[0]);
        btn2.setText(cities[1]);
        btn3.setText(cities[2]);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button)v;
        edit_search.setText(btn.getText());
        edit_search.setSelection(btn.getText().length());
    }
}
