package com.meds;

public class Titular {
	private String titulo;
	private String subtitulo;
	private String hora;
	private String tipo;
	private String via;
	private String fecha;
	private String doctor;
	private String farmacia;
	private String nota;
	private boolean visible;

	public Titular(String tit, String sub, String hora, String tipo,
			String via, String fecha, String doctor, String farmacia,
			String nota, boolean visible) {
		titulo = tit;
		subtitulo = sub;
		this.hora = hora;
		this.visible = visible;
		this.tipo = tipo;
		this.via = via;
		this.fecha = fecha;
		this.doctor = doctor;
		this.farmacia = farmacia;
		this.nota = nota;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public String getHora() {
		return hora;
	}

	public String getTipo() {
		return tipo;
	}

	public String getVia() {
		return via;
	}

	public String getFecha() {
		return fecha;
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

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
