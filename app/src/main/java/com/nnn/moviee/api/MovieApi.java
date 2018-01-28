package com.nnn.moviee.api;

import com.nnn.moviee.model.response.ListMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ridhaaaaazis on 27/01/18.
 */

public interface MovieApi {
    @GET("movie/popular")
    Call<ListMovieResponse> getPopularMovies();

    @GET("search/movie")
    Call<ListMovieResponse> getSearch(@Query("query") String query);
}
