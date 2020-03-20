package com.example.inserttest;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PlaceDetail extends AppCompatActivity {
    RecyclerView mRecyclerView;
    DatabaseReference reff;

    FirebaseRecyclerOptions<Model> options;
    FirebaseRecyclerAdapter<Model,ViewHolder2> adapter;

    //Place Detail CardView
    CardView cardView;
    ConstraintLayout expandableView1, expandableView2, expandableView3;
    Button arrowBtn1, arrowBtn2, arrowBtn3;

    public void expand1(View view){
        expandableView1 = findViewById(R.id.expandableView1);
        arrowBtn1 = findViewById(R.id.arrowBtn1);
        cardView = findViewById(R.id.cardView);
        if(expandableView1.getVisibility()==View.GONE){
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView1.setVisibility(View.VISIBLE);
            arrowBtn1.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }else{
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView1.setVisibility(View.GONE);
            arrowBtn1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }
    }

    public void expand2(View view){
        expandableView2 = findViewById(R.id.expandableView2);
        arrowBtn2 = findViewById(R.id.arrowBtn2);
        cardView = findViewById(R.id.cardView);
        if(expandableView2.getVisibility()==View.GONE){
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView2.setVisibility(View.VISIBLE);
            arrowBtn2.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }else{
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView2.setVisibility(View.GONE);
            arrowBtn2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }
    }

    public void expand3(View view){
        expandableView3 = findViewById(R.id.expandableView3);
        arrowBtn3 = findViewById(R.id.arrowBtn3);
        cardView = findViewById(R.id.cardView);
        if(expandableView3.getVisibility()==View.GONE){
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView3.setVisibility(View.VISIBLE);
            arrowBtn3.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }else{
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView3.setVisibility(View.GONE);
            arrowBtn3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        /*arrowBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        String pname = getIntent().getStringExtra("placename");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List Of Places");
        mRecyclerView = findViewById(R.id.cycle);
        mRecyclerView.setHasFixedSize(true);
        reff = FirebaseDatabase.getInstance().getReference().child("Places");
        Query q = FirebaseDatabase.getInstance().getReference("Places")
                .orderByChild("name")
                .equalTo(pname);

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(q,Model.class).build();
        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder2 holder, int position, @NonNull Model model) {
                Picasso.get().load(model.getImage()).into(holder.i1, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(PlaceDetail.this,"Success",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(PlaceDetail.this,"ERROR",Toast.LENGTH_LONG).show();
                    }
                });
                holder.t1.setText(model.getName());
                holder.t2.setText(model.getSpecification());
                holder.t3.setText(model.getDescription());
                holder.t4.setText(model.getView());
                holder.t5.setText(model.getHowtoreach());
                final String specification = model.getSpecification();
                final String lat = model.getLatitude();
                final String lon = model.getLongitude();
                final String name = model.getName();
                holder.b1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(PlaceDetail.this,PlaceView.class);
                        i.putExtra("specification",specification);
                        i.putExtra("latitude",lat);
                        i.putExtra("longitude",lon);
                        startActivity(i);
                    }
                });
                holder.b2.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(PlaceDetail.this,Review.class);
                        i.putExtra("pname",name);
                        i.putExtra("specification",specification);
                        startActivity(i);
                    }
                });
                holder.b3.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(PlaceDetail.this,MainActivity.class);
                        i.putExtra("latitude",lat);
                        i.putExtra("longitude",lon);
                        startActivity(i);
                    }
                });
            }
            @NonNull
            @Override
            public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placedetail,parent,false);
                ViewHolder2 viewHolder = new ViewHolder2(view);
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