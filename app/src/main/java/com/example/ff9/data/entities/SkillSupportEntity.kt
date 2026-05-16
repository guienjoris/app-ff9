package com.example.ff9.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_skill_support")
data class SkillSupport (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val idSkillSupport: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name="description") val description : String,
    @ColumnInfo(name="picture_id") val pictureId: String,
)