package com.medicinetracker;

import android.app.Activity;
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
		db.eliminarDB();
		db.close();
		Toast.makeText(this, "Base de datos restaurada", Toast.LENGTH_LONG)
				.show();
	}
}
