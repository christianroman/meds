package com.meds;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Resumen extends Activity {
	
	DatabaseHelper db;
	Cursor c;
	AdaptadorTitulares adaptador;
	private ListView lv1;
	private ArrayList<Titular> datos = new ArrayList<Titular>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resumen);

		db = new DatabaseHelper(this);

		((TextView) findViewById(R.id.TextViewDosisActivas)).setText(this
				.getString(R.string.dosisActivas)
				+ db.getCantidadDosisActivas());
		((TextView) findViewById(R.id.TextViewMedicamentos)).setText(this
				.getString(R.string.medicamentosActivos)
				+ db.getCantidadMedicamentos());

		if (db.getCantidadDosisActivas() > 0) {

			Boolean dosisSiguientes = false;

			adaptador = new AdaptadorTitulares(this);

			lv1 = (ListView) findViewById(R.id.LstOpciones);

			long actual = System.currentTimeMillis();

			try {
				c = db.getResumen();
				while (c.moveToNext()) {

					long sig = c.getLong(1);

					if (sig > actual) {

						Calendar ca = Calendar.getInstance();
						ca.setTimeInMillis(c.getLong(1));

						Calendar c_horas = Calendar.getInstance();
						c_horas.setTimeInMillis(c.getLong(2));
						int dias = (int) (c_horas.getTimeInMillis() - actual)
								/ (24 * 60 * 60 * 1000);

						Calendar c_inicio = Calendar.getInstance();
						c_inicio.setTimeInMillis(c.getLong(5));

						datos.add(new Titular(c.getString(0), ca.getTime()
								.toLocaleString(), String.valueOf(dias), c
								.getString(3), c.getString(4), c_inicio
								.getTime().toLocaleString(), c.getString(6), c
								.getString(7), c.getString(8), c.getString(9),
								false));
						dosisSiguientes = true;
					}

				}
			} finally {
				if (c != null) {
					c.close();
				}
				db.getReadableDatabase().close();
			}

			lv1.setAdapter(adaptador);
			lv1.setClickable(true);
			lv1.setOnItemClickListener(funcionClick);

			if (!dosisSiguientes) {
				((ListView) findViewById(R.id.LstOpciones))
						.setVisibility(View.GONE);
				((LinearLayout) findViewById(R.id.avisoDosis))
						.setVisibility(View.VISIBLE);
			}

		} else {
			((ListView) findViewById(R.id.LstOpciones))
					.setVisibility(View.GONE);
			((LinearLayout) findViewById(R.id.avisoDosis))
					.setVisibility(View.VISIBLE);
		}
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (c!=null){
		    c.close();
		}
		if (db!=null){
		    db.close();
		}
	}

	private OnItemClickListener funcionClick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			datos.get(position).setVisible(!datos.get(position).getVisible());
			lv1.setAdapter(adaptador);
		}
	};

	public void AgregarMedicina(View button) {
		Intent intent = new Intent(this, AgregarMedicina.class);
		startActivity(intent);
	}

	public void AgregarDosis(View button) {
		DatabaseHelper db = new DatabaseHelper(this);
		if (db.getCantidadMedicamentos() > 0) {
			db.close();
			Intent intent = new Intent(this, AgregarDosis.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, this.getString(R.string.noMedicamentos),
					Toast.LENGTH_LONG).show();
		}
		db.close();
	}

	@SuppressWarnings("rawtypes")
	class AdaptadorTitulares extends ArrayAdapter {
		LinearLayout LLExpandir;
		Activity context;

		@SuppressWarnings("unchecked")
		AdaptadorTitulares(Activity context) {
			super(context, R.layout.listitem_titular, datos);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.listitem_titular, null);

			TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
			lblTitulo.setText(datos.get(position).getTitulo());

			TextView lblSubtitulo = (TextView) item
					.findViewById(R.id.LblSubTitulo);
			lblSubtitulo.setText(datos.get(position).getSubtitulo());

			TextView lblHora = (TextView) item.findViewById(R.id.LblHora);
			lblHora.setText(datos.get(position).getHora());

			TextView tipo = (TextView) item.findViewById(R.id.tipo);
			tipo.setText(datos.get(position).getTipo());

			TextView via = (TextView) item.findViewById(R.id.via);
			via.setText(datos.get(position).getVia());

			TextView fecha = (TextView) item.findViewById(R.id.fechaIni);
			fecha.setText(datos.get(position).getFecha());

			String persona = datos.get(position).getPersona();
			if (!persona.equals("")) {
				((LinearLayout) item.findViewById(R.id.personaLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.tvPersona)).setText(persona);
			}

			String doctor = datos.get(position).getDoctor();
			if (!doctor.equals("")) {
				((LinearLayout) item.findViewById(R.id.doctorLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.doctor)).setText(doctor);
			}

			String farmacia = datos.get(position).getFarmacia();
			if (!farmacia.equals("")) {
				((LinearLayout) item.findViewById(R.id.farmaciaLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.farmacia)).setText(farmacia);
			}

			String nota = datos.get(position).getNota();
			if (!nota.equals("")) {
				((LinearLayout) item.findViewById(R.id.notaLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.nota)).setText(nota);
			}

			LLExpandir = (LinearLayout) item.findViewById(R.id.LLExpandir);
			LLExpandir
					.setVisibility(datos.get(position).getVisible() ? View.VISIBLE
							: View.GONE);

			return (item);
		}

	}
}
