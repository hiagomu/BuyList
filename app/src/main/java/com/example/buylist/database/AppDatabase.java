package com.example.buylist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.buylist.dao.AlimentoDao;
import com.example.buylist.model.Alimento;

@Database(entities = {Alimento.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract AlimentoDao alimentoDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "lista_de_compras.dp").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
