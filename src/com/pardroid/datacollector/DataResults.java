package com.pardroid.datacollector;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class DataResults extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.show_data);
    	SharedPreferences sharedPref = this.getSharedPreferences("myData", Context.MODE_PRIVATE);
    	Integer currValue = (Integer)sharedPref.getInt("OnCount", 0);
    	EditText et = (EditText) findViewById(R.id.editText1);
    	et.setText(currValue.toString());
    	et.setKeyListener(null);
    }

}
