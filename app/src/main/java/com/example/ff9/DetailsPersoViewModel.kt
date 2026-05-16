package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.Perso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsPersoViewModel (application: Application): AndroidViewModel(application){
    private val persoDao = AppDatabase.getDatabase(application).persoDao()
    private val _persoState = MutableStateFlow<Perso?>(null)

    val personnageState: StateFlow<Perso?> = _persoState.asStateFlow()

    fun getPersoById(id: Int) {
        viewModelScope.launch {
            // On appelle la fonction de ton DAO
            val foundPerso = persoDao.getPersoById(id)

            // On met à jour l'état de l'écran avec le personnage trouvé
            _persoState.value = foundPerso
        }
    }

}