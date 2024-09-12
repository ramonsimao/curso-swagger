package br.com.alura.curso.swagger.rest.json;

import java.time.LocalDateTime;

public class JsonSwaggerExemplo {
	private long id;
	private LocalDateTime data;
	String string;
	private int numero;
	boolean booleano;
	
	/**
	 * 
	 * @param id
	 * @param data
	 * @param string
	 * @param numero
	 * @param booleano
	 */
	public JsonSwaggerExemplo(long id, LocalDateTime data, String string, int numero, boolean booleano) {
		super();
		this.id = id;
		this.data = data;
		this.string = string;
		this.numero = numero;
		this.booleano = booleano;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}
	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}
	/**
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	/**
	 * @return the booleano
	 */
	public boolean isBooleano() {
		return booleano;
	}
	/**
	 * @param booleano the booleano to set
	 */
	public void setBooleano(boolean booleano) {
		this.booleano = booleano;
	}
}
