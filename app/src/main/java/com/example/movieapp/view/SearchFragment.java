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
import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.databinding.FragmentSearchBinding;
import com.example.movieapp.viewmodel.MovieViewModel;
import com.example.movieapp.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getSearchResults().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                MovieAdapter adapter = new MovieAdapter(
                        requireContext(),
                        movies,
                        new ViewModelProvider(requireActivity()).get(MovieViewModel.class),
                        getViewLifecycleOwner()
                );

                binding.searchRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(requireContext(), "No movies found", Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchButton.setOnClickListener(v -> {
            String query = binding.searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                viewModel.searchMovies(query);
            }
        });
    }
}
