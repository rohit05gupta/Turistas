package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextName;
    private EditText editTextPhone;
    //private String editTextPOI;
    private EditText editTextEmail;
    private EditText editTextPass;
    private TextView textViewSignin;

    private DatabaseReference ref;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        ref = FirebaseDatabase.getInstance().getReference().child("UserData");

        user = new UserData();

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        //editTextPOI = (EditText) findViewById(R.id.editTextPOI);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);


        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }
    String POI ="";
    private void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final Long phone = Long.parseLong(editTextPhone.getText().toString().trim());
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPass.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone==0){
            Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        /*if(TextUtils.isEmpty(POI)){
            Toast.makeText(this, "Enter your place of interest", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            String id = ref.push().getKey();
                            user.setId(id);
                            user.setName(name);
                            user.setPhone(phone);
                            user.setPOI(POI);
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setAdventureAndHiking(0);
                            user.setBeach(0);
                            user.setForest(0);
                            user.setHillStation(0);
                            user.setMuseum(0);
                            user.setHistoricalPlace(0);
                            user.setReligiousDestination(0);

                            ref.child(id).setValue(user);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Email id already registered.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        progressDialog.dismiss();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkboxHillStation:
                if (checked)
                    POI = POI+"HillStation,";
                break;
            case R.id.checkboxMuseums:
                if (checked)
                    POI = POI+"Museum,";
                break;
            case R.id.checkboxAdventure:
                if (checked)
                    POI = POI+"AdventureAndHiking,";
                break;
            case R.id.checkboxForests:
                if (checked)
                    POI = POI+"Forest,";
                break;
            case R.id.checkboxHistory:
                if (checked)
                    POI = POI+"HistoricalPlace,";
                break;
            case R.id.checkboxBeach:
                if (checked)
                    POI = POI+"Beach,";
                break;
            case R.id.checkboxReligious:
                if (checked)
                    POI = POI+"ReligiousDestination,";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister){
            registerUser();
        }
        if (v == textViewSignin){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
