package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.CharacterWithAllWeaponDetails
import com.example.ff9.data.entities.CompleteWeaponDetails
import com.example.ff9.data.entities.WeaponUiDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class ListWeaponViewModel (application: Application): AndroidViewModel(application){
    private val weaponDao = AppDatabase.getDatabase(application).weaponDao()

    val charactersWithDetails: StateFlow<List<CharacterWithAllWeaponDetails>> =
        weaponDao.getAllWeaponsWithDetails().map { weaponsList ->
            mapWeaponsToCharacters(weaponsList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
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
                    weapons = pairs.map { (_, weaponDetails) ->
                        // C'est ici qu'on retire les personnages !
                        // On ne recopie que le reste des variables :
                        WeaponUiDetails(
                            weapon = weaponDetails.weapon,
                            combatSkills = weaponDetails.combatSkills,
                            supportSkills = weaponDetails.supportSkills,
                            additionalEffects = weaponDetails.additionalEffects
                        )
                    }
                )
            }
    }
}