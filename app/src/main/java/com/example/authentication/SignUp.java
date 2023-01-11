package com.example.authentication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.authentication.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email;
    String password;
    String confirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        // click listener on button
        binding.SignUp.setOnClickListener(view -> {

            // getting text from xml fields
             email = binding.emailEt.getEditText().getText().toString().trim();
             password = binding.passwordEt.getEditText().getText().toString().trim();
             confirmPass = binding.confirmPasswordEt.getEditText().getText().toString().trim();

             // Validation of xml fields
            if (email.isEmpty()){
                showToast("Email is empty");
            }else if (password.isEmpty()){
                showToast("Password is empty");
            }else if (confirmPass.isEmpty()){
                showToast("Confirm password is empty");
            }else if (password.length()<6){
                showToast("Password at least 6 characters");
            }else if (!password.equals(confirmPass)){
                showToast("Password not matching");
            }else if (!email.matches(emailPattern)){
                showToast("Invalid email");
            }else {


                EnableProgressBar();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),Home.class));
                            finish();
                            showToast("Successfully registered");
                        }
                        else {
                            showToast("Email already registered");
                        }
                        DisableProgressBar();

                    }
                });
            }
        });


        binding.alreadyAccountEt.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this,Login.class);
            startActivity(intent);
        });
    }

    /// Methods
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void EnableProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.SignUp.setVisibility(View.INVISIBLE);
    }

    private void DisableProgressBar(){
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.SignUp.setVisibility(View.VISIBLE);
    }
}