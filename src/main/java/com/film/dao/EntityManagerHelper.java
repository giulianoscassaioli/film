package com.film.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {

	
	private static EntityManagerFactory emf=null;
	
	
	
	public static EntityManager getEntityManager() {
		
		return getEmf().createEntityManager();
	}

	

	public static EntityManagerFactory getEmf() {
		
		if(emf==null) {
			
			emf = Persistence.createEntityManagerFactory("filmPS");
		}
		return emf;
	}

	
	
	
	
}
