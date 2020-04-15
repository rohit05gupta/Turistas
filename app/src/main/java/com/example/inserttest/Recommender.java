package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Recommender extends ControlActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference reff,reff2;
    UserData rec;
    String like;
    String[] separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommender);
        super.onCreateDrawer();

        getSupportActionBar().setTitle("Recommendation");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = user.getEmail();
        int a[] = new int[10];
        Arrays.fill(a, 0);
        reff2 = FirebaseDatabase.getInstance().getReference("UserData");
        reff2.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rec = dataSnapshot.getValue(UserData.class);
                like = rec.getPOI();
                separated = like.split(",");
                reff2 = FirebaseDatabase.getInstance().getReference("Places");
                reff2.orderByChild("specification").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            for(int j=0;j<separated.length;j++){
                                if(snapshot.child("specification").getValue().toString().equals(separated[j]) && a[j] == 0){
                                    a[j] = 1;
                                    CardView card = new CardView(Recommender.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    params.setMargins(15,15,15,15);
                                    card.setLayoutParams(params);
                                    card.setRadius(9);
                                    card.setContentPadding(15, 15, 15, 15);
                                    card.setCardBackgroundColor(0xffffff);
                                    card.setMaxCardElevation(15);
                                    card.setCardElevation(9);

                                    LinearLayout linearLayout2 = new LinearLayout(Recommender.this);
                                    linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT));
                                    linearLayout2.setOrientation(LinearLayout.VERTICAL);

                                    TextView textView1 = new TextView(Recommender.this);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                    textView1.setLayoutParams(layoutParams);
                                    textView1.setText(snapshot.child("name").getValue().toString());
                                    textView1.setBackgroundColor(0x000000);
                                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                                    TextView textView2 = new TextView(Recommender.this);
                                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                    textView2.setLayoutParams(layoutParams2);
                                    textView2.setText(snapshot.child("specification").getValue().toString());
                                    textView2.setBackgroundColor(0x000000);
                                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
                                    linearLayout2.addView(textView1);
                                    linearLayout2.addView(textView2);
                                    card.addView(linearLayout2);
                                    linearLayout.addView(card);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                setContentView(linearLayout);
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
           /*     reff2.orderByChild("email").equalTo(email).addChildEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        rec = dataSnapshot.getValue(UserData.class);
                        like = rec.getPOI();
                        separated = like.split(",");
                        reff = FirebaseDatabase.getInstance().getReference().child("Places");
                        reff2 = FirebaseDatabase.getInstance().getReference("Places");
                                reff2.orderByChild("specification").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        long n = dataSnapshot.getChildrenCount();
                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                            for(int j=0;j<separated.length;j++){
                                                if(snapshot.child("specification").getValue().toString().equals(separated[j])){
                                                    TextView textView1 = new TextView(Recommender.this);
                                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                                    textView1.setLayoutParams(layoutParams);
                                                    textView1.setText(snapshot.child("name").getValue().toString());
                                                    textView1.setBackgroundColor(0x000000);
                                                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                                                    TextView textView2 = new TextView(Recommender.this);
                                                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                                    textView2.setLayoutParams(layoutParams2);
                                                    textView2.setText(snapshot.child("specification").getValue().toString());
                                                    textView2.setBackgroundColor(0x000000);
                                                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                                    layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
                                                    linearLayout.addView(textView1);
                                                    linearLayout.addView(textView2);
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                setContentView(linearLayout);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*/

    }
}
