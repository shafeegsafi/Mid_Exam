package com.example.mid_exam_shafeegsafi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.os.Handler;
import android.os.Looper;
import com.google.android.material.snackbar.Snackbar;
import androidx.activity.OnBackPressedCallback;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mid_exam_shafeegsafi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
        };

        int[] colors = new int[] {
                Color.LTGRAY,
                Color.parseColor("#20A39E")
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        binding.loginButton.setBackgroundTintList(colorStateList);
        binding.loginButton.setTextColor(Color.WHITE);

        binding.loginButton.setTextColor(Color.WHITE);

        setupBackPressed();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        binding.emailEditText.addTextChangedListener(textWatcher);
        binding.passwordEditText.addTextChangedListener(textWatcher);
        validateInputs();

        binding.loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ModeActivity.class);
            startActivity(intent);
        });
    }

    private void validateInputs() {

        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString();

        boolean validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean validPassword = password.length() >= 6;

        binding.loginButton.setEnabled(validEmail && validPassword);
    }

    private void setupBackPressed() {

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (doubleBackToExitPressedOnce) {
                            finish();
                            return;
                        }

                        doubleBackToExitPressedOnce = true;

                        Snackbar.make(binding.getRoot(),
                                "Press back again to exit",
                                Snackbar.LENGTH_SHORT).show();

                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            doubleBackToExitPressedOnce = false;
                        }, 2000);
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}