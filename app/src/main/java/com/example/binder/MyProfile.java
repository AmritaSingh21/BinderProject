package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {

    Button logout;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

//        logout = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth.signOut();
//                Toast.makeText(getApplicationContext(),
//                        "Successfully signed out", Toast.LENGTH_SHORT).show();
//            }
//        });

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MyProfile.this, Welcome.class));
                    //Log.i("testRes","not logged in");
                    finish();
                } else {
                    // TODO fetch user from db and display info
                }
            }
        };

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnedit = findViewById(R.id.btnEditProf);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this,EditProfile.class));
            }
        });

        bottom_menu.setSelectedItemId(R.id.menu_my_profile);
        bottom_menu.setOnItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case(R.id.menu_searchBook):
                    startActivity(new Intent(MyProfile.this,SearchBooks.class));
                    break;
                case (R.id.menu_likedBooks):
                    startActivity(new Intent(MyProfile.this,LikedBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(MyProfile.this,BookSwipe2.class));
                    break;
                default:
                    break;
            }
            return true;
        });
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