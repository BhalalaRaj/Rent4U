package com.example.dell.rent4u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Vehicle_detail extends AppCompatActivity {

    EditText Vehicle_type,Model_name,city_ava,condition_veh;
    Button Front_upload,Interior_upload,Side_upload,submit;
    ImageView Front_img,Interior_img,Side_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        Vehicle_type = findViewById(R.id.Vehicle_type);
        Model_name = findViewById(R.id.Model_name);
        city_ava = findViewById(R.id.city_ava);
        condition_veh = findViewById(R.id.condition_veh);

        Front_upload = findViewById(R.id.Front_upload);
        Interior_upload = findViewById(R.id.Interior_upload);
        Side_upload = findViewById(R.id.Side_upload);
        submit = findViewById(R.id.submit);

        Front_img = findViewById(R.id.Front_img);
        Interior_img = findViewById(R.id.Interior_img);
        Side_img = findViewById(R.id.Side_img);
    }
}