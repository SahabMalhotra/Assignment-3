package com.example.movieapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.movieapp.R;
import com.example.movieapp.adapter.TabsAdapter;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String[] tabTitles = new String[]{"Search", "Favorites"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabsAdapter adapter = new TabsAdapter(this);
        binding.viewPager.setAdapter(adapter);


        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    tab.setCustomView(createCustomTab(tabTitles[position]));
                }
        ).attach();


        setTabSelected(binding.tabLayout.getTabAt(0));


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private View createCustomTab(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        TextView tabText = view.findViewById(R.id.tabText);
        tabText.setText(title);
        return view;
    }

    private void setTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (view != null) {
            TextView tabText = view.findViewById(R.id.tabText);
            tabText.setBackgroundResource(R.drawable.tab_selected_background);
            tabText.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    private void setTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (view != null) {
            TextView tabText = view.findViewById(R.id.tabText);
            tabText.setBackgroundResource(R.drawable.tab_unselected_background);
            tabText.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected_text));
        }
    }

}
