package com.example.dell.rent4u;

import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ProviderDashboard extends AppCompatActivity {


    NeumorphButton btn_viewAllVehicle, btn_viewHistory;
    TextView tv_noVehicleOnRent;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    static public ProviderDataClass PROVIDER_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_dashboard);

        btn_viewAllVehicle = findViewById(R.id.btn_viewAllVehicle);
        btn_viewHistory = findViewById(R.id.btn_viewHistory);
        tv_noVehicleOnRent = findViewById(R.id.tv_noVehicleOnRent);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            PROVIDER_DATA = new ProviderDataClass(user.getUid());
        }


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
}
