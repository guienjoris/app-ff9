package com.example.ff9.data

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.ff9.R

data class Weapon(
    @StringRes val nameId: Int,
    @StringRes val competenceCombatId: Int?,
    @StringRes val competenceSupportId: Int?,
    @StringRes val additionalEffectId: Int?,
    @StringRes val descriptionId: Int?
)

val weaponsDjidane = listOf<Weapon>(
    Weapon(nameId = R.string.dague, competenceCombatId = R.string.d)
)
