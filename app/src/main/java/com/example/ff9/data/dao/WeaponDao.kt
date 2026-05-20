package com.example.ff9.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.ff9.data.entities.CompleteWeaponDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao {
    // On utilise @Transaction car Room va exécuter plusieurs requêtes SELECT
    // en tâche de fond pour remplir toutes les listes du POJO de manière synchronisée.
    @Transaction
    @Query("SELECT * FROM table_weapon WHERE id = :weaponId")
    suspend fun getWeaponDetailsById(weaponId: Int): CompleteWeaponDetails?

    // Version Flow pour observer toute ta liste d'armes ultra-complète en temps réel
    @Transaction
    @Query("SELECT * FROM table_weapon")
    suspend fun getAllWeaponsWithDetails(): List<CompleteWeaponDetails>?

    // Pour avoir toute ta liste d'armes ultra-complète en temps réel pour un personnage donné
    @Transaction
    @Query("SELECT table_weapon.* FROM table_weapon INNER JOIN table_weapon_character_cross_ref ON table_weapon.id = table_weapon_character_cross_ref.idWeapon WHERE table_weapon_character_cross_ref.idCharacter = :idCharacter")
    suspend fun getAllWeaponsForCharacterWithDetails(idCharacter:Int): List<CompleteWeaponDetails>?
}