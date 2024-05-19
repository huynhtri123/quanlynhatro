package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlynhatro.DAO.RoomDAO;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

public class RoomDetailActivity extends AppCompatActivity {
    ImageView RD_icon_back;
    ImageView RD_image_1;
    EditText RD_edt_roomcode;
    EditText RD_edt_roomprice;
    EditText RD_ed_roomSize;
    EditText RD_ed_status;
    Button RD_btn_make_save;
    Button RD_btn_make_delete;
    private void anhxa(){
        RD_icon_back = findViewById(R.id.RD_icon_back);
        RD_image_1 = findViewById(R.id.RD_image_1);
        RD_edt_roomcode = findViewById(R.id.RD_edt_roomcode);
        RD_edt_roomprice = findViewById(R.id.RD_edt_roomprice);
        RD_ed_roomSize = findViewById(R.id.RD_ed_roomSize);
        RD_ed_status = findViewById(R.id.RD_ed_status);
        RD_btn_make_save = findViewById(R.id.RD_btn_make_save);
        RD_btn_make_delete = findViewById(R.id.RD_btn_make_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        getSupportActionBar().hide();
        
        anhxa();
        int room_selectedID = getIntent().getIntExtra("room_selectedID", -1);
        Room room = AppDatabase.getInstance(RoomDetailActivity.this).roomDAO().getRoomById(room_selectedID);
        setText(room);
        setIconBack();
        setBtnDeleteOnclick(room);
        setBtnSaveOnClick(room);
    }
    private void setText(Room room){
        Glide.with(this)
                .load(room.getRoomUrlImage())
                .into(RD_image_1);
        RD_edt_roomcode.setText(room.getRoomCode());
        RD_edt_roomprice.setText(room.getRoomPrice());
        RD_ed_roomSize.setText(room.getRoomSize());
        RD_ed_status.setText(room.getRoomStatus());
    }
    private void setIconBack(){
        RD_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomDetailActivity.this, ListRoomActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
    private void setBtnDeleteOnclick(Room room){
        RD_btn_make_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = SessionManager.getInstance().getCurrentTenant();
                if (tenant == null){
                    // Gọi phương thức xóa Tenant khỏi cơ sở dữ liệu
                    deleteRoomFromDatabase(room,v);
                } else {
                    Toast.makeText(v.getContext(), "Bạn không có quyền thực hiện!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void deleteRoomFromDatabase(Room room,View v){
        if (room.getRoomStatus().equals(RoomStatus.EMPTY.toString())){
            AppDatabase.getInstance(RoomDetailActivity.this).roomDAO().deleteRoom(room);
            Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RoomDetailActivity.this, ListRoomActivity.class);
            startActivity(intent);
            // Đóng hoạt động hiện tại
            finish();
        }
        else {
            Toast.makeText(v.getContext(), "Phòng đang có người thuê!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setBtnSaveOnClick(Room room){
        RD_btn_make_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = SessionManager.getInstance().getCurrentTenant();
                if (tenant == null){
                    // Gọi phương thức xóa Tenant khỏi cơ sở dữ liệu
                    saveRoomFromDatabase(room,v);
                } else {
                    Toast.makeText(v.getContext(), "Bạn không có quyền thực hiện!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void saveRoomFromDatabase(Room room,View v){
        if (validateInputs()){
            room.setRoomCode(RD_edt_roomcode.getText().toString());
            room.setRoomPrice(RD_edt_roomprice.getText().toString());
            room.setRoomSize(RD_ed_roomSize.getText().toString());
            AppDatabase.getInstance(RoomDetailActivity.this).roomDAO().updateRoom(room);
            Toast.makeText(v.getContext(), "SAVED!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(v.getContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateInputs() {
        if (RD_edt_roomcode.getText().toString().trim().isEmpty()) {
            RD_edt_roomcode.setError("Không được để trống trường này");
            RD_edt_roomcode.requestFocus();
            return false;
        }
        if (RD_edt_roomprice.getText().toString().trim().isEmpty()) {
            RD_edt_roomprice.setError("Không được để trống trường này");
            RD_edt_roomprice.requestFocus();
            return false;
        }
        if (RD_ed_roomSize.getText().toString().trim().isEmpty()) {
            RD_ed_roomSize.setError("Không được để trống trường này");
            RD_ed_roomSize.requestFocus();
            return false;
        }

        return true;
    }
}