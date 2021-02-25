package com.example.dell.rent4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphImageButton;

public class UserDashboard extends AppCompatActivity implements View.OnClickListener {

    NeumorphImageButton btn_car, btn_bike, btn_truck, btn_miniTruck, btn_bus, btn_miniBus;
    FirebaseAuth mAuth;
    TextView tv_userName;
    DatabaseReference mDatabase;
    FirebaseUser userId;
    public int counter = 0;

    static UserDataClass userDataClass;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout: {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.email), null);
                editor.putString(getString(R.string.password), null);
                editor.apply();
                mAuth.signOut();
                startActivity(new Intent(UserDashboard.this, Login.class));
                finish();
                break;
            }
            default: {
                onOptionsItemSelected(item);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tv_userName = findViewById(R.id.tv_userName);

        btn_bike = findViewById(R.id.bike);
        btn_car = findViewById(R.id.car);
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");
        mDatabase.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDataClass = new UserDataClass(snapshot.child("Address").getValue().toString(),
                        snapshot.child("City").getValue().toString(),
                        snapshot.child("City_PinCode").getValue().toString(),
                        snapshot.child("Email").getValue().toString(),
                        snapshot.child("License").getValue().toString(),
                        snapshot.child("Mobile_no").getValue().toString(),
                        snapshot.child("Name").getValue().toString(),
                        snapshot.child("Password").getValue().toString());
                tv_userName.setText("Welcome " + userDataClass.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.truck: {
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
            case R.id.bike: {
                getbike();
                break;
            }

            case R.id.minibus: {
                getminibus();
                break;
            }
            case R.id.minitruck: {
                getminitruck();
                break;
            }

            default: {
                onClick(v);
            }
        }
    }

    private void getCar() {
        startActivity(new Intent(UserDashboard.this, DisplayItem.class)
                .putExtra("VehicleType", "Car"));
    }

    private void getbike() {
        startActivity(new Intent(UserDashboard.this, DisplayItem.class)
                .putExtra("VehicleType", "Bike"));
    }

    private void getBus() {
        startActivity(new Intent(UserDashboard.this, DisplayItem.class)
                .putExtra("VehicleType", "Bus"));
    }

    private void getminibus() {
        startActivity(new Intent(UserDashboard.this, DisplayItem.class)
                .putExtra("VehicleType", "MiniBus"));
    }

    private void getminitruck() {
        startActivity(new Intent(UserDashboard.this, DisplayItem.class)
                .putExtra("VehicleType", "MiniTruck"));
    }

    private void getTruck() {
        startActivity(new Intent(UserDashboard.this, DisplayItem.class)
                .putExtra("VehicleType", "Truck"));

    }
}