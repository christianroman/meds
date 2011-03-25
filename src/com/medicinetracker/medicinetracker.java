package com.medicinetracker;

import com.medicinetracker.R;
import com.medicinetracker.Consultar;
import com.medicinetracker.Historial;
import com.medicinetracker.Preferencias;
import com.medicinetracker.Resumen;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class medicinetracker extends TabActivity {	
	
    private TabHost mTabHost;
    private Resources mResources;
    
    private static final String TAG_RESUMEN = "resumen";
    private static final String TAG_CONSULTAR = "consultar";
    private static final String TAG_HISTORIAL = "historial";
    private static final String TAG_PREFERENCIAS = "preferencias";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mTabHost = getTabHost();       
        mResources = getResources();  
        
        añadirTab1();
        añadirTab2();
        añadirTab3();  
        añadirTab4(); 
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTab = prefs.getInt(TAG_PREFERENCIAS, 0);
        mTabHost.setCurrentTab(currentTab);
        
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        int currentTab = mTabHost.getCurrentTab();
        editor.putInt(TAG_PREFERENCIAS, currentTab);
        editor.commit();
    }
    
	/*
	 * Pestaña 1
	 */
     
    private void añadirTab1() {
    	
    	Intent intent = new Intent(this, Resumen.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_RESUMEN);
        spec.setIndicator(mResources.getString(R.string.resumen));
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pestaña 2
	 */
    
    private void añadirTab2() {
    	
    	Intent intent = new Intent(this, Consultar.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_CONSULTAR);
        spec.setIndicator(mResources.getString(R.string.consultar));
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pestaña 3
	 */
    
    private void añadirTab3() {
    	
    	Intent intent = new Intent(this, Historial.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_HISTORIAL);
        spec.setIndicator(mResources.getString(R.string.historial));
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
    /*
	 * Pestaña 4
	 */
    
    private void añadirTab4() {
    	
    	Intent intent = new Intent(this, Preferencias.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_PREFERENCIAS);
        //spec.setIndicator(mResources.getString(R.string.preferencias), mResources.getDrawable(R.drawable.icon));
        spec.setIndicator(mResources.getString(R.string.preferencias));
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
}