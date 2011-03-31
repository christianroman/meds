package com.medicinetracker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

class Titular {
	private String titulo;
	private String subtitulo;
	private String Hora;

	public Titular(String tit, String sub, String hora) {
		titulo = tit;
		subtitulo = sub;
		Hora = hora;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public String getHora() {
		return Hora;
	}
}

public class Resumen extends Activity {

	DatabaseHelper db;

	private ListView lv1;
	private ArrayList<Titular> datos = new ArrayList<Titular>();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resumen);

		db = new DatabaseHelper(this);

		//if (db.getCantidadDosis() > 0) {
			Cursor c = db.getResumen();

			AdaptadorTitulares adaptador = new AdaptadorTitulares(this);

			lv1 = (ListView) findViewById(R.id.LstOpciones);

			if (c.moveToFirst()) {
				do {
					datos.add(new Titular(c.getString(0), c.getString(1), c
							.getString(2)));
				} while (c.moveToNext());
			}

			lv1.setAdapter(adaptador);
		//}
		
		//Log.i("onCreate", String.valueOf(db.getCantidadDosis()));
		
	}

	public void Agregar(View button) {
		Intent intent = new Intent(Resumen.this, Agregar.class);
		startActivity(intent);

	}

	@SuppressWarnings("rawtypes")
	class AdaptadorTitulares extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		AdaptadorTitulares(Activity context) {
			super(context, R.layout.listitem_titular, datos);
			this.context = context;
		}

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

			return (item);
		}
	}
}
