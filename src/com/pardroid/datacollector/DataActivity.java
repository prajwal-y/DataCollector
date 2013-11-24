package com.pardroid.datacollector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.pardroid.util.MyNotificationManager;

public class DataActivity extends Activity {
	
	private static final int REQUEST_PATH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("serviceState", Context.MODE_PRIVATE);
    	boolean state = sharedPref.getBoolean("state", false);
    	CheckBox cb = (CheckBox)findViewById(R.id.checkBox1);
    	cb.setChecked(state);
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
    		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    			MyNotificationManager.notifyUser("Data Collection", "Enabled", new Intent(), this);
    		Toast.makeText(this, "Data Collection enabled", Toast.LENGTH_SHORT).show();
    		editor.putBoolean("state", true);
    		startService(new Intent(this, CollectionService.class));
    	}
    	else {
    		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    			MyNotificationManager.notifyUser("Data Collection", "Disabled", new Intent(), this);
    		Toast.makeText(this, "Data Collection disabled", Toast.LENGTH_SHORT).show();
    		editor.putBoolean("state", false);
    		stopService(new Intent(this, CollectionService.class));
    	}
    	editor.commit();
    }
    
    /**
     * ActionListener for Reset Count Button
     * @param view
     */
    public void resetCount(View view) {
    	SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("myData", Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPref.edit();
    	editor.putInt("OnCount", 0);
    	editor.commit();
    }
    
}
