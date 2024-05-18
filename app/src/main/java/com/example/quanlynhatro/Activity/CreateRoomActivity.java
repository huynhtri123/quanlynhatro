package com.example.quanlynhatro.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.database.AppDatabase;

import java.util.List;

public class CreateRoomActivity extends AppCompatActivity {
    private EditText etRoomCode, etRoomUrlImage, etRoomSize, etRoomPrice;
    private Button btnSave;
    private AppDatabase db;
    private ImageView LR_icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        anhxa();
        setIconBack();

        db = AppDatabase.getInstance(getApplicationContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom(db);
            }
        });
//
    }
    private void anhxa(){
        etRoomCode = findViewById(R.id.etRoomCode);
        etRoomUrlImage = findViewById(R.id.etRoomUrlImage);
        etRoomSize = findViewById(R.id.etRoomSize);
        etRoomPrice = findViewById(R.id.etRoomPrice);
        btnSave = findViewById(R.id.btnSave);
        LR_icon_back = findViewById(R.id.LR_icon_back);
    }
    private void saveRoom(AppDatabase db) {
        String roomCode = etRoomCode.getText().toString();
        String roomUrlImage = etRoomUrlImage.getText().toString();
        String roomSize = etRoomSize.getText().toString();
        String roomPrice = etRoomPrice.getText().toString();

        if (roomCode.isEmpty() || roomUrlImage.isEmpty() || roomSize.isEmpty() || roomPrice.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Room room = new Room(roomCode, roomUrlImage, roomSize, RoomStatus.EMPTY.name(), roomPrice);
        db.roomDAO().insertRoom(room);

        Toast.makeText(this, "Phòng đã được lưu", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CreateRoomActivity.this, ListRoomActivity.class);
        startActivity(intent);

        // Đóng hoạt động hiện tại
        finish();
    }
    private void setIconBack(){
        LR_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRoomActivity.this, ListRoomActivity.class);
                startActivity(intent);
                // Đóng hoạt động hiện tại
                finish();
            }
        });
    }
}