package com.pardroid.datacollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class CollectionReceiver extends BroadcastReceiver {

    private static String TAG = "CollectionReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Received broadcast message");
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        	Log.d(TAG, "Screen turned on");
        	SharedPreferences sharedPref = context.getSharedPreferences("myData", Context.MODE_PRIVATE);
        	int currValue = sharedPref.getInt("OnCount", 0);
        	SharedPreferences.Editor editor = sharedPref.edit();
        	editor.putInt("OnCount", currValue + 1);
        	editor.commit();
        }
/*        Intent i = new Intent(context, CollectionService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);*/
	}

}
