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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.binder.Entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProfile extends AppCompatActivity {

    EditText name, bio;
    TextView txtName;

    private DatePickerDialog datePickerDialog;
    private Button dob;
    private Button confirm;

    private static final String TAG = "EditProfile";

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference dbRef;
    private FirebaseDatabase firebaseInstance;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.editUserName);
        dob = findViewById(R.id.datePickerButton);
        bio = findViewById(R.id.editUserBio);
        txtName = findViewById(R.id.txtUserInfo);
        confirm = findViewById(R.id.btnEditSubmit);

        auth = FirebaseAuth.getInstance();

        initDatePicker();
        //dob.setText(getTodaysDate());

        getUserInfo();

        ImageButton btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this,chatsActivity.class));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = checkMandatoryFields();
                if (!flag) {
                    return;
                }
                userId = auth.getCurrentUser().getUid();
                dbRef = firebaseInstance.getReference("users");
                dbRef.child(userId).child("name").setValue(name.getText().toString());
                dbRef.child(userId).child("bio").setValue(bio.getText().toString());
                dbRef.child(userId).child("dob").setValue(dob.getText().toString());
                startActivity(new Intent(EditProfile.this,MyProfile.class));
            }
        });
    }

    private boolean checkMandatoryFields() {
        boolean flag = true;
        if (TextUtils.isEmpty(name.getText())) {
            name.setError(" Please Enter your Name! ");
            flag = false;
        }
        if(!isDateValid()){
            dob.setError(" Please enter valid date. ");
            flag = false;
        }

        return flag;
    }

    private boolean isDateValid() {
        Date date = getDateFromString(dob.getText().toString());
        Date now = new Date();
        long timeBetween = now.getTime() - date.getTime();
        double yearsBetween = timeBetween / 3.15576e+10;
        int age = (int) Math.floor(yearsBetween);
        if(age < 5){
            return false;
        }
        return true;
    }

    /*
    method to get date of birth from button text
     */
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

    private void getUserInfo() {
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
                bio.setText(user.getBio());
                dob.setText(user.getDob());
                txtName.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read user.");
            }
        });
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
                dob.setText(date);
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