package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.TenantRoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.database.AppDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private EditText edt_email;
    private EditText edt_password;
    private CircleImageView PI_image_1;
    private EditText edt_name;
    private EditText edt_conf_phone;
    private EditText edt_conf_address;
    private EditText edt_conf_age;
    private Spinner R_spinner_gender;
    private ImageView R_img_1;
    private TextView txt_login;
    private Button R_btn_1;

    private void anhxa(){
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        PI_image_1 = findViewById(R.id.PI_image_1);
        edt_name = findViewById(R.id.edt_name);
        edt_conf_phone = findViewById(R.id.edt_conf_phone);
        edt_conf_address = findViewById(R.id.edt_conf_address);
        edt_conf_age = findViewById(R.id.edt_conf_age);
        R_spinner_gender = findViewById(R.id.R_spinner_gender);
        R_btn_1 = findViewById(R.id.R_btn_1);

        R_img_1 = findViewById(R.id.R_img_1);
        txt_login = findViewById(R.id.txt_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        anhxa();
        btn_BackListener();
        txt_loginListener();
        setSpinnerGender();

        R_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    register();
                }
            }
        });
    }
    private void register(){
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String name = edt_name.getText().toString().trim();
        String thumUrl = edt_name.getText().toString().trim();
        String phone = edt_conf_phone.getText().toString().trim();
        String address = edt_conf_address.getText().toString().trim();
        String age = edt_conf_age.getText().toString().trim();
        String gender = R_spinner_gender.getSelectedItem().toString();

        //Kiểm tra email hợp lệ
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
            edt_email.setError("Địa chỉ email không hợp lệ (vd: example@gmail.com)");
            edt_email.requestFocus();
            return;
        }
        //Kiểm tra email đã tồn tại chưa
        int emailExists = AppDatabase.getInstance(RegisterActivity.this).accountDAO().checkEmailExists(email);
        if (emailExists > 0){
            Toast.makeText(this, "Địa chỉ email đã tồn tại1", Toast.LENGTH_SHORT).show();
            // Xác định rằng nhập liệu không hợp lệ
            edt_email.setError("Email đã tồn tại, hãy chọn email khác!");
            edt_email.requestFocus();
            return;
        }

        //Tạo Account và lưu vào db
        Account account = new Account(email, password);
        AppDatabase.getInstance(RegisterActivity.this).accountDAO().insert(account);

        //Lấy acc vừa tạo, tạo Tenant
        Account acc = AppDatabase.getInstance(RegisterActivity.this).accountDAO().getAccountByUsernameAndPassword(email, password);
        Tenant tenant = new Tenant(acc.getId(), name, thumUrl, phone, address, age, gender, TenantRoomStatus.NO_ROOM.name(), null);
        Log.d(">>>check register tenant222: ",  tenant.toString());
        AppDatabase.getInstance(RegisterActivity.this).tenantDAO().insertTenant(tenant);
        Toast.makeText(RegisterActivity.this, "Added New Tenant!", Toast.LENGTH_SHORT).show();

        resertInput();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        // Đóng hoạt động hiện tại
        finish();
    }

    private void resertInput(){
        edt_email.setText("");
        edt_password.setText("");
        edt_name.setText("");
        edt_conf_phone.setText("");
        edt_conf_address.setText("");
        edt_conf_age.setText("");
    }

    private void btn_BackListener(){
        R_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
    private void txt_loginListener(){
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
    private void setSpinnerGender(){
        String[] values = {"men", "women",};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        R_spinner_gender.setAdapter(adapter);
    }

    private boolean validateInputs() {
        if (edt_email.getText().toString().trim().isEmpty()) {
            edt_email.setError("Email không được để trống");
            edt_email.requestFocus();
            return false;
        }

        if (edt_password.getText().toString().trim().isEmpty()) {
            edt_password.setError("Password không được để trống");
            edt_password.requestFocus();
            return false;
        }

        if (edt_name.getText().toString().trim().isEmpty()) {
            edt_name.setError("Name không được để trống");
            edt_name.requestFocus();
            return false;
        }

        if (edt_conf_phone.getText().toString().trim().isEmpty()) {
            edt_conf_phone.setError("Phone Number không được để trống");
            edt_conf_phone.requestFocus();
            return false;
        } else if (!Patterns.PHONE.matcher(edt_conf_phone.getText().toString().trim()).matches()) {
            edt_conf_phone.setError("Phone Number không hợp lệ");
            edt_conf_phone.requestFocus();
            return false;
        }

        if (edt_conf_address.getText().toString().trim().isEmpty()) {
            edt_conf_address.setError("Address không được để trống");
            edt_conf_address.requestFocus();
            return false;
        }

        if (edt_conf_age.getText().toString().trim().isEmpty()) {
            edt_conf_age.setError("Age không được để trống");
            edt_conf_age.requestFocus();
            return false;
        } else {
            try {
                int age = Integer.parseInt(edt_conf_age.getText().toString().trim());
                if (age <= 0) {
                    edt_conf_age.setError("Age phải lớn hơn 0");
                    edt_conf_age.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                edt_conf_age.setError("Age phải là số");
                edt_conf_age.requestFocus();
                return false;
            }
        }

        return true;
    }
}