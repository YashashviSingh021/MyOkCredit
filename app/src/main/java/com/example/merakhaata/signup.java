package com.example.merakhaata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class signup extends AppCompatActivity {
EditText et1,et2,et3;
Button b1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et1=findViewById(R.id.signup_et1);
        et2=findViewById(R.id.signup_et2);
        et3=findViewById(R.id.signup_et3);
        b1=findViewById(R.id.signup_bt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("name", et1.getText().toString());
                user.put("pno", et2.getText().toString());
                user.put("password", et3.getText().toString());

// Add a new document with a generated ID
                db.collection("myusers")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(signup.this, "User Added", Toast.LENGTH_SHORT).show();
                                Intent in=new Intent(signup.this,signin.class);
                                startActivity(in);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signup.this, "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}
