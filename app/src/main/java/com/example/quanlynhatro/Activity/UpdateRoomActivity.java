package com.example.quanlynhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.database.AppDatabase;

public class UpdateRoomActivity extends AppCompatActivity {
    private EditText PI_edt_room_code;
    private EditText PI_edt_room_price;
    private EditText PI_edt_room_size;
    private EditText PI_edt_room_image;
    private Button PI_btn_update;
    private ImageView PI_icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        getSupportActionBar().hide();
        anhxa();
        setIconBack();

        Intent intent = getIntent();
        String roomIdString = intent.getStringExtra("roomIdString");
        int roomId = Integer.parseInt(roomIdString);

        Room room = AppDatabase.getInstance(this).roomDAO().getRoomById(roomId);
        if (room != null){
            setText(room);
            //update room
            PI_btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String roomCode = PI_edt_room_code.getText().toString();
                    String roomUrlImage = PI_edt_room_image.getText().toString();
                    String roomSize = PI_edt_room_size.getText().toString();
                    String roomPrice = PI_edt_room_price.getText().toString();

                    if (roomCode.isEmpty() || roomUrlImage.isEmpty() || roomSize.isEmpty() || roomPrice.isEmpty()) {
                        Toast.makeText(UpdateRoomActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    room.setRoomCode(roomCode);
                    room.setRoomPrice(roomPrice);
                    room.setRoomSize(roomSize);
                    room.setRoomUrlImage(roomUrlImage);
                    AppDatabase.getInstance(UpdateRoomActivity.this).roomDAO().updateRoom(room);
                    setText(room);
                    Toast.makeText(UpdateRoomActivity.this, "Updated room!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setText(Room room){
        PI_edt_room_code.setText(room.getRoomCode());
        PI_edt_room_price.setText(room.getRoomPrice());
        PI_edt_room_size.setText(room.getRoomSize());
        PI_edt_room_image.setText(room.getRoomUrlImage());
    }

    private void anhxa(){
        PI_edt_room_code = findViewById(R.id.PI_edt_room_code);
        PI_edt_room_price = findViewById(R.id.PI_edt_room_price);
        PI_edt_room_size = findViewById(R.id.PI_edt_room_size);
        PI_edt_room_image = findViewById(R.id.PI_edt_room_image);
        PI_btn_update = findViewById(R.id.PI_btn_update);
        PI_icon_back = findViewById(R.id.PI_icon_back);
    }

    private void setIconBack(){
        PI_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateRoomActivity.this, ListRoomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}