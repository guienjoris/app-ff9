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

@Entity(
    tableName = "table_character_skill_support_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = SkillSupport::class,
            parentColumns = ["id"],
            childColumns = ["idSkillSupport"]
        ),
        ForeignKey(
            entity = Character::class,
            parentColumns = ["id"],
            childColumns = ["idCharacter"]
        ),

    ]// Les deux noms utilisés dans SQLite
)
data class CharacterSkillSupportCrossRef(
    @PrimaryKey(autoGenerate = true) val id:Int,
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