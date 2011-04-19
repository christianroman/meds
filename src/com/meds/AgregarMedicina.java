package com.meds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarMedicina extends Activity {

	DatabaseHelper db;
	String vias[];
	String tipos[];
	Spinner s1, s2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregarmedicina);

		((Button) findViewById(R.id.Button01)).getBackground().setColorFilter(
				0xFFFFE25B, PorterDuff.Mode.MULTIPLY);
		((Button) findViewById(R.id.Button02)).getBackground().setColorFilter(
				0xFFFFE25B, PorterDuff.Mode.MULTIPLY);

		db = new DatabaseHelper(this);
		vias = db.getAllVias();
		tipos = db.getAllTipos();

		SpinnerVias();
		SpinnerTipos();

		db.close();

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
		Intent intent = new Intent(this, Meds.class);
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
			String persona = ((EditText) findViewById(R.id.EditTextPersona))
					.getText().toString();
			String farmacia = ((EditText) findViewById(R.id.EditTextFarmacia))
					.getText().toString();
			String doctor = ((EditText) findViewById(R.id.EditTextDoctor))
					.getText().toString();
			String nota = ((EditText) findViewById(R.id.EditTextNota))
					.getText().toString();

			if (!nombre.equals("")) {
				Medicina m = new Medicina(nombre, via, tipo, persona, farmacia,
						doctor, nota);
				db.AgregaMedicina(m);
				agregado = true;
			} else
				Toast.makeText(this, "El nombre no puede estar vacio",
						Toast.LENGTH_LONG).show();

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

	public void notificaAgregado() {
		Toast.makeText(this, "Medicina agregada", Toast.LENGTH_LONG).show();
	}

}
