package com.medicinetracker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AgregarDosis extends Activity {

	DatabaseHelper db;
	String medicamentos[];
	Spinner s1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregardosis);

		db = new DatabaseHelper(this);

		if (db.getCantidadMedicamentos() > 0) {
			medicamentos = db.getMedicamentos();
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
		Intent intent = new Intent(this, medicinetracker.class);
		startActivity(intent);
	}

	public void Aceptar(View button) {

		Boolean agregado = false;

		try {

			int idMedicamento = (int) (((Spinner) findViewById(R.id.spinnerMedicamento))
					.getSelectedItemId() + 1);

			String medicina = (String) ((Spinner) findViewById(R.id.spinnerMedicamento))
					.getSelectedItem();

			int dia = ((DatePicker) findViewById(R.id.datePickerFechaInicio))
					.getDayOfMonth();
			int mes = ((DatePicker) findViewById(R.id.datePickerFechaInicio))
					.getMonth();
			int anio = ((DatePicker) findViewById(R.id.datePickerFechaInicio))
					.getYear();

			int cantidad = Integer
					.valueOf(((EditText) findViewById(R.id.EditTextCantidad))
							.getText().toString());

			int dias = Integer
					.valueOf(((EditText) findViewById(R.id.EditTextDias))
							.getText().toString());

			int repeticion = Integer
					.valueOf(((EditText) findViewById(R.id.EditTextRepeticion))
							.getText().toString());

			int hora = ((TimePicker) findViewById(R.id.timePickerHoraInicio))
					.getCurrentHour();
			int min = ((TimePicker) findViewById(R.id.timePickerHoraInicio))
					.getCurrentMinute();

			Dosis dosis = new Dosis(idMedicamento, dia, mes, anio, hora, min,
					repeticion, dias, cantidad);
			
			db.AgregaDosis(dosis);
			programarAlarmas(dosis.getFechaInicio(), dias, repeticion, medicina);
			agregado = true;
		}

		catch (Exception ex) {
			agregado = false;
			// CatchError(ex.toString());
		} finally {
			if (agregado == true) {
				notificaAgregado();
				Intent intent = new Intent(this, medicinetracker.class);
				startActivity(intent);
			}
			db.close();
		}
	}

	private void programarAlarmas(String fechaInicio, int dias, int repeticion,
			String medicina) {

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date fecha = (Date) df.parse(fechaInicio);
			Calendar c = Calendar.getInstance();
			c.setTime(fecha);

			for (int i = 0; i < dias; i++) {
				c.add(Calendar.MINUTE, 1);
				setAlarma(c, medicina);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public void setAlarma(Calendar c, String medicina) {

		Intent intent = new Intent(AgregarDosis.this, AlarmReceiver.class);
		final int id = (int) System.currentTimeMillis();
		intent.putExtra("id", id);
		intent.putExtra("medicina", medicina);
		PendingIntent appIntent = PendingIntent.getBroadcast(AgregarDosis.this,
				id, intent, PendingIntent.FLAG_ONE_SHOT);

		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), appIntent);

	}

	public void notificaAgregado() {
		Toast.makeText(this, "Dosis agregada", Toast.LENGTH_LONG).show();
	}

}
