package com.example.inserttest;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Console;

public class PostsListActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    DatabaseReference reff;

    FirebaseRecyclerOptions<Model> options;
    FirebaseRecyclerAdapter<Model,ViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List Of Places");
        mRecyclerView = findViewById(R.id.cycle);
        mRecyclerView.setHasFixedSize(true);
        reff = FirebaseDatabase.getInstance().getReference().child("Places");

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(reff,Model.class).build();
        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                Picasso.get().load(model.getImage()).into(holder.i1, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(PostsListActivity.this,"Success",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(PostsListActivity.this,"ERROR",Toast.LENGTH_LONG).show();
                    }
                });
                holder.t1.setText(model.getName());
                holder.t2.setText(model.getSpecification());
            }
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView name = view.findViewById(R.id.name);
                        String tname = name.getText().toString();
                        Intent intent = new Intent(getBaseContext(), PlaceDetail.class);
                        intent.putExtra("placename",tname);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolder;
            }

        };
        GridLayoutManager g = new GridLayoutManager(getApplicationContext(),1);
        mRecyclerView.setLayoutManager(g);

        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
    }

    //search function
    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery = reff.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(firebaseSearchQuery,Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                Picasso.get().load(model.getImage()).into(holder.i1, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(PostsListActivity.this,"Success",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(PostsListActivity.this,"ERROR",Toast.LENGTH_LONG).show();
                    }
                });
                holder.t1.setText(model.getName());
                holder.t2.setText(model.getSpecification());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);

                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView name = view.findViewById(R.id.name);
                        String tname = name.getText().toString();
                        Intent intent = new Intent(getBaseContext(), PlaceDetail.class);
                        intent.putExtra("placename",tname);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolder;
            }
        };
        GridLayoutManager g = new GridLayoutManager(getApplicationContext(),1);
        mRecyclerView.setLayoutManager(g);

        adapter.startListening();
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
