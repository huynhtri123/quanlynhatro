package com.example.quanlynhatro;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhatro.Entity.Room;

import java.util.List;

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.ListRoomViewHolder>{
    private List<Room> mListRoom;
    public void setData(List<Room> mListRoom){
        this.mListRoom = mListRoom;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_room_box, parent, false);
        return new ListRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRoomViewHolder holder, int position) {
        Room room = mListRoom.get(position);
        Log.d(">>>check rom in list room: ", room.toString());
        if (room == null){
            return;
        }

        holder.LR_tv_roomCode.setText(room.getRoomCode());
//        Glide.with(holder.itemView.getContext())
//                .load(room.getRoomUrlImage())
//                .into (holder.LR_image_1);
        holder.LR_tv_roomSize.setText(room.getRoomSize());
        holder.LR_tv_roomStatus.setText(room.getRoomStatus());
        holder.LR_tv_roomPrice.setText(room.getRoomPrice());
    }

    @Override
    public int getItemCount() {
        return mListRoom != null ? mListRoom.size(): 0;
    }

    public class ListRoomViewHolder extends RecyclerView.ViewHolder{
        private ImageView LR_image_1;
        private TextView LR_tv_roomCode;
        private TextView LR_tv_roomSize;
        private TextView LR_tv_roomStatus;
        private TextView LR_tv_roomPrice;
        private Button LR_btn_1;
        public ListRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            LR_image_1 = itemView.findViewById(R.id.LR_image_1);
            LR_tv_roomCode = itemView.findViewById(R.id.LR_tv_roomCode);
            LR_tv_roomSize = itemView.findViewById(R.id.LR_tv_roomSize);
            LR_tv_roomStatus = itemView.findViewById(R.id.LR_tv_roomStatus);
            LR_tv_roomPrice = itemView.findViewById(R.id.LR_tv_roomPrice);
            LR_btn_1 = itemView.findViewById(R.id.LR_btn_1);
        }
    }
}
