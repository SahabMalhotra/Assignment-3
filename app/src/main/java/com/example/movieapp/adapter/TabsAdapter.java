package com.example.movieapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.movieapp.view.FavouritesFragment;
import com.example.movieapp.view.SearchFragment;

public class TabsAdapter extends FragmentStateAdapter {

    public TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new FavouritesFragment();
        } else {
            return new SearchFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
