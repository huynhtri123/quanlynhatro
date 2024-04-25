package com.example.quanlynhatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_InfoActivity extends AppCompatActivity {
    private EditText PI_edt_username;
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
        PI_edt_username = findViewById(R.id.PI_edt_username);
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

        //Du lieu cung
        Account account = new Account("ternant001", "123456");
        tenant = new Tenant(account, "Pep Guardiola", "https://s.net.vn/hMT4", "0384576368", "Long An", "18", "men");
        setText(tenant);
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
    private void setText(Tenant tenant){
        PI_edt_username.setText(tenant.getAccount().getUsername());
        PI_edt_password.setText(tenant.getAccount().getPassword());
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
}