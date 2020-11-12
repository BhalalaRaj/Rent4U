package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProviderRegistration extends AppCompatActivity {

    Button adharupload, licenseupload, Submit;
    ImageView licenseimage, adharimage;
    EditText Cname, Owner_name, provider_email, provider_password, provider_address, provider_city, provider_pincode, provider_contactno;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath1;
    private Uri filePath2;
    FirebaseAuth mAuth;
    FirebaseUser userId;
    DatabaseReference mDatabase;

    Map<String, String> data;
    // request code
    private final int PICK_IMAGE_REQUEST_ADHAR = 22;
    private final int PICK_IMAGE_REQUEST_LICENSE = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_registration);

        adharupload = findViewById(R.id.adharupload);
        licenseupload = findViewById(R.id.licenseupload);
        Submit = findViewById(R.id.submit);
        licenseimage = findViewById(R.id.licenseimage);
        adharimage = findViewById(R.id.adharimage);

        Cname = findViewById(R.id.provider_companyname);
        Owner_name = findViewById(R.id.provider_ownername);
        provider_email = findViewById(R.id.provider_email);
        provider_password = findViewById(R.id.provider_password);
        provider_address = findViewById(R.id.provider_address);
        provider_city = findViewById(R.id.provider_city);
        provider_contactno = findViewById(R.id.provider_contactno);
        provider_pincode = findViewById(R.id.provider_citypincode);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();

        adharupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectAdharImage();
            }
        });

        licenseupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectLicenseImage();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crateAcc(provider_email.getText().toString().trim(), provider_password.getText().toString().trim());
            }
        });

    }

    private void crateAcc(String providerEmail, String providerPassword) {

        mAuth.createUserWithEmailAndPassword(providerEmail, providerPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userId's information
                            userId = mAuth.getCurrentUser();
                            data = new HashMap<>();
                            data.put("Company_Name", Cname.getText().toString().trim());
                            data.put("Owner_Name", Owner_name.getText().toString().trim());
                            data.put("Email", providerEmail);
                            data.put("Password", providerPassword);
                            data.put("Address", provider_address.getText().toString().trim());
                            data.put("City", provider_city.getText().toString().trim());
                            data.put("Contact No:", provider_contactno.getText().toString().trim());
                            data.put("City_PinCode", provider_pincode.getText().toString().trim());
                            data.put("License", "false");
                            uploadImage1();

                            Toast.makeText(ProviderRegistration.this, "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the userId.
                            Toast.makeText(ProviderRegistration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void writeUser(Map<String, String> data) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rental_Provider").child(userId.getUid());
        mDatabase.setValue(data);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId.getUid());
        Map<String, String> a = new HashMap<>();
        a.put(userId.getUid(), "Rental_Provider");
        mDatabase.setValue(a);
        startActivity(new Intent(ProviderRegistration.this, Login.class));
        Toast.makeText(ProviderRegistration.this, "Your data Successfully Registered now you can Login", Toast.LENGTH_LONG).show();
        finish();
    }

    private void SelectAdharImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST_ADHAR);
    }

    private void SelectLicenseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST_LICENSE);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST_ADHAR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath1 = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                adharimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST_LICENSE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath2 = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                licenseimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage1() {
        if (filePath1 != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Adhar Card...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("Provider Images").child(userId.getUid() + "/" + "1");

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(ProviderRegistration.this, "Adhar Card Uploaded!!", Toast.LENGTH_SHORT).show();
                    uploadImage2();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(ProviderRegistration.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(
                        UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading " + (int) progress + "%");
                }
            });
        }
    }

    private void uploadImage2() {
        if (filePath2 != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading License...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("Provider Images").child(userId.getUid() + "/" + "2");

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(ProviderRegistration.this, "License Uploaded!!", Toast.LENGTH_SHORT).show();
                    writeUser(data);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(ProviderRegistration.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(
                        UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading " + (int) progress + "%");
                }
            });
        }
    }
}
