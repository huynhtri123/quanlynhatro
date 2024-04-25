package com.example.quanlynhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;

import java.util.ArrayList;
import java.util.List;

public class ListTenantActivity extends AppCompatActivity {

    private RecyclerView rcv_list_tenant;
    private ListTenantAdapter listTenantAdapter;
    private List<Tenant> listTenant;
    private void anhxa(){
        rcv_list_tenant = findViewById(R.id.rcv_list_tenant);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tenant);
        getSupportActionBar().hide();
        anhxa();

        listTenantAdapter = new ListTenantAdapter();
        listTenant = new ArrayList<>();

        //Du lieu cung
        Account account1 = new Account("A", "123");
        Account account2 = new Account("B", "123");
        Account account3 = new Account("C", "123");
        Account account4 = new Account("D", "123");
        Tenant tenant1 = new Tenant(account1, "Le Van A", "", "O123456789", "HCM","18","Nam");
        Tenant tenant2 = new Tenant(account2, "Dang Van B", "", "O123456789", "HCM","18","Nam");
        Tenant tenant3 = new Tenant(account3, "Tran Van C", "", "O123456789", "HCM","18","Nam");
        Tenant tenant4 = new Tenant(account4, "Nguyen Van D", "", "O123456789", "HCM","18","Nam");
        listTenant.add(tenant1);
        listTenant.add(tenant2);
        listTenant.add(tenant3);
        listTenant.add(tenant4);

        listTenantAdapter.setData(listTenant);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_list_tenant.setLayoutManager(linearLayoutManager);
        rcv_list_tenant.setAdapter(listTenantAdapter);
    }
}