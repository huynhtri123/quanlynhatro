package com.example.quanlynhatro.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "contract",
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
public class Contract {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer tenantId;
    private Integer roomId;
    private String rentDay;
    private String status;

    public Contract(int tenantId, int roomId, String rentDay, String status) {
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.rentDay = rentDay;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", roomId=" + roomId +
                ", rentDay=" + rentDay +
                ", status='" + status + '\'' +
                '}';
    }
}
