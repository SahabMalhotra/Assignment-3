package com.example.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movieapp.databinding.ItemFavouriteBinding;
import com.example.movieapp.model.Movie;
import com.example.movieapp.util.DialogUtil;
import com.example.movieapp.view.EditFavouriteActivity;
import com.example.movieapp.R;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MovieViewHolder> {

    private final Context context;
    private final List<Movie> favorites;
    private final OnMovieActionListener listener;

    public interface OnMovieActionListener {
        void onDeleteClicked(String movieId);
    }

    public FavouriteAdapter(Context context, List<Movie> favorites, OnMovieActionListener listener) {
        this.context = context;
        this.favorites = favorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemFavouriteBinding binding = ItemFavouriteBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = favorites.get(position);
        ItemFavouriteBinding b = holder.binding;

        b.titleTextView.setText(movie.getTitle());
        b.descriptionTextView.setText(movie.getPlot());
        b.yearTextView.setText(movie.getYear());

        Glide.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_launcher_background)
                .into(b.posterImageView);

        b.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditFavouriteActivity.class);
            intent.putExtra("movieId", movie.getId());
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("year", movie.getYear());
            intent.putExtra("rating", movie.getImdbRating());
            intent.putExtra("genre", movie.getGenre());
            intent.putExtra("runtime", movie.getRuntime());
            intent.putExtra("poster", movie.getPoster());
            intent.putExtra("description", movie.getPlot());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemFavouriteBinding binding;

        public MovieViewHolder(ItemFavouriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
