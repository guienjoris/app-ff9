package com.example.ff9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
}