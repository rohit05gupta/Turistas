package com.example.inserttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class ProfileActivity extends ControlActivity{
    TextView textName, textEmail;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        super.onCreateDrawer();

        mAuth = FirebaseAuth.getInstance();
        textName = findViewById(R.id.textViewName);
        textEmail = findViewById(R.id.textViewEmail);
        FirebaseUser user = mAuth.getCurrentUser();
        textName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());


    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }
}

