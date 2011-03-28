package com.medicinetracker;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class Historial extends Activity implements OnClickListener {
	
	
	
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

		searchByType = (Button) findViewById(R.id.searchByType);
		searchByType.setOnClickListener(this);

		searchByVia = (Button) findViewById(R.id.searchByVia);
		searchByVia.setOnClickListener(this);

	}
	

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
		case TYPE_DIALOG_ID:
			typeActions();
			break;
		case VIA_DIALOG_ID:
			viaActions();
			break;
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int año, int mes, int dia) {
			String date_selected = String.valueOf(mes + 1) + " /"
					+ String.valueOf(dia) + " /" + String.valueOf(año);
			Toast.makeText(Historial.this,
					"Selected Date is =" + date_selected, Toast.LENGTH_SHORT)
					.show();
		}
	};

	@Override
	public void onClick(View v) {
		if (v == searchByDate)
			showDialog(DATE_DIALOG_ID);
		if (v == searchByType)
			showDialog(TYPE_DIALOG_ID);
		if (v == searchByVia)
			showDialog(VIA_DIALOG_ID);
	}

	public void typeActions() {
		final CharSequence[] items = { "Red", "Green", "Blue" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pick a color");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item],
						Toast.LENGTH_SHORT).show();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void viaActions() {
		final CharSequence[] items = { "Red", "Green", "Blue" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pick a color");
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						Toast.makeText(getApplicationContext(), items[item],
								Toast.LENGTH_SHORT).show();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
		
	}

}
