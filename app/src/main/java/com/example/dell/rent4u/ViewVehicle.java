package com.example.dell.rent4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewVehicle extends AppCompatActivity {

    FloatingActionButton fab_addVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);

        fab_addVehicle = findViewById(R.id.fab_addVehicle);

        fab_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewVehicle.this, Vehicle_detail.class));
            }
        });
    }
}