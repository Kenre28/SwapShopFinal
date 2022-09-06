package com.example.swapshop2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchProduct extends AppCompatActivity {
    EditText edtSProductName;
    LinearLayout llSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        edtSProductName = findViewById(R.id.edtSProductName);
        llSearch = findViewById(R.id.llSearchProduct);

        Button btnSearchProduct = findViewById(R.id.btnSSearch);
        btnSearchProduct.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findProduct();
            }
        });
    }

    public void findProduct(){
        llSearch.removeAllViews();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                String name = edtSProductName.getText().toString().trim();
                int iFound = 0;
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    String pName = snapshot.child("name").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String UID = snapshot.child("UID").getValue().toString();
                    if(pName.contains(name)){
                        iFound = 1;
                        TextView txtProduct = new TextView(SearchProduct.this);
                        txtProduct.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        txtProduct.setText(pName +"\n" + description + "/n" + location);
                        llSearch.addView(txtProduct);

                    }
                }
                if(iFound == 0){
                    TextView txtProduct = new TextView(SearchProduct.this);
                    txtProduct.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    txtProduct.setText("No Results have been found");
                    llSearch.addView(txtProduct);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}