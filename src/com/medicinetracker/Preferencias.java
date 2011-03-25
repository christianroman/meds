package com.medicinetracker;

import android.app.Activity;
import android.os.Bundle;

import com.medicinetracker.R;

public class Preferencias extends Activity{
	
	DatabaseHelper dbhelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencias);
        dbhelper=new DatabaseHelper(this);
    }
    
    @Override
	public void onStart()
	{
		super.onStart();
		
	}
}
