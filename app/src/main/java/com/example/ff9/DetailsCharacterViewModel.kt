package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.Character
import com.example.ff9.data.entities.CharacterWithSkillsCombat
import com.example.ff9.data.entities.CompleteWeaponDetails
import com.example.ff9.data.entities.SkillCombat
import com.example.ff9.data.entities.SkillSupport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsCharacterViewModel (application: Application): AndroidViewModel(application){
    private val characterDao = AppDatabase.getDatabase(application).characterDao()
    private val skillCombatDao = AppDatabase.getDatabase(application).skillCombatDao()
    private val skillSupportDao = AppDatabase.getDatabase(application).skillSupportDao()
    private val weaponDao = AppDatabase.getDatabase(application).weaponDao()


    private val _characterState = MutableStateFlow<Character?>(null)
    val characterState: StateFlow<Character?> = _characterState.asStateFlow()

    fun getCharacterById(idCharacter: Int) {
        viewModelScope.launch {
            // On appelle la fonction de ton DAO
            val foundCharacter = characterDao.getCharacterById(idCharacter)

            // On met à jour l'état de l'écran avec le personnage trouvé
            _characterState.value = foundCharacter
        }
    }

    private val _skillsCombatState = MutableStateFlow<List<SkillCombat>?>(null)
    val skillsCombatState: StateFlow<List<SkillCombat>?> = _skillsCombatState.asStateFlow()

    fun getSkillsCombatByCharacterId(idCharacter:Int){
        viewModelScope.launch{
            _skillsCombatState.value = skillCombatDao.getSkillsCombatForCharacter(idCharacter)?.skillsCombat

        }
    }

    private val _skillsSupportState = MutableStateFlow<List<SkillSupport>?>(null)
    val skillsSupportState: StateFlow<List<SkillSupport>?> = _skillsSupportState.asStateFlow()

    fun getSkillsSupportByCharacterId(idCharacter:Int){
        viewModelScope.launch{
            _skillsSupportState.value = skillSupportDao.getSkillsSupportForCharacter(idCharacter)?.skillsSupport

        }
    }

    private val _weaponsForCharacterState = MutableStateFlow<List<CompleteWeaponDetails>?>(null)

    val weaponsForCharacterState : StateFlow<List<CompleteWeaponDetails>?> = _weaponsForCharacterState.asStateFlow()

    fun getWeaponsDetailsByCharacterId(idCharacter:Int){
        viewModelScope.launch{
            _weaponsForCharacterState.value = weaponDao.getAllWeaponsForCharacterWithDetails(idCharacter)
        }
    }

}