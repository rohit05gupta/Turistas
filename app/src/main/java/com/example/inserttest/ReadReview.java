package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ReadReview extends ControlActivity {
    RecyclerView mRecyclerView;
    FirebaseRecyclerOptions<ReviewData> options;
    FirebaseRecyclerAdapter<ReviewData,ReviewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        setContentView(R.layout.activity_read_review);
        String pname = getIntent().getStringExtra("pname");
        mRecyclerView = findViewById(R.id.cycle);
        mRecyclerView.setHasFixedSize(true);
        Query q = FirebaseDatabase.getInstance().getReference("Reviews")
                .orderByChild("pname")
                .equalTo(pname);
        options = new FirebaseRecyclerOptions.Builder<ReviewData>().setQuery(q,ReviewData.class).build();
        adapter = new FirebaseRecyclerAdapter<ReviewData, ReviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ReviewHolder holder, int position, @NonNull ReviewData model) {
                holder.t1.setText(model.getEmail());
                holder.t2.setText(model.getHeading());
                holder.t3.setText(model.getComment());
                //holder.t4.setText(model.getCost);
                //holder.r1.setRating(Float.parseFloat(model.getRating()));
            }
            @NonNull
            @Override
            public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card,parent,false);
                ReviewHolder viewHolder = new ReviewHolder(view);
                return viewHolder;
            }
        };
        GridLayoutManager g = new GridLayoutManager(getApplicationContext(),1);
        mRecyclerView.setLayoutManager(g);
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
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
