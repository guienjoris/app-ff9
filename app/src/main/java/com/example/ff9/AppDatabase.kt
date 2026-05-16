package com.example.ff9

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ff9.data.dao.CharacterDao
import com.example.ff9.data.entities.Character

// On liste les entités et on définit la version de la base
@Database(entities = [Character::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    // Singleton pour éviter d'ouvrir plusieurs instances de la base de données en même temps
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database.db"
                ).createFromAsset("database_default.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}