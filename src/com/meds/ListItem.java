package com.meds;

public class ListItem {
	private String nombre;
	private String fechainicio;
	private String repeticion;
	private String tipo;
	private String via;
	private String fechafin;
	private String persona;
	private String doctor;
	private String farmacia;
	private String nota;
	private int dosisID;
	private boolean visible;

	public ListItem(String nombre, String fechainicio, String repeticion,
			String tipo, String via, String fechafin, String persona,
			String doctor, String farmacia, String nota, int dosisID,
			boolean visible) {
		super();
		this.nombre = nombre;
		this.fechainicio = fechainicio;
		this.repeticion = repeticion;
		this.tipo = tipo;
		this.via = via;
		this.fechafin = fechafin;
		this.persona = persona;
		this.doctor = doctor;
		this.farmacia = farmacia;
		this.nota = nota;
		this.dosisID = dosisID;
		this.visible = visible;
	}

	public String getNombre() {
		return nombre;
	}

	public String getFechainicio() {
		return fechainicio;
	}

	public String getRepeticion() {
		return repeticion;
	}

	public String getTipo() {
		return tipo;
	}

	public String getVia() {
		return via;
	}

	public String getFechafin() {
		return fechafin;
	}

	public String getPersona() {
		return persona;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getFarmacia() {
		return farmacia;
	}

	public String getNota() {
		return nota;
	}

	public int getDosisID() {
		return dosisID;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
