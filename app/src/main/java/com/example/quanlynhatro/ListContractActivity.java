package com.example.quanlynhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;

import java.util.ArrayList;
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

        listContractAdapter = new ListContractAdapter();
        listContract= new ArrayList<>();
        //Du lieu cung
        Room room1 = new Room("123","","130m2","Empty","1000");
        Account account1 = new Account("A","123");
        Tenant tenant1= new Tenant(account1,"Van A","","0123456789","HCM","18","Nam");
        Contract contract1 = new Contract(tenant1, room1, "12-2-2024", "NotApproved");

        listContract.add(contract1);


        listContractAdapter.setData(listContract);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_list_contract.setLayoutManager(linearLayoutManager);
        rcv_list_contract.setAdapter(listContractAdapter);
    }
    private void setIconBack(){
        LR_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContractActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}