package com.example.movieapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.movieapp.adapter.FavouriteAdapter;
import com.example.movieapp.databinding.FragmentFavouritesBinding;
import com.example.movieapp.viewmodel.MovieViewModel;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;
    private MovieViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);

        binding.favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.loadFavourites();
        viewModel.getFavouriteMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                FavouriteAdapter adapter = new FavouriteAdapter(requireContext(), movies, movieId -> {
                    viewModel.deleteMovie(movieId);
                    Toast.makeText(requireContext(), "Movie deleted", Toast.LENGTH_SHORT).show();
                });
                binding.favouritesRecyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.loadFavourites();
        }
    }
}
