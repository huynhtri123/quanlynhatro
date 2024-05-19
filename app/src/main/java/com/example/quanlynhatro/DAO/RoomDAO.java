package com.example.quanlynhatro.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlynhatro.Entity.Contract;
import com.example.quanlynhatro.Entity.Room;

import java.util.List;

@Dao
public interface RoomDAO {
    @Insert
    public void insertRoom(Room room);

    @Query("SELECT * FROM room")
    List<Room> getAllRooms();

    @Update
    void updateRoom(Room room);

    @Query("SELECT * FROM room WHERE id = :id")
    Room getRoomById(int id);
    @Delete
    public void deleteRoom(Room room);
}
