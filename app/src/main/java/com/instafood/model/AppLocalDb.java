package com.instafood.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.instafood.MainActivity;

@Database(entities = {Dish.class, Chef.class}, version = 9)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract DishDao dishDao();
    public abstract ChefDao ChefDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MainActivity.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();

}
