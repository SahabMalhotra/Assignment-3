package com.example.movieapp.view;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityEditFavouriteBinding;
import com.example.movieapp.util.DialogUtil;
import com.example.movieapp.viewmodel.MovieViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditFavouriteActivity extends AppCompatActivity {

    private ActivityEditFavouriteBinding binding;
    private MovieViewModel viewModel;
    private String movieId;
    private String originalDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new MovieViewModel();


        movieId = getIntent().getStringExtra("movieId");
        String title = getIntent().getStringExtra("title");
        String year = getIntent().getStringExtra("year");
        String rating = getIntent().getStringExtra("rating");
        String genre = getIntent().getStringExtra("genre");
        String runtime = getIntent().getStringExtra("runtime");
        String poster = getIntent().getStringExtra("poster");
        originalDescription = getIntent().getStringExtra("description");


        Glide.with(this)
                .load(poster)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.detailPoster);


        binding.detailTitle.setText(title);
        binding.detailYear.setText("Year: " + year);
        binding.detailRating.setText("Rating: " + rating);
        binding.detailGenre.setText("Genre: " + genre);
        binding.detailRuntime.setText("Runtime: " + runtime);


        binding.detailDescription.setText(originalDescription);

        binding.saveButton.setOnClickListener(v -> {
            String updated = binding.detailDescription.getText().toString().trim();
            if (!updated.equals(originalDescription) && movieId != null) {
                viewModel.updateDescription(movieId, updated);
                Toast.makeText(this, "Description updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No changes made or missing movie ID", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
        // inside onCreate() after binding

        binding.deleteButton.setOnClickListener(v -> {
            String movieId = getIntent().getStringExtra("movieId");
            if (movieId != null) {
                DialogUtil.showConfirmDialog(this, "Delete Movie",
                        "Are you sure you want to delete this movie?",
                        () -> {
                            FirebaseFirestore.getInstance()
                                    .collection("favorites")
                                    .document(movieId)
                                    .delete()
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Movie deleted", Toast.LENGTH_SHORT).show();
                                        finish(); // go back
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        });
            }
        });


        binding.backButton.setOnClickListener(v -> finish());
    }
}
