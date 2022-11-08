package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {
    EditText inpEmail,inpPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        inpEmail = findViewById(R.id.inpEmail);
        inpPass = findViewById(R.id.inpPass);

        inpEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_person_outline_24,0,0,0);
        inpPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24,0,0,0);

    }
}