package com.example.buylist.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.buylist.model.Alimento;

import java.util.List;

@Dao
public interface AlimentoDao {

    @Insert
    void insert(Alimento alimento);

    @Delete
    void delete(Alimento alimento);

    @Update
    void update(Alimento alimento);

    @Query("SELECT * FROM alimento")
    List<Alimento> getAll();

}
