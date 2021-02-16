package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class DisplayItem extends AppCompatActivity {

    RecyclerView rv_DisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);
        rv_DisplayList = findViewById(R.id.rv_DisplayList);

        rv_DisplayList.setLayoutManager(new LinearLayoutManager(this));


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
                makeText(DisplayItem.this, vehicleDataClass.getRent(), LENGTH_SHORT).show();

                StorageReference mStorageRef = FirebaseStorage.getInstance().
                        getReferenceFromUrl(vehicleDataClass.getSide());

                GlideApp.with(DisplayItem.this).load(mStorageRef)
                        .into(vehicleViewHolder.image);


                vehicleViewHolder.vehicleName.setText(vehicleDataClass.getModelName() + " | " + vehicleDataClass.getNumberPlate());
                vehicleViewHolder.seatingCapacity.setText("Seating: " + vehicleDataClass.getSeating());
                vehicleViewHolder.rentPerKm.setText("Rent: " + vehicleDataClass.getRent() + "/km");
                vehicleViewHolder.vehicleType.setText("Type: " + vehicleDataClass.getVehicleType());
            }
        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        rv_DisplayList.setAdapter(adapter);
    }
}
class DisplayViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    TextView vehicleName, seatingCapacity, vehicleType, rentPerKm;

    public DisplayViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.row_vehicle_image);
        vehicleName = itemView.findViewById(R.id.row_vehicleName);
        seatingCapacity = itemView.findViewById(R.id.row_seatingCapacity);
        vehicleType = itemView.findViewById(R.id.row_vehicleType);
        rentPerKm = itemView.findViewById(R.id.row_vehicleRent);
    }
}