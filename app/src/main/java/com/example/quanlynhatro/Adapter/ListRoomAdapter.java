package com.example.quanlynhatro.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatro.Activity.ContractDetailActivity;
import com.example.quanlynhatro.Activity.HomeActivity;
import com.example.quanlynhatro.Activity.UpdateRoomActivity;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.ContractStatus;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.Enum.TenantRoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        //Đặt phòng
        holder.LR_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = SessionManager.getInstance().getCurrentTenant();
                String status = ContractStatus.NOT_APPROVED.name();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = dateFormat.format(calendar.getTime());

                // Kiểm tra xem tenant có tồn tại và không phải là admin mới cho đặt phòng
                if (tenant != null) {
                    // Kiểm tra xem tenant đã có hợp đồng nào chưa
                    List<Contract> existingContracts = AppDatabase.getInstance(v.getContext())
                            .contractDAO().getContractsByTenantIdAndRoomId(tenant.getId(), room.getId());
                    if (existingContracts != null && existingContracts.size() > 0) {
                        Toast.makeText(v.getContext(), "Bạn đã gửi yêu cầu với phòng này trước đó rồi!", Toast.LENGTH_SHORT).show();
                    } else {
                        //nếu tenant chưa có phòng mới cho đặt
                        if (tenant.getRoomStatus().equals(TenantRoomStatus.NO_ROOM.name())) {
                            //nếu phòng đó còn trống mới cho đặt
                            if (!room.getRoomStatus().equals(RoomStatus.OCCUPIED.name())) {
                                //Tạo Contract mới
                                Contract contract = new Contract(tenant.getId(), room.getId(), dateTime.toString(), status);
                                AppDatabase.getInstance(v.getContext()).contractDAO().insertContract(contract);

                                Toast.makeText(v.getContext(), "Đã gửi yêu cầu đặt phòng!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                                v.getContext().startActivity(intent);
                                // Đóng hoạt động hiện tại
                                ((Activity) v.getContext()).finish();
                            } else {
                                Toast.makeText(v.getContext(), "Phòng không còn trống!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Bạn chỉ có thể đặt tối đa 1 phòng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(v.getContext(), "Admin không thể đặt phòng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //update phong (admin)
        holder.LR_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = SessionManager.getInstance().getCurrentTenant();
                Admin admin = SessionManager.getInstance().getAdmin();
                if (admin != null){
                    int roomId = room.getId();
                    String roomIdString = String.valueOf(roomId);

                    Intent intent = new Intent(v.getContext(), UpdateRoomActivity.class);
                    intent.putExtra("roomIdString", roomIdString);
                    holder.itemView.getContext().startActivity(intent);
                    ((Activity) holder.itemView.getContext()).finish();
                }
            }
        });
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
            LR_image_1 = itemView.findViewById(R.id.LR_image_1);
        }
    }
}
