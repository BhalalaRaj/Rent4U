package com.example.dell.rent4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import soup.neumorphism.NeumorphButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {

    EditText username, usermobile, useremail, userpassword, useraddress, usercity, usercity_pincode;
    NeumorphButton submit;
    FirebaseAuth mAuth;
    FirebaseUser userId;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        username = findViewById(R.id.username);
        usermobile = findViewById(R.id.usermobile);
        useremail = findViewById(R.id.useremail);
        userpassword = findViewById(R.id.userpassword);
        useraddress = findViewById(R.id.useraddress);
        usercity = findViewById(R.id.usercity);
        usercity_pincode = findViewById(R.id.usercity_pincode);

        submit = findViewById(R.id.BookNow);
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(v -> {
            if(validateData()){
                createAccount(useremail.getText().toString().trim(), userpassword.getText().toString().trim());
            }
        });
    }

    private boolean validateData() {
        if(username.getText().toString().trim().isEmpty()){
            toast("User Name is empty!");
            return false;
        }
        if(usermobile.getText().toString().trim().isEmpty()){
            toast("User Mobile is empty!");
            return false;
        }
        if(useremail.getText().toString().trim().isEmpty()){
            toast("User Email is empty!");
            return false;
        }
        if(userpassword.getText().toString().trim().isEmpty()){
            toast("User Password is empty!");
            return false;
        }
        if(useraddress.getText().toString().trim().isEmpty()){
            toast("User Address is empty!");
            return false;
        }
        if(usercity.getText().toString().trim().isEmpty()){
            toast("User city is empty!");
            return false;
        }
        if(usercity_pincode.getText().toString().trim().isEmpty()){
            toast("City pin is empty!");
            return false;
        }
        return true;
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void createAccount(String useremail, String userpassword) {

        mAuth.createUserWithEmailAndPassword(useremail, userpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userId's information
                            userId = mAuth.getCurrentUser();
                            Map<String, String> data = new HashMap<>();
                            data.put("Name", username.getText().toString().trim());
                            data.put("Mobile_no", usermobile.getText().toString().trim());
                            data.put("Email", useremail);
                            data.put("Password", userpassword);
                            data.put("Address", useraddress.getText().toString().trim());
                            data.put("City", usercity.getText().toString().trim());
                            data.put("City_PinCode", usercity_pincode.getText().toString().trim());
                            data.put("License", "false");

                            writeUser(data);

                            Toast.makeText(UserRegistration.this, "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the userId.
                            Toast.makeText(UserRegistration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void writeUser(Map<String, String> data) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer").child(userId.getUid());
        mDatabase.setValue(data);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId.getUid());
        Map<String, String> a = new HashMap<>();
        a.put(userId.getUid(), "Customer");
        mDatabase.setValue(a);
        startActivity(new Intent(UserRegistration.this, Login.class));
        Toast.makeText(UserRegistration.this, "Your data Successfully Registered now you can Login", Toast.LENGTH_LONG).show();
        finish();
    }
}
