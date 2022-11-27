package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignIn extends AppCompatActivity {
    EditText inpEmail, inpPass;
    FloatingActionButton signIn;
    FloatingActionButton btnTest;
    TextView errorMsg;

    static final String TAG = "SignInActivity";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inpEmail = findViewById(R.id.inpEmail);
        inpPass = findViewById(R.id.inpPass);
        errorMsg = findViewById(R.id.errorMsg);

        inpEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_person_outline_24, 0, 0, 0);
        inpPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0);


       // Sign In Auth
        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

    }

    private void signIn() {
        String email = inpEmail.getText().toString().trim();
        String password = inpPass.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignIn.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInUserWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Log.d(TAG, "Auth Failed." + task.getException());
                                    if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                        errorMsg.setText("Invalid credentials.");
                                    }
                                } else {
                                    errorMsg.setText("");
                                    startActivity(new Intent(SignIn.this,
                                            BookSwipe2.class));
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully signed in", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }
}