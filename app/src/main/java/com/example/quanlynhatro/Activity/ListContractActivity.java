package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Adapter.ListContractAdapter;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListContractActivity extends AppCompatActivity {
    private RecyclerView rcv_list_contract;
    private ListContractAdapter listContractAdapter;
    private List<Contract> listContract;
    private ImageView LR_icon_back;
    private void anhxa(){
        rcv_list_contract = findViewById(R.id.rcv_list_contract);
        LR_icon_back = findViewById(R.id.LR_icon_back);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contract);

        getSupportActionBar().hide();
        anhxa();
        setIconBack();

        listContract= new ArrayList<>();
        //nếu ngươi dùng là admin
        Admin admin = SessionManager.getInstance().getAdmin();
        Tenant tenant = SessionManager.getInstance().getCurrentTenant();
        if (admin != null){
            listContractAdapter = new ListContractAdapter();
            listContract = AppDatabase.getInstance(ListContractActivity.this).contractDAO().getAllContracts();

            listContractAdapter.setData(listContract);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rcv_list_contract.setLayoutManager(linearLayoutManager);
            rcv_list_contract.setAdapter(listContractAdapter);
        }
        //nếu người dùng là tenant
        else {
            listContractAdapter = new ListContractAdapter();
            listContract = AppDatabase.getInstance(ListContractActivity.this).contractDAO().getContractsByTenantId(tenant.getId());

            listContractAdapter.setData(listContract);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rcv_list_contract.setLayoutManager(linearLayoutManager);
            rcv_list_contract.setAdapter(listContractAdapter);
        }


    }
    private void setIconBack(){
        LR_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContractActivity.this, HomeActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
}