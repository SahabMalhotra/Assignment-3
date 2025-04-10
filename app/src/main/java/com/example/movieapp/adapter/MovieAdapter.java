package com.example.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movieapp.databinding.ItemMovieBinding;
import com.example.movieapp.model.Movie;
import com.example.movieapp.view.MovieDetailsActivity;
import com.example.movieapp.viewmodel.MovieViewModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private final List<Movie> movieList;
    private final MovieViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;

    public MovieAdapter(Context context, List<Movie> movieList, MovieViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.movieList = movieList;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        ItemMovieBinding b = holder.binding;

        b.movieTitle.setText(movie.getTitle());
        b.movieYear.setText("Year: " + movie.getYear());
        b.movieRating.setText("Rating: " + (movie.getImdbRating() != null ? movie.getImdbRating() : "Loading..."));

        Glide.with(context)
                .load(movie.getPoster())
                .into(b.moviePoster);

        // If rating not yet loaded, fetch from API
        if (movie.getImdbRating() == null || movie.getImdbRating().equals("N/A")) {
            viewModel.loadMovieDetails(movie.getImdbID());
            viewModel.getSelectedMovie().observe(lifecycleOwner, fullMovie -> {
                if (fullMovie != null && fullMovie.getImdbID().equals(movie.getImdbID())) {
                    movie.setImdbRating(fullMovie.getImdbRating());
                    notifyItemChanged(position);
                }
            });
        }

        b.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("year", movie.getYear());
            intent.putExtra("poster", movie.getPoster());
            intent.putExtra("imdbID", movie.getImdbID());
            intent.putExtra("description", movie.getPlot());
            intent.putExtra("rating", movie.getImdbRating());
            intent.putExtra("genre", movie.getGenre());
            intent.putExtra("runtime", movie.getRuntime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
