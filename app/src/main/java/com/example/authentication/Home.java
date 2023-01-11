package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.authentication.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();




        binding.SignOut.setOnClickListener(view -> {

            EnableProgressBar();
            auth.signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
            DisableProgressBar();
        });

        // getting data from login activity
        Intent intent = getIntent();
        String mail = intent.getStringExtra("email");
        String uid = intent.getStringExtra("uid");

        binding.emailTV.setText(mail);
        binding.uidTV.setText(uid);

    }

    // Methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.logout) {
            auth.signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        return true;
    }
    private void EnableProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.SignOut.setVisibility(View.INVISIBLE);
    }

    private void DisableProgressBar(){
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.SignOut.setVisibility(View.VISIBLE);
    }

}