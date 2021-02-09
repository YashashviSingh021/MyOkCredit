package com.example.MyOkCredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addcustomer extends AppCompatActivity {
EditText et1,et2;
Button bt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomer);
        et1=findViewById(R.id.add_et1);
        et2=findViewById(R.id.add_et2);
        bt=findViewById(R.id.add_bt);
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        final String uname=sp.getString("username","NA");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("cus_name", et1.getText().toString());
                user.put("cus_pno", et2.getText().toString());
                user.put("due","0");
                user.put("advance", "0");


// Add a new document with a generated ID
                db.collection(uname)
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(addcustomer.this, "Customer Added", Toast.LENGTH_SHORT).show();
                                Intent in=new Intent(addcustomer.this,home.class);
                                startActivity(in);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addcustomer.this, "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
