package com.pardroid.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.pardroid.datacollector.R;

public class MyNotificationManager {
	static int mId;

	@SuppressLint("NewApi") public static void notifyUser(String title, String text, Intent intent, Activity activity) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity).setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).setContentText(text);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
		stackBuilder.addParentStack(activity.getClass());
		stackBuilder.addNextIntent(intent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mId, mBuilder.build());
	}

}
