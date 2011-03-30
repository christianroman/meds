package com.medicinetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AgregarMedicina extends Activity {

	DatabaseHelper db;
	String vias[];
	String tipos[];
	Spinner s1, s2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregarmedicina);

		db = new DatabaseHelper(this);
		vias = db.getAllVias();
		tipos = db.getAllTipos();

		SpinnerVias();
		SpinnerTipos();

	}

	public void SpinnerVias() {
		s1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, vias);

		s1.setAdapter(adapter);
	}

	public void SpinnerTipos() {
		s2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, tipos);

		s2.setAdapter(adapter);
	}

	public void Cancelar(View button) {
		Intent intent = new Intent(this, Agregar.class);
		startActivity(intent);
	}

	public void Aceptar(View button) {

		Boolean agregado = false;

		try {
			String nombre = ((EditText) findViewById(R.id.EditTextNombre))
					.getText().toString();
			int via = (int) ((Spinner) findViewById(R.id.spinner1))
					.getSelectedItemId() + 1;
			int tipo = (int) ((Spinner) findViewById(R.id.spinner2))
					.getSelectedItemId() + 1;
			String contenido = ((EditText) findViewById(R.id.EditTextContenido))
					.getText().toString();
			String persona = ((EditText) findViewById(R.id.EditTextPersona))
					.getText().toString();
			String farmacia = ((EditText) findViewById(R.id.EditTextFarmacia))
					.getText().toString();
			String doctor = ((EditText) findViewById(R.id.EditTextDoctor))
					.getText().toString();
			String nota = ((EditText) findViewById(R.id.EditTextNota))
					.getText().toString();

			Medicina m = new Medicina(nombre, via, tipo, contenido, persona,
					farmacia, doctor, nota);

			db.AgregaMedicina(m);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Medicamento Añadido").setCancelable(false);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

}
