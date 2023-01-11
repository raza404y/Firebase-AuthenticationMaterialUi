package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.authentication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email;
    String password;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(view -> {

            email = binding.emailEt.getEditText().getText().toString().trim();
            password = binding.passwordEt.getEditText().getText().toString().trim();

            if (email.isEmpty()){
                showToast("Enter Email");
            }else if (password.isEmpty()){
                showToast("Enter Password");
            }else {

                EnableProgressBar();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            // getting user uid and email and send it to next activity
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            intent.putExtra("uid",auth.getCurrentUser().getUid());
                            intent.putExtra("email",auth.getCurrentUser().getEmail());
                            startActivity(intent);
                            finish();
                            showToast("Login Successfully");
                        }
                        else{
                            showToast("Email or Password wrong");
                        }
                            DisableProgressBar();
                    }
                });

            }
        });


        binding.CreateNewAccount.setOnClickListener(view ->{

            Intent intent = new Intent(Login.this,SignUp.class);
            startActivity(intent);

        });
    }


    // Methods

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void EnableProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setVisibility(View.INVISIBLE);
    }

    private void DisableProgressBar(){
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.loginBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
    }
}