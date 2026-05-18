package com.example.ff9.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_additional_effect")
data class AdditionalEffect (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val idAdditionalEffect: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name="description") val description : String?,
)