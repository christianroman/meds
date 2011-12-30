package com.meds;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AgregarDosis extends Activity {

	DatabaseHelper db;
	String medicamentos[];
	int ids[];
	Spinner s1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregardosis);

		((Button) findViewById(R.id.Button01)).getBackground().setColorFilter(
				0xFFFFE25B, PorterDuff.Mode.MULTIPLY);
		((Button) findViewById(R.id.Button02)).getBackground().setColorFilter(
				0xFFFFE25B, PorterDuff.Mode.MULTIPLY);

		db = new DatabaseHelper(this);

		if (db.getCantidadMedicamentos() > 0) {
			Cursor cursor = db.getMedicamentos();
			int items = cursor.getCount();

			medicamentos = new String[items];
			ids = new int[items];

			int i = 0;

			while (cursor.moveToNext()) {
				ids[i] = cursor.getInt(0);
				medicamentos[i] = cursor.getString(1);
				i++;
			}
			cursor.close();

			SpinnerMedicamentos();
		}
		db.close();
	}

	public void SpinnerMedicamentos() {
		s1 = (Spinner) findViewById(R.id.spinnerMedicamento);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, medicamentos);

		s1.setAdapter(adapter);

	}

	public void Cancelar(View button) {
		Intent intent = new Intent(this, Meds.class);
		startActivity(intent);
	}

	public void Aceptar(View button) {

		Boolean agregado = false;

		try {

			int idMedicamento = ids[((Spinner) findViewById(R.id.spinnerMedicamento))
					.getSelectedItemPosition()];

			String medicina = (String) ((Spinner) findViewById(R.id.spinnerMedicamento))
					.getSelectedItem();

			int dia = ((DatePicker) findViewById(R.id.datePickerFechaInicio))
					.getDayOfMonth();
			int mes = ((DatePicker) findViewById(R.id.datePickerFechaInicio))
					.getMonth();
			int anio = ((DatePicker) findViewById(R.id.datePickerFechaInicio))
					.getYear();

			String cantidad = ((EditText) findViewById(R.id.EditTextCantidad))
					.getText().toString();

			String dias = ((EditText) findViewById(R.id.EditTextDias))
					.getText().toString();

			String repeticion = ((EditText) findViewById(R.id.EditTextRepeticion))
					.getText().toString();

			int hora = ((TimePicker) findViewById(R.id.timePickerHoraInicio))
					.getCurrentHour();
			int min = ((TimePicker) findViewById(R.id.timePickerHoraInicio))
					.getCurrentMinute();

			Calendar fechaComprueba = Calendar.getInstance();
			fechaComprueba.set(Calendar.SECOND, 0);
			fechaComprueba.set(Calendar.MILLISECOND, 0);
			fechaComprueba.set(anio, mes, dia, hora, min);

			if (fechaComprueba.getTimeInMillis() > System.currentTimeMillis()) {
				if (!cantidad.equals("") && !dias.equals("")
						&& !repeticion.equals("")) {
					Dosis dosis = new Dosis(idMedicamento, dia, mes, anio,
							hora, min, Integer.valueOf(repeticion),
							Integer.valueOf(dias), Integer.valueOf(cantidad));
					db.AgregaDosis(dosis);
					programarAlarmas(dosis.getFechaInicio(),
							Integer.valueOf(dias), Integer.valueOf(repeticion),
							medicina);
					agregado = true;
				} else
					Toast.makeText(this,
							this.getString(R.string.camposNecesarios),
							Toast.LENGTH_LONG).show();

			} else {
				agregado = false;
				Toast.makeText(this, this.getString(R.string.fechaMayorActual),
						Toast.LENGTH_LONG).show();
			}

		}

		catch (Exception ex) {
			agregado = false;
			// CatchError(ex.toString());
		} finally {
			if (agregado == true) {
				notificaAgregado();
				Intent intent = new Intent(this, Meds.class);
				startActivity(intent);
			}
		}
	}

	private void programarAlarmas(long fechaInicio, int dias, int repeticion,
			String medicina) {

		Calendar c = Calendar.getInstance();

		c.setTimeInMillis(fechaInicio);

		setAlarma(c, medicina);
		Calendar hasta = Calendar.getInstance();
		hasta.setTimeInMillis(fechaInicio);

		hasta.add(Calendar.DAY_OF_MONTH, dias);
		while (c.getTime().compareTo(hasta.getTime()) < 0) {
			c.add(Calendar.HOUR, repeticion);
			setAlarma(c, medicina);
		}

	}

	public void setAlarma(Calendar c, String medicina) {

		Intent intent = new Intent(AgregarDosis.this, AlarmReceiver.class);
		final int id = (int) System.currentTimeMillis();

		int dosis = db.getLastDosis();
		db.AgregarAlarma(dosis, id, c.getTimeInMillis());

		intent.putExtra("id", id);
		intent.putExtra("dosis", dosis);
		intent.putExtra("medicina", medicina);
		PendingIntent appIntent = PendingIntent.getBroadcast(AgregarDosis.this,
				id, intent, PendingIntent.FLAG_ONE_SHOT);

		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), appIntent);

	}

	public void notificaAgregado() {
		Toast.makeText(this, this.getString(R.string.dosisAgregada),
				Toast.LENGTH_LONG).show();
	}

}
