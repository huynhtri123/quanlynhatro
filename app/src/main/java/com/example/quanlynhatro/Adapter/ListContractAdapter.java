package com.example.quanlynhatro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quanlynhatro.Activity.ContractDetailActivity;
import com.example.quanlynhatro.Activity.ListContractActivity;
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

        Context context = holder.itemView.getContext();
        Tenant tenant = AppDatabase.getInstance(context).tenantDAO().getTenantById(contract.getTenantId());
        Room room = AppDatabase.getInstance(context).roomDAO().getRoomById(contract.getRoomId());
        Log.d(">>>check tenant, room990: ", tenant.toString() + room.toString());

        holder.LC_tv_Tenant_2.setText(tenant.getName());
        holder.LC_tv_roomCode_2.setText(room.getRoomCode());
        holder.LC_tv_rentDay_2.setText((CharSequence) contract.getRentDay());
        holder.LC_tv_status_2.setText(contract.getStatus());

        int contractId = contract.getId();
        String contractIdString = String.valueOf(contractId);

        holder.LC_btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContractDetailActivity.class);
                intent.putExtra("contractIdString", contractIdString);
                context.startActivity(intent);
                ((Activity) context).finish();
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

        public ListContractViewHolder(@NonNull View itemView) {
            super(itemView);

            LC_tv_Tenant_2 = itemView.findViewById(R.id.LC_tv_Tenant_2);
            LC_tv_roomCode_2 = itemView.findViewById(R.id.LC_tv_roomCode_2);
            LC_tv_rentDay_2 = itemView.findViewById(R.id.LC_tv_rentDay_2);
            LC_tv_status_2 = itemView.findViewById(R.id.LC_tv_status_2);
            LC_btn_approve = itemView.findViewById(R.id.LC_btn_approve);
            LC_btn_approve.setText("Detail");
        }
    }
}
