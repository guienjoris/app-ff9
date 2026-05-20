package com.example.ff9.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "table_weapon")
data class Weapon(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="picture_id")
    val pictureId: String,
)

@Entity(
    tableName = "table_weapon_skill_combat_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Weapon::class,
            parentColumns = ["id"],
            childColumns = ["idWeapon"]
        ),
        ForeignKey(
            entity = SkillCombat::class,
            parentColumns = ["id"],
            childColumns = ["idSkillCombat"]
        ),

    ]
)
data class WeaponSkillCombatCrossRef(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name= "idWeapon")
    val idWeapon: Int,
    @ColumnInfo(name="idSkillCombat")
    val idSkillCombat: Int?
)

@Entity(
    tableName = "table_weapon_skill_support_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Weapon::class,
            parentColumns = ["id"],
            childColumns = ["idWeapon"]
        ),
        ForeignKey(
            entity = SkillSupport::class,
            parentColumns = ["id"],
            childColumns = ["idSkillSupport"]
        ),

    ]
)
data class WeaponSkillSupportCrossRef(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name= "idWeapon")
    val idWeapon: Int,
    @ColumnInfo(name= "idSkillSupport")
    val idSkillSupport: Int?
)

@Entity(
    tableName = "table_weapon_character_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Weapon::class,
            parentColumns = ["id"],
            childColumns = ["idWeapon"]
        ),
        ForeignKey(
            entity = Character::class,
            parentColumns = ["id"],
            childColumns = ["idCharacter"]
        ),

    ]
)
data class WeaponCharacterCrossRef(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name= "idWeapon")
    val idWeapon: Int,
    @ColumnInfo(name="idCharacter")
    val idCharacter: Int
)

@Entity(
    tableName = "table_weapon_additional_effect_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Weapon::class,
            parentColumns = ["id"],
            childColumns = ["idWeapon"]
        ),
        ForeignKey(
            entity = AdditionalEffect::class,
            parentColumns = ["id"],
            childColumns = ["idAdditionalEffect"]
        ),

    ]
)
data class WeaponAdditionalEffectCrossRef(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name= "idWeapon")
    val idWeapon: Int,
    @ColumnInfo(name="idAdditionalEffect")
    val idAdditionalEffect: Int?
)

data class CompleteWeaponDetails(
    @Embedded val weapon: Weapon,

    // Récupère tous les skills de combat liés à cette arme
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WeaponSkillCombatCrossRef::class,
            parentColumn = "idWeapon",
            entityColumn = "idSkillCombat"
        )
    )
    val combatSkills: List<SkillCombat>,

    // Récupère tous les skills de support liés à cette arme
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WeaponSkillSupportCrossRef::class,
            parentColumn = "idWeapon",
            entityColumn = "idSkillSupport"
        )
    )
    val supportSkills: List<SkillSupport>,

    // Récupère tous les effets additionnels liés à cette arme
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WeaponAdditionalEffectCrossRef::class,
            parentColumn = "idWeapon",
            entityColumn = "idAdditionalEffect"
        )
    )
    val additionalEffects: List<AdditionalEffect>,

    // Récupère tous les personnages qui peuvent utiliser cette arme
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WeaponCharacterCrossRef::class,
            parentColumn = "idWeapon",
            entityColumn = "idCharacter"
        )
    )
    val linkedCharacters: List<Character>
)

data class WeaponUiDetails(
    val weapon: Weapon,
    val combatSkills: List<SkillCombat>,
    val supportSkills: List<SkillSupport>,
    val additionalEffects: List<AdditionalEffect>
)
data class CharacterWithAllWeaponDetails(
    val character: Character,
    val weapons: List<WeaponUiDetails>
)




