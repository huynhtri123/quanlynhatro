package com.example.quanlynhatro.Entity;

import java.util.Date;

public class Contract {
    private Tenant tenant;
    private Room room;
    private String rentDay;
    private String status;

    public Contract(Tenant tenant, Room room, String rentDay, String status) {
        this.tenant = tenant;
        this.room = room;
        this.rentDay = rentDay;
        this.status = status;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getRentDay() {
        return rentDay;
    }

    public void setRentDay(String rentDay) {
        this.rentDay = rentDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
