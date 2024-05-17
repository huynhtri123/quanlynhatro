package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_InfoActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText PI_edt_email;
    private EditText PI_edt_password;
    private CircleImageView PI_image_1;
    private EditText PI_edt_name;
    private EditText PI_edt_phone;
    private EditText PI_edt_address;
    private EditText PI_edt_age;
    private Spinner PI_spinner_gender;
    private Button PI_btn_save;
    private ImageView PI_icon_back;
    private Tenant tenant;
    private void anhxa(){
        PI_edt_email = findViewById(R.id.PI_edt_email);
        PI_edt_password = findViewById(R.id.PI_edt_password);
        PI_image_1 = findViewById(R.id.PI_image_1);
        PI_edt_name = findViewById(R.id.PI_edt_name);
        PI_edt_phone = findViewById(R.id.PI_edt_phone);
        PI_edt_address = findViewById(R.id.PI_edt_address);
        PI_edt_age = findViewById(R.id.PI_edt_age);
        PI_spinner_gender = findViewById(R.id.PI_spinner_gender);
        PI_btn_save = findViewById(R.id.PI_btn_save);
        PI_icon_back = findViewById(R.id.PI_icon_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getSupportActionBar().hide();

        anhxa();
        setSpinnerGender();
        setBtnSaveOnclick();
        setIconBack();

        tenant = SessionManager.getInstance().getCurrentTenant();
        //tam thoi, gan cung image cho dep
        tenant.setThumUrl("https://s.net.vn/hMT4");

        Account account = SessionManager.getInstance().getAccount();

        setText(tenant, account);

    }

    private void setSpinnerGender(){
        String[] values = {"men", "women",};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PI_spinner_gender.setAdapter(adapter);
    }
    private void setBtnSaveOnclick(){
        PI_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = PI_spinner_gender.getSelectedItem().toString();
                Log.d(">>>check spinner gender: ", selectedItem);
            }
        });
    }
    private void setText(Tenant tenant, Account account){
        PI_edt_email.setText(account.getUsername());
        PI_edt_password.setText(account.getPassword());

        PI_edt_name.setText(tenant.getName());
        Log.d(">>>check image:", tenant.getThumUrl());
        Glide.with(this)
            .load(tenant.getThumUrl())
            .into(PI_image_1);
        PI_edt_phone.setText(tenant.getPhone());
        PI_edt_address.setText(tenant.getAddress());
        PI_edt_age.setText(tenant.getAge());
        if (tenant.getGender().equals("men")){
            PI_spinner_gender.setSelection(0);
        } else PI_spinner_gender.setSelection(1);
    }
    private void setIconBack(){
        PI_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Personal_InfoActivity.this, HomeActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }

}
