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

import com.example.quanlynhatro.Activity.HomeActivity;
import com.example.quanlynhatro.Activity.InvoiceDetailActivity;
import com.example.quanlynhatro.Activity.ListInvoiceActivity;
import com.example.quanlynhatro.Entity.Invoice;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.SessionManager;
import com.example.quanlynhatro.database.AppDatabase;

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

        Context context = holder.itemView.getContext();
        Room room = AppDatabase.getInstance(context).roomDAO().getRoomById(invoice.getRoomId());
        Tenant tenant = AppDatabase.getInstance(context).tenantDAO().getTenantById(invoice.getTenantId());

        if (room != null && tenant != null){
            holder.LI_tv_roomCode_2.setText(room.getRoomCode());
            holder.LI_tv_tenantName_2.setText(tenant.getName());

            //tinh tổng tiền
            double totalPrice = 0.0;
            try {
                double roomPrice = Double.parseDouble(room.getRoomPrice().trim());
                totalPrice = roomPrice + invoice.calculateTotalBill();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(context, "Lỗi trong quá trình ép kiểu giá tiền!", Toast.LENGTH_SHORT).show();
            }
            Log.d("Total Price", "Total Invoice Price: " + totalPrice);

            holder.LI_tv_totalInvoice_2.setText(String.valueOf(totalPrice));
            holder.LI_tv_status_2.setText(invoice.getStatus());
        }

        holder.LI_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance().setInvoice(invoice);
                Intent intent = new Intent(context, InvoiceDetailActivity.class);
                context.startActivity(intent);
                // Đóng hoạt động hiện tại
                ((Activity) context).finish();
            }
        });

    }
    public int getItemCount() {
        return mListInvoice != null ? mListInvoice.size(): 0;
    }
    public class ListInvoiceViewHolder extends RecyclerView.ViewHolder{
        private TextView LI_tv_roomCode_2;
        private TextView LI_tv_tenantName_2;
        private TextView LI_tv_totalInvoice_2;
        private TextView LI_tv_status_2;
        private Button LI_btn_1;
        public ListInvoiceViewHolder(@NonNull View itemView) {
            super(itemView);

            LI_tv_roomCode_2 = itemView.findViewById(R.id.LI_tv_roomCode_2);
            LI_tv_tenantName_2 = itemView.findViewById(R.id.LI_tv_tenantName_2);
            LI_tv_totalInvoice_2 = itemView.findViewById(R.id.LI_tv_totalInvoice_2);
            LI_tv_status_2 = itemView.findViewById(R.id.LI_tv_status_2);
            LI_btn_1 = itemView.findViewById(R.id.LI_btn_1);
        }
    }
}
