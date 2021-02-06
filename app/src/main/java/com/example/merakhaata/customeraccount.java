package com.example.merakhaata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class customeraccount extends AppCompatActivity {
TextView tv1,tv2,tv3;
Button bt1,bt2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customeraccount);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.due_tv);
        tv3=findViewById(R.id.adv_tv);
        bt1=findViewById(R.id.acc_bt);
        bt2=findViewById(R.id.cre_bt);
        SharedPreferences sp1=getSharedPreferences("mysp1",MODE_PRIVATE);
        final String cus=sp1.getString("cusname","NA");
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        String uname=sp.getString("username","NA");
        tv1.setText(cus+"'s Account");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(customeraccount.this,acceptpayment.class);
                startActivity(in);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(customeraccount.this,givecredit.class);
                startActivity(in);
            }
        });

        db.collection(uname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("cus_name").toString().equals(cus)){
                                    tv2.setText(document.getData().get("due").toString());
                                    tv3.setText(document.getData().get("advance").toString());
                                }
                            }
                        } else {

                        }
                    }
                });
    }
}
