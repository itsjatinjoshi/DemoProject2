package com.example.demoproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import utils.PreferenceUtils;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    TextView tvWelcome;
    Button btnLogout;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userName", MODE_PRIVATE);
        String userfirstname = sharedPreferences.getString("userfirstname", "");
        String occupation = sharedPreferences.getString("occupation", "");
        String qualification = sharedPreferences.getString("qualification", "");

        Log.d("HOMEFRAGMENT USERNAME ", userfirstname);
        Log.d("HOMEFRAGMENT_OCCUPATION", occupation);
        Log.d("HOMEFRAGMENT_QUALIFI", qualification);

        tvWelcome = view.findViewById(R.id.tvWelcome);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvWelcome.setText("Welcome " + userfirstname);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                PreferenceUtils.savePassword(null, getContext());
                PreferenceUtils.saveUsername(null, getContext());
                Intent intent = new Intent(getActivity(), LoginUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

    }
}
