package com.example.quanlynhatro.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.R;


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

        holder.LC_tv_Tenant_2.setText(contract.getTenant().getName());
        holder.LC_tv_roomCode_2.setText(contract.getRoom().getRoomCode());
        holder.LC_tv_rentDay_2.setText((CharSequence) contract.getRentDay());
        holder.LC_tv_status_2.setText(contract.getStatus());
    }
    public int getItemCount() {
        return mListContract != null ? mListContract.size(): 0;
    }
    public class ListContractViewHolder extends RecyclerView.ViewHolder{
        private TextView LC_tv_Tenant_2;
        private TextView LC_tv_roomCode_2;
        private TextView LC_tv_rentDay_2;
        private TextView LC_tv_status_2;
//        private Button LR_btn_1;
        public ListContractViewHolder(@NonNull View itemView) {
            super(itemView);

            LC_tv_Tenant_2 = itemView.findViewById(R.id.LC_tv_Tenant_2);
            LC_tv_roomCode_2 = itemView.findViewById(R.id.LC_tv_roomCode_2);
            LC_tv_rentDay_2 = itemView.findViewById(R.id.LC_tv_rentDay_2);
            LC_tv_status_2 = itemView.findViewById(R.id.LC_tv_status_2);
//            LR_btn_1 = itemView.findViewById(R.id.LR_btn_1);
        }
    }
}
