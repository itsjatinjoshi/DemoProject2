package com.example.demoproject;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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

import FireBaseObjects.UploadDocumentsToFirebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDoc extends Fragment {

    EditText etFilePath;
    Button btnUpload, btnUploadMore;
    TextView tvSuccessMsg;

    StorageReference storageReference;
    DatabaseReference databaseReference;


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

        final String value = getArguments().getString("pdfPath");

        Log.d("[UPLOADDOC]", value);


        btnUpload = view.findViewById(R.id.btnUpload);
        btnUploadMore = view.findViewById(R.id.btnUploadMore);

        etFilePath = view.findViewById(R.id.etFilePath);
        tvSuccessMsg = view.findViewById(R.id.tvSuccessMsg);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("upload_documents");
        clearEditTextField(false);
        etFilePath.setText(value);

        final Uri myUri = Uri.parse(value);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPDFFile(myUri);
                clearEditTextField(true);
            }
        });


    }


    private void uploadPDFFile(Uri data) {
        if (etFilePath.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please attach file", Toast.LENGTH_SHORT).show();
        } else {
            final String editField = etFilePath.getText().toString();
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("File Uploading...");
            progressDialog.setMessage("Please wait");
            progressDialog.show();

            StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis() + ".pdf");
            reference.putFile(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while ((!uri.isComplete())) ;
                            Uri url = uri.getResult();
                            UploadDocumentsToFirebase documents = new UploadDocumentsToFirebase(editField, url.toString());
                            databaseReference.child(databaseReference.push().getKey()).setValue(documents);
                            Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("UPLOADING : " + (int) progress + "%");
                }
            });
        }
    }

    public void clearEditTextField(boolean clear) {

        if (clear) {
            etFilePath.setText("");
        }

    }


}
