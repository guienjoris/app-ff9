package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.CharacterWithAllWeaponDetails
import com.example.ff9.data.entities.CompleteWeaponDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListWeaponViewModel (application: Application): AndroidViewModel(application){
    private val weaponDao = AppDatabase.getDatabase(application).weaponDao()

    private val _allWeaponsState = MutableStateFlow<List<CompleteWeaponDetails>?>(null)

    val allWeaponsState: StateFlow<List<CompleteWeaponDetails>?> = _allWeaponsState.asStateFlow()

    fun getAllWeapons(){
        viewModelScope.launch {
            _allWeaponsState.value = weaponDao.getAllWeaponsWithDetails()
        }
    }

    fun mapWeaponsToCharacters(
        weaponsList: List<CompleteWeaponDetails>
    ): List<CharacterWithAllWeaponDetails> {
        return weaponsList
            // 1. On extrait chaque couple (Personnage, Arme)
            .flatMap { weaponDetails ->
                weaponDetails.linkedCharacters.map { character ->
                    character to weaponDetails
                }
            }
            // 2. On groupe par le Personnage (ou par character.name selon ta préférence)
            // Ici on groupe par l'objet Character complet pour garder l'ID et ses infos
            .groupBy { (character, _) -> character }
            // 3. On transforme le résultat en notre data class d'UI
            .map { (character, pairs) ->
                CharacterWithAllWeaponDetails(
                    character = character,
                    // On récupère toutes les armes associées à ce personnage précis
                    weapons = pairs.map { it.second }
                )
            }

    }
}