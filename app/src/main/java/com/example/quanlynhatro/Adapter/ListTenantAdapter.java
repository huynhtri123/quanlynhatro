package com.example.quanlynhatro.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.quanlynhatro.Activity.Tenant_Infor_Activity;
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;
import com.example.quanlynhatro.database.AppDatabase;

import java.util.List;

public class ListTenantAdapter extends RecyclerView.Adapter<ListTenantAdapter.ListTenantViewHolder>{
    private List<Tenant> mListTenant;
    private Tenant mSelectedTenant; // Biến để lưu trữ tenant được chọn

    public void setData(List<Tenant> mListTenant){
        this.mListTenant = mListTenant;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListTenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_tenant_box, parent, false);
        return new ListTenantAdapter.ListTenantViewHolder(view);
    }
    public Tenant getSelectedTenant() {
        return mSelectedTenant;
    }
    @Override
    public void onBindViewHolder(@NonNull ListTenantAdapter.ListTenantViewHolder holder, int position) {
        Tenant tenant = mListTenant.get(position);
        Log.d(">>>check rom in list tenant: ", tenant.toString());
        if (tenant == null){
            return;
        }

        holder.LT_tv_name.setText(tenant.getName());
        holder.LT_tv_address.setText(tenant.getAddress());
        holder.LT_tv_roomSdt.setText(tenant.getPhone());


        Context context = holder.itemView.getContext();
        Tenant tenant_selected = AppDatabase.getInstance(context).tenantDAO().getTenantById(tenant.getId());
        // Thiết lập OnClickListener cho nút
        holder.LT_btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Tenant_Infor_Activity.class);
                intent.putExtra("selectedTenantId", tenant_selected.getId());

                // Thông báo sự thay đổi trong danh sách để cập nhật giao diện
                notifyDataSetChanged();
                context.startActivity(intent);
                // Đóng hoạt động hiện tại
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListTenant != null ? mListTenant.size(): 0;
    }

    public class ListTenantViewHolder extends RecyclerView.ViewHolder{
        private ImageView LT_image_1;
        private TextView LT_tv_name;
        private TextView LT_tv_address;
        private TextView LT_tv_roomSdt;
        private Button LT_btn_detail;
        public ListTenantViewHolder(@NonNull View itemView) {
            super(itemView);
            LT_image_1 = itemView.findViewById(R.id.LT_image_1);
            LT_tv_name = itemView.findViewById(R.id.LT_tv_name);
            LT_tv_address = itemView.findViewById(R.id.LT_tv_address);
            LT_tv_roomSdt = itemView.findViewById(R.id.LT_tv_roomSdt);
            LT_btn_detail = itemView.findViewById(R.id.LT_btn_detail);
        }
    }
}
