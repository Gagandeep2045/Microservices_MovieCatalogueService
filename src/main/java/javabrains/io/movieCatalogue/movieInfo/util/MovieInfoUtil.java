package javabrains.io.movieCatalogue.movieInfo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import javabrains.io.movieCatalogue.movieInfo.vo.Movie;

@Component
public class MovieInfoUtil {

	@Autowired
	private RestTemplate restTemplate;

	// @Value("${movieInfoServiceURL}")
	@Value("${movieInfoServiceEurekaClientNamedUrl}")
	private String movieInfoServiceURL;

	@HystrixCommand(fallbackMethod = "getMovieInfoFallback",commandProperties={@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"),
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="6"),
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="8000")
	})
	public Movie getMovieInfo(String movieId) {
		String url = UriComponentsBuilder.fromHttpUrl(movieInfoServiceURL).queryParam("movieId", movieId).toUriString();
		System.out.println("movieInfoURL:  " + url);
		return restTemplate.getForObject(url, Movie.class, new Object[0]);
	}

	public Movie getMovieInfoFallback(String movieId) {
		return new Movie(movieId, "Movie Details Not Found", "Movie Description Not Found");

	}
}
