package com.moviecatlog.moviecatlogmovies.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.moviecatlog.moviecatlogmovies.model.Movie;
import com.moviecatlog.moviecatlogmovies.model.Page;

@Service
public class MovieServiceImpl implements MovieService{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	ElasticsearchOperations elasticsearchOperations;
	@Override
	public List<Movie> getMovies() throws RestClientException, URISyntaxException {
		
		return getMoviePage().getResults();
	}
	
	public Page getMoviePage() throws RestClientException, URISyntaxException{
		String uri="https://api.themoviedb.org/3/discover/movie?api_key=9495c2fa35e6fe0ea60794d11f31dd26&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
		Page page=restTemplate.getForObject(new URI(uri), Page.class);
		return page;
	}

	@Override
	public String loadMoviesData() throws RestClientException, URISyntaxException {
		List<Movie> movies=getMovies();
		System.out.println("Movies fetched count : "+movies.size());
		for(Movie movie:movies) {
			IndexQuery indexQuery = new IndexQueryBuilder()
				      .withId(movie.getId().toString())
				      .withObject(movie)
				      .build();
				    String documentId = elasticsearchOperations.index(indexQuery);
				    System.out.println("saved doc id :"+documentId);
		}
		return "success";
	}
	

}
