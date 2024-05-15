package com.example.quanlynhatro.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quanlynhatro.Entity.Account;

@Dao
public interface AccountDAO {
    @Insert
    void insert(Account account);

    @Query("SELECT * FROM account WHERE username = :username AND password = :password")
    Account getAccountByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM account WHERE id = :id")
    Account getAccountById(int id);

    @Query("SELECT COUNT(*) FROM account WHERE username = :email")
    int checkEmailExists(String email);
}
