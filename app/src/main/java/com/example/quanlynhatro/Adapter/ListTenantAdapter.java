package com.example.quanlynhatro.Adapter;

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
import com.example.quanlynhatro.Entity.Tenant;
import com.example.quanlynhatro.R;

import java.util.List;

public class ListTenantAdapter extends RecyclerView.Adapter<ListTenantAdapter.ListTenantViewHolder>{
    private List<Tenant> mListTenant;
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
//        Glide.with(holder.itemView.getContext())
//            .load(tenant.getThumUrl())
//            .into (holder.LT_image_1);
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
        private Button LT_btn_1;
        public ListTenantViewHolder(@NonNull View itemView) {
            super(itemView);
            LT_image_1 = itemView.findViewById(R.id.LT_image_1);
            LT_tv_name = itemView.findViewById(R.id.LT_tv_name);
            LT_tv_address = itemView.findViewById(R.id.LT_tv_address);
            LT_tv_roomSdt = itemView.findViewById(R.id.LT_tv_roomSdt);
            LT_btn_1 = itemView.findViewById(R.id.LT_btn_1);
        }
    }
}
