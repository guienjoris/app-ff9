package com.example.ff9.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.ff9.data.entities.CharacterWithSkillsCombat
import com.example.ff9.data.entities.CharacterWithSkillsSupport
import com.example.ff9.data.entities.SkillCombat
import com.example.ff9.data.entities.SkillSupport
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillCombatDao {
    @Query("SELECT * FROM table_skill_combat ORDER BY id DESC")
    fun getAllSkillsCombat(): Flow<List<SkillCombat>>

    @Query("SELECT * FROM table_skill_combat WHERE id = :id")
    suspend fun getSkillCombatById(id: Int): SkillCombat?

    @Transaction
    @Query("SELECT * FROM table_character WHERE id = :id")
    suspend fun getSkillsCombatForCharacter(id: Int): CharacterWithSkillsCombat?
}

@Dao
interface SkillSupportDao {
    @Query("SELECT * FROM table_skill_support ORDER BY id DESC")
    fun getAllSkillsSupport(): Flow<List<SkillSupport>>

    @Query("SELECT * FROM table_skill_support WHERE id = :id")
    suspend fun getSkillSupportById(id: Int): SkillSupport?

    @Transaction
    @Query("SELECT * FROM table_character WHERE id = :id")
    suspend fun getSkillsSupportForCharacter(id: Int): CharacterWithSkillsSupport?
}