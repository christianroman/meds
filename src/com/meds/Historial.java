package com.meds;

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
		searchByDate.getBackground().setColorFilter(0xFFFFE25B,
				PorterDuff.Mode.MULTIPLY);

		searchByType = (Button) findViewById(R.id.searchByType);
		searchByType.setOnClickListener(this);
		searchByType.getBackground().setColorFilter(0xFFFFE25B,
				PorterDuff.Mode.MULTIPLY);

		searchByVia = (Button) findViewById(R.id.searchByVia);
		searchByVia.setOnClickListener(this);
		searchByVia.getBackground().setColorFilter(0xFFFFE25B,
				PorterDuff.Mode.MULTIPLY);

		db = new DatabaseHelper(this);

	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, cyear, cmonth,
					cday);
		case TYPE_DIALOG_ID:
			dialog = typeActions();
			break;
		case VIA_DIALOG_ID:
			dialog = viaActions();
			break;
		}
		db.close();
		return dialog;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int anio, int mes, int dia) {
			Calendar c = Calendar.getInstance();
			c.set(anio, mes, dia);

			Intent intent = new Intent(Historial.this, HistorialVista.class);
			intent.putExtra("cabecera", String.valueOf(c.getTimeInMillis()));
			intent.putExtra("id", 2);
			startActivity(intent);
		}
	};

	public void onClick(View v) {
		if (v == searchByDate) {
			if (db.getCantidadDosis() > 0)
				showDialog(DATE_DIALOG_ID);
			else
				Toast.makeText(this, this.getString(R.string.noDosisHistorial),
						Toast.LENGTH_LONG).show();
			db.close();
		}
		if (v == searchByType) {
			if (db.getCantidadDosis() > 0)
				showDialog(TYPE_DIALOG_ID);
			else
				Toast.makeText(this, this.getString(R.string.noDosisHistorial),
						Toast.LENGTH_LONG).show();
			db.close();
		}
		if (v == searchByVia) {
			if (db.getCantidadDosis() > 0)
				showDialog(VIA_DIALOG_ID);
			else
				Toast.makeText(this, this.getString(R.string.noDosisHistorial),
						Toast.LENGTH_LONG).show();
			db.close();
		}
	}

	public Dialog typeActions() {
		final CharSequence[] items = db.getAllTipos();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.seleccionaTipo));
		builder.setItems(items, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item],
						Toast.LENGTH_SHORT).show();
				String tipo = (String) items[item];

				Intent intent = new Intent(Historial.this, HistorialVista.class);
				intent.putExtra("cabecera", tipo);
				intent.putExtra("id", 0);
				startActivity(intent);

			}

		});
		AlertDialog alert = builder.create();
		return alert;
	}

	public Dialog viaActions() {
		final CharSequence[] items = db.getAllVias();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.seleccionaVia));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item],
						Toast.LENGTH_SHORT).show();
				String via = (String) items[item];

				Intent intent = new Intent(Historial.this, HistorialVista.class);
				intent.putExtra("cabecera", via);
				intent.putExtra("id", 1);
				startActivity(intent);
			}
		});
		AlertDialog alert = builder.create();
		return alert;
	}

}