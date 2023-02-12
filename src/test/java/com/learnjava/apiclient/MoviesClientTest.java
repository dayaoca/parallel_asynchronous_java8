package com.learnjava.apiclient;

import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.web.reactive.function.client.WebClient.builder;

public class MoviesClientTest {

    WebClient webClient = builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    MoviesClient moviesClient = new MoviesClient(webClient);

   // @Test
    @RepeatedTest(10)
    void retrieveMovie(){
        CommonUtil.startTimer();
        //given
        Long movieInfoId = 1L;
        //when
        var movie = moviesClient.retrieveMovie(movieInfoId);
        System.out.println("movie is ===="+movie);
        CommonUtil.timeTaken();
        //then
        assert movie!=null;
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert(movie.getReviewList().size()==1);

    }

    //@Test
    @RepeatedTest(10)
    void retrievemovie_CF() {
        CommonUtil.startTimer();
        //given
        var movieInfoId=1L;
        //when
        var movie = moviesClient.retrievemovie_CF(movieInfoId).join();
        System.out.println("movie inside retrieve_CF is=="+movie);
        CommonUtil.timeTaken();
        //then
        assert movie!=null;
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size()==1;

    }
}
