package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class VehicleBooking extends AppCompatActivity {

    TextView name, tvRent, seating, tv_owner_name, tv_company_name, tv_Condition;
    EditText DateFrom, DateTo, Source, Destination;
    DatePickerDialog datepicker;
    Button BookNow;
    String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    DatabaseReference databaseReference;

    FirebaseAuth auth;

    VehicleDataClass vehicleDataClass;

    String userMobileNumber;
    String providerMobileNumber;

    String dateFrom;
    String dateTo;

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

        auth = FirebaseAuth.getInstance();

        Source.setText(UserDashboard.userDataClass.getAddress());

        userMobileNumber = UserDashboard.userDataClass.getMobile_no();

        Intent intent = getIntent();

        Bundle itemBundle = intent.getExtras().getParcelable("VehicleDetail");
        vehicleDataClass = itemBundle.getParcelable("VehicleDetail");
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
                                dateFrom = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
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
                                dateTo = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                DateTo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });
        BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookVehicle();
                sendSMSMessage();
            }
        });
    }

    private void bookVehicle() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("History");
        Map bookingData = new HashMap<String, String>();
        bookingData.put("UserName", UserDashboard.userDataClass.getName());
        bookingData.put("UserAddress", UserDashboard.userDataClass.getAddress());
        bookingData.put("UserUId", auth.getUid());
        bookingData.put("UserContactNo", UserDashboard.userDataClass.getMobile_no());
        bookingData.put("VehicleName", vehicleDataClass.getModelName());
        bookingData.put("VehicleNumberPlate", vehicleDataClass.getNumberPlate());
        bookingData.put("ProviderUid", vehicleDataClass.getOwner());
        bookingData.put("VehicleId", vehicleDataClass.getVehicleId());
        bookingData.put("Source", Source.getText().toString());
        bookingData.put("Destination", Destination.getText().toString());
        bookingData.put("BookingDate", dateFrom);
        bookingData.put("ReturnDate", dateTo);
        bookingData.put("VehicleImage", vehicleDataClass.getFront());

        String bookingId = databaseReference.push().getKey();
        databaseReference.child(bookingId).setValue(bookingData);

        Toast.makeText(this, "Booking Done", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, UserDashboard.class));
        finish();

    }

    public void sendSMSMessage() {
        //phoneNo = txtphoneNo.getText().toString();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SendSms();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    private void SendSms() {
        message = "Booking Request for " + vehicleDataClass.getModelName() + " : " + vehicleDataClass.getNumberPlate() +
                " Customer Name: " + UserDashboard.userDataClass.getName() + " From: " + dateFrom + " To: " + dateTo +
                "Contact No of Customer: " + UserDashboard.userDataClass.getMobile_no() +
                " Source: " + Source.getText() + " Destination: " + Destination.getText();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(providerMobileNumber, null, message, null, null);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SendSms();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            default: {
                return;
            }
        }

    }

}