package com.example.quanlynhatro.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "invoice",
        foreignKeys = {
                @ForeignKey(entity = Tenant.class,
                        parentColumns = "id",
                        childColumns = "tenantId",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Room.class,
                        parentColumns = "id",
                        childColumns = "roomId",
                        onDelete = ForeignKey.SET_NULL)
        })
public class Invoice{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer roomId;
    private Integer tenantId;
    private String electricityCost;
    private String waterCost;
    private String parkingCost;
    private String wifiCost;
    private String status;

    public Invoice(Integer roomId, Integer tenantId, String electricityCost, String waterCost, String parkingCost, String wifiCost, String status) {
        this.roomId = roomId;
        this.tenantId = tenantId;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.parkingCost = parkingCost;
        this.wifiCost = wifiCost;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", tenantId=" + tenantId +
                ", electricityCost='" + electricityCost + '\'' +
                ", waterCost='" + waterCost + '\'' +
                ", parkingCost='" + parkingCost + '\'' +
                ", wifiCost='" + wifiCost + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(String electricityCost) {
        this.electricityCost = electricityCost;
    }

    public String getWaterCost() {
        return waterCost;
    }

    public void setWaterCost(String waterCost) {
        this.waterCost = waterCost;
    }

    public String getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(String parkingCost) {
        this.parkingCost = parkingCost;
    }

    public String getWifiCost() {
        return wifiCost;
    }

    public void setWifiCost(String wifiCost) {
        this.wifiCost = wifiCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // Hàm tính tổng hóa đơn tiền phụ phí
    public double calculateTotalBill() {
//        double roomPrice = Double.parseDouble(room.getRoomPrice());
        double electricity = Double.parseDouble(electricityCost.trim());
        double water = Double.parseDouble(waterCost.trim());
        double parking = Double.parseDouble(parkingCost.trim());
        double wifi = Double.parseDouble(wifiCost.trim());

//        return roomPrice + electricity + water + parking + wifi;
        return electricity + water + parking + wifi;
    }

}
