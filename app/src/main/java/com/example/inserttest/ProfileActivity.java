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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout,displayplaces,recommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+user.getEmail());

        displayplaces = findViewById(R.id.displayplaces);
        displayplaces.setOnClickListener(this);
        recommendation = (Button) findViewById(R.id.recommendation);
        recommendation.setOnClickListener(this);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == displayplaces){
            Intent i = new Intent(ProfileActivity.this,PostsListActivity.class);
            startActivity(i);
        }
        else if(v == recommendation){
            Intent i = new Intent(ProfileActivity.this,Recommendation.class);
            startActivity(i);
        }
        else if(v == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}

