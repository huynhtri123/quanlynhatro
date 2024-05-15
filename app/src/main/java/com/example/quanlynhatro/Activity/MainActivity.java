package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SeedDataInitializer;
import com.example.quanlynhatro.database.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btn_1;
    private Button btn_2;
    private void anhxa(){
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //tạo tài khoản cho admin lần đầu
        AppDatabase db = AppDatabase.getInstance(this);
        SeedDataInitializer.initialize(db);

        anhxa();
        btnLoginListener();
        btnRegisternListener();

    }

    private void btnLoginListener(){
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void btnRegisternListener(){
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}