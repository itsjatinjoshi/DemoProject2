package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import FireBaseObjects.FirebaseRegisterUser;

public class RegisterUser extends AppCompatActivity {

    static final String LOG_TAG = RegisterUser.class.getSimpleName();

    EditText etUsername, etFirstName, etLastName, etMail, etPass, etConfirmPass, etPhone;
    Spinner spinnerQualification, spinnerOccupation;
    Button btnRegister;

    String userName, userFName, userLName, userEmail, userPassword, userQualification, userOccupation;
    int userPhone;
    String[] occupation = {"--", "Student", "Teacher", "Profesional"};
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
                Toast.makeText(getApplicationContext(), "On Register", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void registerUser() {

        if (etUsername.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter user name", Toast.LENGTH_SHORT).show();
        } else if (etFirstName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter First name", Toast.LENGTH_SHORT).show();
        } else if (etLastName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Last name", Toast.LENGTH_SHORT).show();
        } else if (etMail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Email Id", Toast.LENGTH_SHORT).show();
        } else if (etPass.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
        } else if (etConfirmPass.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Phone Number", Toast.LENGTH_SHORT).show();
        } else if (spinnerQualification.getSelectedItem().toString().trim().equals("--")) {
            Toast.makeText(getApplicationContext(), "Please Select Qualification Option", Toast.LENGTH_SHORT).show();
        } else if (spinnerOccupation.getSelectedItem().toString().trim().equals("--")) {
            Toast.makeText(getApplicationContext(), "Please Select Qualification Option", Toast.LENGTH_SHORT).show();
        } else {

            userName = etUsername.getText().toString().trim();
            Log.d(LOG_TAG, "[REGISTER USERNAMR ]" + userName);
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
            userQualification = String.valueOf(spinnerQualification.getSelectedItemPosition());
            Log.d(LOG_TAG, "[REGISTER QUALIFICATION ]" + userQualification);
            userOccupation = String.valueOf(spinnerOccupation.getSelectedItemPosition());
            Log.d(LOG_TAG, "[REGISTER OCCUPATION ]" + userOccupation);

//            String method = "Register User";
//            RegisterAPI registerAPI = new RegisterAPI(this);
//            registerAPI.execute(method, userName, userFName, userLName, userEmail, userPassword, String.valueOf(userPhone), userQualification, userOccupation);
//            finish();

            String register_user_id = databaseReference.push().getKey();
            FirebaseRegisterUser registerUser = new FirebaseRegisterUser(register_user_id, userPhone, userName, userFName, userLName,
                    userEmail, userPassword, userQualification, userOccupation);

            assert register_user_id != null;
            databaseReference.child(register_user_id).setValue(registerUser);
            Toast.makeText(this, "Register new user successfully", Toast.LENGTH_SHORT).show();
        }

    }
}
