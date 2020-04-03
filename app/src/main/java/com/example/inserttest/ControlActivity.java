package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        onCreateDrawer();
    }
    protected void onCreateDrawer(){

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(ControlActivity.this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(ControlActivity.this,"Home Selected",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), PostsListActivity.class));
                break;
            case R.id.profile:
                Toast.makeText(ControlActivity.this,"Profile Selected",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.location:
                Toast.makeText(ControlActivity.this,"Location Selected",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                break;
            case R.id.directions:
                Toast.makeText(ControlActivity.this,"Directions Selected",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nearby:
                Toast.makeText(ControlActivity.this,"NearBy Places Selected",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), Main4Activity.class));
                break;
            case R.id.recommendation:
                Toast.makeText(ControlActivity.this,"Recommendation Selected",Toast.LENGTH_LONG).show();
                break;
            case R.id.logout:
                AuthUI.getInstance()
                        .signOut(ControlActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent i = new Intent(ControlActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                        });
                Toast.makeText(ControlActivity.this,"Logout Success",Toast.LENGTH_LONG).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
