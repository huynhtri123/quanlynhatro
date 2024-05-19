package com.example.quanlynhatro.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tenant",
        foreignKeys = {
                @ForeignKey(entity = Room.class,
                        parentColumns = "id",
                        childColumns = "roomId",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Account.class,
                        parentColumns = "id",
                        childColumns = "accountId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Tenant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int accountId;
    private String name;
    private String thumUrl;
    private String phone;
    private String address;
    private String age;
    private String gender;
    private String roomStatus;
    private Integer roomId; //Interger để có thể null

    // Constructor
    public Tenant(int accountId, String name, String thumUrl, String phone, String address, String age, String gender, String roomStatus, Integer roomId) {
        this.accountId = accountId;
        this.name = name;
        this.thumUrl = thumUrl;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.roomStatus = roomStatus;
        this.roomId = roomId;
    }

    // Getters and Setters
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", thumUrl='" + thumUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}
