package com.pardroid.datacollector;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.pardroid.util.MyNotificationManager;

public class DataActivity extends Activity {
	
	private static final int REQUEST_PATH = 1;
	private static final String TAG = "DataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("serviceState", Context.MODE_PRIVATE);
    	boolean state = sharedPref.getBoolean("state", false);
    	String time = sharedPref.getString("time", "");
    	CheckBox cb = (CheckBox)findViewById(R.id.checkBox1);
    	cb.setChecked(state);
    	TextView tv = (TextView)findViewById(R.id.textView1);
    	Log.d(TAG, "State: " + state + " and Time: " + time);
    	if(state)
    		tv.setText(time); 
    	else 
    		tv.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data, menu);
        return true;
    }
    
    /**
     * Navigate to Data Screen
     * @param view
     */
    public void showData(View view) {
    	  Intent intent = new Intent(this, DataResults.class);
          startActivityForResult(intent, REQUEST_PATH);
    }
    
    /**
     * ActionListener for change in CheckBox selection state
     * @param view
     */
    public void toggleCollection(View view) {
    	boolean checked = ((CheckBox) view).isChecked();
    	int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    	SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("serviceState", Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPref.edit();
    	if (checked){
    		setDate();
    		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    			MyNotificationManager.notifyUser("Data Collection", "Enabled", new Intent(), this);
    		Toast.makeText(this, "Data Collection enabled", Toast.LENGTH_SHORT).show();
    		editor.putBoolean("state", true);
    		startService(new Intent(this, CollectionService.class));
    	}
    	else {
    		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    			MyNotificationManager.notifyUser("Data Collection", "Disabled and count reset", new Intent(), this);
    		Toast.makeText(this, "Data Collection disabled and count reset", Toast.LENGTH_SHORT).show();
    		editor.putBoolean("state", false);
    		stopService(new Intent(this, CollectionService.class));
    		resetValues();
    		TextView tv = (TextView)findViewById(R.id.textView1);
    		tv.setText("");
    	}
    	editor.commit();
    }
    
    /**
     * ActionListener for Reset Count Button
     * @param view
     */
    public void resetCount(View view) {
    	setDate();
    	resetValues();
    }
    
    private void setDate() {
    	SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("serviceState", Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPref.edit();
    	String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText(myDate);
		editor.putString("time", myDate);
		editor.commit();
    }
    
    private void resetValues() {
    	SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("myData", Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPref.edit();
    	editor.putInt("OnCount", 0);
    	editor.commit();
    }
    
}
