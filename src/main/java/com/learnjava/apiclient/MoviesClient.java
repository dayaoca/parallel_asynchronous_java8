package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MoviesClient {

    private final WebClient webClient;

    public MoviesClient(WebClient webClient){
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId){
        var movieInfo = invokeMovieInfoService(movieInfoId);
        var reviews = invokeReviewService(movieInfoId);
        return new Movie(movieInfo, reviews);
    }

    private MovieInfo invokeMovieInfoService(Long movieInfoId) {
        var movieInfoUrlPath = "/v1/movie_infos/{movieInfoId}";
        var movieInfo =  webClient
                .get()
                .uri(movieInfoUrlPath,movieInfoId)
                //.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();
        return movieInfo;
    }

    private List<Review> invokeReviewService(Long movieInfoId){
        var reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId",movieInfoId)
                .buildAndExpand()
                .toString();

        return webClient
                .get()
                .uri(reviewUri)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();

    }
}
