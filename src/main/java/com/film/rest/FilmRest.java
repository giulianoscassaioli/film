package com.film.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.film.dao.FilmDao;
import com.film.dao.FilmDaoImp;
import com.film.entity.Film;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/film")
@Api(value = "FilmRest", tags = "Gestione informazioni film")
public class FilmRest {

	Logger logger = LoggerFactory.getLogger(getClass());
	private FilmDao dao;

	public FilmRest() {
		this.dao = new FilmDaoImp();
	}

	@PostMapping
	@ApiOperation(value = "inserimento film", notes = "Permette di inserire un film nel db con incasso criptato....CANCELLARE IL CAMPO ID PRIMA DI INSERIRLO")
	public ResponseEntity<String> saveFilm(@RequestBody Film f) {

		Film f1 = getFilmById(f.getId());
		Film f2 = getFilmByTitolo(f.getTitolo());

		if (f1 != null) {
			logger.error("il film con questo id esiste gia");
			return new ResponseEntity<String>("e' stato gia inserito un film con lo stesso id ",
					HttpStatus.BAD_REQUEST);
		}
		if (f2 != null) {
			logger.error("il film con questo titolo esiste gia");
			return new ResponseEntity<String>("e' stato gia inserito un film con lo stesso titolo ",
					HttpStatus.BAD_REQUEST);
		}

		else {
			dao.salva(f);
			return new ResponseEntity<String>("film inserito con successo", HttpStatus.OK);
		}

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "modifica film", notes = "Permette di modificare un film dato il suo id......CANCELLARE IL CAMPO ID DAL JSON E METTERLO NEL CAMPO ID DELLO SWAGGER PRIMA DI MODIFICARLO")
	public ResponseEntity<String> modificaFilm(@RequestBody Film f, @PathVariable int id) {

		try {
			Film film = dao.findById(id);
			film.setAnno(f.getAnno());
			film.setRegista(f.getRegista());
			film.setIncasso(f.getIncasso());
			film.setTipo(f.getTipo());
			film.setTitolo(f.getTitolo());
			dao.aggiorna(film);
			return new ResponseEntity<String>("il film modificato con successo ", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("il film che vuoi aggiornare non esiste!");
			return new ResponseEntity<String>("il film che vuoi aggiornare non esiste ", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "trova film by id", notes = "Permette di trovare un film dato il suo id")
	public Film getFilmById(@PathVariable int id) {
		try {
			return dao.findById(id);
		} catch (Exception e) {
			logger.error("il film non esiste!");
			return null;
		}

	}

	@GetMapping("/titolo/{titolo}")
	@ApiOperation(value = "trova film by titolo", notes = "Permette di trovare un film dato il suo titolo")
	public Film getFilmByTitolo(@PathVariable String titolo) {
         try {
			return dao.trovaFilmNome(titolo);
		} catch (Exception e) {
			logger.error("il film non esiste!");
			return null;
		}

	}

	@GetMapping("/regista/{regista}")
	@ApiOperation(value = "trova film per regista", notes = "Permette di trovare un film dato un regista")
	public ResponseEntity<List<Film>> filmByRegista(@PathVariable String regista) {
		List<Film> filmtrovati = dao.trovaFilmRegista(regista);
		if (filmtrovati.isEmpty()) {
			logger.error("il regista non esiste!");
			return new ResponseEntity<List<Film>>(filmtrovati, HttpStatus.NOT_FOUND);

		} else {

			return new ResponseEntity<List<Film>>(filmtrovati, HttpStatus.OK);
		}

	}

	@GetMapping
	@ApiOperation(value = "Lista dei film", notes = "Permette di vedere la lista dei films presenti nel db")
	public List<Film> getAllFilm() {

		return (List<Film>) dao.findAll();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "cancellazione film", notes = "Permette di cancellare un film nel db")
	public ResponseEntity<String> cancellaFilm(@PathVariable int id) {
		try {
			dao.cancella(id);
			return new ResponseEntity<String>("film cancellato con successo", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("film non cancellato perche l'id non esiste");
			return new ResponseEntity<String>("film non cancellato " + e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

}
