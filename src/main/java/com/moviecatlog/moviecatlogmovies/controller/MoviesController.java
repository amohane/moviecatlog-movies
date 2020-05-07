package com.moviecatlog.moviecatlogmovies.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.moviecatlog.moviecatlogmovies.model.Page;
import com.moviecatlog.moviecatlogmovies.service.MovieService;

@RestController
@RequestMapping("/movies")
@RefreshScope
public class MoviesController {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	MovieService movieService;
	
	@Value("${msg:Hi}")
	private String msg;
	@GetMapping("/hi")
	public String sayHi() {
		return msg;
	}
	
	@GetMapping("/")
	public ResponseEntity<Page> getMovies() {
		try {
			Page page=movieService.getMoviePage();
			return new ResponseEntity<Page>(page, HttpStatus.OK);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Page>(new Page(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("load")
	public ResponseEntity<String> loadMovies(){
		String msg="";
		try {
			msg = movieService.loadMoviesData();
		} catch (RestClientException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(new String(msg),HttpStatus.OK);
	}
}
