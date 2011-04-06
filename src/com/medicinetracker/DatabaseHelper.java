package com.medicinetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	SQLiteDatabase db = null;

	static final String dbNombre = "database.db";
	static final String tablaMedicamento = "medicamento";
	static final String colID = "idmedicamento";
	static final String colNombre = "nombre";
	static final String colVia = "via";
	static final String colTipo = "tipo";
	static final String colContenido = "contenido";
	static final String colPersona = "persona";
	static final String colFarmacia = "farmacia";
	static final String colDoctor = "doctor";
	static final String colNota = "nota";

	/* Tabla dosis */

	static final String tablaDosis = "dosis";
	static final String colDosisID = "iddosis";
	static final String colMedicamentoID = "idmedicamento";
	static final String colFechaInicio = "fechainicio";
	static final String colFechaFin = "fechafin";
	static final String colCantidad = "cantidad";
	static final String colRepeticion = "repeticion";
	static final String colDias = "dias";
	static final String colEstado = "estado";

	/* Tabla tipos */

	static final String tablaTipos = "tipos";
	static final String colTiposID = "idtipos";
	static final String colTiposNombre = "nombre";
	static final String colTiposEdo = "estado";

	/* Tabla vias */

	static final String tablaVias = "vias";
	static final String colViasID = "idvias";
	static final String colViasNombre = "nombre";

	static final String viewDosisMedicamento = "viewDosisMedicamento";

	static final String queryResumen = "SELECT " + colNombre + ", "
			+ colRepeticion + ", " + colFechaInicio + ", " + "strftime('%j',"
			+ colFechaFin + ") - strftime('%j','now')" + ", " + "(SELECT "
			+ colTiposNombre + " from " + tablaTipos + " where " + colTiposID
			+ "=" + colTipo + ")" + ", " + "(SELECT " + colViasNombre
			+ " from " + tablaVias + " where " + colViasID + "=" + colVia + ")"
			+ ", " + colFechaInicio + ", " + colDoctor + ", " + colFarmacia
			+ " FROM " + tablaMedicamento + ", " + tablaDosis + " WHERE "
			+ tablaDosis + "." + colMedicamentoID + " = " + tablaMedicamento
			+ "." + colID + " AND " + colEstado + "=?";

	static final String queryTipo =  "SELECT " + colNombre + ", "
	+ colRepeticion + ", " + colFechaInicio + ", " + "strftime('%j',"
	+ colFechaFin + ") - strftime('%j','now')" + ", " + "(SELECT "
	+ colTiposNombre + " from " + tablaTipos + " where " + colTiposID
	+ "=" + colTipo + ")" + ", " + "(SELECT " + colViasNombre
	+ " from " + tablaVias + " where " + colViasID + "=" + colVia + ")"
	+ ", " + colFechaInicio + ", " + colDoctor + ", " + colFarmacia
	+ " FROM " + tablaMedicamento + ", " + tablaDosis + " WHERE "
	+ tablaDosis + "." + colMedicamentoID + " = " + tablaMedicamento
	+ "." + colID + " AND " + colEstado + "=?";

	/*
	 * static final String queryResumen = "SELECT " + colNombre + ", " +
	 * colRepeticion + ", " + colFechaInicio + ", " + "strftime('%j'," +
	 * colFechaFin + ") - strftime('%j','now')" + " FROM " + tablaMedicamento +
	 * ", " + tablaDosis + " WHERE " + tablaDosis + "." + colMedicamentoID +
	 * " = " + tablaMedicamento + "." + colMedicamentoID + " AND " + colEstado +
	 * "=? ORDER BY " + colDosisID + " DESC LIMIT 4";
	 * 
	 * 
	 * static final String queryResumen = "SELECT " + colNombre + ", " + colVia
	 * + ", " + colRepeticion + " FROM " + tablaMedicamento + ", " + tablaDosis
	 * + " WHERE " + tablaDosis + "." + colMedicamentoID + " = " +
	 * tablaMedicamento + "." + colMedicamentoID + " AND " + colEstado + "=?";
	 */

	static final String queryMedicinaDosis = "SELECT " + colNombre + " FROM "
			+ tablaMedicamento + " WHERE " + colID + "=?";

	public DatabaseHelper(Context context) {
		super(context, dbNombre, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + tablaMedicamento + " (" + colID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colNombre
				+ " TEXT , " + colVia + " TEXT , " + colTipo + " TEXT , "
				+ colContenido + " TEXT , " + colPersona + " TEXT , "
				+ colFarmacia + " TEXT , " + colDoctor + " TEXT , " + colNota
				+ " TEXT)");

		db.execSQL("CREATE TABLE " + tablaDosis + " (" + colDosisID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colMedicamentoID
				+ " INTEGER , " + colFechaInicio + " DATETIME , " + colFechaFin
				+ " DATETIME , " + colCantidad + " TEXT , " + colRepeticion
				+ " TEXT , " + colDias + " INTEGER , " + colEstado
				+ " INTEGER)");

		db.execSQL("CREATE TABLE " + tablaTipos + " (" + colTiposID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colTiposNombre
				+ " TEXT)");

		db.execSQL("CREATE TABLE " + tablaVias + " (" + colViasID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colViasNombre
				+ " TEXT)");

		insertarVias(db);
		insertarTipos(db);
	}

	private void insertarTipos(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		String tipos[] = { "Pastilla", "Capsulas", "Jarabe", "Emulsion",
				"Polvo", "Parche", "Supositorio", "Ovulo", "Gragea",
				"Inyeccion", "Suspension", "Aerosol", "Solucion", "Crema",
				"Pasta", "Liposoma", "Unguentos", "Comprimido", "Pildora",
				"Loción", "Gel", "Gotas", "Inhalador" };

		for (String v : tipos) {
			cv.put(colTiposNombre, v);
			db.insert(tablaTipos, null, cv);
		}
	}

	private void insertarVias(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		String vias[] = { "Oral", "Sublingual", "Gastroentérica", "Oftálmica",
				"Nasal", "Tópica", "Parental", "Rectal", "Vaginal",
				"Subcutanea", "Transdermica", "Intramuscular", "Endevenosa" };

		for (String v : vias) {
			cv.put(colViasNombre, v);
			db.insert(tablaVias, null, cv);
		}
	}

	/**
	 * Obtiene las vias de la base de datos
	 * 
	 * @return String[]
	 */
	public String[] getAllVias() {
		Cursor cursor = this.getReadableDatabase().query(tablaVias, null, null,
				null, null, null, null);

		if (cursor.getCount() > 0) {
			String[] str = new String[cursor.getCount()];
			int i = 0;

			while (cursor.moveToNext()) {
				str[i] = cursor.getString(cursor.getColumnIndex(colViasNombre));
				i++;
			}
			// cursor.close();
			return str;
		} else {
			return new String[] {};
		}
	}

	/**
	 * Obtiene todos los tipos de la base de datos
	 * 
	 * @return String[]
	 */
	public String[] getAllTipos() {
		Cursor cursor = this.getReadableDatabase().query(tablaTipos, null,
				null, null, null, null, null);

		if (cursor.getCount() > 0) {
			String[] str = new String[cursor.getCount()];
			int i = 0;

			while (cursor.moveToNext()) {
				str[i] = cursor
						.getString(cursor.getColumnIndex(colTiposNombre));
				i++;
			}
			// cursor.close();
			return str;
		} else {
			return new String[] {};
		}
	}

	public Cursor getResumen() {
		String args[] = new String[] { "1" };
		return (Cursor) this.getReadableDatabase().rawQuery(queryResumen, args);
	}

	public Cursor getByTipo(String tipo) {
		Log.i("getByTipo", queryTipo);
		String args[] = new String[] { tipo };
		return (Cursor) this.getReadableDatabase().rawQuery(queryTipo, args);
	}

	public String[] getMedicamentos() {
		Cursor cursor = this.getReadableDatabase().query(tablaMedicamento,
				null, null, null, null, null, null);

		if (cursor.getCount() > 0) {
			String[] str = new String[cursor.getCount()];
			int i = 0;

			while (cursor.moveToNext()) {
				str[i] = cursor.getString(cursor.getColumnIndex(colNombre));
				i++;
			}
			cursor.close();
			return str;
		} else {
			return new String[] {};
		}
	}

	public int getCantidadMedicamentos() {
		Cursor cursor = this.getReadableDatabase().query(tablaMedicamento,
				null, null, null, null, null, null);

		int count = cursor.getCount();
		cursor.close();
		return count;

	}

	public int getCantidadDosis() {
		Cursor cursor = this.getReadableDatabase().query(tablaDosis, null,
				null, null, null, null, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	public void AgregaMedicina(Medicina m) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(colNombre, m.getNombre());
		cv.put(colVia, m.getVia());
		cv.put(colTipo, m.getTipo());
		cv.put(colContenido, m.getContenido());
		cv.put(colPersona, m.getPersona());
		cv.put(colFarmacia, m.getFarmacia());
		cv.put(colDoctor, m.getDoctor());
		cv.put(colNota, m.getNota());

		db.insert(tablaMedicamento, colNombre, cv);
		db.close();
	}

	public void AgregaDosis(Dosis dosis) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(colMedicamentoID, dosis.getIdmedicamento());
		cv.put(colFechaInicio, dosis.getFechaInicio());
		cv.put(colFechaFin, dosis.getFechaFin());
		cv.put(colCantidad, dosis.getCantidad());
		cv.put(colRepeticion, dosis.getRepeticion());
		cv.put(colDias, dosis.getDias());
		cv.put(colEstado, dosis.getEstado());

		db.insert(tablaDosis, colMedicamentoID, cv);
		db.close();

	}

	@Override
	public synchronized void close() {
		if (db != null) {
			db.close();
			super.close();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + tablaMedicamento);
		db.execSQL("DROP TABLE IF EXISTS " + tablaDosis);
		db.execSQL("DROP TABLE IF EXISTS " + tablaTipos);
		db.execSQL("DROP TABLE IF EXISTS " + tablaVias);
		onCreate(db);
	}

}