package com.medicinetracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	public void onReceiveIntent(Context context, Intent intent) {
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent contentIntent = new Intent();

		int id = intent.getExtras().getInt("id");
		PendingIntent theappIntent = PendingIntent.getBroadcast(context, id,
				contentIntent, PendingIntent.FLAG_ONE_SHOT);
		CharSequence text = "Alarm Manager";
		CharSequence message = "Was Fired";

		Notification notification = new Notification(R.drawable.icon, text,
				System.currentTimeMillis());
		notification.setLatestEventInfo(context, text, message, theappIntent);
		nm.notify(id, notification);

	}

}
