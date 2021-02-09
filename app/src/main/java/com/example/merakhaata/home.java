package com.example.MyOkCredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class home extends AppCompatActivity {
LinearLayout ll;
TextView tv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ll=findViewById(R.id.ll);
        tv=findViewById(R.id.tv);
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        String uname=sp.getString("username","NA");
        tv.setText("Welcome "+uname);
        SharedPreferences sp1=getSharedPreferences("mysp1",MODE_PRIVATE);
        final SharedPreferences.Editor ed=sp1.edit();
        db.collection(uname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                               TextView tv1=new TextView(home.this);
                               tv1.setText(document.getData().get("cus_name").toString());
                               tv1.setTextSize(30);

                               tv1.setPadding(20,20,20,20);
                               ll.addView(tv1);
                               tv1.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       ed.putString("cusname",document.getData().get("cus_name").toString());
                                       ed.commit();
                                       Intent in=new Intent(home.this,customeraccount.class);
                                       startActivity(in);
                                   }
                               });
                            }
                        } else {
                            Toast.makeText(home.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=this.getMenuInflater();
        mi.inflate(R.menu.homemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.m1)
        {
            Intent in=new Intent(home.this,addcustomer.class);
            startActivity(in);
        }
        if(item.getItemId()==R.id.m2)
        {

        }
        if(item.getItemId()==R.id.m3)
        {

        }
        return super.onOptionsItemSelected(item);
    }
}
