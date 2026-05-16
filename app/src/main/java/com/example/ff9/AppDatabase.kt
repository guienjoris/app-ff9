package com.example.ff9

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ff9.data.dao.CharacterDao
import com.example.ff9.data.dao.SkillCombatDao
import com.example.ff9.data.dao.SkillSupportDao
import com.example.ff9.data.entities.Character
import com.example.ff9.data.entities.CharacterSkillCombatCrossRef
import com.example.ff9.data.entities.CharacterSkillSupportCrossRef
import com.example.ff9.data.entities.SkillCombat
import com.example.ff9.data.entities.SkillSupport

// On liste les entités et on définit la version de la base
@Database(entities = [
    Character::class,
    SkillCombat::class,
    SkillSupport::class,
    CharacterSkillCombatCrossRef::class,
    CharacterSkillSupportCrossRef::class
                     ],
    version = 2,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun skillCombatDao() : SkillCombatDao
    abstract fun skillSupportDao(): SkillSupportDao

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
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}