package com.dedirosandi.demologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {private EditText username,
        password;
    private Button btn_login;
    Switch active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Halaman Login");
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        active = findViewById(R.id.active);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("login").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = username.getText().toString();
                        String input2 = password.getText().toString();

                        if(dataSnapshot.child(input1).exists()){
                            if (Objects.equals(dataSnapshot.child(input1).child("password").getValue(String.class), input2)){
                                if (active.isChecked()){
                                    if (Objects.equals(dataSnapshot.child(input1).child("as").getValue(String.class), "admin")){
                                        preferences.setDataLogin(MainActivity.this,true);
                                        preferences.setDataAs(MainActivity.this,"admin");
                                        startActivity(new Intent(MainActivity.this, AdminActivity.class));
                                    }else if (Objects.equals(dataSnapshot.child(input1).child("as").getValue(String.class), "user")){
                                        preferences.setDataLogin(MainActivity.this,true);
                                        preferences.setDataAs(MainActivity.this,"user");
                                        startActivity(new Intent(MainActivity.this,UserActivity.class));

                                    }
                                }else{
                                    if (Objects.equals(dataSnapshot.child(input1).child("as").getValue(String.class), "admin")){
                                        preferences.setDataLogin(MainActivity.this,false);
                                        startActivity(new Intent(MainActivity.this,AdminActivity.class));
                                        preferences.setUsername(MainActivity.this, input1);



                                    }else if (Objects.equals(dataSnapshot.child(input1).child("as").getValue(String.class), "user")){
                                        preferences.setDataLogin(MainActivity.this,false);
                                        startActivity(new Intent(MainActivity.this,UserActivity.class));
                                        preferences.setUsername(MainActivity.this, input1);


                                    }
                                }


                            }else{
                                Toast.makeText(MainActivity.this, "Masukan Data Login", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Data Belum Terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)){
            if (preferences.getDataAs(this).equals("admin")){

                startActivity(new Intent(MainActivity.this,AdminActivity.class));
                finish();
            }else if (preferences.getDataAs(this).equals("user")){
                startActivity(new Intent(MainActivity.this,UserActivity.class));
                finish();
            }
        }
    }
}