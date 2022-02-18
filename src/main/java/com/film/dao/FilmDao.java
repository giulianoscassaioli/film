package com.film.dao;

import java.util.List;

import com.film.entity.Film;

public interface FilmDao {
	
	public void salva(Film f);
	public void aggiorna(Film f);
	public void cancella(int id);
	public Film findById(int id);
	public List<Film> findAll();
	public List<Film> trovaFilmRegista(String regista);
	public Film trovaFilmNome (String titoloFilm);
	
}
