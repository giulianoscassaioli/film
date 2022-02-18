package com.film.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.film.entity.Film;


public class FilmDaoImp implements  FilmDao{

	private EntityManager em = null;

	/**
	 *  Salva un film nel database
	 *  con incasso criptato
	 * 
	 *  @author  Giuliano Scassaioli
	 *  @param  f Film da salvare
	 *  @return  
	 */
	@Override
	public void salva(Film f) {
		try {
			String incassoCriptato= BCrypt.hashpw(f.getIncasso(),BCrypt.gensalt());
	        f.setIncasso(incassoCriptato);
			getEm().getTransaction().begin();
			em.persist(f);
		}catch(Exception e) {
			getEm().getTransaction().rollback();
		}finally {
			getEm().getTransaction().commit();
		}	
	}


	/**
	 *  Aggiorna un film se esiste
	 *  con incasso criptato
	 * 
	 *  @author  Giuliano Scassaioli
	 *  @param   Film film  da aggiornare
	 *  
	 */
	@Override
	public void aggiorna(Film f) {
		    String incassoCriptato= BCrypt.hashpw(f.getIncasso(),BCrypt.gensalt());
	        f.setIncasso(incassoCriptato);
			getEm().getTransaction().begin();
			getEm().merge(f);
			getEm().getTransaction().commit();
			
		}
		
	

	/**
	 *  Ricerca film per id
	 *
	 * 
	 *  @author  Giuliano Scassaioli
	 *  @param   id  id film da ricercare 
	 *  @return  film trovato
	 */
	@Override
	public Film findById(int id) {
		return getEm().find(Film.class, id);
	}

	/**
	 *  Ricerca film per regista
	 *
	 * 
	 *  @author  Giuliano Scassaioli
	 *  @param   regista regista da ricercare 
	 *  @return  lista dei registi trovati
	 */
	@Override
	public List<Film> trovaFilmRegista(String regista) {
		
        Query q= getEm().createNamedQuery("film.trovaRegista");
		q.setParameter("reg","%" + regista + "%");
		@SuppressWarnings("unchecked")
		List<Film> filmtrovati = q.getResultList();
		return filmtrovati;
	}


	/**
	 *  Cancella un  film se esiste
	 *
	 * 
	 *  @author  Giuliano Scassaioli
	 *  @param   id id da cancellare 
	 * 
	 */
	@Override
	public void cancella(int id) {
		try {
			getEm().getTransaction().begin();
			getEm().remove(em.find(Film.class, id));
		}catch(Exception e) {
			getEm().getTransaction().rollback();
		}finally {
			getEm().getTransaction().commit();
		}
		
	}


	/**
	 *  Lista di tutti i film presenti nel sistema
	 *
	 * 
	 *  @author  Giuliano Scassaioli
	 * 
	 *  @return  lista di tutti i film
	 */
	@Override
	public List<Film> findAll() {
		return getEm().createQuery("SELECT f FROM Film f").getResultList();
	}

	
	public EntityManager getEm() {

		if (em == null) {
			em = EntityManagerHelper.getEntityManager();
		}
		return em;
	}
	
	@Override
     public Film trovaFilmNome(String titoloFilm) {
		
        Query q= getEm().createNamedQuery("film.trovaTitolo");
		q.setParameter("titolo",titoloFilm );
		return (Film) q.getSingleResult();
	}
	
}
