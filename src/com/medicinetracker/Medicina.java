package com.medicinetracker;

public class Medicina {

	private String nombre;
	private int via;
	private int tipo;
	private String persona;
	private String farmacia;
	private String doctor;
	private String nota;

	public Medicina(String nombre, int via, int tipo, String persona,
			String farmacia, String doctor, String nota) {
		super();
		this.nombre = nombre;
		this.via = via;
		this.tipo = tipo;
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

	public int getVia() {
		return via;
	}

	public void setVia(int via) {
		this.via = via;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
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
