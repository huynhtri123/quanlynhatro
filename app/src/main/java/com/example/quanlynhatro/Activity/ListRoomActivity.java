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

import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Adapter.ListRoomAdapter;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.R;
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
//////////chèn dữ liệu cứng cho Room, chạy 1 lần xong comment nó lại nha
//        Room room1 = new Room("ROOM001", "https://s.net.vn/T2FO", "100m2", RoomStatus.EMPTY.toString(), "3000");
//        Room room2 = new Room("ROOM002", "https://s.net.vn/T2FO", "140m2", RoomStatus.EMPTY.toString(), "600");
//        Room room3 = new Room("ROOM003", "https://s.net.vn/T2FO", "280m2", RoomStatus.EMPTY.toString(), "700");
//        Room room4 = new Room("ROOM004", "https://s.net.vn/T2FO", "120m2", RoomStatus.EMPTY.toString(), "300");
//        AppDatabase.getInstance(ListRoomActivity.this).roomDAO().insertRoom(room1);
//        AppDatabase.getInstance(ListRoomActivity.this).roomDAO().insertRoom(room2);
//        AppDatabase.getInstance(ListRoomActivity.this).roomDAO().insertRoom(room3);
//        AppDatabase.getInstance(ListRoomActivity.this).roomDAO().insertRoom(room4);

        listRoom = AppDatabase.getInstance(ListRoomActivity.this).roomDAO().getAllRooms();

//        db = AppDatabase.getInstance(getApplicationContext());
//        // Kiểm tra nếu db là null
//        if (db == null) {
//            Log.e(TAG, "Cơ sở dữ liệu không được khởi tạo!");
//            return;
//        }
//        List<Room> roomList = db.roomDAO().getAllRooms();
//
//        for (Room r : roomList) {
//            Log.d(TAG, r.toString());
//            listRoom.add(r);
//        }

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
                Intent intent = new Intent(ListRoomActivity.this, CreateRoomActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
}