package com.pardroid.datacollector;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class CollectionService extends Service {
	
	private static String TAG = "CollectionService";
	
	BroadcastReceiver mReceiver = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting Collection Service");
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new CollectionReceiver();
        registerReceiver(mReceiver, filter);
	}

    @Override
    public void onStart(Intent intent, int startId) {
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {
            // YOUR CODE
        } else {
            // YOUR CODE
        }
    }
    
    @Override
    public void onDestroy() {
    	Log.d(TAG, "Stopping Collection Service");
    	unregisterReceiver(mReceiver);
    }

}
