package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.Character
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ChooseCharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val characterDao = AppDatabase.getDatabase(application).characterDao()

    // On transforme le Flow de Room en StateFlow pour Jetpack Compose
    val characterState: StateFlow<List<Character>> = characterDao.getAllCharacters()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


}