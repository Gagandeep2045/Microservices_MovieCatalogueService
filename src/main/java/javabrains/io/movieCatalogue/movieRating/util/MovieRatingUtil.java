package javabrains.io.movieCatalogue.movieRating.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import javabrains.io.movieCatalogue.movieRating.vo.MovieRating;

@Component
public class MovieRatingUtil {

	@Autowired
	private RestTemplate restTemplate;

	// @Value("${ratingDataServiceURL}")
	@Value("${ratingDataServiceEurekaClientNamedUrl}")
	private String ratingDataServiceURL;

	@HystrixCommand(fallbackMethod = "getMovieRatingFallback")
	public List<MovieRating> getMovieRating(String userName) {
		String url = UriComponentsBuilder.fromHttpUrl(ratingDataServiceURL).queryParam("user", userName).toUriString();
		System.out.println("ratingURL:  " + url);
		return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieRating>>() {

		}, new Object()).getBody();
	}

	public List<MovieRating> getMovieRatingFallback(String userName) {
		return Arrays.asList(new MovieRating("NoMovieFound", 0));
	}

}
