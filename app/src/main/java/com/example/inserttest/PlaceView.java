package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlaceView extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference reff,reff2;
    UserData rec;
    TextView t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_view);
        String specification = getIntent().getStringExtra("specification");
        String lat = getIntent().getStringExtra("latitude");
        String lon = getIntent().getStringExtra("longitude");
        t1 = findViewById(R.id.latitiude);
        t2 = findViewById(R.id.longitude);
        t1.setText(lat);
        t2.setText(lon);
        final String final_specification = specification.substring(0, 1).toLowerCase() + specification.substring(1);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = user.getEmail();
        reff2 = FirebaseDatabase.getInstance().getReference("UserData");
        reff2.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rec = dataSnapshot.getValue(UserData.class);

                int count = 100;
                if(final_specification.equals("hillStation")) {
                    count = rec.getHillStation();
                    count++;
                    rec.setHillStation(count);
                }
                if(final_specification.equals("museum")) {
                    count = rec.getMuseum();
                    count++;
                    rec.setMuseum(count);
                }
                if(final_specification.equals("adventureAndHiking")) {
                    count = rec.getAdventureAndHiking();
                    count++;
                    rec.setAdventureAndHiking(count);
                }
                if(final_specification.equals("forest")) {
                    count = rec.getForest();
                    count++;
                    rec.setForest(count);
                }
                if(final_specification.equals("historicalPlace")) {
                    count = rec.getHistoricalPlace();
                    count++;
                    rec.setHistoricalPlace(count);
                }
                if(final_specification.equals("beach")) {
                    count = rec.getBeach();
                    count++;
                    rec.setBeach(count);
                }
                if(final_specification.equals("religiousDestination")) {
                    count = rec.getReligiousDestination();
                    count++;
                    rec.setReligiousDestination(count);
                }
                reff2 = FirebaseDatabase.getInstance().getReference("UserData");
                reff2.child(rec.getId()).child(final_specification).setValue(count);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
        reff = FirebaseDatabase.getInstance().getReference().child("UserData");
        reff.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String a = dataSnapshot.getValue().toString();
                //e1.setText(a);
                rec = dataSnapshot.getValue(UserData.class);
                String a = rec.getName();
                e1.setText(a);
                /*
                int count = 100;
                if(final_specification.equals("hillStation")) {
                    count = rec.getHillStation();
                    count++;
                    rec.setHillStation(count);
                }
                if(final_specification.equals("museum")) {
                    count = rec.getMuseum();
                    count++;
                    rec.setMuseum(count);
                }
                if(final_specification.equals("adventureAndHiking")) {
                    count = rec.getAdventureAndHiking();
                    count++;
                    rec.setAdventureAndHiking(count);
                }
                if(final_specification.equals("forest")) {
                    count = rec.getForest();
                    count++;
                    rec.setForest(count);
                }
                if(final_specification.equals("historicalPlace")) {
                    count = rec.getHistoricalPlace();
                    count++;
                    rec.setHistoricalPlace(count);
                }
                if(final_specification.equals("beach")) {
                    count = rec.getBeach();
                    count++;
                    rec.setBeach(count);
                }
                if(final_specification.equals("ReligiousDestination")) {
                    count = rec.getReligiousDestination();
                    count++;
                    rec.setReligiousDestination(count);
                }


                //reff.push().setValue(rec);
                //String key = reff.push().getKey();

                //reff2 = FirebaseDatabase.getInstance().getReference().child("UserData");
                //reff2.child(rec.getId()).setValue(rec);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }
}
