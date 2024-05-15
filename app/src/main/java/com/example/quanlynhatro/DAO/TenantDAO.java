package com.example.quanlynhatro.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlynhatro.Entity.Tenant;

import java.util.List;

@Dao
public interface TenantDAO {
    @Insert
    void insertTenant(Tenant tenant);

    @Query("SELECT * FROM tenant")
    List<Tenant> getListTenant();

    @Query("SELECT * FROM tenant WHERE accountId = :accountId")
    Tenant getTenantByAccountId(int accountId);

    @Update
    void updateTenant(Tenant tenant);

    @Query("SELECT * FROM tenant WHERE id = :id")
    Tenant getTenantById(int id);

}
