package com.example.swapshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtLEmail,edtLPassword;
        Button btnLRegister;
        Button btnLogin;
        FirebaseAuth fAuth;
        btnLRegister = findViewById(R.id.btnLRegister);
        edtLEmail = findViewById(R.id.edtLEmail);
        edtLPassword = findViewById(R.id.edtLPassword);
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();

        //my code
        //if(fAuth.getCurrentUser()!= null)
        //{
        //startActivity(new Intent(MainActivity.this, Home.class));
        //finish();
        //}else {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtLEmail.getText().toString().trim();
                String pass = edtLPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    edtLEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edtLPassword.setError("Password is Required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Error occurred!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //}
        btnLRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }
}