package com.medicinetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbNombre = "database";
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
	static final String colMedicamentoID = "idMedicamento";
	static final String colFechaInicio = "fechaInicio";
	static final String colFechaFin = "fechaFin";
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
				+ " INTEGER PRIMARY KEY , " + colMedicamentoID + " INTEGER , "
				+ colFechaInicio + " DATETIME , " + colFechaFin
				+ " DATETIME , " + colCantidad + " TEXT , " + colRepeticion
				+ " TEXT , " + colDias + " INTEGER , " + colEstado
				+ " INTEGER)");

		db.execSQL("CREATE TABLE " + tablaTipos + " (" + colTiposID
				+ " INTEGER PRIMARY KEY , " + colTiposNombre + " TEXT)");

		db.execSQL("CREATE TABLE " + tablaVias + " (" + colViasID
				+ " INTEGER PRIMARY KEY , " + colViasNombre + " TEXT)");

		insertarVias(db);
		//insertarTipos();

	}

	private void insertarVias(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		
		String vias[] = {"Oral", "Oftálmica","Nasal", "Tópica", "Parental", "Vaginal", "Rectal"};
		
		int i = 0;
		
		for(String v : vias){
			cv.put(colViasID, i++);
			cv.put(colViasNombre, v);
			db.insert(tablaVias, colViasID, cv);
		}
		
		db.insert(tablaVias, colViasID, cv);
		
		/*cv.put(colDeptID, 1);
		cv.put(colDeptName, "Sales");
		db.insert(deptTable, colDeptID, cv);
		cv.put(colDeptID, 2);
		cv.put(colDeptName, "IT");
		db.insert(deptTable, colDeptID, cv);
		cv.put(colDeptID, 3);
		cv.put(colDeptName, "HR");
		db.insert(deptTable, colDeptID, cv);
		db.insert(deptTable, colDeptID, cv);*/

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}