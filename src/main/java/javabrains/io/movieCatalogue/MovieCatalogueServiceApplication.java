package javabrains.io.movieCatalogue;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javabrains.io.movieCatalogue.interceptor.MovieCatalogueInterceptor;

@SpringBootApplication
public class MovieCatalogueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogueServiceApplication.class, args);
	}

	@Bean
	RestTemplate getRestTemplate() {
		RestTemplate r = new RestTemplate();
		r.setInterceptors(Arrays.asList(new MovieCatalogueInterceptor()));
		return r;
	}
}
