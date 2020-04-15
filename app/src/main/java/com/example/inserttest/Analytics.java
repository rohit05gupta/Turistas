package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class Analytics extends ControlActivity {

    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reff2;
    UserData rec;
    BarDataSet bardataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        super.onCreateDrawer();
        getSupportActionBar().setTitle("User History");

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Analytics.this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = user.getEmail();
        reff2 = FirebaseDatabase.getInstance().getReference("UserData");
        reff2.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rec = dataSnapshot.getValue(UserData.class);
                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(rec.getHillStation(),0));
                entries.add(new BarEntry(rec.getMuseum(),1));
                entries.add(new BarEntry(rec.getAdventureAndHiking(),2));
                entries.add(new BarEntry(rec.getForest(),3));
                entries.add(new BarEntry(rec.getHistoricalPlace(),4));
                entries.add(new BarEntry(rec.getBeach(),5));
                entries.add(new BarEntry(rec.getReligiousDestination(),6));

                bardataset = new BarDataSet(entries, "Cells");
                ArrayList<String> labels = new ArrayList<String>();
                labels.add("Hills");
                labels.add("Museum");
                labels.add("Hiking");
                labels.add("Forest");
                labels.add("Histoy");
                labels.add("Beach");
                labels.add("Religion");

                BarData data = new BarData(labels, bardataset);
                barChart.setData(data); // set the data and list of labels into chart
                barChart.setDescription("User History");

                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.animateY(5000);
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





    }
}
