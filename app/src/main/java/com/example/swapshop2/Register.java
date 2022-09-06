package com.example.swapshop2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText edtRName,edtREmail,edtRPassword;
        Button btnRegister;
        FirebaseAuth fAuth;
        edtRName = findViewById(R.id.edtRName);
        edtREmail = findViewById(R.id.edtREmail);
        edtRPassword = findViewById(R.id.edtRPassword);
        btnRegister  = findViewById(R.id.btnRegister);
        fAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtRName.getText().toString().trim();
                String email = edtREmail.getText().toString().trim();
                String pass = edtRPassword.getText().toString().trim();
                String emailReg = "\\S+@\\S+";
                Pattern pPattern = Pattern.compile(emailReg);
                Matcher mMatcher = pPattern.matcher(email);
                String sPasswordRegex = "^(?=.*[0-9])"
                        + "(?=.[a-z])(?=.[A-Z])"
                        + "(?=.*[!@#$%^&+=])"
                        + "(?=\\S+$).{8,20}$";
                Pattern p = Pattern.compile(sPasswordRegex);
                Matcher m = p.matcher(pass);

                if(TextUtils.isEmpty(email)){
                    edtREmail.setError("Email is Required.");
                    return;
                }
                if(Boolean.toString(mMatcher.matches()).equals("false")){
                    edtREmail.setError("Invalid Email");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    edtRPassword.setError("Password is Required.");
                    return;
                }
                if(Boolean.toString(m.matches()).equals("false")){
                    edtRPassword.setError("Invalid Password");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(Register.this, "User made", Toast.LENGTH_SHORT).show();
                            User user = new User(name,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Register.this,"Successful",Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            }
                                            else{
                                                Toast.makeText(Register.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(Register.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });





    }
}