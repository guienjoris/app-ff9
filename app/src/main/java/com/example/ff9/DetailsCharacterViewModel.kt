package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsCharacterViewModel (application: Application): AndroidViewModel(application){
    private val characterDao = AppDatabase.getDatabase(application).characterDao()
    private val _characterState = MutableStateFlow<Character?>(null)

    val characterState: StateFlow<Character?> = _characterState.asStateFlow()

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            // On appelle la fonction de ton DAO
            val foundCharacter = characterDao.getCharacterById(id)

            // On met à jour l'état de l'écran avec le personnage trouvé
            _characterState.value = foundCharacter
        }
    }

}