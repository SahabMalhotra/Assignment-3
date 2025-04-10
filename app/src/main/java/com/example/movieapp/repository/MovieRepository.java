
package com.example.movieapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.api.OmdbApiService;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;

public class MovieRepository {
    private final OmdbApiService apiService;
    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static final String API_KEY = "63b4ae58";

    public MovieRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(OmdbApiService.class);
    }

    public void searchMovies(String query, MutableLiveData<List<Movie>> moviesLiveData) {
        apiService.searchMovies(query, API_KEY).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null && "True".equals(response.body().getResponse())) {
                    moviesLiveData.setValue(response.body().getSearch());
                } else {
                    moviesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                moviesLiveData.setValue(null);
            }
        });
    }

    public void getMovieDetails(String imdbID, MutableLiveData<Movie> movieLiveData) {
        apiService.getMovieDetails(imdbID, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                movieLiveData.setValue(null);
            }
        });
    }
}
