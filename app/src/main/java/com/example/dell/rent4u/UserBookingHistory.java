package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserBookingHistory extends AppCompatActivity {


    RecyclerView rv_history;
    FirebaseRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_history);

        rv_history = findViewById(R.id.rv_history);
        configureRecyclerView();

    }


    private void configureRecyclerView() {
        final DatabaseReference vehicleList = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<VehicleHistoryDataClass> options = new FirebaseRecyclerOptions.Builder<VehicleHistoryDataClass>()
                .setQuery(vehicleList.child("History").orderByChild("UserUId").equalTo(FirebaseAuth.getInstance().getUid()), VehicleHistoryDataClass.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<VehicleHistoryDataClass, VehicleHistory>(options) {
            @NonNull
            @Override
            public VehicleHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.on_rent_item, parent, false);
                return new VehicleHistory(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull VehicleHistory viewHolder, int i, @NonNull VehicleHistoryDataClass dataClass) {
                //makeText(ViewVehicle.this, vehicleHi.getRent(), LENGTH_SHORT).show();

                StorageReference mStorageRef = FirebaseStorage.getInstance().
                        getReferenceFromUrl(dataClass.getVehicleImage());

                GlideApp.with(UserBookingHistory.this).load(mStorageRef)
                        .into(viewHolder.image);

                viewHolder.bookingDate.setText(dataClass.getBookingDate());
                viewHolder.riderName.setText(dataClass.getCompanyName());
                viewHolder.row_vehicleName.setText(dataClass.getVehicleName() + " | " + dataClass.getVehicleNumberPlate());
                viewHolder.destination.setText(dataClass.getDestination());

            }

        };

        adapter.notifyDataSetChanged();
        adapter.startListening();
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        rv_history.setAdapter(adapter);

    }
}