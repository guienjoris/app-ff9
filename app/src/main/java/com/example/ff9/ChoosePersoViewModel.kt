package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ff9.data.entities.Perso
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChoosePersoViewModel(application: Application) : AndroidViewModel(application) {

    private val persoDao = AppDatabase.getDatabase(application).persoDao()

    // On transforme le Flow de Room en StateFlow pour Jetpack Compose
    val persoState: StateFlow<List<Perso>> = persoDao.getAllPersos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


}