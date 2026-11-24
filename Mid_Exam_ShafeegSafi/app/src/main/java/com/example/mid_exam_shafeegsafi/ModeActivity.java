package com.example.mid_exam_shafeegsafi;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.graphics.Color;

import com.example.mid_exam_shafeegsafi.databinding.ActivityModeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatDelegate;

public class ModeActivity extends AppCompatActivity {

    private ActivityModeBinding binding;
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Application");
        }

        if (binding.toolbar.getNavigationIcon() != null) {

            android.graphics.drawable.Drawable arrow =
                    binding.toolbar.getNavigationIcon().mutate();

            arrow.setTint(Color.WHITE);

            binding.toolbar.setNavigationIcon(arrow);
        }

        binding.setModeButton.setOnClickListener(v -> showModeMenu());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {

                    if (position == 0) {
                        tab.setText("First");
                    } else if (position == 1) {
                        tab.setText("Second");
                    } else {
                        tab.setText("Third");
                    }

                }).attach();

        setupBackButton();
    }

    private void setupBackButton() {

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (binding.viewPager.getCurrentItem() != 0) {

                            binding.viewPager.setCurrentItem(0, true);

                        } else {

                            setEnabled(false);
                            getOnBackPressedDispatcher().onBackPressed();

                        }
                    }
                });
    }

    private static class ViewPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

        public ViewPagerAdapter(androidx.fragment.app.FragmentActivity activity) {
            super(activity);
        }

        @Override
        public androidx.fragment.app.Fragment createFragment(int position) {

            if (position == 0) {
                return new FirstFragment();
            } else if (position == 1) {
                return new SecondFragment();
            } else {
                return new ThirdFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    private void showModeMenu() {

        PopupMenu popupMenu = new PopupMenu(this, binding.setModeButton);

        popupMenu.getMenu().add("Light");
        popupMenu.getMenu().add("Night");
        popupMenu.getMenu().add("System");

        popupMenu.setOnMenuItemClickListener(item -> {

            String mode = item.getTitle().toString();

            if (mode.equals("Light")) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);

            } else if (mode.equals("Night")) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);

            } else if (mode.equals("System")) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }

            return true;
        });

        popupMenu.show();
    }
}