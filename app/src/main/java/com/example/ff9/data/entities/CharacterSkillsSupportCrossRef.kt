package com.example.ff9.data.entities


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "table_character_skill_support_cross_ref",
    primaryKeys = ["idCharacter", "idSkillSupport"] // Les deux noms utilisés dans SQLite
)
data class CharacterSkillSupportCrossRef(
    @ColumnInfo(name = "idCharacter") val idCharacter: Int,
    @ColumnInfo(name = "idSkillSupport") val idSkillSupport: Int
)

data class CharacterWithSkillsSupport(
    @Embedded val character: Character,

    @Relation(
        parentColumn = "id", // Clé primaire du Character
        entityColumn = "id", // Clé primaire de la compétence
        associateBy = Junction(
            value = CharacterSkillSupportCrossRef::class,
            parentColumn = "idCharacter", // La colonne dans le pont qui lie le Character
            entityColumn = "idSkillSupport"        // La colonne dans le pont qui lie la compétence
        )
    )
    val skillsSupport: List<SkillSupport>
)