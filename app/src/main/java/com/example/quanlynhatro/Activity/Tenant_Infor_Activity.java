package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlynhatro.DAO.AccountDAO;
import com.example.quanlynhatro.DAO.TenantDAO;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tenant_Infor_Activity extends AppCompatActivity {
    private EditText TI_edt_email;
    private EditText TI_edt_password;
    private CircleImageView TI_image_1;
    private EditText TI_edt_name;
    private EditText TI_edt_phone;
    private EditText TI_edt_address;
    private EditText TI_edt_age;
    private Spinner TI_spinner_gender;
    private Button TI_btn_delete;
    private ImageView TI_icon_back;
    private Tenant tenant;
    private void anhxa(){
        TI_edt_email = findViewById(R.id.TI_edt_email);
        TI_edt_password = findViewById(R.id.TI_edt_password);
        TI_image_1 = findViewById(R.id.TI_image_1);
        TI_edt_name = findViewById(R.id.TI_edt_name);
        TI_edt_phone = findViewById(R.id.TI_edt_phone);
        TI_edt_address = findViewById(R.id.TI_edt_address);
        TI_edt_age = findViewById(R.id.TI_edt_age);
        TI_spinner_gender = findViewById(R.id.TI_spinner_gender);
        TI_btn_delete = findViewById(R.id.TI_btn_delete);
        TI_icon_back = findViewById(R.id.TI_icon_back);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_infor);
        getSupportActionBar().hide();

        int selectedTenantId = getIntent().getIntExtra("selectedTenantId", -1);
        Tenant tenant = AppDatabase.getInstance(Tenant_Infor_Activity.this).tenantDAO().getTenantById(selectedTenantId);
        Account account = AppDatabase.getInstance(Tenant_Infor_Activity.this).accountDAO().getAccountById(tenant.getAccountId());
        anhxa();
        setText(tenant,account);
        setBtnDeleteOnclick(tenant);
        setIconBack();

//        setSpinnerGender();
//        setBtnDeleteOnclick();
//        setIconBack();

//        tenant = SessionManager.getInstance().getCurrentTenant();
//        //tam thoi, gan cung image cho dep
//        tenant.setThumUrl("https://s.net.vn/hMT4");
//
//        Account account = SessionManager.getInstance().getAccount();
//
//        setText(tenant, account);
    }
    private void setSpinnerGender(){
        String[] values = {"men", "women",};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TI_spinner_gender.setAdapter(adapter);
    }
    private void setBtnDeleteOnclick(Tenant tenant){
        TI_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức xóa Tenant khỏi cơ sở dữ liệu
                deleteTenantFromDatabase(tenant);
                Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void deleteTenantFromDatabase(Tenant tenant) {
        // Truy cập vào đối tượng TenantDAO để thực hiện xóa Tenant
        TenantDAO tenantDAO = AppDatabase.getInstance(getApplicationContext()).tenantDAO();

        // Thực hiện xóa Tenant từ cơ sở dữ liệu bằng cách gọi phương thức deleteTenant() từ TenantDAO
        tenantDAO.deleteTenantById(tenant.getId());
        AccountDAO accountDAO = AppDatabase.getInstance(getApplicationContext()).accountDAO();
        accountDAO.deleteAccountById(tenant.getAccountId());

        Intent intent = new Intent(Tenant_Infor_Activity.this, ListTenantActivity.class);
        startActivity(intent);
        // Hiển thị thông báo hoặc cập nhật giao diện người dùng sau khi xóa
    }
    private void setText(Tenant tenant, Account account){
        TI_edt_email.setText(account.getUsername());
        TI_edt_password.setText(account.getPassword());

        TI_edt_name.setText(tenant.getName());
        Log.d(">>>check image:", tenant.getThumUrl());
        Glide.with(this)
                .load(tenant.getThumUrl())
                .into(TI_image_1);
        TI_edt_phone.setText(tenant.getPhone());
        TI_edt_address.setText(tenant.getAddress());
        TI_edt_age.setText(tenant.getAge());
        if (tenant.getGender().equals("men")){
            TI_spinner_gender.setSelection(0);
        } else TI_spinner_gender.setSelection(1);
    }
    private void setIconBack(){
        TI_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tenant_Infor_Activity.this, ListTenantActivity.class);
                startActivity(intent);
            }
        });
    }
}