package com.example.inserttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignin;
    private EditText editTextEmail, editTextPass;
    private TextView textViewSignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);

        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging you in please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSignin){
            userLogin();
        }
        if(v == textViewSignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
