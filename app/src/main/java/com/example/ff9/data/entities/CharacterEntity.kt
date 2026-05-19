package com.example.ff9.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

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




