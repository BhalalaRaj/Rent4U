package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ViewHistoryActivity extends AppCompatActivity {

    FirebaseRecyclerAdapter adapter;

    RecyclerView rv_vehicleHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);


        rv_vehicleHistory = findViewById(R.id.rv_vehicleHistory);

        final DatabaseReference vehicleList = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<VehicleHistoryDataClass> options = new FirebaseRecyclerOptions.Builder<VehicleHistoryDataClass>()
                .setQuery(vehicleList.child("History").orderByChild("ProviderUid").equalTo(ProviderDashboard.PROVIDER_DATA.getProviderId()), VehicleHistoryDataClass.class)
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

                GlideApp.with(ViewHistoryActivity.this).load(mStorageRef)
                        .into(viewHolder.image);

                viewHolder.bookingDate.setText(dataClass.getBookingDate());
                viewHolder.riderName.setText(dataClass.getUserName());
                viewHolder.row_vehicleName.setText(dataClass.getVehicleName() + " | " + dataClass.getVehicleNumberPlate());
                viewHolder.destination.setText(dataClass.getDestination());

                

            }
        };

        adapter.notifyDataSetChanged();
        adapter.startListening();
        rv_vehicleHistory.setLayoutManager(new LinearLayoutManager(this));
        rv_vehicleHistory.setAdapter(adapter);

    }
}



class VehicleHistory extends RecyclerView.ViewHolder {
    ImageView image;

    TextView riderName, destination, bookingDate, row_vehicleName;

    public VehicleHistory(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.row_vehicle_image);
        row_vehicleName = itemView.findViewById(R.id.row_vehicleName);
        riderName = itemView.findViewById(R.id.row_rider);
        destination = itemView.findViewById(R.id.destination);
        bookingDate = itemView.findViewById(R.id.booking_date);
    }
}