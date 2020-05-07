package com.moviecatlog.moviecatlogmovies.service;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.client.RestClientException;

import com.moviecatlog.moviecatlogmovies.model.Movie;
import com.moviecatlog.moviecatlogmovies.model.Page;

public interface MovieService {
	public List<Movie> getMovies() throws RestClientException, URISyntaxException;
	public Page getMoviePage() throws RestClientException, URISyntaxException;
	public String loadMoviesData() throws RestClientException, URISyntaxException;
}
