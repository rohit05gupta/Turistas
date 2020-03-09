package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Recommendation extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference reff,reff2;
    UserData rec;
    String like;
    String[] separated;

    RecyclerView mRecyclerView,mRecyclerView2;
    FirebaseRecyclerOptions<Model> options;
    FirebaseRecyclerOptions<Model> options2;
    FirebaseRecyclerAdapter<Model,ViewHolder> adapter;
    FirebaseRecyclerAdapter<Model,ViewHolder> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);


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
                like = rec.getPOI();
                separated = like.split(",");

                mRecyclerView = findViewById(R.id.cycle);
                mRecyclerView.setHasFixedSize(true);


                reff = FirebaseDatabase.getInstance().getReference().child("Places");
                Query q = FirebaseDatabase.getInstance().getReference("Places")
                        .orderByChild("specification")
                        .equalTo(separated[0]);

                options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(q, Model.class).build();
                adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                        holder.t1.setText(model.getName());
                        holder.t2.setText(model.getSpecification());
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
                        ViewHolder viewHolder = new ViewHolder(view);
                        return viewHolder;
                    }
                };
                GridLayoutManager g = new GridLayoutManager(getApplicationContext(), 1);
                mRecyclerView.setLayoutManager(g);
                adapter.startListening();
                mRecyclerView.setAdapter(adapter);




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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Recommendation");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStart(){
        if(adapter != null)
            adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(adapter != null)
            adapter.startListening();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if(adapter != null)
            adapter.startListening();
        super.onResume();
    }
}
