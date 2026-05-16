package com.example.ff9.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.ff9.data.entities.Perso
import kotlinx.coroutines.flow.Flow

@Dao
interface PersoDao {
    @Query("SELECT * FROM table_perso ORDER BY id DESC")
    fun getAllPersos(): Flow<List<Perso>>

    @Query("SELECT * FROM table_perso WHERE id = :id")
    suspend fun getPersoById(id: Int): Perso?
}