package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

public class LoginActivity extends AppCompatActivity {
    private ImageView L_img_1;
    private TextView txt_register;
    private EditText L_edt_email;
    private EditText L_edt_password;
    private Button L_btn_1;
    private void anhxa(){
        L_img_1 = findViewById(R.id.L_img_1);
        txt_register = findViewById(R.id.txt_register);
        L_edt_email = findViewById(R.id.L_edt_email);
        L_edt_password = findViewById(R.id.L_edt_password);
        L_btn_1 = findViewById(R.id.L_btn_1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        anhxa();
        btn_BackListener();
        txt_RegisterListener();

        L_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        String email = L_edt_email.getText().toString().trim();
        String password = L_edt_password.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Hiển thị thông báo lỗi
            Toast.makeText(this, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
            // Xác định rằng nhập liệu không hợp lệ
            L_edt_email.requestFocus();
            return;
        }

        // Lấy Account từ database
        Account account = AppDatabase.getInstance(this).accountDAO().getAccountByUsernameAndPassword(email, password);

        if (account != null) {
            // Lấy Tenant dựa trên accountId
            Tenant tenant = AppDatabase.getInstance(this).tenantDAO().getTenantByAccountId(account.getId());
            Admin admin = AppDatabase.getInstance(this).adminDAO().getAdminByAccountId(account.getId());

            if (tenant != null) {
                Log.d(">>check tenantcr7: " , tenant.getName());
                // Đăng nhập thành công
                // Lưu thông tin đăng nhập vào session
                SessionManager.getInstance().setCurrentTenant(tenant);
                SessionManager.getInstance().setAccount(account);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else if (admin != null) {
                Log.d(">>check admincr7: " , admin.getName());
                // Đăng nhập thành công
                // Lưu thông tin đăng nhập vào session
                SessionManager.getInstance().setAdmin(admin);
                SessionManager.getInstance().setAccount(account);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else {
                // Không tìm thấy
                Toast.makeText(this, "Tenant not found for the provided account", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Đăng nhập không thành công, hiển thị thông báo lỗi
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

    }

    private void btn_BackListener(){
        L_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void txt_RegisterListener(){
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}