package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(email.getText().toString().trim().equals("") && password.getText().toString().trim().equals(""))) {
                    startLogin(email.getText().toString().trim(), password.getText().toString().trim());
                } else {
                    Toast.makeText(Login.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }

        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, UserRegistration.class));
            }
        });

        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ProviderRegistration.class));
            }
        });

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
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();
                            if (userType.equals("Customer")) {
                                Toast.makeText(Login.this, userType, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, Dashboard.class));
                            } else {
                                Toast.makeText(Login.this, userType, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, ProviderDashboard.class));
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


                } else {
                    Toast.makeText(Login.this, "Invalid credentials :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
