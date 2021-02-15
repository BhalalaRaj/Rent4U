//package com.example.dell.rent4u;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Query;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//class VehicleDataAdapter extends FirebaseRecyclerAdapter<VehicleDataClass, VehicleDataAdapter.VehicleViewHolder> {
//
//    Context context;
//
////    @Override
////    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position, @NonNull List<Object> payloads) {
////        VehicleDataClass d = (VehicleDataClass) payloads.get(position);
////        Glide.with(context).load(d.getFront()).into(holder.image);
////        holder.vehicleName.setText(d.getModelName() + "|" + d.getNumberPlate());
////        holder.rentPerKm.setText("Rent" + d.getRent());
////        holder.vehicleType.setText("Type: " + d.get());
////        holder.seatingCapacity.setText("Seating; " + d.getSeating());
////    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
//    }
//
//    @Override
//    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row, parent, false);
//        return new VehicleViewHolder(view);
//    }
//
//    public VehicleDataAdapter(Class<VehicleDataClass> modelClass, int modelLayout, Class<VehicleViewHolder> viewHolderClass, Query ref) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//
//    }
//
//    public VehicleDataAdapter(Class<VehicleDataClass> modelClass, int modelLayout, Class<VehicleViewHolder> viewHolderClass, DatabaseReference ref) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//    }
//
//    @Override
//    protected void populateViewHolder(VehicleViewHolder vehicleViewHolder, VehicleDataClass vehicleDataClass, int i) {
//
//    }
//
//    class VehicleViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView image;
//        TextView vehicleName, seatingCapacity, vehicleType, rentPerKm;
//
//        public VehicleViewHolder(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.row_vehicle_image);
//            vehicleName = itemView.findViewById(R.id.row_vehicleName);
//            seatingCapacity = itemView.findViewById(R.id.row_seatingCapacity);
//            vehicleType = itemView.findViewById(R.id.row_vehicleType);
//            rentPerKm = itemView.findViewById(R.id.row_vehicleRent);
//        }
//    }
//
//}
