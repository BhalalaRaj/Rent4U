package com.example.dell.rent4u;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//

public class ViewVehicle extends AppCompatActivity {

    FloatingActionButton fab_addVehicle;

    RecyclerView rv_vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);

        fab_addVehicle = findViewById(R.id.fab_addVehicle);
        rv_vehicleList = findViewById(R.id.rv_vehicleList);

        rv_vehicleList.setLayoutManager(new LinearLayoutManager(this));


        final DatabaseReference vehicleList = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<VehicleDataClass> options = new FirebaseRecyclerOptions.Builder<VehicleDataClass>()
                .setQuery(vehicleList.child("Vehicles"), VehicleDataClass.class)
                .build();

        FirebaseRecyclerAdapter<VehicleDataClass, VehicleViewHolder> adapter = new FirebaseRecyclerAdapter<VehicleDataClass, VehicleViewHolder>(options) {
            @NonNull
            @Override
            public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row, parent, false);
                return new VehicleViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull VehicleViewHolder vehicleViewHolder, int i, @NonNull VehicleDataClass vehicleDataClass) {
                Toast.makeText(ViewVehicle.this, vehicleDataClass.getRent(), Toast.LENGTH_SHORT).show();

                StorageReference mStorageRef = FirebaseStorage.getInstance().
                        getReferenceFromUrl(vehicleDataClass.getSide());

                GlideApp.with(ViewVehicle.this).load(mStorageRef)
                        .into(vehicleViewHolder.image);


                vehicleViewHolder.vehicleName.setText(vehicleDataClass.getModelName() + " | " + vehicleDataClass.getNumberPlate());
                vehicleViewHolder.seatingCapacity.setText("Seating: " + vehicleDataClass.getSeating());
                vehicleViewHolder.rentPerKm.setText("Rent: " + vehicleDataClass.getRent() + "/km");
                vehicleViewHolder.vehicleType.setText("Type: " + vehicleDataClass.getVehicleType());
            }
        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        rv_vehicleList.setAdapter(adapter);

        fab_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewVehicle.this, Vehicle_detail.class));
            }
        });

    }
}


class VehicleViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    TextView vehicleName, seatingCapacity, vehicleType, rentPerKm;

    public VehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.row_vehicle_image);
        vehicleName = itemView.findViewById(R.id.row_vehicleName);
        seatingCapacity = itemView.findViewById(R.id.row_seatingCapacity);
        vehicleType = itemView.findViewById(R.id.row_vehicleType);
        rentPerKm = itemView.findViewById(R.id.row_vehicleRent);
    }
}
//
//https://medium.com/@egemenhamutcu/displaying-images-from-firebase-storage-using-glide-for-kotlin-projects-3e4950f6c103
