package com.example.ff9.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_character")
data class Character (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val idCharacter: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name="description") val description : String?,
    @ColumnInfo(name="profile_picture_id") val profilePictureId: String?,
    @ColumnInfo(name="picture_id") val pictureId: String?,
)