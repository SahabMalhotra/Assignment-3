package com.example.movieapp.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import com.google.firebase.firestore.*;
import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MovieRepository repository = new MovieRepository();

    public LiveData<List<Movie>> getSearchResults() {
        return movieList;
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

    public void searchMovies(String query) {
        repository.searchMovies(query, movieList);
    }
    public LiveData<List<Movie>> getFavouriteMovies() {
        return movieList;
    }

    public void loadMovieDetails(String imdbID) {
        repository.getMovieDetails(imdbID, selectedMovie);
    }

    public void loadFavourites() {
        db.collection("favorites").get().addOnSuccessListener(querySnapshot -> {
            List<Movie> list = new ArrayList<>();
            for (DocumentSnapshot doc : querySnapshot) {
                Movie movie = doc.toObject(Movie.class);
                movie.setId(doc.getId());
                list.add(movie);
            }
            movieList.setValue(list);
        });
    }

    public void deleteMovie(String movieId) {
        db.collection("favorites").document(movieId).delete()
                .addOnSuccessListener(unused -> loadFavourites());
    }

    public void updateDescription(String movieId, String newDescription) {
        db.collection("favorites")
                .document(movieId)
                .update("Plot", newDescription) // <- Use "Plot", not "description"
                .addOnSuccessListener(unused -> loadFavourites())
                .addOnFailureListener(e -> Log.e("UPDATE_DESC", "Failed: " + e.getMessage()));
    }



    public void addToFavourites(Movie movie) {
        db.collection("favorites").add(movie)
                .addOnSuccessListener(documentReference -> loadFavourites());
    }
}
