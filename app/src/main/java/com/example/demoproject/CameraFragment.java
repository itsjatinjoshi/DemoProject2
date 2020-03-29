package com.example.demoproject;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {

    FrameLayout frameLayout;
    ImageView ivGallary;
    Button btnCamera;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    ConstraintLayout constraintLayout;

    CardView cardview;



    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivGallary = view.findViewById(R.id.ivGallary);
        btnCamera = view.findViewById(R.id.btnCamera);
        frameLayout = view.findViewById(R.id.frameLayout);
        constraintLayout = view.findViewById(R.id.cameraFragment);
        cardview = view.findViewById(R.id.cardview);

        ivGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectDocuments();
                cardview.setVisibility(View.GONE);
               // cardview.setVisibility(View.GONE);
            }
        });


    }

    private void selectDocuments() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select for PDF"), 1);
        Toast.makeText(getContext(), "SELECTED FILE 1", Toast.LENGTH_LONG).show();
        Log.d("[CAMERAFRAGMENT]", "to check");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("[CAMERAFRAGMENT]", "to check 1");

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String pdfPath = uri.getPath();
            String filename = uri.getLastPathSegment();
            File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CustomFolder/" + filename);

            Log.d("[CAMERAFRAGMENT]", "URI" + pdfPath);
            Log.d("[CAMERAFRAGMENT]", "destination" + destination);
            Log.d("[CAMERAFRAGMENT]", "filename" + filename);
            Log.d("[CAMERAFRAGMENT]", "REAL URI" + uri);

            UploadDoc fragment = new UploadDoc();
            Bundle args = new Bundle();
            args.putString("pdfPath", String.valueOf(uri));
            fragment.setArguments(args);

            CameraFragment cameraFragment = new CameraFragment();
            UploadDoc uploadDoc = new UploadDoc();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(cameraFragment);
            fragmentTransaction.show(uploadDoc);
            getFragmentManager().beginTransaction().add(R.id.cameraFragment, fragment).commit();


        }
    }


}
