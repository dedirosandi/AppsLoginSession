package com.dedirosandi.demologin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    private TextView Tv1;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Tv1 = findViewById(R.id.NamaTVAdmin);
        Tv1.setText(preferences.getUsername(AdminActivity.this));
    }

    public void logout(View view) {
        startActivity(new Intent(AdminActivity.this,MainActivity.class));
        preferences.clearData(this);
        finish();
    }
}