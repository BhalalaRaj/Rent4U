package com.example.dell.rent4u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class booking extends AppCompatActivity {

    TextView name,tvRent,seating;
    EditText DateFrom,DateTo;
    Button BookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        name = findViewById(R.id.name);
        tvRent = findViewById(R.id.tvRent);
        seating = findViewById(R.id.seating);

        DateFrom = findViewById(R.id.DateFrom);
        DateTo = findViewById(R.id.DateTo);
        BookNow = findViewById(R.id.BookNow);

    }
}