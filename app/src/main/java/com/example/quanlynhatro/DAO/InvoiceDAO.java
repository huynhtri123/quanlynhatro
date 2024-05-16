package com.example.quanlynhatro.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Invoice;

import java.util.List;

@Dao
public interface InvoiceDAO {
    @Insert
    public void insertInvoice(Invoice invoice);

    @Query("SELECT * FROM invoice WHERE id = :id")
    public Invoice getInvoiceById(int id);

    @Update
    public void updateInvoice(Invoice invoice);

    @Delete
    public void deleteInvoice(Invoice invoice);

    @Query("SELECT * FROM invoice")
    public List<Invoice> getAllInvoices();

    @Query("SELECT * FROM invoice WHERE tenantId = :uid")
    public Invoice getInvoiceByUserId(int uid);

    @Query("SELECT * FROM invoice WHERE tenantId = :rid")
    public Invoice getInvoiceByRoomId(int rid);
}
