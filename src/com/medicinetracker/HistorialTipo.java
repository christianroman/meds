package com.medicinetracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HistorialTipo extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historialtipo);
		
		TextView t = (TextView) findViewById(R.id.tipoTextView);
		t.setText(this.getIntent().getExtras().getString("tipo_texto"));
		
		
	}

}
