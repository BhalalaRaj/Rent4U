package com.example.dell.rent4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphButton;

public class Login extends AppCompatActivity {

    TextView user, provider;
    NeumorphButton login;
    EditText email, password;

    // Firebase
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.user);
        provider = findViewById(R.id.provider);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            if (validateData()) {
                startLogin(email.getText().toString().trim(), password.getText().toString().trim());
            }
        });

        user.setOnClickListener(v -> startActivity(new Intent(Login.this, UserRegistration.class)));

        provider.setOnClickListener(v -> startActivity(new Intent(Login.this, ProviderRegistration.class)));

    }

    private boolean validateData() {
        if (email.getText().toString().trim().isEmpty()) {
            toast("Email is empty!");
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            toast("Password is empty!");
            return false;
        }
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void startLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storeDataForLogin(email, password);
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userType = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();
                        if (userType.equals("Customer")) {
                            startActivity(new Intent(Login.this, UserDashboard.class));
                            finish();
                        } else {
                            startActivity(new Intent(Login.this, ProviderDashboard.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            } else {
                Toast.makeText(Login.this, "Invalid credentials :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void storeDataForLogin(String email, String password) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.email), email);
        editor.putString(getString(R.string.password), password);
        editor.apply();
        Log.e("DATA STORED", email + " " + password);
    }
}
