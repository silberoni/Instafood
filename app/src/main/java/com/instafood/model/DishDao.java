package com.instafood.model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DishDao{
    @Query("select * from Dish ORDER BY lastUpdated DESC")
    LiveData<List<Dish>> getAll();

    // Can be used as update: Insert the object with same primary-key and new data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Dish... dishs);

    @Query("select * from Dish where makerID = :id")
    LiveData<List<Dish>> getByChef(String id);

    @Delete
    void delete(Dish dish);
}
