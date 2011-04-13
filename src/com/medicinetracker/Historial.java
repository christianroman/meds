package com.medicinetracker;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class Historial extends Activity implements OnClickListener {

	DatabaseHelper db;

	private Button searchByType;
	private Button searchByVia;
	private Button searchByDate;

	static final int DATE_DIALOG_ID = 0;
	static final int TYPE_DIALOG_ID = 1;
	static final int VIA_DIALOG_ID = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

		searchByDate = (Button) findViewById(R.id.searchByDate);
		searchByDate.setOnClickListener(this);
		searchByDate.getBackground().setColorFilter(0xFFFFE25B, PorterDuff.Mode.MULTIPLY);

		searchByType = (Button) findViewById(R.id.searchByType);
		searchByType.setOnClickListener(this);
		searchByType.getBackground().setColorFilter(0xFFFFE25B, PorterDuff.Mode.MULTIPLY);

		searchByVia = (Button) findViewById(R.id.searchByVia);
		searchByVia.setOnClickListener(this);
		searchByVia.getBackground().setColorFilter(0xFFFFE25B, PorterDuff.Mode.MULTIPLY);

		db = new DatabaseHelper(this);

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, cyear, cmonth,
					cday);
		case TYPE_DIALOG_ID:
			typeActions();
			break;
		case VIA_DIALOG_ID:
			viaActions();
			break;
		}
		db.close();
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int anio, int mes, int dia) {
			String cmes, cdia;
			String date_selected = String.valueOf(mes + 1) + " /"
					+ String.valueOf(dia) + " /" + String.valueOf(anio);
			Toast.makeText(Historial.this,
					"Fecha Seleccionada: =" + date_selected, Toast.LENGTH_SHORT)
					.show();
			///////
			if(mes<10){
				cmes="0"+String.valueOf(mes+1);
			}
			else{
				cmes=String.valueOf(mes+1);
			}
			if(dia<10){
				cdia="0"+String.valueOf(dia);
			}
			else{
				cdia=String.valueOf(dia);
			}
			Intent intent = new Intent(Historial.this, HistorialFecha.class);
			intent.putExtra("fecha_texto", String.valueOf(anio)+"-"+cmes+"-"+cdia);
			startActivity(intent);
		}
	};

	public void onClick(View v) {
		if (v == searchByDate)
			showDialog(DATE_DIALOG_ID);
		if (v == searchByType)
			showDialog(TYPE_DIALOG_ID);
		if (v == searchByVia)
			showDialog(VIA_DIALOG_ID);
	}

	public void typeActions() {
		final CharSequence[] items = db.getAllTipos();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Selecciona Tipo");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item],
						Toast.LENGTH_SHORT).show();
				String tipo = (String) items[item];

				Intent intent = new Intent(Historial.this, HistorialTipo.class);
				intent.putExtra("tipo_texto", tipo);
				startActivity(intent);

			}

		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void viaActions() {
		final CharSequence[] items = db.getAllVias();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Selecciona Vía");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item],
						Toast.LENGTH_SHORT).show();
				String via = (String) items[item];

				Intent intent = new Intent(Historial.this, HistorialVia.class);
				intent.putExtra("via_texto", via);
				startActivity(intent);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}