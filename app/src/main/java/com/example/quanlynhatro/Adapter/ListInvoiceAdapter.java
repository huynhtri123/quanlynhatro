package com.example.quanlynhatro.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.R;

import java.util.List;

public class ListInvoiceAdapter extends RecyclerView.Adapter<ListInvoiceAdapter.ListInvoiceViewHolder>{
    private List<Invoice> mListInvoice;
    public void setData(List<Invoice> mListInvoice){
        this.mListInvoice = mListInvoice;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListInvoiceAdapter.ListInvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_invoice_box, parent, false);
        return new ListInvoiceAdapter.ListInvoiceViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ListInvoiceAdapter.ListInvoiceViewHolder holder, int position) {
        Invoice invoice = mListInvoice.get(position);
        Log.d(">>>check rom in list room: ", invoice.toString());
        if (invoice == null){
            return;
        }

        holder.LI_tv_roomCode_2.setText(invoice.getRoom().getRoomCode());
        holder.LI_tv_tenantName_2.setText(invoice.getTenant().getName());
        holder.LI_tv_totalInvoice_2.setText(String.valueOf((int) invoice.calculateTotalBill()));
        holder.LI_tv_status_2.setText(invoice.getStatus());
    }
    public int getItemCount() {
        return mListInvoice != null ? mListInvoice.size(): 0;
    }
    public class ListInvoiceViewHolder extends RecyclerView.ViewHolder{
        private TextView LI_tv_roomCode_2;
        private TextView LI_tv_tenantName_2;
        private TextView LI_tv_totalInvoice_2;
        private TextView LI_tv_status_2;
        //        private Button LR_btn_1;
        public ListInvoiceViewHolder(@NonNull View itemView) {
            super(itemView);

            LI_tv_roomCode_2 = itemView.findViewById(R.id.LI_tv_roomCode_2);
            LI_tv_tenantName_2 = itemView.findViewById(R.id.LI_tv_tenantName_2);
            LI_tv_totalInvoice_2 = itemView.findViewById(R.id.LI_tv_totalInvoice_2);
            LI_tv_status_2 = itemView.findViewById(R.id.LI_tv_status_2);
//            LR_btn_1 = itemView.findViewById(R.id.LR_btn_1);
        }
    }
}
