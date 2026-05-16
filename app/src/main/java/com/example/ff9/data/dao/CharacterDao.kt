package com.example.ff9.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.ff9.data.entities.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM table_character ORDER BY idCharacter DESC")
    fun getAllCharacters(): Flow<List<Character>>

    @Query("SELECT * FROM table_character WHERE idCharacter = :id")
    suspend fun getCharacterById(id: Int): Character?
}