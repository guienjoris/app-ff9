package com.example.ff9.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.ff9.R

data class Perso(
    @StringRes val firstnameResourceId: Int,
    @StringRes val nameResourceId: Int,
    @DrawableRes val profilePictureId: Int,
    @DrawableRes val pictureId: Int,
    @StringRes val descriptionResourceId:Int,
)


val persos = listOf(
    Perso(R.string.djidane_firstname,
        R.string.djidane_name,
        R.drawable.djidane_profile,
        R.drawable.djidane,
        R.string.djidane_description
    ),
)