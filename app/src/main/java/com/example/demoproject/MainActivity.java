package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import BackgroundTasks.RegisterAPI;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etFirstName, etLastName, etMail, etPass, etConfirmPass, etPhone;
    Spinner spinnerQualification, spinnerOccupation;
    Button btnRegister;

    String userName, userFName, userLName, userEmail, userPassword, userQualification, userOccupation;
    int userPhone;
    String[] occupation = {"--", "Student", "Teacher", "Profesional"};
    String[] qualification = {"--", "Graduation", "Master", "Diploma", "School"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMail = findViewById(R.id.etMail);
        etPass = findViewById(R.id.etPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        etPhone = findViewById(R.id.etPhone);
        spinnerQualification = findViewById(R.id.spinnerQualification);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> qualific = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,qualification);
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
        ArrayAdapter<String> occuptn = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,occupation);
        occuptn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQualification.setAdapter(occuptn);

        spinnerQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        } else if (spinnerQualification.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select Qualification Option", Toast.LENGTH_SHORT).show();
        } else if (spinnerOccupation.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select Qualification Option", Toast.LENGTH_SHORT).show();
        } else {
            userName = etUsername.getText().toString().trim();
            userFName = etFirstName.getText().toString().trim();
            userLName = etLastName.getText().toString().trim();
            userEmail = etMail.getText().toString().trim();
            userPassword = etPass.getText().toString().trim();
            userPhone = Integer.parseInt(etPhone.getText().toString().trim());
            userQualification = spinnerQualification.getTag().toString();
            userOccupation = spinnerOccupation.getTag().toString();

            String method = "Register User";
            RegisterAPI registerAPI = new RegisterAPI(this);
            registerAPI.execute(method, userName, userFName, userLName, userEmail, userPassword, String.valueOf(userPhone), userQualification, userOccupation);
            finish();

        }

    }
}
