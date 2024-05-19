package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

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
    private  Admin admin;
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

        getInfoUser();
        logout();

        func1_personal_info();
        func2_listRooms();
        func3_TenantManagement();
        func4_invoiceManagement();
        func6_ContractManagement();
    }

    private void func1_personal_info(){
        H_img_func1_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin admin = SessionManager.getInstance().getAdmin();
                //nếu đang là tenant
                if (admin == null) {
                    Intent intent = new Intent(HomeActivity.this, Personal_InfoActivity.class);
                    startActivity(intent);
                    // Đóng hoạt động hiện tại
                    finish();
                } else {
                    Toast.makeText(HomeActivity.this, "This function is not for admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void func2_listRooms(){
        H_img_func2_listRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListRoomActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
    private void logout(){
        H_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa session
                SessionManager.getInstance().clearSession();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }

    //lấy dữ liệu user hiện lên trang home
    private void getInfoUser(){
        tenant = SessionManager.getInstance().getCurrentTenant();
        admin = SessionManager.getInstance().getAdmin();

        if (tenant != null) {
            //tam thoi, gan cung image cho dep
            tenant.setThumUrl("https://s.net.vn/hMT4");
            H_tv_username.setText(tenant.getName());
            Glide.with(this)
                    .load(tenant.getThumUrl())
                    .into(H_image_1);
        } else if (admin != null){
            H_tv_username.setText(admin.getName());
            Glide.with(this)
                    .load(admin.getThumUrl())
                    .into(H_image_1);
        } else {
            Log.e("HomeActivity", "Admin is null");
            Toast.makeText(this, "Admin information is not available", Toast.LENGTH_SHORT).show();
        }
    }
    private void func3_TenantManagement(){
        H_img_func3_tenant_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chỉ có admin mới được sài
                Admin admin = SessionManager.getInstance().getAdmin();
                if (admin != null){
                    Intent intent = new Intent(HomeActivity.this, ListTenantActivity.class);
                    startActivity(intent);
                    // Đóng hoạt động hiện tại
                    finish();
                } else {
                    Toast.makeText(v.getContext(), "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //invoice
    private void func4_invoiceManagement(){
        H_img_func4_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nếu là admin thì dẫn vào trang list invoice
                if (admin != null){
                    Intent intent = new Intent(HomeActivity.this, ListInvoiceActivity.class);
                    startActivity(intent);
                    // Đóng hoạt động hiện tại
                    finish();
                } else {
                    //nếu là tenant thì dẫn vào trang invoice detail (nếu có)
                    Invoice invoice = AppDatabase.getInstance(HomeActivity.this).invoiceDAO().getInvoiceByUserId(tenant.getId());
                    if (invoice != null){
                        Intent intent = new Intent(HomeActivity.this, InvoiceDetailActivity.class);
                        startActivity(intent);
                        // Đóng hoạt động hiện tại
                        finish();
                    } else {
                        Toast.makeText(v.getContext(), "Bạn không có hoá đơn nào!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //chỉ có admin mới được sài
    private void func6_ContractManagement(){
        H_img_func6_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListContractActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }

}