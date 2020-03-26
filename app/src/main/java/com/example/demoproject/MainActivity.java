package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import FireBaseObjects.Home;
import utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {
    static final String LOG_TAG = MainActivity.class.getSimpleName();

    TextView tvWelcome;
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MAINACTIVITY", "IN main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("userName", MODE_PRIVATE);
        String userfirstname = sharedPreferences.getString("userfirstname", "");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.METHOD, "Signin");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Log.d(LOG_TAG, "FIREBASE AUTH MAIN" +firebaseAuth );
        Log.d(LOG_TAG, "FIREBASE USER MAIN " +firebaseUser );

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome.setText("Welcome " + userfirstname);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "SIGNOUT ");
                FirebaseAuth.getInstance().signOut();
                PreferenceUtils.savePassword(null, getApplicationContext());
                PreferenceUtils.saveUsername(null, getApplicationContext());
                Intent intent = new Intent(MainActivity.this, LoginUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                Log.d(LOG_TAG, "SIGNOUT 1 ");
                finish();
            }
        });
    }

}
