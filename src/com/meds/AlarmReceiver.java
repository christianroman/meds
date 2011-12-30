package com.meds;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

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
		CharSequence message = context.getString(R.string.tomarDosis);

		Notification notification = new Notification(R.drawable.icon, medicina,
				System.currentTimeMillis());
		notification.setLatestEventInfo(context, medicina, message,
				theappIntent);

		// notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;

		nm.notify(id, notification);

		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null) {
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		Ringtone r = RingtoneManager.getRingtone(context, alert);
		r.play();

		if (System.currentTimeMillis() > db.getFechaFinDosis(dosis)) {
			db.desactivaDosis(dosis);
		}
		db.close();

	}

}
