package com.medicinetracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Preferencias extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencias);

		((Button) findViewById(R.id.rbd)).getBackground().setColorFilter(
				0xFFFFE25B, PorterDuff.Mode.MULTIPLY);
	}

	public void Restaurar(View button) {
		DatabaseHelper db = new DatabaseHelper(this);

		Cursor c = db.getAllAlarmas();

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

		while (c.moveToNext()) {

			Intent intent = new Intent(this, AlarmReceiver.class);
			PendingIntent appIntent = PendingIntent.getBroadcast(this,
					c.getInt(0), intent, PendingIntent.FLAG_ONE_SHOT);

			am.cancel(appIntent);

		}
		c.close();

		db.eliminarDB();
		db.close();
		Toast.makeText(this, "Base de datos restaurada", Toast.LENGTH_LONG)
				.show();
	}
}
