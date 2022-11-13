package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignIn extends AppCompatActivity {
    EditText inpEmail,inpPass;
    FloatingActionButton btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        inpEmail = findViewById(R.id.inpEmail);
        inpPass = findViewById(R.id.inpPass);

        inpEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_person_outline_24,0,0,0);
        inpPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24,0,0,0);
        btnTest = findViewById(R.id.fab2);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,UserProfile.class));
            }
        });
    }
}