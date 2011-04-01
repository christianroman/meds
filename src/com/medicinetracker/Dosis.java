package com.medicinetracker;

import java.util.Calendar;

public class Dosis {

	private int idmedicamento;
	private int dia;
	private int mes;
	private int año;
	private int hora;
	private int minuto;
	private String fechaInicio;
	private int repeticion;
	private int dias;
	private int cantidad;
	private int estado;
	private String fechaFin;

	public Dosis(int idmedicamento, int dia, int mes, int año, int hora,
			int minuto, int repeticion, int dias, int cantidad) {
		super();
		this.idmedicamento = idmedicamento;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.hora = hora;
		this.minuto = minuto;
		this.repeticion = repeticion;
		this.dias = dias;
		this.cantidad = cantidad;
		estado = 1;
		CalcularFechaFin();
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	public int getIdmedicamento() {
		return idmedicamento;
	}

	public void setIdmedicamento(int idmedicamento) {
		this.idmedicamento = idmedicamento;
	}

	public int getRepeticion() {
		return repeticion;
	}

	public void setRepeticion(int repeticion) {
		this.repeticion = repeticion;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String getFechaInicio(){
		return fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void CalcularFechaFin() {
		
		String DATE_FORMAT = "yyyy-MM-dd HH:mm";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		Calendar c = Calendar.getInstance();
		c.set(año, mes, dia, hora, minuto);
		fechaInicio = sdf.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, +dias);
		fechaFin = sdf.format(c.getTime());
		
	}
}
