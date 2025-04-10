package com.example.movieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.SearchResponse;
import com.example.movieapp.api.OmdbApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> searchResults = new MutableLiveData<>();
    private final OmdbApiService apiService;

    public SearchViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(OmdbApiService.class);
    }

    public LiveData<List<Movie>> getSearchResults() {
        return searchResults;
    }

    public void searchMovies(String query) {
        apiService.searchMovies(query, "63b4ae58").enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null && "True".equals(response.body().getResponse())) {
                    searchResults.setValue(response.body().getSearch());
                } else {
                    searchResults.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                searchResults.setValue(null);
            }
        });
    }
}
