package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.quanlynhatro.DAO.TenantDAO;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Adapter.ListTenantAdapter;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListTenantActivity extends AppCompatActivity {
    private RecyclerView rcv_list_tenant;
    private ListTenantAdapter listTenantAdapter;
    private List<Tenant> listTenant;
    private ImageView LT_icon_back;
//    private Button LT_btn_detail;
    private void anhxa(){
        rcv_list_tenant = findViewById(R.id.rcv_list_tenant);
        LT_icon_back = findViewById(R.id.LT_icon_back);
//        LT_btn_detail = findViewById(R.id.LT_btn_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tenant);
        getSupportActionBar().hide();
        anhxa();

        setIconBack();

        listTenantAdapter = new ListTenantAdapter();
        listTenant = new ArrayList<>();
        listTenant = AppDatabase.getInstance(ListTenantActivity.this).tenantDAO().getListTenant();

        listTenantAdapter.setData(listTenant);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_list_tenant.setLayoutManager(linearLayoutManager);
        rcv_list_tenant.setAdapter(listTenantAdapter);
    }

    private void setIconBack(){
        LT_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTenantActivity.this, HomeActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }

}