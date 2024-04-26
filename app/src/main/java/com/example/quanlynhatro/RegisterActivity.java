package com.example.quanlynhatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quanlynhatro.Entity.Tenant;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private EditText edt_username;
    private EditText edt_password;
    private CircleImageView PI_image_1;
    private EditText edt_name;
    private EditText edt_conf_phone;
    private EditText edt_conf_address;
    private EditText edt_conf_age;
    private Spinner R_spinner_gender;
    private ImageView R_img_1;
    private TextView txt_login;
    private Tenant tenant;
    private void anhxa(){
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        PI_image_1 = findViewById(R.id.PI_image_1);
        edt_name = findViewById(R.id.edt_name);
        edt_conf_phone = findViewById(R.id.edt_conf_phone);
        edt_conf_address = findViewById(R.id.edt_conf_address);
        edt_conf_age = findViewById(R.id.edt_conf_age);
        R_spinner_gender = findViewById(R.id.R_spinner_gender);

        R_img_1 = findViewById(R.id.R_img_1);
        txt_login = findViewById(R.id.txt_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        anhxa();
        btn_BackListener();
        txt_loginListener();
        setSpinnerGender();
    }

    private void btn_BackListener(){
        R_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void txt_loginListener(){
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setSpinnerGender(){
        String[] values = {"men", "women",};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        R_spinner_gender.setAdapter(adapter);
    }
}