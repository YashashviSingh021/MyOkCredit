package com.example.MyOkCredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class givecredit extends AppCompatActivity {
EditText et;
Button bt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givecredit);
        et=findViewById(R.id.creamount_et1);
        bt=findViewById(R.id.give_bt);
        SharedPreferences sp1=getSharedPreferences("mysp1",MODE_PRIVATE);
        final String cus=sp1.getString("cusname","NA");
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        final String uname=sp.getString("username","NA");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int amt = Integer.parseInt(et.getText().toString());
                db.collection(uname)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("cus_name").toString().equals(cus))
                                        {
                                            int advance=Integer.parseInt(document.getData().get("advance").toString());
                                            int due=Integer.parseInt(document.getData().get("due").toString());
                                            String id=document.getId();
                                            int a=0,d=0;
                                            if(advance==0 && due==0)
                                            {
                                                a=0;
                                                d=amt;
                                            }
                                            else if(advance==0 && due>0)
                                            {
                                                a=0;
                                                d=due+amt;
                                            }
                                            else if((advance>0 && due==0)&& amt<advance)
                                            {
                                                a=advance-amt;
                                                d=0;

                                            }
                                            else if((advance>0 && due==0)&& amt>advance){
                                                d=amt-advance;
                                                a=0;
                                            }
                                            DocumentReference ref=db.collection(uname).document(id);
                                            ref.update("advance",a);
                                            ref.update("due",d);
                                            Toast.makeText(givecredit.this, "Payment Accepted", Toast.LENGTH_SHORT).show();
                                            Intent in=new Intent(givecredit.this,customeraccount.class);
                                            startActivity(in);
                                            givecredit.this.finish();
                                        }

                                    }
                                } else {
                                    Toast.makeText(givecredit.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });









            }
        });
    }
}
