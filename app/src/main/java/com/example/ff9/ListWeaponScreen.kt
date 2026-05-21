package com.example.ff9

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ff9.components.ButtonBack
import com.example.ff9.components.ComposableOrText
import com.example.ff9.components.GridItemPair
import com.example.ff9.components.GridSection
import com.example.ff9.data.entities.Character
import com.example.ff9.data.entities.CharacterWithAllWeaponDetails
import com.example.ff9.data.entities.CompleteWeaponDetails
import com.example.ff9.data.entities.WeaponUiDetails
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.groupBy

@Composable
fun ListWeaponScreen(viewModel: ListWeaponViewModel,onBack: ()-> Unit){
    val weaponsByCharacter by viewModel.charactersWithDetails.collectAsState()


    Column(modifier = Modifier.verticalScroll(rememberScrollState())){
        ButtonBack(onBack)
        weaponsByCharacter.forEach { weaponByCharacter ->
            DisplayWeaponsForCharacter(weaponByCharacter.weapons,weaponByCharacter.character)
        }
    }

}

@Composable
fun DisplayWeaponsForCharacter(weapons: List<WeaponUiDetails>, character: Character) {

    // Une SEULE liste au lieu de deux ! Chaque élément contient l'affichage ET sa description intégrée
    val gridItems = weapons.map { weaponDetails ->
        // On crée un nouvel objet ou une structure qui couple le visuel et sa description
        GridItemPair(
            display = ComposableOrText.Custom({
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(getResIdByName(weaponDetails.weapon.pictureId)),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(text = weaponDetails.weapon.name)
                }
            }),
            description = ComposableOrText.Custom({
                // On ajoute le scroll ici pour éviter le freeze de Compose
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    if(weaponDetails.weapon.obtaining != null){
                        Column(modifier = Modifier.padding(5.dp)) {
                            Text(
                                text = "Moyens d'obtentions: ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = weaponDetails.weapon.obtaining,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                    // 1. Effets additionnels
                    if (weaponDetails.additionalEffects.isNotEmpty()) {
                        Column(modifier = Modifier.padding(5.dp)) {
                            Text(text = "Effets Additionnels : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            weaponDetails.additionalEffects.forEach { effect ->
                                Text(text = effect.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = effect.description ?: "")
                            }
                        }
                    }
                    // 2. Compétences de combat
                    if (weaponDetails.combatSkills.isNotEmpty()) {
                        Column(modifier = Modifier.padding(5.dp)) {
                            Text(text = "Compétences de combat : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            weaponDetails.combatSkills.forEach { skill ->
                                Text(text = skill.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = skill.description ?: "")
                            }
                        }
                    }
                    // 3. Compétences de support
                    if (weaponDetails.supportSkills.isNotEmpty()) {
                        Column(modifier = Modifier.padding(5.dp)) {
                            Text(text = "Compétences de support : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            weaponDetails.supportSkills.forEach { support ->
                                Text(text = support.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = support.description ?: "")
                            }
                        }
                    }
                }
            })
        )
    }

    if (gridItems.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxHeight()) {
            // On passe notre liste unifiée à GridSection
            GridSection(
                title = ComposableOrText.Custom{
                    Row(modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                        ){
                        Image(
                            painter = painterResource(getResIdByName(character.profilePictureId)),
                            contentDescription = "photo de profil de ${character.firstName} ${character.lastName}"
                        )
                        Spacer(modifier=Modifier.width(8.dp))
                        Text(text="${character.firstName} ${character.lastName}")
                    }
                },
                items = gridItems,
                modifier = Modifier
            )
        }
    } else {
        Text(text = "Chargement des armes ...")
    }
}





