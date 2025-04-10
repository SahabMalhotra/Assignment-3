package com.example.movieapp.view;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import com.bumptech.glide.Glide;
import com.example.movieapp.databinding.ActivityMovieDetailsBinding;
import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private FirebaseFirestore db;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        currentMovie = new Movie();

        String imdbID = getIntent().getStringExtra("imdbID");
        if (imdbID == null || imdbID.isEmpty()) {
            Toast.makeText(this, "Movie ID not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        MovieRepository repository = new MovieRepository();
        MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();

        repository.getMovieDetails(imdbID, movieLiveData);

        movieLiveData.observe(this, movie -> {
            if (movie != null) {
                currentMovie = movie;

                Glide.with(this).load(movie.getPoster()).into(binding.detailPoster);

                binding.detailTitle.setText(movie.getTitle());
                binding.detailYear.setText("Year: " + movie.getYear());
                binding.detailRating.setText("Rating: " + (movie.getImdbRating() != null ? movie.getImdbRating() : "N/A"));
                binding.detailGenre.setText("Genre: " + (movie.getGenre() != null ? movie.getGenre() : "N/A"));
                binding.detailRuntime.setText("Runtime: " + (movie.getRuntime() != null ? movie.getRuntime() : "N/A"));
                binding.detailDescription.setText(
                        movie.getPlot() != null && !movie.getPlot().isEmpty()
                                ? movie.getPlot()
                                : "No description available"
                );

            }
        });

        binding.addToFavouritesButton.setOnClickListener(view -> {
            if (currentMovie != null) {
                db.collection("favorites")
                        .add(currentMovie)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
                            currentMovie.setId(documentReference.getId());
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to add: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        binding.backButton.setOnClickListener(v -> finish());
    }
}
