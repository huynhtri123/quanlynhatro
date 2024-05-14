package com.example.quanlynhatro.Entity;

public class Invoice {
    private Room room;
    private Tenant tenant;
    private String electricityCost;
    private String waterCost;
    private String parkingCost;
    private String wifiCost;
    private String status;

    public Invoice(Room room, Tenant tenant, String electricityCost, String waterCost, String parkingCost, String wifiCost, String status) {
        this.room = room;
        this.tenant = tenant;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.parkingCost = parkingCost;
        this.wifiCost = wifiCost;
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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
    // Hàm tính tổng hóa đơn
    public double calculateTotalBill() {
        double roomPrice = Double.parseDouble(room.getRoomPrice());
        double electricity = Double.parseDouble(electricityCost);
        double water = Double.parseDouble(waterCost);
        double parking = Double.parseDouble(parkingCost);
        double wifi = Double.parseDouble(wifiCost);

        return roomPrice + electricity + water + parking + wifi;
    }
}
