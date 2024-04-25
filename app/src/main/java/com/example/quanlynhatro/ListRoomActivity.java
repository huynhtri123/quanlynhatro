package com.example.quanlynhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.quanlynhatro.Entity.Room;

import java.util.ArrayList;
import java.util.List;

public class ListRoomActivity extends AppCompatActivity {
    private RecyclerView rcv_list_room;
    private ListRoomAdapter listRoomAdapter;
    private List<Room> listRoom;
    private void anhxa(){
        rcv_list_room = findViewById(R.id.rcv_list_room);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);
        getSupportActionBar().hide();
        anhxa();

        listRoomAdapter = new ListRoomAdapter();
        listRoom = new ArrayList<>();
        Room room1 = new Room("ROOM001", "https://s.net.vn/T2FO", "100m2", "Occupied", "3000$");
        Room room2 = new Room("ROOM002", "https://s.net.vn/T2FO", "140m2", "Occupied", "600$");
        Room room3 = new Room("ROOM003", "https://s.net.vn/T2FO", "280m2", "Occupied", "700$");
        Room room4 = new Room("ROOM003", "https://s.net.vn/T2FO", "120m2", "Empty", "300$");
        listRoom.add(room1);
        listRoom.add(room2);
        listRoom.add(room3);
        listRoom.add(room4);

        listRoomAdapter.setData(listRoom);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_list_room.setLayoutManager(linearLayoutManager);
        rcv_list_room.setAdapter(listRoomAdapter);


    }
}