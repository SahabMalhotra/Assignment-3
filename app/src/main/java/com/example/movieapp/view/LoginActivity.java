package com.example.movieapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(v -> loginUser());
        binding.registerRedirectButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(binding.getRoot(), "Login Failed: " + e.getMessage(), Snackbar.LENGTH_LONG)
                            .setBackgroundTint(ContextCompat.getColor(this, R.color.black))
                            .setTextColor(ContextCompat.getColor(this, R.color.white))
                            .show();
                });

        if (email.isEmpty() || password.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Input Error")
                    .setMessage("Please enter both email and password.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
            return;
        }

    }
}
