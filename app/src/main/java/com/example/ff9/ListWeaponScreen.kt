package com.example.ff9

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.ff9.components.GridSection
import com.example.ff9.data.entities.CharacterWithAllWeaponDetails
import com.example.ff9.data.entities.CompleteWeaponDetails
import com.example.ff9.data.entities.WeaponUiDetails
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.groupBy

@Composable
fun ListWeaponScreen(viewModel: ListWeaponViewModel,onBack: ()-> Unit){
    val weapons by viewModel.charactersWithDetails.collectAsStateWithLifecycle()

    println(weapons)

    Column{
        ButtonBack(onBack)
        weapons.forEach {
            Text(text=it.character.firstName)
            DisplayWeaponsForCharacter(it.weapons)
        }
    }

}

@Composable
fun DisplayWeaponsForCharacter(weapons:List<WeaponUiDetails>){

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
                null,
                modifier = Modifier.fillMaxSize(),

                )
        }
    }else{
        Text(text="Chargement des armes ...")
    }

}

