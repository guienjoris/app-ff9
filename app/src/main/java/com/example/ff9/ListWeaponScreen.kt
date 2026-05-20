package com.example.ff9

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.ff9.components.ButtonBack

@Composable
fun ListWeaponScreen(viewModel: ListWeaponViewModel,onBack: ()-> Unit){
    val weapons by viewModel.allWeaponsState.collectAsState()

    val groupByCharacter = weapons?.groupBy { it -> it.linkedCharacters }

    println(groupByCharacter)

    ButtonBack(onBack)
}