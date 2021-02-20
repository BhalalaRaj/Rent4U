package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends AppCompatActivity {

    final int SCREEN_TIMER = 3000;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );


        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), 0);
        String email = sharedPref.getString(getString(R.string.email), "null");
        String password = sharedPref.getString(getString(R.string.password), "null");

        Log.e("EMAIL", email.toString());
        Log.e("PASSWORD", password.toString());

        if(email.equals("null") || password.equals("null")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash_Screen.this, Login.class));
                    finish();
                }
            }, SCREEN_TIMER);
        } else {
            startLogin(email, password);
        }


    }

    public void startLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String userType = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();
                            if (userType.equals("Customer")) {
//                                Toast.makeText(Splash_Screen.this, userType, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Splash_Screen.this, UserDashboard.class));
                                finish();
                            } else {
//                                Toast.makeText(Splash_Screen.this, userType, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Splash_Screen.this, ProviderDashboard.class));
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    Toast.makeText(Splash_Screen.this, "Invalid credentials :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
