package com.example.ff9

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.ff9.components.ButtonBack
import com.example.ff9.components.ComposableOrText
import com.example.ff9.components.GridSection
import com.example.ff9.data.entities.CharacterWithAllWeaponDetails
import com.example.ff9.data.entities.CompleteWeaponDetails
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.groupBy

@Composable
fun ListWeaponScreen(viewModel: ListWeaponViewModel,onBack: ()-> Unit){
    val weapons by viewModel.allWeaponsState.collectAsState()

    val weaponsByCharacter = weapons?.flatMap { weaponDetails ->
        weaponDetails.linkedCharacters.map { character ->
            character to weaponDetails
        }
    }
        // 2. On groupe par le Personnage (ou par character.name selon ta préférence)
        // Ici on groupe par l'objet Character complet pour garder l'ID et ses infos
        ?.groupBy { (character, _) -> character }
        // 3. On transforme le résultat en notre data class d'UI
        ?.map { (character, pairs) ->
            CharacterWithAllWeaponDetails(
                character = character,
                // On récupère toutes les armes associées à ce personnage précis
                weapons = pairs.map { it.second }
            )
        }

    Column{
        ButtonBack(onBack)
        weaponsByCharacter?.forEach {
            Text(text=it.character.firstName)
            DisplayArmesForCharacter(it.weapons)
        }
    }

}

@Composable
fun DisplayArmesForCharacter(weapons:List<CompleteWeaponDetails>){

    val weaponsItems = weapons.map{
        ComposableOrText.Custom({
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier= Modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(getResIdByName(it.weapon.pictureId)),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                Text(text = it.weapon.name)
            }
        })
    }

    val weaponsDescription = weapons.map {
        ComposableOrText.Custom({
            Column() {
                if(it.additionalEffects.isNotEmpty()){
                    Column(modifier=Modifier.padding(5.dp)) {
                        Text(text="Effets Additionnels : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Column(){
                            it.additionalEffects.forEach{ it->
                                Text(text=it.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier=Modifier.width(5.dp))
                                Text(text=it.description ?: "")
                            }
                        }
                    }

                }
                if(it.combatSkills.isNotEmpty()){
                    Column(modifier=Modifier.padding(5.dp)) {
                        Text(text="Compétences de combat : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Column(){
                            it.combatSkills.forEach{ it->
                                Text(text=it.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier=Modifier.width(5.dp))
                                Text(text=it.description ?: "")
                            }
                        }
                    }
                }
                if(it.supportSkills.isNotEmpty()){
                    Column(modifier=Modifier.padding(5.dp)) {
                        Text(text="Compétences de support : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Column(){
                            it.supportSkills.forEach{ it->
                                Text(text=it.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier=Modifier.width(5.dp))
                                Text(text=it.description ?: "")
                            }
                        }
                    }
                }
            }
        }
        )
    }

    if(weaponsItems.isNotEmpty() && weaponsDescription.isNotEmpty()){
        Column(modifier=Modifier.fillMaxHeight()){
            GridSection(title="Armes",
                lists=weaponsItems,
                listDescription = weaponsDescription,
                height = 500.dp,
                null
            )
        }
    }else{
        Text(text="Chargement des armes ...")
    }

}

