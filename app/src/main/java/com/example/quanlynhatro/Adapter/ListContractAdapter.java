package com.example.quanlynhatro.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quanlynhatro.Activity.ListContractActivity;
import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.Enum.ContractStatus;
import com.example.quanlynhatro.Enum.RoomStatus;
import com.example.quanlynhatro.Enum.TenantRoomStatus;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;


import java.util.List;

public class ListContractAdapter extends RecyclerView.Adapter<ListContractAdapter.ListContractViewHolder>{
    private List<Contract> mListContract;
    public void setData(List<Contract> mListContract){
        this.mListContract = mListContract;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListContractAdapter.ListContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_contract_box, parent, false);
        return new ListContractAdapter.ListContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListContractAdapter.ListContractViewHolder holder, int position) {
        Contract contract = mListContract.get(position);
        Log.d(">>>check rom in list room: ", contract.toString());
        if (contract == null){
            return;
        }

        Context context = holder.itemView.getContext();
        Tenant tenant = AppDatabase.getInstance(context).tenantDAO().getTenantById(contract.getTenantId());
        Room room = AppDatabase.getInstance(context).roomDAO().getRoomById(contract.getRoomId());
        Log.d(">>>check tenant, room990: ", tenant.toString() + room.toString());

        holder.LC_tv_Tenant_2.setText(tenant.getName());
        holder.LC_tv_roomCode_2.setText(room.getRoomCode());
        holder.LC_tv_rentDay_2.setText((CharSequence) contract.getRentDay());
        holder.LC_tv_status_2.setText(contract.getStatus());

        // Lưu lại giá trị position vào biến cuối cùng (final)
        final int currentPosition = position;

        // đổi button
        if (contract.getStatus().equals(ContractStatus.NOT_APPROVED.name())) {
            holder.LC_btn_remove.setVisibility(View.GONE);
            holder.LC_btn_approve.setVisibility(View.VISIBLE);
        } else {
            holder.LC_btn_remove.setVisibility(View.VISIBLE);
            holder.LC_btn_approve.setVisibility(View.GONE);
        }

        // duyệt đặt phòng
        holder.LC_btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room.getRoomStatus().equals(RoomStatus.EMPTY.name())){
                    if (tenant.getRoomStatus().equals(TenantRoomStatus.NO_ROOM.name())){
                        // đổi trạng thái cho tenant:
                        tenant.setRoomStatus(TenantRoomStatus.HAS_ROOM.name());
                        tenant.setRoomId(room.getId());
                        AppDatabase.getInstance(context).tenantDAO().updateTenant(tenant);

                        // đổi trạng thái phòng
                        room.setRoomStatus(RoomStatus.OCCUPIED.name());
                        AppDatabase.getInstance(context).roomDAO().updateRoom(room);

                        // đổi trạng thái hợp đồng
                        contract.setStatus(ContractStatus.APPROVED.name());
                        AppDatabase.getInstance(context).contractDAO().updateContract(contract);

                        //tạo hoá đơn
                        Invoice invoice = new Invoice(room.getId(), tenant.getId(), "0", "0", "0", "0", "0");
                        AppDatabase.getInstance(context).invoiceDAO().insertInvoice(invoice);

                        notifyDataSetChanged();
                        notifyItemChanged(currentPosition);

                        Toast.makeText(v.getContext(), "Đã duyệt hợp đồng!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Mỗi tenant chỉ có thể đặt tối đa 1 phòng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "Phòng không còn trống!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // huỷ duyệt hợp đồng
        holder.LC_btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // đổi trạng thái cho tenant:
                tenant.setRoomStatus(TenantRoomStatus.NO_ROOM.name());
                tenant.setRoomId(null);
                AppDatabase.getInstance(context).tenantDAO().updateTenant(tenant);

                // đổi trạng thái phòng
                room.setRoomStatus(RoomStatus.EMPTY.name());
                AppDatabase.getInstance(context).roomDAO().updateRoom(room);

                // đổi trạng thái hợp đồng
                contract.setStatus(ContractStatus.NOT_APPROVED.name());
                AppDatabase.getInstance(context).contractDAO().updateContract(contract);

                //xoá invoice
                Invoice invoice = AppDatabase.getInstance(context).invoiceDAO().getInvoiceByUserId(tenant.getId());
                AppDatabase.getInstance(context).invoiceDAO().deleteInvoice(invoice);

                notifyDataSetChanged();
                notifyItemChanged(currentPosition);

                Toast.makeText(v.getContext(), "Đã huỷ phê duyệt hợp đồng này!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getItemCount() {
        return mListContract != null ? mListContract.size(): 0;
    }
    public class ListContractViewHolder extends RecyclerView.ViewHolder{
        private TextView LC_tv_Tenant_2;
        private TextView LC_tv_roomCode_2;
        private TextView LC_tv_rentDay_2;
        private TextView LC_tv_status_2;
        private Button LC_btn_approve;
        private Button LC_btn_remove;

        public ListContractViewHolder(@NonNull View itemView) {
            super(itemView);

            LC_tv_Tenant_2 = itemView.findViewById(R.id.LC_tv_Tenant_2);
            LC_tv_roomCode_2 = itemView.findViewById(R.id.LC_tv_roomCode_2);
            LC_tv_rentDay_2 = itemView.findViewById(R.id.LC_tv_rentDay_2);
            LC_tv_status_2 = itemView.findViewById(R.id.LC_tv_status_2);
            LC_btn_approve = itemView.findViewById(R.id.LC_btn_approve);
            LC_btn_remove = itemView.findViewById(R.id.LC_btn_remove);
        }
    }
}
