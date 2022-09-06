package com.example.swapshopfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadProduct extends AppCompatActivity {
    TextView edtAName, edtADecription, edtALocation;
    Button btnAPAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        edtAName = findViewById(R.id.edtAName);
        edtADecription = findViewById(R.id.edtADescription);
        edtALocation = findViewById(R.id.edtALocation);

        btnAPAdd = findViewById(R.id.btnAUpload);
        btnAPAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewProduct();
            }
        });
    }

    public void AddNewProduct(){
        String name = edtAName.getText().toString().trim();
        String description = edtADecription.getText().toString().trim();
        String location = edtALocation.getText().toString().trim();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Product product = new Product(name,description,location,UID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");
        String key = ref.push().getKey();
        ref.child(key).setValue(product);

    }
}