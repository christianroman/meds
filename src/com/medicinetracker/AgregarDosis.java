package com.medicinetracker;

import android.app.Activity;
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

	}

	public void SpinnerMedicamentos() {
		s1 = (Spinner) findViewById(R.id.spinnerMedicamento);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, medicamentos);

		s1.setAdapter(adapter);
	}

	public void Cancelar(View button) {
		Intent intent = new Intent(this, Agregar.class);
		startActivity(intent);
	}

	public void Aceptar(View button) {

		Boolean agregado = false;

		try {
			
			int idMedicamento = (int) (((Spinner)findViewById(R.id.spinnerMedicamento)).getSelectedItemId()+1);
			
			int dia = ((DatePicker)findViewById(R.id.datePickerFechaInicio)).getDayOfMonth();
			int mes = ((DatePicker)findViewById(R.id.datePickerFechaInicio)).getMonth();
			int año = ((DatePicker)findViewById(R.id.datePickerFechaInicio)).getYear();
			
			int cantidad = Integer.valueOf(((EditText) findViewById(R.id.EditTextCantidad)).getText().toString());
			
			int dias = Integer.valueOf(((EditText) findViewById(R.id.EditTextDias)).getText().toString());
			
			int repeticion = Integer.valueOf(((EditText) findViewById(R.id.EditTextRepeticion)).getText().toString());
			
			int hora = ((TimePicker)findViewById(R.id.timePickerHoraInicio)).getCurrentHour();
			int min = ((TimePicker)findViewById(R.id.timePickerHoraInicio)).getCurrentMinute();
			
			Dosis dosis = new Dosis(idMedicamento,dia,mes,año,hora,min,repeticion,dias,cantidad);
			
			db.AgregaDosis(dosis);
			
			agregado = true;
		}

		catch (Exception ex) {
			agregado = false;
			// CatchError(ex.toString());
		} finally {
			if (agregado == true) {
				notificaAgregado();
				Intent intent = new Intent(this, Agregar.class);
				startActivity(intent);
			}
		}
	}

	public void notificaAgregado() {
		Toast.makeText(this, "Dosis agregada", Toast.LENGTH_LONG).show();
	}

}
