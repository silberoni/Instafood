package com.instafood.model;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChefDao {
    @Query("select * from Chef")
    List<Chef> getAll();

    // TODO: build single chef select
    @Query("select * from Chef where id = :id")
    Chef getChef(String id);

    // Can be used as update: Insert the object with same primary-key and new data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Chef... chefs);

    @Delete
    void delete(Chef chef);
}
