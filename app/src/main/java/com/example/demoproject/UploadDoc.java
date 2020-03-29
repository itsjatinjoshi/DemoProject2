package com.example.demoproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import FireBaseObjects.UploadDocumentsToFirebase;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDoc extends Fragment {

    EditText etFilePath;
    Button btnUpload, btnUploadMore;
    TextView tvSuccessMsg;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    ConstraintLayout constrainLayout;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    String editField, value;
    Uri myUri;

    public UploadDoc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_doc_to_firebase, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        value = getArguments().getString("pdfPath");

        Log.d("[UPLOADDOC]", value);


        btnUpload = view.findViewById(R.id.btnUpload);
        btnUploadMore = view.findViewById(R.id.btnUploadMore);

        etFilePath = view.findViewById(R.id.etFilePath);
        tvSuccessMsg = view.findViewById(R.id.tvSuccessMsg);
        constrainLayout = view.findViewById(R.id.constrainLayout);
        constrainLayout.setVisibility(View.GONE);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("upload_documents");

        etFilePath.setText(value);

        btnUploadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDocuments();
            }
        });

        myUri = Uri.parse(value);
        Log.d("UPLOAD_URI", "final Uri myUri" + myUri);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (etFilePath.getText().toString().isEmpty()) {
//                    Toast.makeText(getContext(), "Please attach file", Toast.LENGTH_SHORT).show();
//                    Log.d("[UPLOAD_DOCUMENT]", "CHECKPOINT 2");
//                } else {
                    uploadPDFFile(myUri);
                    etFilePath.setText("");
             //   }
            }
        });


    }


    private void uploadPDFFile(Uri data) {
        Log.d("[UPLOAD_DOCUMENT]", "CHECKPOINT 1");

        long tsLong = System.currentTimeMillis() / 1000;
        final String ts = Long.toString(tsLong);


        //Todo - make sure edit text check the last extension as .pdf, docs, txt etc
        Log.d("[UPLOAD_DOCUMENT]", "CHECKPOINT 3");
        editField = etFilePath.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("File Uploading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis());
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while ((!uri.isComplete())) ;
                        Uri url = uri.getResult();
                        UploadDocumentsToFirebase documents = new UploadDocumentsToFirebase(editField, url.toString(), ts);
                        databaseReference.child(databaseReference.push().getKey()).setValue(documents);
                        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        constrainLayout.setVisibility(View.VISIBLE);
                        etFilePath.setText("");

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("UPLOADING : " + (int) progress + "%");
                constrainLayout.setVisibility(View.VISIBLE);
                Log.d("[UPLOAD_DOCUMENT]", "CHECKPOINT 4");
            }
        });

    }

    private void selectDocuments() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select for PDF"), 1);
        Toast.makeText(getContext(), "SELECTED FILE 1", Toast.LENGTH_LONG).show();
        Log.d("[UPLOAD_DOCUMENT]", "to check");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("[UPLOAD_DOCUMENT]", "to check 1");

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            Log.d("[UPLOAD_DOCUMENT]", "REAL URI" + uri);


            Bundle args = new Bundle();
            args.putString("pdfPath", String.valueOf(uri));

            UploadDoc fragment = new UploadDoc();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment.setArguments(args);
            ft.add(R.id.uploadDoc, fragment).commit();
        }
    }

}
