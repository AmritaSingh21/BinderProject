package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binder.Entities.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyProfile extends AppCompatActivity {

    private static final String TAG = "MyProfile";
    FloatingActionButton logout;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference dbRef;
    private FirebaseDatabase firebaseInstance;

    TextView name, age, bio;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = findViewById(R.id.txtName);
        age = findViewById(R.id.txtAge);
        bio = findViewById(R.id.txtBio);

        logout = findViewById(R.id.logOut);
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
                    startActivity(new Intent(MyProfile.this, Welcome.class));
                    finish();
                } else {
                    displayUserInfo();
                }
            }
        };

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnedit = findViewById(R.id.btnEditProf);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, EditProfile.class));
            }
        });

        bottom_menu.setSelectedItemId(R.id.menu_my_profile);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case (R.id.menu_searchBook):
                    startActivity(new Intent(MyProfile.this, SearchBooks.class));
                    break;
                case (R.id.menu_likedBooks):
                    startActivity(new Intent(MyProfile.this, LikedBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(MyProfile.this, BookSwipe2.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void displayUserInfo() {
        firebaseInstance = FirebaseDatabase.getInstance();
        userId = auth.getCurrentUser().getUid();
        dbRef = firebaseInstance.getReference("users");
        dbRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user == null) {
                    Log.e(TAG, "User data is null");
                    return;
                }
                name.setText(user.getName());
                age.setText(String.valueOf(getAge(getDateFromString(user.getDob()))));
                bio.setText(user.getBio());
            }

            private int getAge(Date date) {
                Date now = new Date();
                long timeBetween = now.getTime() - date.getTime();
                double yearsBetween = timeBetween / 3.15576e+10;
                int age = (int) Math.floor(yearsBetween);
                return age;
            }

            private Date getDateFromString(String text) {
                DateFormat formatter = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = formatter.parse(text);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read user.");
            }
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

    private void test(){
//        String name = "book";
//        int id = MyProfile.this.getResources().getIdentifier(name, "drawable",
//                MyProfile.this.getPackageName());
//        imageView.setImageResource(id);
    }
}