package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphButton;

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

public class ProviderDashboard extends AppCompatActivity {


    NeumorphButton btn_viewAllVehicle, btn_viewHistory;
    TextView tv_noVehicleOnRent, welcome_provider;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    static public ProviderDataClass PROVIDER_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_dashboard);

        btn_viewAllVehicle = findViewById(R.id.btn_viewAllVehicle);
        btn_viewHistory = findViewById(R.id.btn_viewHistory);
        tv_noVehicleOnRent = findViewById(R.id.tv_noVehicleOnRent);
        welcome_provider = findViewById(R.id.welcome_provider);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rental_Provider");
        mDatabase.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PROVIDER_DATA = new ProviderDataClass(snapshot.child("Address").getValue().toString(),
                        snapshot.child("City").getValue().toString(),
                        snapshot.child("City_PinCode").getValue().toString(),
                        snapshot.child("Company_Name").getValue().toString(),
                        snapshot.child("Contact No:").getValue().toString(),
                        snapshot.child("Email").getValue().toString(),
                        snapshot.child("License").getValue().toString(),
                        snapshot.child("Owner_Name").getValue().toString(),
                        snapshot.child("Password").getValue().toString(),
                        mAuth.getCurrentUser().getUid()
                );
                welcome_provider.setText("Welcome " + PROVIDER_DATA.getOwner_Name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_viewAllVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProviderDashboard.this, ViewVehicle.class));
            }
        });

        btn_viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ProviderDashboard.this, ViewVehicle.class));
            }
        });

    }


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
                startActivity(new Intent(ProviderDashboard.this, Login.class));
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
}
