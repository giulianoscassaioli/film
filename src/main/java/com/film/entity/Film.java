package com.film.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="film")
@NamedQuery(name = "film.trovaRegista", query = "SELECT f FROM Film f WHERE UPPER(f.regista) LIKE UPPER (:reg)")
@NamedQuery(name = "film.trovaTitolo", query = "SELECT f FROM Film f WHERE UPPER(f.titolo) = UPPER(:titolo)")
public class Film {

	 private int id;
	 private String titolo;
	 private String anno;
	 private String regista;
	 private String tipo;
	 private String incasso;
	 
	 
	 
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="titolo")
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	@Column(name="anno")
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	@Column(name="regista")
	public String getRegista() {
		return regista;
	}
	public void setRegista(String regista) {
		this.regista = regista;
	}
	@Column(name="tipo")
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Column(name="incasso")
	public String getIncasso() {
		return incasso;
	}
	public void setIncasso(String incasso) {
		this.incasso = incasso;
	}
	 
	 
	 
	
	
}
