package com.example.ff9.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "table_character_skill_combat_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = SkillCombat::class,
            parentColumns = ["id"],
            childColumns = ["idSkillCombat"]
        ),
        ForeignKey(
            entity = Character::class,
            parentColumns = ["id"],
            childColumns = ["idCharacter"]
        ),

    ]// Les deux noms utilisés dans SQLite
)
data class CharacterSkillCombatCrossRef(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "idCharacter") val idCharacter: Int,
    @ColumnInfo(name = "idSkillCombat") val idSkillCombat: Int
)

data class CharacterWithSkillsCombat(
    @Embedded val character: Character,

    @Relation(
        parentColumn = "id", // Clé primaire du Character
        entityColumn = "id", // Clé primaire de la compétence
        associateBy = Junction(
            value = CharacterSkillCombatCrossRef::class,
            parentColumn = "idCharacter", // La colonne dans le pont qui lie le Character
            entityColumn = "idSkillCombat"        // La colonne dans le pont qui lie la compétence
        )
    )
    val skillsCombat: List<SkillCombat>
)