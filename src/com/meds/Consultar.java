package com.meds;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Consultar extends Activity {

	DatabaseHelper db;
	AdaptadorTitulares adaptador;
	private ListView lv1;
	private ArrayList<ListItem> datos = new ArrayList<ListItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultar);

		db = new DatabaseHelper(this);

		if (db.getCantidadDosisActivas() > 0) {
			Cursor c = db.getConsulta();

			adaptador = new AdaptadorTitulares(this);

			lv1 = (ListView) findViewById(R.id.LstOpciones);

			while (c.moveToNext()) {

				Calendar c_inicio = Calendar.getInstance();
				c_inicio.setTimeInMillis(c.getLong(1));

				Calendar c_fin = Calendar.getInstance();
				c_fin.setTimeInMillis(c.getLong(6));

				String repeticion = c.getString(3) + " cada " + c.getString(2)
						+ " hora" +  ((Integer.parseInt(c.getString(2)) == 1) ? "" : "s");

				datos.add(new ListItem(c.getString(0), c_inicio.getTime()
						.toLocaleString(), repeticion, c.getString(4), c
						.getString(5), c_fin.getTime().toLocaleString(), c
						.getString(7), c.getString(8), c.getString(9), c
						.getString(10), c.getInt(11), false));
			}
			c.close();

			lv1.setAdapter(adaptador);
			lv1.setClickable(true);
			lv1.setOnItemClickListener(funcionClick);
		} else {
			((ListView) findViewById(R.id.LstOpciones))
					.setVisibility(View.GONE);
			((LinearLayout) findViewById(R.id.avisoConsultar))
					.setVisibility(View.VISIBLE);
		}

		db.close();

	}

	public void AgregarDosis(View button) {
		DatabaseHelper db = new DatabaseHelper(this);
		if (db.getCantidadMedicamentos() > 0) {
			db.close();
			Intent intent = new Intent(this, AgregarDosis.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, "No hay medicamentos agregados",
					Toast.LENGTH_LONG).show();
		}
		db.close();
	}

	private OnItemClickListener funcionClick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			datos.get(position).setVisible(!datos.get(position).isVisible());
			lv1.setAdapter(adaptador);
		}
	};

	public void cancelarAlarma(int id) {
		Cursor c = db.getAlarmas(id);

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

		while (c.moveToNext()) {

			Intent intent = new Intent(this, AlarmReceiver.class);
			PendingIntent appIntent = PendingIntent.getBroadcast(this,
					c.getInt(0), intent, PendingIntent.FLAG_ONE_SHOT);

			am.cancel(appIntent);
		}
		c.close();

		db.desactivaDosis(id);
		db.eliminaAlarmas(id);
		db.close();

		Toast.makeText(this, "Dosis eliminada y alarmas canceladas",
				Toast.LENGTH_LONG).show();

	}

	@SuppressWarnings("rawtypes")
	class AdaptadorTitulares extends ArrayAdapter implements OnClickListener {
		RelativeLayout LLExpandir;
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

			((Button) item.findViewById(R.id.botonItem)).getBackground()
					.setColorFilter(0xFFFFE25B, PorterDuff.Mode.MULTIPLY);

			Button btn = (Button) item.findViewById(R.id.botonItem);
			btn.setOnClickListener(new OnItemClickListener(position));

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
				((TextView) item.findViewById(R.id.tvFarmacia))
						.setText(farmacia);
			}

			String nota = datos.get(position).getNota();
			if (!nota.equals("")) {
				((LinearLayout) item.findViewById(R.id.notaLayout))
						.setVisibility(View.VISIBLE);
				((TextView) item.findViewById(R.id.tvNota)).setText(nota);
			}

			LLExpandir = (RelativeLayout) item.findViewById(R.id.LLExpandir);
			LLExpandir
					.setVisibility(datos.get(position).isVisible() ? View.VISIBLE
							: View.GONE);

			return (item);
		}

		class OnItemClickListener implements OnClickListener {
			int position;

			public OnItemClickListener(int pos) {
				this.position = pos;
			}

			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				{
					cancelarAlarma(datos.get(position).getDosisID());
					adaptador.remove(adaptador.getItem(position));
					adaptador.notifyDataSetChanged();

					if (db.getCantidadDosisActivas() < 1) {
						((ListView) findViewById(R.id.LstOpciones))
								.setVisibility(View.GONE);
						((LinearLayout) findViewById(R.id.avisoConsultar))
								.setVisibility(View.VISIBLE);
					}
					db.close();

				}
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

}
