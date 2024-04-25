package com.example.quanlynhatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Tenant;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    private TextView H_tv_username;
    private CircleImageView H_image_1;
    private Button H_btn_1;
    private Button H_btn_logout;
    private ImageView H_img_func1_personal_info;
    private ImageView H_img_func2_listRooms;
    private ImageView H_img_func3_tenant_management;
    private ImageView H_img_func4_invoice;
    private ImageView H_img_func5_statistic;
    private ImageView H_img_func6_contract;
    private ImageView H_img_func7_expense_management;
    private ImageView H_img_func8_rent_room;
    private ImageView H_img_func9_reminders;
    private Tenant tenant;
    private void anhxa(){
        H_tv_username = findViewById(R.id.H_tv_username);
        H_image_1 = findViewById(R.id.H_image_1);
        H_btn_1 = findViewById(R.id.H_btn_1);
        H_btn_logout = findViewById(R.id.H_btn_logout);
        H_img_func1_personal_info = findViewById(R.id.H_img_func1_personal_info);
        H_img_func2_listRooms = findViewById(R.id.H_img_func2_listRooms);
        H_img_func3_tenant_management = findViewById(R.id.H_img_func3_tenant_management);
        H_img_func4_invoice = findViewById(R.id.H_img_func4_invoice);
        H_img_func5_statistic = findViewById(R.id.H_img_func5_statistic);
        H_img_func6_contract = findViewById(R.id.H_img_func6_contract);
        H_img_func7_expense_management = findViewById(R.id.H_img_func7_expense_management);
        H_img_func8_rent_room = findViewById(R.id.H_img_func8_rent_room);
        H_img_func9_reminders = findViewById(R.id.H_img_func9_reminders);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        anhxa();

        logout();
        getInfoUser();
        func1_personal_info();
        func2_listRooms();
    }

    private void func1_personal_info(){
        H_img_func1_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Personal_InfoActivity.class);
                startActivity(intent);
            }
        });
    }
    private void func2_listRooms(){
        H_img_func2_listRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListRoomActivity.class);
                startActivity(intent);
            }
        });
    }
    private void logout(){
        H_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getInfoUser(){
        //Du lieu cung
        Account account = new Account("ternant001", "123456");
        tenant = new Tenant(account, "Pep Guardiola", "https://s.net.vn/hMT4", "0384576368", "Long An", "18", "men");
        H_tv_username.setText(tenant.getAccount().getUsername());
        Glide.with(this)
                .load(tenant.getThumUrl())
                .into(H_image_1);
    }
}