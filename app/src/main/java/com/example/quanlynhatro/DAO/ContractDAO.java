package com.example.quanlynhatro.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlynhatro.Entity.Contract;

import java.util.List;

@Dao
public interface ContractDAO {
    @Insert
    public void insertContract(Contract contract);

    @Update
    public void updateContract(Contract contract);

    @Query("SELECT * FROM contract")
    public List<Contract> getAllContracts();

    @Delete
    public void deleteContract(Contract contract);

    @Query("SELECT * FROM contract WHERE id = :contractId")
    public Contract getContractById(int contractId);

    @Query("SELECT * FROM contract WHERE tenantId = :tenantId")
    public List<Contract> getContractsByTenantId(int tenantId);

    @Query("SELECT * FROM contract WHERE tenantId = :tenantId AND roomId = :roomId")
    public List<Contract> getContractsByTenantIdAndRoomId(int tenantId, int roomId);

    @Query("SELECT * FROM contract WHERE status = 'NOT_APPROVED'")
    public List<Contract> getNotApprovedContracts();

    @Query("SELECT * FROM contract WHERE tenantID = :tenantId AND status = 'NOT_APPROVED'")
    public List<Contract> getNotApprovedContractsById(int tenantId);
    @Delete
    public void deleteContract(Contract contract);

}
