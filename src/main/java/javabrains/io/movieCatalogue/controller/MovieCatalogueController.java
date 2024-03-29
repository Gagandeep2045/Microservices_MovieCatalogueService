package javabrains.io.movieCatalogue.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javabrains.io.movieCatalogue.model.MovieCatalogue;
import javabrains.io.movieCatalogue.movieRating.vo.MovieRating;
import javabrains.io.movieCatalogue.service.MovieCatalogueService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movieCatalogue")
public class MovieCatalogueController {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	MovieCatalogueService service;

	@RequestMapping("/{userName}")
	MovieCatalogue getCatalogue(@PathVariable() String userName) {
		logger.info("Delegating request to Service class");
		MovieCatalogue m = service.getMovieCatalogueGranular(userName);
		logger.info("Returning response from Service class");
		return m;
		// getMovieCatalogue(userName);
	}
	
	@RequestMapping("/webclient/{userName}")
	Flux<MovieRating> getCatalogue_WebCliengt(@PathVariable()String userName)
	{
		return service.getMovieCatalogue_WebClient(userName);
	}
	
	@RequestMapping("/getServicesInstanceMap")
	public Map getServicesInstanceMap()
	{return service.getServicesInstanceMap();
		
	}
}
