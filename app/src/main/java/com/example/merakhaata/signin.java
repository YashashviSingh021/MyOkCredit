package com.example.MyOkCredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class signin extends AppCompatActivity {
EditText et1,et2;
Button bt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        et1=findViewById(R.id.signin_et1);
        et2=findViewById(R.id.signin_et2);
        bt=findViewById(R.id.signin_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
                final SharedPreferences.Editor ed=sp.edit();
                db.collection("myusers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    String pno=et1.getText().toString();
                                    String pass=et2.getText().toString();

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("pno").toString().equals(pno)){
                                            if(document.getData().get("password").toString().equals(pass)){
                                                Toast.makeText(signin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                ed.putString("username",document.getData().get("name").toString());
                                                ed.commit();
                                                Intent in=new Intent(signin.this,home.class);
                                                startActivity(in);
                                            }
                                            else
                                            {
                                                Toast.makeText(signin.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }
                                } else {
                                    Toast.makeText(signin.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }
}
