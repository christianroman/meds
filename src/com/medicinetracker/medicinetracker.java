package com.medicinetracker;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class medicinetracker extends TabActivity implements OnTabChangeListener {

	DatabaseHelper dbHelper;
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

		mTabHost.setOnTabChangedListener(this);

		agregarTab1();
		agregarTab2();
		agregarTab3();
		agregarTab4();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int currentTab = prefs.getInt(TAG_PREFERENCIAS, 0);
		mTabHost.setCurrentTab(currentTab);

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(R.color.titlecolor);
			mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
		}

		// mTabHost.getTabWidget().setCurrentTab(1);
		mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#00abd7"));

		((TextView) mTabHost.getTabWidget()
				.getChildAt(mTabHost.getCurrentTab())
				.findViewById(android.R.id.title)).setTextColor(this
				.getResources().getColorStateList(R.color.yellow));

	}

	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(R.color.titlecolor);

			((TextView) mTabHost.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title)).setTextColor(this
					.getResources().getColorStateList(R.color.white));

		}

		mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#00abd7"));

		((TextView) mTabHost.getTabWidget()
				.getChildAt(mTabHost.getCurrentTab())
				.findViewById(android.R.id.title)).setTextColor(this
				.getResources().getColorStateList(R.color.yellow));

	}

	@Override
	protected void onPause() {
		super.onPause();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		int currentTab = mTabHost.getCurrentTab();
		editor.putInt(TAG_PREFERENCIAS, currentTab);
		editor.commit();

	}

	/*
	 * Pestaña 1
	 */

	private void agregarTab1() {

		Intent intent = new Intent(this, Resumen.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		TabSpec spec = mTabHost.newTabSpec(TAG_RESUMEN);
		spec.setIndicator(mResources.getString(R.string.resumen),
				getResources().getDrawable(R.drawable.ic_menu_resumen));
		spec.setContent(intent);

		mTabHost.addTab(spec);

	}

	/*
	 * Pestaña 2
	 */

	private void agregarTab2() {

		Intent intent = new Intent(this, Consultar.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		TabSpec spec = mTabHost.newTabSpec(TAG_CONSULTAR);
		spec.setIndicator(mResources.getString(R.string.consultar),
				getResources().getDrawable(R.drawable.ic_menu_consultar));
		spec.setContent(intent);

		mTabHost.addTab(spec);

	}

	/*
	 * Pestaña 3
	 */

	private void agregarTab3() {

		Intent intent = new Intent(this, Historial.class);

		TabSpec spec = mTabHost.newTabSpec(TAG_HISTORIAL);
		spec.setIndicator(mResources.getString(R.string.historial),
				getResources().getDrawable(R.drawable.ic_menu_historial));
		spec.setContent(intent);

		mTabHost.addTab(spec);

	}

	/*
	 * Pestaña 4
	 */

	private void agregarTab4() {

		Intent intent = new Intent(this, Preferencias.class);

		TabSpec spec = mTabHost.newTabSpec(TAG_PREFERENCIAS);

		spec.setIndicator(mResources.getString(R.string.preferencias),
				getResources().getDrawable(R.drawable.ic_menu_preferencias));
		spec.setContent(intent);
		mTabHost.addTab(spec);

	}
}