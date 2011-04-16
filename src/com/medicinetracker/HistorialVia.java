package com.medicinetracker;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
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

public class HistorialVia extends Activity{
	DatabaseHelper db;
	AdaptadorTitulares adaptador;
	private ListView lv1;
	private ArrayList<ListItem> datos = new ArrayList<ListItem>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historialvista);
		
		TextView t = (TextView) findViewById(R.id.tipoTextView);
		String via = this.getIntent().getExtras().getString("via_texto");
		t.setText(via);
		
		db = new DatabaseHelper(this);
		
		if (db.getCantidadDosis() > 0) {
			Cursor c = db.getByVia(via);

			adaptador = new AdaptadorTitulares(this);

			lv1 = (ListView) findViewById(R.id.LstOpciones);

			while (c.moveToNext()) {

				Calendar c_inicio = Calendar.getInstance();
				c_inicio.setTimeInMillis(c.getLong(1));

				Calendar c_fin = Calendar.getInstance();
				c_fin.setTimeInMillis(c.getLong(6));
				
				String repeticion = c.getString(3) + " cada " + c.getString(2) + " horas";

				datos.add(new ListItem(c.getString(0), c_inicio.getTime()
						.toLocaleString(), repeticion, c.getString(4), c
						.getString(5), c_fin.getTime().toLocaleString(), c
						.getString(7), c.getString(8), c.getString(9), c
						.getString(10), false));
			}

			db.close();

			lv1.setAdapter(adaptador);
			lv1.setClickable(true);
			lv1.setOnItemClickListener(funcionClick);
		}

	}

	private OnItemClickListener funcionClick = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			datos.get(position).setVisible(!datos.get(position).isVisible());
			lv1.setAdapter(adaptador);
		}
	};

	@SuppressWarnings("rawtypes")
	class AdaptadorTitulares extends ArrayAdapter {
		LinearLayout LLExpandir;
		Activity context;

		@SuppressWarnings("unchecked")
		AdaptadorTitulares(Activity context) {
			super(context, R.layout.listitem, datos);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.listitem, null);

			TextView lblTitulo = (TextView) item.findViewById(R.id.tvNombre);
			lblTitulo.setText(datos.get(position).getNombre());

			TextView lblSubtitulo = (TextView) item
					.findViewById(R.id.tvFechaInicio);
			lblSubtitulo.setText(datos.get(position).getFechainicio());

			TextView lblHora = (TextView) item.findViewById(R.id.tvRepeticion);
			lblHora.setText(datos.get(position).getRepeticion());

			TextView tipo = (TextView) item.findViewById(R.id.tvTipo);
			tipo.setText(datos.get(position).getTipo());

			TextView via = (TextView) item.findViewById(R.id.tvVia);
			via.setText(datos.get(position).getVia());

			TextView fecha = (TextView) item.findViewById(R.id.tvFechafin);
			fecha.setText(datos.get(position).getFechafin());

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
				((TextView) item.findViewById(R.id.tvDoctor)).setText(doctor);
			}

			String farmacia = datos.get(position).getFarmacia();
			if (!farmacia.equals("")) {
				((LinearLayout) item.findViewById(R.id.farmaciaLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.tvFarmacia)).setText(farmacia);
			}

			String nota = datos.get(position).getNota();
			if (!nota.equals("")) {
				((LinearLayout) item.findViewById(R.id.notaLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.tvNota)).setText(nota);
			}

			LLExpandir = (LinearLayout) item.findViewById(R.id.LLExpandir);
			LLExpandir
					.setVisibility(datos.get(position).isVisible() ? View.VISIBLE
							: View.GONE);

			return (item);
		}

	}

}
