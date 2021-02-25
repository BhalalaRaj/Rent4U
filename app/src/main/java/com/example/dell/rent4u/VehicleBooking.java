package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleBooking extends AppCompatActivity {

    TextView name, tvRent, seating, tv_owner_name, tv_company_name, tv_Condition;
    EditText DateFrom, DateTo, Source, Destination;
    DatePickerDialog datepicker;
    Button BookNow;

    DatabaseReference databaseReference;

    String userMobileNumber;
    String providerMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        name = findViewById(R.id.name);
        tvRent = findViewById(R.id.tvRent);
        seating = findViewById(R.id.seating);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_owner_name = findViewById(R.id.tv_owner_name);
        tv_Condition = findViewById(R.id.tv_Condition);

        DateFrom = findViewById(R.id.DateFrom);
        DateTo = findViewById(R.id.DateTo);
        Source = findViewById(R.id.Source);
        Destination = findViewById(R.id.Destination);
        BookNow = findViewById(R.id.BookNow);

        Source.setText(UserDashboard.userDataClass.getAddress());

        userMobileNumber = UserDashboard.userDataClass.getMobile_no();

        Intent intent = getIntent();

        Bundle itemBundle = intent.getExtras().getParcelable("VehicleDetail");
        VehicleDataClass vehicleDataClass = itemBundle.getParcelable("VehicleDetail");
        ArrayList<String> imageUrl = new ArrayList<>();
        imageUrl.add(vehicleDataClass.getFront());
        imageUrl.add(vehicleDataClass.getInterior());
        imageUrl.add(vehicleDataClass.getSide());

        SliderView sliderView = findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapter(this, imageUrl));

        name.setText("Model Name: " + vehicleDataClass.getModelName());
        tvRent.setText("Rent: " + vehicleDataClass.getRent() + " / km");
        seating.setText("Seating: " + vehicleDataClass.getSeating());
        tv_Condition.setText("Condtion: " + vehicleDataClass.getCondition());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rental_Provider").child(vehicleDataClass.getOwner());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_owner_name.setText("Owner Name: " + snapshot.child("Owner_Name").getValue());
                tv_company_name.setText("Company Name: " + snapshot.child("Company_Name").getValue());
                providerMobileNumber = snapshot.child("Contact No:").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(VehicleBooking.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DateFrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        DateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(VehicleBooking.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DateTo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        BookNow.setOnClickListener(v -> {
            
        });
    }
}