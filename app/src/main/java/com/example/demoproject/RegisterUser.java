package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FireBaseObjects.FirebaseRegisterUser;

public class RegisterUser extends AppCompatActivity {

    static final String LOG_TAG = RegisterUser.class.getSimpleName();

    EditText etUsername, etFirstName, etLastName, etMail, etPass, etConfirmPass, etPhone;
    Spinner spinnerQualification, spinnerOccupation;
    Button btnRegister;
    TextView tvLogin;
    String userName, userFName, userLName, userEmail, userPassword, userQualification, userOccupation;
    int userPhone;
    String[] occupation = {"--", "Student", "Teacher", "Professional"};
    String[] qualification = {"--", "Graduation", "Master", "Diploma", "School"};

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        etUsername = findViewById(R.id.etUsername);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMail = findViewById(R.id.etMail);
        etPass = findViewById(R.id.etPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        etPhone = findViewById(R.id.etPhone);
        spinnerQualification = findViewById(R.id.spinnerQualification);
        spinnerOccupation = findViewById(R.id.spinnerOccupation);
        tvLogin = findViewById(R.id.tvLogin);

        databaseReference = FirebaseDatabase.getInstance().getReference("register_users");

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> qualific = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualification);
        qualific.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQualification.setAdapter(qualific);

        spinnerQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), qualification[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerOccupation = findViewById(R.id.spinnerOccupation);
        ArrayAdapter<String> occuptn = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, occupation);
        occuptn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOccupation.setAdapter(occuptn);

        spinnerOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), occupation[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "[REGISTER BTN]");

                registerUser();

            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterUser.this, LoginUser.class);
                startActivity(intent);
            }
        });


    }


    public void registerUser() {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        final CharSequence target = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        if (etUsername.getText().toString().isEmpty()) {
            Log.d(LOG_TAG, "[REGISTER INSIDE THE LOOP 1]");
            Toast.makeText(getApplicationContext(), "Please enter user name", Toast.LENGTH_SHORT).show();
        } else if (etFirstName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter First name", Toast.LENGTH_SHORT).show();
        } else if (etLastName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Last name", Toast.LENGTH_SHORT).show();
        } else if (etMail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Email Id", Toast.LENGTH_SHORT).show();
        } else if (!etMail.getText().toString().matches(target.toString())) {
            Toast.makeText(getApplicationContext(), "Please enter Valid Email Id", Toast.LENGTH_SHORT).show();
        } else if (etPass.getText().toString().isEmpty()) {
            Log.d(LOG_TAG, "Password 1");
            Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
        } else if (etPass.getText().toString().length() < 8) {
            Log.d(LOG_TAG, "Password 2");
            Toast.makeText(RegisterUser.this, "Password Must be more than 8 Letters", Toast.LENGTH_SHORT).show();
        } else if (!etPass.getText().toString().matches(PASSWORD_PATTERN)) {
            Log.d(LOG_TAG, "Password 3");
            Toast.makeText(getApplicationContext(), "Please choose 1 Lower, 1 Small, 1 Numeric and 1 Special Characters", Toast.LENGTH_SHORT).show();
        } else if (etConfirmPass.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (!etConfirmPass.getText().toString().equals(etPass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Password Doesn't Match", Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Phone Number", Toast.LENGTH_SHORT).show();
        } else if (spinnerQualification.getSelectedItem().toString().trim().equals("--")) {
            Toast.makeText(getApplicationContext(), "Please Select Qualification Option", Toast.LENGTH_SHORT).show();
        } else if (spinnerOccupation.getSelectedItem().toString().trim().equals("--")) {
            Toast.makeText(getApplicationContext(), "Please Select Qualification Option", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(LOG_TAG, "[REGISTER INSIDE THE ELSE CONDITION]");
            userName = etUsername.getText().toString().trim();
            Log.d(LOG_TAG, "[REGISTER USERNAME ]" + userName);
            userFName = etFirstName.getText().toString().trim();
            Log.d(LOG_TAG, "[REGISTER FNAME ]" + userFName);
            userLName = etLastName.getText().toString().trim();
            Log.d(LOG_TAG, "[REGISTER LNAME ]" + userLName);
            userEmail = etMail.getText().toString().trim();
            Log.d(LOG_TAG, "[REGISTER EMAIL ]" + userEmail);
            userPassword = etPass.getText().toString().trim();
            Log.d(LOG_TAG, "[REGISTER PSWD ]" + userPassword);
            userPhone = Integer.parseInt(etPhone.getText().toString().trim());
            Log.d(LOG_TAG, "[REGISTER PHN ]" + userPhone);
            userQualification = spinnerQualification.getSelectedItem().toString();
            Log.d(LOG_TAG, "[REGISTER QUALIFICATION ]" + userQualification);
            userOccupation = spinnerOccupation.getSelectedItem().toString();
            Log.d(LOG_TAG, "[REGISTER OCCUPATION ]" + userOccupation);

            Log.d(LOG_TAG, "[REGISTER INSIDE THE END OF THE ELSE LOOP]");

            Toast.makeText(getApplicationContext(), "On Register", Toast.LENGTH_SHORT).show();

            String register_user_id = databaseReference.push().getKey();
            FirebaseRegisterUser registerUser = new FirebaseRegisterUser(register_user_id, userPhone, userName, userFName, userLName,
                    userEmail, userPassword, userQualification, userOccupation);

            assert register_user_id != null;
            databaseReference.child(register_user_id).setValue(registerUser);
            Toast.makeText(this, "Register new user successfully", Toast.LENGTH_SHORT).show();
        }


    }


}
