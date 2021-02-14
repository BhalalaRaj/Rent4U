package com.example.dell.rent4u;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphImageButton;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    NeumorphImageButton btn_car, btn_bike, btn_truck, btn_miniTruck, btn_bus, btn_miniBus;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_bike = findViewById(R.id.bike);
        btn_car  = findViewById(R.id.car);
        btn_bus = findViewById(R.id.bus);
        btn_miniBus = findViewById(R.id.minibus);
        btn_miniTruck = findViewById(R.id.minitruck);
        btn_truck = findViewById(R.id.truck);

        btn_bike.setOnClickListener(this);
        btn_car.setOnClickListener(this);
        btn_bus.setOnClickListener(this);
        btn_miniBus.setOnClickListener(this);
        btn_truck.setOnClickListener(this);
        btn_miniTruck.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        userId = mAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.truck : {
                getTruck();
                break;
            }

            case R.id.bus: {
                getBus();
                break;
            }

            case R.id.car: {
                getCar();
                break;
            }

            default: {
                onClick(v);
            }
        }
    }

    private void getCar() {
    }

    private void getBus() {
    }

    private void getTruck() {

    }
}
