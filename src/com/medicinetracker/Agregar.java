package com.medicinetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Agregar extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar);
    }
    
    public void Regresar(View button) {
		Intent intent = new Intent(this, medicinetracker.class);
		startActivity(intent);
    }
    
    public void AgregarMedicina(View button) {
		Intent intent = new Intent(this, AgregarMedicina.class);
		startActivity(intent);
    }
    
    public void AgregarReceta(View button) {
		Intent intent = new Intent(this, medicinetracker.class);
		startActivity(intent);
    }
}
