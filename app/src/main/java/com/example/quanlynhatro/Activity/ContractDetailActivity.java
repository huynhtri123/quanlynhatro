package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.ContractStatus;
import com.example.quanlynhatro.Enum.InvoiceStatus;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.Enum.TenantRoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

public class ContractDetailActivity extends AppCompatActivity {
    private EditText PI_edt_name;
    private EditText PI_edt_phone;
    private EditText PI_edt_address;
    private EditText PI_edt_roomcode;
    private EditText PI_edt_roomprice;
    private EditText PI_edt_room_createdday;
    private EditText PI_edt_roomstatus;
    private Button PI_btn_1;
    private Button PI_btn_2;
    private ImageView PI_icon_back;
    private Tenant tenant = SessionManager.getInstance().getCurrentTenant();
    private Admin admin = SessionManager.getInstance().getAdmin();
    private void anhxa(){
        PI_edt_name = findViewById(R.id.PI_edt_name);
        PI_edt_phone = findViewById(R.id.PI_edt_phone);
        PI_edt_address = findViewById(R.id.PI_edt_address);
        PI_edt_roomcode = findViewById(R.id.PI_edt_roomcode);
        PI_edt_roomprice = findViewById(R.id.PI_edt_roomprice);
        PI_edt_room_createdday = findViewById(R.id.PI_edt_room_createdday);
        PI_edt_roomstatus = findViewById(R.id.PI_edt_roomstatus);
        PI_btn_1 = findViewById(R.id.PI_btn_1);
        PI_btn_2 = findViewById(R.id.PI_btn_2);
        PI_icon_back = findViewById(R.id.PI_icon_back);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_detail);
        getSupportActionBar().hide();

        anhxa();
        btn_BackListener();

        Intent intent = getIntent();
        String contractIdString = intent.getStringExtra("contractIdString");
        int contractId = Integer.parseInt(contractIdString);

        Contract contract = AppDatabase.getInstance(this).contractDAO().getContractById(contractId);
        Log.d(">>check contract5: " , contract.toString());
        Room room = AppDatabase.getInstance(this).roomDAO().getRoomById(contract.getRoomId());

        if (tenant != null){    //đang tư cách là tenant
            Log.d(">>checkUser12: tenant" , tenant.getName());
            setTextButton("Cancel", "Terminate");

            //đổi button
            if (contract.getStatus().equals(ContractStatus.APPROVED.name())){
                PI_btn_1.setVisibility(View.GONE);
                PI_btn_2.setVisibility(View.VISIBLE);
            } else if (contract.getStatus().equals(ContractStatus.NOT_APPROVED.name())) {
                PI_btn_1.setVisibility(View.VISIBLE);
                PI_btn_2.setVisibility(View.GONE);
            }

            setText(contract, room);

            //huỷ đặt phòng
            cancelRequest(contract);
            //gửi yêu cầu trả phòng
            terminate_request(contract);

        } else if (admin != null){  //đang tư cách là admin
            Log.d(">>checkUser12: admin" , admin.getName());
            setTextButton("Approve", "Terminate");
            PI_btn_1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.xanh1));

            //lấy tennat:
            tenant = AppDatabase.getInstance(this).tenantDAO().getTenantById(contract.getTenantId());
            setText(contract, room);

            //duyệt hợp đồng
            approveContract(contract, room);
            //huỷ hợp đồng
            terminateContract(contract, room);

        } else {
            Log.d(">>checkUser12: " , "ERROR");
        }

    }

    private void setText(Contract contract, Room room){
        PI_edt_name.setText(tenant.getName());
        PI_edt_phone.setText(tenant.getPhone());
        PI_edt_address.setText(tenant.getAddress());
        PI_edt_roomcode.setText(room.getRoomCode());
        PI_edt_roomprice.setText(room.getRoomPrice() + "$");
        PI_edt_room_createdday.setText(contract.getRentDay());
        PI_edt_roomstatus.setText(contract.getStatus());
    }

    private void cancelRequest(Contract contract){
        PI_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xoá contract thôi
                AppDatabase.getInstance(ContractDetailActivity.this).contractDAO().deleteContract(contract);
                Toast.makeText(ContractDetailActivity.this, "Đã huỷ yêu cầu đặt phòng!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ContractDetailActivity.this, ListContractActivity.class);
                ContractDetailActivity.this.startActivity(intent);
                ContractDetailActivity.this.finish();
            }
        });
    }

    private void terminate_request(Contract contract){
        PI_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contract.getStatus().equals(ContractStatus.TERMINATE_REQUEST.name())){
                    contract.setStatus(ContractStatus.TERMINATE_REQUEST.name());
                    AppDatabase.getInstance(ContractDetailActivity.this).contractDAO().updateContract(contract);
                    Toast.makeText(ContractDetailActivity.this, "Đã gửi yêu cầu trả phòng!", Toast.LENGTH_SHORT).show();

                    //set status giao dien
                    PI_edt_roomstatus.setText(ContractStatus.TERMINATE_REQUEST.name());
                } else {
                    Toast.makeText(ContractDetailActivity.this, "Đã gửi yêu cầu trước đó rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void approveContract(Contract contract, Room room){
        PI_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room.getRoomStatus().equals(RoomStatus.EMPTY.name())){
                    if (tenant.getRoomStatus().equals(TenantRoomStatus.NO_ROOM.name())){
                        // đổi trạng thái cho tenant:
                        tenant.setRoomStatus(TenantRoomStatus.HAS_ROOM.name());
                        tenant.setRoomId(room.getId());
                        AppDatabase.getInstance(ContractDetailActivity.this).tenantDAO().updateTenant(tenant);

                        // đổi trạng thái phòng
                        room.setRoomStatus(RoomStatus.OCCUPIED.name());
                        AppDatabase.getInstance(ContractDetailActivity.this).roomDAO().updateRoom(room);

                        // đổi trạng thái hợp đồng
                        contract.setStatus(ContractStatus.APPROVED.name());
                        AppDatabase.getInstance(ContractDetailActivity.this).contractDAO().updateContract(contract);

                        //tạo hoá đơn
                        Invoice invoice = new Invoice(room.getId(), tenant.getId(), "0", "0", "0", "0", InvoiceStatus.UN_PAID.name());
                        AppDatabase.getInstance(ContractDetailActivity.this).invoiceDAO().insertInvoice(invoice);

                        //set status giao dien
                        PI_edt_roomstatus.setText(ContractStatus.APPROVED.name());

                        Toast.makeText(ContractDetailActivity.this, "Đã duyệt hợp đồng!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ContractDetailActivity.this, "Mỗi tenant chỉ có thể đặt tối đa 1 phòng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ContractDetailActivity.this, "Phòng không còn trống!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void terminateContract(Contract contract, Room room){
        PI_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //huỷ phê duyệt
                if (contract.getStatus().equals(ContractStatus.APPROVED.name())){
                    // đổi trạng thái cho tenant:
                    tenant.setRoomStatus(TenantRoomStatus.NO_ROOM.name());
                    tenant.setRoomId(null);
                    AppDatabase.getInstance(ContractDetailActivity.this).tenantDAO().updateTenant(tenant);

                    // đổi trạng thái phòng
                    room.setRoomStatus(RoomStatus.EMPTY.name());
                    AppDatabase.getInstance(ContractDetailActivity.this).roomDAO().updateRoom(room);

                    // đổi trạng thái hợp đồng
                    contract.setStatus(ContractStatus.NOT_APPROVED.name());
                    AppDatabase.getInstance(ContractDetailActivity.this).contractDAO().updateContract(contract);

                    //xoá invoice
                    Invoice invoice = AppDatabase.getInstance(ContractDetailActivity.this).invoiceDAO().getInvoiceByUserId(tenant.getId());
                    AppDatabase.getInstance(ContractDetailActivity.this).invoiceDAO().deleteInvoice(invoice);

                    //set status giao dien
                    PI_edt_roomstatus.setText(ContractStatus.NOT_APPROVED.name());

                    Toast.makeText(v.getContext(), "Đã huỷ phê duyệt hợp đồng này!", Toast.LENGTH_SHORT).show();
                } else //huỷ hợp đồng
                    if (contract.getStatus().equals(ContractStatus.TERMINATE_REQUEST.name())) {
                        // đổi trạng thái cho tenant:
                        tenant.setRoomStatus(TenantRoomStatus.NO_ROOM.name());
                        tenant.setRoomId(null);
                        AppDatabase.getInstance(ContractDetailActivity.this).tenantDAO().updateTenant(tenant);

                        // đổi trạng thái phòng
                        room.setRoomStatus(RoomStatus.EMPTY.name());
                        AppDatabase.getInstance(ContractDetailActivity.this).roomDAO().updateRoom(room);

                        //xoá invoice
                        Invoice invoice = AppDatabase.getInstance(ContractDetailActivity.this).invoiceDAO().getInvoiceByUserId(tenant.getId());
                        AppDatabase.getInstance(ContractDetailActivity.this).invoiceDAO().deleteInvoice(invoice);

                        // xoá hợp đồng
                        contract.setStatus(ContractStatus.TERMINATED.name());
                        contract.setTenantId(null);
                        contract.setRoomId(null);
                        AppDatabase.getInstance(ContractDetailActivity.this).contractDAO().deleteContract(contract);

                        Toast.makeText(v.getContext(), "Đã xoá hợp đồng này!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ContractDetailActivity.this, ListContractActivity.class);
                        ContractDetailActivity.this.startActivity(intent);
                        ContractDetailActivity.this.finish();

                } else {
                    Toast.makeText(v.getContext(), "Hợp đồng này chưa được duyệt!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setTextButton(String btn1, String btn2){
        PI_btn_1.setText(btn1);
        PI_btn_2.setText(btn2);
    }

    private void btn_BackListener(){
        PI_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractDetailActivity.this, ListContractActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
}