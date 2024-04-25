package com.example.quanlynhatro.Entity;

public class Room {
    private String roomCode;
    private String roomUrlImage;
    private String roomSize;
    private String roomStatus;
    private String roomPrice;

    public Room(String roomCode, String roomUrlImage, String roomSize, String roomStatus, String roomPrice) {
        this.roomCode = roomCode;
        this.roomUrlImage = roomUrlImage;
        this.roomSize = roomSize;
        this.roomStatus = roomStatus;
        this.roomPrice = roomPrice;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomUrlImage() {
        return roomUrlImage;
    }

    public void setRoomUrlImage(String roomUrlImage) {
        this.roomUrlImage = roomUrlImage;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }
    @Override
    public String toString() {
        return "Room{" +
                "roomCode='" + roomCode + '\'' +
                ", roomUrlImage='" + roomUrlImage + '\'' +
                ", roomSize='" + roomSize + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                ", roomPrice='" + roomPrice + '\'' +
                '}';
    }
}
