package com.medicinetracker;

public class Medicamento {
	
	private String nombre;
	private String via;
	private String tipo;
	private String contenido;
	private String persona;
	private String farmacia;
	private String doctor;
	private String nota;
	
	
	public Medicamento(String nombre, String via, String tipo,
			String contenido, String persona, String farmacia, String doctor,
			String nota) {
		super();
		this.nombre = nombre;
		this.via = via;
		this.tipo = tipo;
		this.contenido = contenido;
		this.persona = persona;
		this.farmacia = farmacia;
		this.doctor = doctor;
		this.nota = nota;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getContenido() {
		return contenido;
	}


	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public String getPersona() {
		return persona;
	}


	public void setPersona(String persona) {
		this.persona = persona;
	}


	public String getFarmacia() {
		return farmacia;
	}


	public void setFarmacia(String farmacia) {
		this.farmacia = farmacia;
	}


	public String getDoctor() {
		return doctor;
	}


	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}
	
	
	
	

}
