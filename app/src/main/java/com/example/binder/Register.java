package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.binder.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private EditText signupInputEmail;
    private EditText signupInputPassword;
    private EditText name, bio;
    private RadioButton male, female, others;
    private CheckBox fiction, mystery, fantasy, biography, romance, poetry, scienceFic, adventure,
            nonFic;

    private FirebaseAuth auth;
    private String userId;

    private static final String TAG = "REGISTER";

    private DatabaseReference firebaseDatabase;
    private FirebaseDatabase firebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        name = findViewById(R.id.editName);
        bio = findViewById(R.id.editBio);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        others = findViewById(R.id.others);
        fiction = findViewById(R.id.fiction);
        mystery = findViewById(R.id.mystery);
        fantasy = findViewById(R.id.fantasy);
        biography = findViewById(R.id.biography);
        romance = findViewById(R.id.romance);
        poetry = findViewById(R.id.poetry);
        scienceFic = findViewById(R.id.scienceFiction);
        adventure = findViewById(R.id.adventure);
        nonFic = findViewById(R.id.nonFiction);


        //DB
        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseInstance.getReference("app_title").setValue("Binder");

        //register auth
        auth = FirebaseAuth.getInstance();
        signupInputEmail = findViewById(R.id.editEmail);
        signupInputPassword = findViewById(R.id.editUserPw);
        Button signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        boolean flag = checkMandatoryFields();
        if (!flag) {
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Log.d(TAG, "Auth Failed." + task.getException());
                                } else {
                                    startActivity(new Intent(Register.this,
                                            MyProfile.class));
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully registered", Toast.LENGTH_SHORT).show();
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

    private boolean checkMandatoryFields() {
        boolean flag = true;
        if (TextUtils.isEmpty(signupInputEmail.getText())) {
            signupInputEmail.setError(" Please Enter Email! ");
            flag = false;
        }
        if (TextUtils.isEmpty(signupInputPassword.getText())) {
            signupInputPassword.setError(" Please Enter Password! ");
            flag = false;
        }
        if (TextUtils.isEmpty(name.getText())) {
            name.setError(" Please Enter your Name! ");
            flag = false;
        }
        return flag;
    }

    private void createUser() {
        Log.d(TAG, "going to create user.");
        if (TextUtils.isEmpty(userId)) {
            userId = firebaseDatabase.push().getKey();
            Log.d(TAG, "UserKey: " + userId);
        }
        ArrayList pref = new ArrayList();
        if (fantasy.isChecked()) {
            pref.add(fantasy.getText().toString());
        }
        if (mystery.isChecked()) {
            pref.add(mystery.getText().toString());
        }
        if (fiction.isChecked()) {
            pref.add(fiction.getText().toString());
        }
        if (biography.isChecked()) {
            pref.add(biography.getText().toString());
        }
        if (romance.isChecked()) {
            pref.add(romance.getText().toString());
        }
        if (poetry.isChecked()) {
            pref.add(poetry.getText().toString());
        }
        if (scienceFic.isChecked()) {
            pref.add(scienceFic.getText().toString());
        }
        if (adventure.isChecked()) {
            pref.add(adventure.getText().toString());
        }
        if (nonFic.isChecked()) {
            pref.add(nonFic.getText().toString());
        }
        Date dob = getDateFromString(dateButton.getText().toString());
        String gender = "";
        if (female.isSelected()) {
            gender = female.getText().toString();
        } else if (male.isSelected()) {
            gender = male.getText().toString();
        } else {
            gender = others.getText().toString();
        }
        User user = new User(signupInputEmail.toString(),
                name.toString(), bio.toString(), dob, pref, gender);
        firebaseDatabase.child(userId).setValue(user);
        //addUserChangeListener();
    }


    private Date getDateFromString(String text) {
        // TODO
        return new Date();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}