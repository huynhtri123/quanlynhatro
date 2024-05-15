package com.example.quanlynhatro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.quanlynhatro.DAO.AccountDAO;
import com.example.quanlynhatro.DAO.AdminDAO;
import com.example.quanlynhatro.DAO.RoomDAO;
import com.example.quanlynhatro.DAO.TenantDAO;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Room;
import com.example.quanlynhatro.Entity.Tenant;

@Database(entities = {Room.class, Tenant.class, Account.class, Admin.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "app_database.db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null){
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract RoomDAO roomDAO();
    public abstract TenantDAO tenantDAO();
    public abstract AccountDAO accountDAO();
    public abstract AdminDAO adminDAO();
}
