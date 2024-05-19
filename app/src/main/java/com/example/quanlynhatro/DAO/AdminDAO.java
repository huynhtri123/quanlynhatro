package com.example.quanlynhatro.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quanlynhatro.Entity.Admin;

@Dao
public interface AdminDAO {
    @Insert
    void insert(Admin admin);

    @Query("SELECT * FROM admin WHERE accountId = :accountId")
    Admin getAdminByAccountId(int accountId);


}
