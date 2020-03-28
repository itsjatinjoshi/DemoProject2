package com.example.demoproject;

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
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tvUserName, tvOccupation, tvQualification;
    Button btnEditProfile;
    ImageView ivImage;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userName", MODE_PRIVATE);
        String userfirstname = sharedPreferences.getString("userfirstname", "");
        String occupation = sharedPreferences.getString("occupation", "");
        String qualification = sharedPreferences.getString("qualification", "");

        Log.d("PROFILE_USERNAME", userfirstname);
        Log.d("PROFILE_OCCUPATION", occupation);
        Log.d("PROFILE_QUALIFI", qualification);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvOccupation = view.findViewById(R.id.tvOccupation);
        tvQualification = view.findViewById(R.id.tvQualification);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        ivImage = view.findViewById(R.id.ivImage);

        tvUserName.setText(userfirstname);
        tvQualification.setText(qualification);
        tvOccupation.setText(occupation);

    }
}
