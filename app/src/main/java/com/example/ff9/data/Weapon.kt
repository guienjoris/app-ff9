package com.example.ff9.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ff9.R

data class Weapon(
    val name: String,
    val competenceCombat: String?,
    val competenceSupport: String?,
    val additionalEffect: String?,
    val description: String?
)

@Composable
fun weaponsDjidane(): List<Weapon> {
    return listOf(
        Weapon(name = stringResource(R.string.dague),
            competenceCombat = stringResource(R.string.malandrin),
            competenceSupport = null,
            additionalEffect= null,
            description = stringResource(R.string.desc_malandrin)
        ),
        Weapon(name = stringResource(R.string.dague_magik),
            competenceCombat = stringResource(R.string.malandrin) + ", " +
                    stringResource(R.string.troisieme_oeil),
            competenceSupport = null,
            additionalEffect = stringResource(R.string.effect_mutisme),
            description = stringResource(R.string.desc_malandrin) + ", " +
                    stringResource(R.string.desc_troisieme_oeil) + ", " +
                    stringResource(R.string.effect_mutisme_description)
        ),
    )
}
