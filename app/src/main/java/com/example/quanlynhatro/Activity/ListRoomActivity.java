package com.example.quanlynhatro.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Adapter.ListRoomAdapter;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListRoomActivity extends AppCompatActivity {
    private RecyclerView rcv_list_room;
    private ListRoomAdapter listRoomAdapter;
    private List<Room> listRoom;
    private ImageView LR_icon_back;
    private AppDatabase db;
    private Button btnCreateRoom;
    private Tenant tenant = SessionManager.getInstance().getCurrentTenant();
    private Admin admin = SessionManager.getInstance().getAdmin();

    private void anhxa(){
        rcv_list_room = findViewById(R.id.rcv_list_room);
        LR_icon_back = findViewById(R.id.LR_icon_back);
        btnCreateRoom = findViewById(R.id.btnCreateRoom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);
        getSupportActionBar().hide();
        anhxa();
        setIconBack();
        createRoomButton();

        listRoomAdapter = new ListRoomAdapter();
        listRoom = new ArrayList<>();

        listRoom = AppDatabase.getInstance(ListRoomActivity.this).roomDAO().getAllRooms();

        listRoomAdapter.setData(listRoom);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_list_room.setLayoutManager(linearLayoutManager);
        rcv_list_room.setAdapter(listRoomAdapter);
    }

    private void setIconBack(){
        LR_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRoomActivity.this, HomeActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
    private void createRoomButton(){
        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin != null){
                    Intent intent = new Intent(ListRoomActivity.this, CreateRoomActivity.class);
                    startActivity(intent);
                    // Đóng hoạt động hiện tại
                    finish();
                } else {
                    Toast.makeText(ListRoomActivity.this, "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}