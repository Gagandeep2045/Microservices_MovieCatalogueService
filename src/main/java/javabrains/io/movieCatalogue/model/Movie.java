package javabrains.io.movieCatalogue.model;

public class Movie {

	public Movie()
	{}
	
	public Movie(String movieId, String movieName, int rating, String movieDescription) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.rating = rating;
		this.movieDescription = movieDescription;
	}

	private String movieId;

	private String movieName;
	
	private int rating;
	
	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	private String movieDescription;

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
