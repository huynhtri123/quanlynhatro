package com.example.quanlynhatro;

import com.example.quanlynhatro.Activity.MainActivity;
import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.database.AppDatabase;

//khởi tạo tài khoản cho admin ở lần chạy đầu tiên
public class SeedDataInitializer {
    public static void initialize(AppDatabase db) {
        if (db.accountDAO().getAccountByUsernameAndPassword("admin@gmail.com", "12345") == null) {
            Account adminAcc = new Account("admin@gmail.com", "12345");
            db.accountDAO().insert(adminAcc);

            int accountId = db.accountDAO().getAccountByUsernameAndPassword("admin@gmail.com", "12345").getId();

            Admin admin = new Admin(accountId, "Admin", "https://s.net.vn/hMT4");
            db.adminDAO().insert(admin);
        }
    }
}
