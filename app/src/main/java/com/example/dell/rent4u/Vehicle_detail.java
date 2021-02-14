package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Vehicle_detail extends AppCompatActivity {

    EditText Vehicle_type, Model_name, city_ava, condition_veh, seating_cap, rent;
    Button Front_upload, Interior_upload, Side_upload, submit;
    ImageView Front_img, Interior_img, Side_img;

    String VehicleId = "";

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Map<String, String> dataMap;

    int FRONT_IMAGE_CODE = 1;
    int SIDE_IMAGE_CODE = 2;
    int INTERIOR_IMAGE_CODE = 3;

    private Uri frontImagePath, sideImagePath, interiorImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        Vehicle_type = findViewById(R.id.Vehicle_type);
        Model_name = findViewById(R.id.Model_name);
        city_ava = findViewById(R.id.city_ava);
        condition_veh = findViewById(R.id.condition_veh);
        seating_cap = findViewById(R.id.edt_seatingCapacity);
        rent = findViewById(R.id.edt_rent);

        Front_upload = findViewById(R.id.Front_upload);
        Interior_upload = findViewById(R.id.Interior_upload);
        Side_upload = findViewById(R.id.Side_upload);
        submit = findViewById(R.id.submit);

        Front_img = findViewById(R.id.Front_img);
        Interior_img = findViewById(R.id.Interior_img);
        Side_img = findViewById(R.id.Side_img);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Front_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrontImage();
            }
        });

        Front_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrontImage();
            }
        });

        Interior_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInteriorImage();
            }
        });

        Side_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSideImage();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUniqueVehicleId();
                uploadFrontImage();
            }
        });


    }

    private void getUniqueVehicleId() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        VehicleId = databaseReference.push().getKey();
    }

    private void uploadVehicleData() {

        dataMap = new HashMap<>();
        dataMap.put("VehicleType", Vehicle_type.getText().toString().trim());
        dataMap.put("ModelName", Model_name.getText().toString().trim());
        dataMap.put("City", city_ava.getText().toString().trim());
        dataMap.put("Condition", condition_veh.getText().toString().trim());
        dataMap.put("Rent", rent.getText().toString().trim());
        dataMap.put("Seating", seating_cap.getText().toString().trim());
        dataMap.put("Owner", ProviderDashboard.PROVIDER_DATA.id);
        dataMap.put("Front", "/VehicleImages/" + VehicleId + "/front");
        dataMap.put("Side", "/VehicleImages/" + VehicleId + "/side");
        dataMap.put("Interior", "/VehicleImages/" + VehicleId + "/interior");
        databaseReference.child(VehicleId).setValue(dataMap);

    }

    private void selectSideImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                SIDE_IMAGE_CODE);

    }

    private void selectInteriorImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                INTERIOR_IMAGE_CODE);
    }

    private void selectFrontImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                FRONT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    frontImagePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), frontImagePath);
                        Front_img.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case 2: {
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    sideImagePath = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), sideImagePath);
                        Side_img.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case 3: {
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    interiorImagePath = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), interiorImagePath);
                        Interior_img.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            default: {
                break;
            }
        }

    }


    private void uploadFrontImage() {
        if (frontImagePath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Front Image...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("VehicleImages").child(VehicleId).child("front");

            // adding listeners on upload
            // or failure of image
            ref.putFile(frontImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle_detail.this, "FrontImage Uploaded!!", Toast.LENGTH_SHORT).show();
                    uploadSideImage();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle_detail.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadSideImage() {

        if (sideImagePath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Side Image...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("VehicleImages").child(VehicleId).child("side");

            // adding listeners on upload
            // or failure of image
            ref.putFile(sideImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle_detail.this, "Side Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    uploadInteriorImage();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle_detail.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadInteriorImage() {
        if (interiorImagePath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Interior Image...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("VehicleImages").child(VehicleId).child("interior");

            // adding listeners on upload
            // or failure of image
            ref.putFile(interiorImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle_detail.this, "Interior Uploaded!!", Toast.LENGTH_SHORT).show();
                    uploadVehicleData();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle_detail.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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