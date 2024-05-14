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
import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;

import java.util.ArrayList;
import java.util.List;

public class ListInvoiceActivity extends AppCompatActivity {
    private RecyclerView rcv_list_invoice;
    private ListInvoiceAdapter listInvoiceAdapter;
    private List<Invoice> listInvoice;
    private ImageView LR_icon_back;
    private void anhxa(){
        rcv_list_invoice = findViewById(R.id.rcv_list_invoice);
        LR_icon_back = findViewById(R.id.LR_icon_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_invoice);

        getSupportActionBar().hide();
        anhxa();
        setIconBack();

        listInvoiceAdapter = new ListInvoiceAdapter();
        listInvoice= new ArrayList<>();
        //Du lieu cung
        Room room1 = new Room("123","","130m2","Empty","1000");
        Account account1 = new Account("A","123");
        Tenant tenant1= new Tenant(account1,"Van A","","0123456789","HCM","18","Nam");
        Invoice invoice1 = new Invoice(room1,tenant1,"100000","100000","100000","100000","Unpaid");

        listInvoice.add(invoice1);


        listInvoiceAdapter.setData(listInvoice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_list_invoice.setLayoutManager(linearLayoutManager);
        rcv_list_invoice.setAdapter(listInvoiceAdapter);
    }
    private void setIconBack(){
        LR_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListInvoiceActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}