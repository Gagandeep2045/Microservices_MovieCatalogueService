package javabrains.io.movieCatalogue.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import javabrains.io.movieCatalogue.interceptor.MovieCatalogueInterceptor;
import javabrains.io.movieCatalogue.model.MovieCatalogue;
import javabrains.io.movieCatalogue.movieInfo.vo.Movie;
import javabrains.io.movieCatalogue.movieRating.vo.MovieRating;
import reactor.core.publisher.Flux;

@Service
public class MovieCatalogueService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient webClient;

	@Value("${ratingDataServiceURL}")
	private String ratingDataServiceURL;

	@Value("${movieInfoServiceURL}")
	private String movieInfoServiceURL;

	public MovieCatalogue getMovieCatalogue(String userName) {
		// Map<String, String> m = Stream.of("user").collect(Collectors.toMap(a -> a, a
		// -> "gagan"));
		// Plz read pont
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("user", userName);
		String url = UriComponentsBuilder.fromHttpUrl(ratingDataServiceURL).queryParams(multiValueMap).toUriString();
		System.out.println("URL with query String " + url);
		// ResponseEntity<List> movieRatingResponse = restTemplate.getForEntity(url,
		// List.class);
		ResponseEntity<List<MovieRating>> movieRatingResponse = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MovieRating>>() {
				}, new Object());
		List<MovieRating> movieRating = movieRatingResponse.getBody();
		MovieCatalogue movieCatalogue = new MovieCatalogue();
		movieCatalogue.setUserName(userName);
		for (MovieRating rating : movieRating) {
			String movieId = rating.getMovieId();
			String urlWithQueryString = UriComponentsBuilder.fromHttpUrl(movieInfoServiceURL)
					.queryParam("movieId", movieId).build().toUriString();
			Movie movie = restTemplate.exchange(urlWithQueryString, HttpMethod.GET, null, Movie.class, new Object())
					.getBody();
			javabrains.io.movieCatalogue.model.Movie mov = new javabrains.io.movieCatalogue.model.Movie();
			mov.setMovieId(movieId);
			mov.setMovieName(movie.getMovieName());
			mov.setRating(rating.getRating());
			mov.setMovieDescription(movie.getMovieDescription());
			movieCatalogue.getMovies().add(mov);
		}

		return movieCatalogue;
	}

	public Flux<MovieRating> getMovieCatalogue_WebClient(String userName) {
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("user", userName);
		URI uri = UriComponentsBuilder.fromHttpUrl(ratingDataServiceURL).queryParams(multiValueMap).build().toUri();
		Flux<MovieRating> a = webClient.get().uri(uri).retrieve().bodyToFlux(MovieRating.class);
		return a;
	}

	@Bean
	WebClient getWebClient() {
		return WebClient.create();
	}
}