package com.example.movieapp.api;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApiService {
    @GET("/")
    Call<SearchResponse> searchMovies(
            @Query("s") String query,
            @Query("apikey") String apiKey
    );
    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("i") String imdbId,
            @Query("apikey") String apiKey
    );

}
