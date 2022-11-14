package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.binder.Adapters.ImageListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {

    Button logout;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.book);

        RecyclerView recyclerViewTunes = findViewById(R.id.recycler);
        ImageListAdapter adapter = new ImageListAdapter(list);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerViewTunes.setLayoutManager(lm);
        recyclerViewTunes.setAdapter(adapter);

        logout = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(getApplicationContext(),
                        "Successfully signed out", Toast.LENGTH_SHORT).show();
            }
        });

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.d("MyProfile", "user null");
                    startActivity(new Intent(MyProfile.this, Welcome.class));
                    finish();
                } else {
                    // TODO fetch user from db and display info
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authListener);
    }
}