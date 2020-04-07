package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Review extends ControlActivity {
    private FirebaseAuth firebaseAuth;
    EditText ed1,ed2;
    RatingBar r1;
    DatabaseReference reff;
    ReviewData rd;
    Button b1;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        super.onCreateDrawer();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        ed1 = (EditText)findViewById(R.id.heading);
        ed2 = (EditText)findViewById(R.id.comment);
        r1 = (RatingBar)findViewById(R.id.ratingBar);
        b1 = (Button)findViewById(R.id.b1);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = user.getEmail();
        String specification = getIntent().getStringExtra("specification");
        String pname = getIntent().getStringExtra("pname");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String heading = ed1.getText().toString().trim();
                final String comment = ed2.getText().toString().trim();
                float rating = r1.getRating();

                reff = FirebaseDatabase.getInstance().getReference().child("Reviews");
                rd = new ReviewData();
                rd.setComment(comment);
                rd.setPname(pname);
                rd.setHeading(heading);
                rd.setEmail(email);
                rd.setRating(rating);
                rd.setSpecification(specification);
                reff.push().setValue(rd);

                Intent i = new Intent(Review.this,ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}
