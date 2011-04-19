package com.meds;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
	DatabaseHelper db;

	public void onReceiveIntent(Context context, Intent intent) {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		db = new DatabaseHelper(context);

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent contentIntent = new Intent();

		int id = intent.getExtras().getInt("id");
		int dosis = intent.getExtras().getInt("dosis");
		CharSequence medicina = intent.getExtras().getCharSequence("medicina");
		PendingIntent theappIntent = PendingIntent.getBroadcast(context, id,
				contentIntent, PendingIntent.FLAG_ONE_SHOT);
		CharSequence message = "Tomar dosis pendiente";

		Notification notification = new Notification(R.drawable.icon, medicina,
				System.currentTimeMillis());
		notification.setLatestEventInfo(context, medicina, message,
				theappIntent);

		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		nm.notify(id, notification);
		
		if(System.currentTimeMillis() > db.getFechaFinDosis(dosis) ){
			db.desactivaDosis(dosis);
		}
		db.close();

	}

}
