package com.example.quanlynhatro.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "admin",
        foreignKeys = {
        @ForeignKey(entity = Account.class,
                parentColumns = "id",
                childColumns = "accountId",
                onDelete = ForeignKey.CASCADE)
})
public class Admin {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int accountId;
    private String name;
    private String thumUrl;

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", thumUrl='" + thumUrl + '\'' +
                '}';
    }

    public Admin(int accountId, String name, String thumUrl) {
        this.accountId = accountId;
        this.name = name;
        this.thumUrl = thumUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumUrl() {
        return thumUrl;
    }

    public void setThumUrl(String thumUrl) {
        this.thumUrl = thumUrl;
    }
}
