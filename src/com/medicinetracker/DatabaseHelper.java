package com.medicinetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	static final String dbNombre = "database";
	static final String tablaMedicamento = "medicamento";
	static final String colID = "idMedicamento";
	static final String colNombre = "nombre";
	static final String colVia = "via";
	static final String colTipo = "tipo";
	static final String colContenido = "contenido";
	static final String colPersona = "persona";
	static final String colFarmacia = "farmacia";
	static final String colDoctor = "doctor";
	static final String colNota = "nota";
	static final String tablaDosis = "dosis";
	static final String colDosisID = "idDosis";
	static final String colMedicamentoID = "idMedicamento";
	static final String colFechaInicio = "fechaInicio";
	static final String colFechaFin = "fechaFin";
	static final String colCantidad = "cantidad";
	static final String colRepeticion = "repeticion";
	static final String colDias = "dias";
	static final String viewDosisMedicamento = "viewDosisMedicamento";

	public DatabaseHelper(Context context) {
		super(context, dbNombre, null, 33);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + tablaMedicamento + " (" + colID
				+ " INTEGER PRIMARY KEY , " + colNombre + " TEXT , " + colVia
				+ " TEXT , " + colTipo + " TEXT , " + colContenido + " TEXT , "
				+ colPersona + " TEXT , " + colFarmacia + " TEXT , "
				+ colDoctor + " TEXT , " + colNota + " TEXT)");
		db.execSQL("CREATE TABLE " + tablaDosis + " (" + colDosisID
				+ "INTEGER PRIMARY KEY , " + colMedicamentoID + " INTEGER , "
				+ colFechaInicio + " DATETIME , " + colFechaFin
				+ " DATETIME , " + colCantidad + " TEXT , " + colRepeticion
				+ " TEXT , " + colDias + " INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}