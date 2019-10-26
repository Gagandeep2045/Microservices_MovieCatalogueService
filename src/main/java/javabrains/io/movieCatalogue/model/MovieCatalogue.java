package javabrains.io.movieCatalogue.model;

import java.util.ArrayList;
import java.util.List;

public class MovieCatalogue {
private String userName;

private List<Movie> movies;

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public List<Movie> getMovies() {
	if(movies==null)
		movies=new ArrayList<Movie>();
	return movies;
}

public void setMovies(List<Movie> movies) {
	this.movies = movies;
}

}
