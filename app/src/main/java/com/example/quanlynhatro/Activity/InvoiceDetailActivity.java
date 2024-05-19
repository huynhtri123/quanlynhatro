package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.ContractStatus;
import com.example.quanlynhatro.Enum.InvoiceStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

public class InvoiceDetailActivity extends AppCompatActivity {
    private EditText ID_ed_name, PI_edt_roomcode, PI_edt_roomprice, ID_ed_electricity_cost,
            ID_ed_water_cost, ID_ed_parking_cost, ID_ed_wifi_cost, ID_ed_total;
    private Button ID_btn_make_payment, ID_btn_make_save;
    private ImageView PI_icon_back;
    private Invoice invoice;
    private void anhxa(){
        ID_ed_name = findViewById(R.id.ID_ed_name);
        PI_edt_roomcode = findViewById(R.id.PI_edt_roomcode);
        PI_edt_roomprice = findViewById(R.id.PI_edt_roomprice);
        ID_ed_electricity_cost = findViewById(R.id.ID_ed_electricity_cost);
        ID_ed_water_cost = findViewById(R.id.ID_ed_water_cost);
        ID_ed_parking_cost = findViewById(R.id.ID_ed_parking_cost);
        ID_ed_wifi_cost = findViewById(R.id.ID_ed_wifi_cost);
        ID_ed_total = findViewById(R.id.ID_ed_total);
        ID_btn_make_payment = findViewById(R.id.ID_btn_make_payment);
        ID_btn_make_save = findViewById(R.id.ID_btn_make_save);
        PI_icon_back = findViewById(R.id.PI_icon_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        getSupportActionBar().hide();

        anhxa();
        btn_BackListener();

        Tenant tenant = SessionManager.getInstance().getCurrentTenant();

        //vào trang với tư cách là tenant
        if (tenant != null){
            invoice = AppDatabase.getInstance(InvoiceDetailActivity.this).invoiceDAO().getInvoiceByUserId(tenant.getId());
            Room room = AppDatabase.getInstance(InvoiceDetailActivity.this).roomDAO().getRoomById(invoice.getRoomId());

            double totalPrice = calculatorTotalPrice(room, invoice);

            //set text
            setText(tenant, invoice, room, totalPrice);

            //khoá input
            makeEditTextNonEditable(ID_ed_name);
            makeEditTextNonEditable(PI_edt_roomcode);
            makeEditTextNonEditable(PI_edt_roomprice);
            makeEditTextNonEditable(ID_ed_electricity_cost);
            makeEditTextNonEditable(ID_ed_water_cost);
            makeEditTextNonEditable(ID_ed_parking_cost);
            makeEditTextNonEditable(ID_ed_wifi_cost);
            makeEditTextNonEditable(ID_ed_total);

            // đổi button
            ID_btn_make_save.setVisibility(View.GONE);
            ID_btn_make_payment.setVisibility(View.VISIBLE);

            //tenant thanh toán
            make_payment(invoice, tenant, room);

        } else {
            //vào trang với tư cách là admin, vào từ trang list invoice
            invoice = SessionManager.getInstance().getInvoice();
            tenant = AppDatabase.getInstance(InvoiceDetailActivity.this).tenantDAO().getTenantById(invoice.getTenantId());
            Room room = AppDatabase.getInstance(InvoiceDetailActivity.this).roomDAO().getRoomById(invoice.getRoomId());

            double totalPrice = calculatorTotalPrice(room, invoice);

            //set text
            setText(tenant, invoice, room, totalPrice);

            //khoá input
            makeEditTextNonEditable(ID_ed_name);
            makeEditTextNonEditable(PI_edt_roomcode);
            makeEditTextNonEditable(PI_edt_roomprice);
            makeEditTextNonEditable(ID_ed_total);

            // đổi button
            ID_btn_make_payment.setVisibility(View.GONE);
            ID_btn_make_save.setVisibility(View.VISIBLE);

            //admin nhập tiền cho hoá đơn
            fill_invoice(tenant, invoice, room, totalPrice);
        }

    }

    private double calculatorTotalPrice(Room room, Invoice invoice){
        double totalPrice = 0.0;
        try {
            double roomPrice = Double.parseDouble(room.getRoomPrice().trim());
            totalPrice = roomPrice + invoice.calculateTotalBill();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(InvoiceDetailActivity.this, "Lỗi trong quá trình ép kiểu giá tiền!", Toast.LENGTH_SHORT).show();
        }
        Log.d("Total Price", "Total Invoice Price: " + totalPrice);
        return totalPrice;
    }

    private void make_payment(Invoice invoice, Tenant tenant, Room room){
        ID_btn_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invoice.getStatus().equals(InvoiceStatus.UN_PAID.name())){
                    invoice.setElectricityCost("0");
                    invoice.setWaterCost("0");
                    invoice.setParkingCost("0");
                    invoice.setWifiCost("0");
                    invoice.setStatus(InvoiceStatus.PAID.name());
                    AppDatabase.getInstance(InvoiceDetailActivity.this).invoiceDAO().updateInvoice(invoice);

                    double totalPrice = 0.0;
                    setText(tenant, invoice, room, totalPrice);
                    Toast.makeText(InvoiceDetailActivity.this, "Đã thanh toán!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvoiceDetailActivity.this, "Bạn đã thanh toán trước đó rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fill_invoice(Tenant tenant, Invoice invoice, Room room, double totalPrice){
        ID_btn_make_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    String electric_cost = String.valueOf(ID_ed_electricity_cost.getText());
                    String water_cost = String.valueOf(ID_ed_water_cost.getText());
                    String parking_cost = String.valueOf(ID_ed_parking_cost.getText());
                    String wifi_cost = String.valueOf(ID_ed_wifi_cost.getText());

                    invoice.setElectricityCost(electric_cost);
                    invoice.setWaterCost(water_cost);
                    invoice.setParkingCost(parking_cost);
                    invoice.setWifiCost(wifi_cost);
                    invoice.setStatus(InvoiceStatus.UN_PAID.name());
                    AppDatabase.getInstance(InvoiceDetailActivity.this).invoiceDAO().updateInvoice(invoice);

                    double new_totalPrice = calculatorTotalPrice(room, invoice);
                    setText(tenant, invoice, room, new_totalPrice);
                    Toast.makeText(InvoiceDetailActivity.this, "Updated invoice!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvoiceDetailActivity.this, "Invalid input!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validateInputs() {
        if (ID_ed_electricity_cost.getText().toString().trim().isEmpty()) {
            ID_ed_electricity_cost.setError("Không được để trống trường này");
            ID_ed_electricity_cost.requestFocus();
            return false;
        }
        if (ID_ed_water_cost.getText().toString().trim().isEmpty()) {
            ID_ed_water_cost.setError("Không được để trống trường này");
            ID_ed_water_cost.requestFocus();
            return false;
        }
        if (ID_ed_parking_cost.getText().toString().trim().isEmpty()) {
            ID_ed_parking_cost.setError("Không được để trống trường này");
            ID_ed_parking_cost.requestFocus();
            return false;
        }
        if (ID_ed_wifi_cost.getText().toString().trim().isEmpty()) {
            ID_ed_wifi_cost.setError("Không được để trống trường này");
            ID_ed_wifi_cost.requestFocus();
            return false;
        }

        return true;
    }

    private void setText(Tenant tenant, Invoice invoice, Room room, double totalPrice){
        ID_ed_name.setText(tenant.getName());
        PI_edt_roomcode.setText(room.getRoomCode());
        PI_edt_roomprice.setText(room.getRoomPrice());
        ID_ed_electricity_cost.setText(invoice.getElectricityCost());
        ID_ed_water_cost.setText(invoice.getWaterCost());
        ID_ed_parking_cost.setText(invoice.getParkingCost());
        ID_ed_wifi_cost.setText(invoice.getWifiCost());
        ID_ed_total.setText(String.valueOf(totalPrice));
    }

    private void makeEditTextNonEditable(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setClickable(false);
    }

    private void btn_BackListener(){
        PI_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = SessionManager.getInstance().getCurrentTenant();
                SessionManager.getInstance().clearInvoice();
                if (tenant != null){
                    Intent intent = new Intent(InvoiceDetailActivity.this, HomeActivity.class);
                    startActivity(intent);
                    // Đóng hoạt động hiện tại
                    finish();
                } else {
                    Intent intent = new Intent(InvoiceDetailActivity.this, ListInvoiceActivity.class);
                    startActivity(intent);
                    // Đóng hoạt động hiện tại
                    finish();
                }
            }
        });
    }
}