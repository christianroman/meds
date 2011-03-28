package com.medicinetracker;

import android.content.ContentValues;
import android.content.Context;
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

	public DatabaseHelper(Context context) {
		super(context, dbNombre, null, 1);
		// TODO Auto-generated constructor stub
		//context.deleteDatabase(dbNombre); // Esta linea se ocupa cuando se requiere eliminar la base de datos. Solo usarla una vez, una vez que se elimina, se comenta y se crea una base de datos nueva.
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.i("onCreate", "Creating the database...");
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + tablaMedicamento + " (" + colID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colNombre + " TEXT , " + colVia
				+ " TEXT , " + colTipo + " TEXT , " + colContenido + " TEXT , "
				+ colPersona + " TEXT , " + colFarmacia + " TEXT , "
				+ colDoctor + " TEXT , " + colNota + " TEXT)");

		db.execSQL("CREATE TABLE " + tablaDosis + " (" + colDosisID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colMedicamentoID + " INTEGER , "
				+ colFechaInicio + " DATETIME , " + colFechaFin
				+ " DATETIME , " + colCantidad + " TEXT , " + colRepeticion
				+ " TEXT , " + colDias + " INTEGER , " + colEstado
				+ " INTEGER)");

		db.execSQL("CREATE TABLE " + tablaTipos + " (" + colTiposID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colTiposNombre + " TEXT)");

		db.execSQL("CREATE TABLE " + tablaVias + " (" + colViasID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colViasNombre + " TEXT)");

		insertarVias(db);
		insertarTipos(db);
	}

	private void insertarTipos(SQLiteDatabase db) {
		
		ContentValues cv = new ContentValues();
		String tipos[] = {
				"Pastilla", "Capsulas", "Jarabe", "Emulsion",
				"Polvo", "Parche", "Supositorio", "Ovulo", "Gragea",
				"Inyeccion", "Suspension", "Aerosol", "Solucion",
				"Crema", "Pasta", "Liposoma", "Unguentos",
				"Comprimido", "Pildora", "Loción", "Gel",
				"Gotas", "Inhalador"};
		
		for(String v : tipos){
			cv.put(colTiposNombre, v);
			db.insert(tablaTipos, null, cv);
		}
		
	}

	private void insertarVias(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		
		String vias[] = {
				"Oral", "Sublingual", "Gastroentérica", "Oftálmica",
				"Nasal", "Tópica", "Parental", "Rectal", "Vaginal",
				"Subcutanea", "Transdermica", "Intramuscular", "Endevenosa"
				};
		
		for(String v : vias){
			cv.put(colViasNombre, v);
			db.insert(tablaVias, null, cv);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+tablaMedicamento);
		db.execSQL("DROP TABLE IF EXISTS "+tablaDosis);
		db.execSQL("DROP TABLE IF EXISTS "+tablaTipos);
		db.execSQL("DROP TABLE IF EXISTS "+tablaVias);
		
		onCreate(db);
	}

}