package com.example.ff9


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ff9.data.entities.Character
import com.example.ff9.data.entities.CompleteWeaponDetails
import com.example.ff9.data.entities.SkillCombat
import com.example.ff9.data.entities.SkillSupport
import androidx.compose.ui.platform.LocalResources
import com.example.ff9.components.ButtonBack
import com.example.ff9.components.ComposableOrText
import com.example.ff9.components.ExpendableCard
import com.example.ff9.components.GridItemPair
import com.example.ff9.components.GridSection


enum class Category(val value: String){
    SKILLS("Compétences"),
    HISTORY("Histoire"),
    WEAPONS("Armes")
}
@Composable
fun DetailsCharacterScreen(viewModel: DetailsCharacterViewModel, onBack: ()-> Unit){

    val character by viewModel.characterState.collectAsState()
    val weapons by viewModel.weaponsForCharacterState.collectAsState()

    val density = LocalResources.current.displayMetrics.density
    val widthInDp = (400 / density).dp
    val heightInDp = (800 / density).dp

    if(character != null){
        Column(modifier = Modifier.padding(8.dp)) {
            ButtonBack(onBack)
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Image(painter = painterResource(getResIdByName(character?.pictureId)),
                    contentDescription = "Profile ${character?.firstName ?: ""} ",
                contentScale = ContentScale.Fit,
                    modifier = Modifier.size(width = widthInDp, height = heightInDp)
                )
                Column() {
                    Text(text= character?.firstName ?: "",
                        style= MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier=Modifier.width(8.dp))
                    Text(text= character?.lastName ?: "",
                        style= MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
            HistoryCharacter(character!!)
            Spacer(Modifier.height(8.dp))
            SkillsCharacter(skillsCombat=weapons?.flatMap{it.combatSkills}?.toSet()?.toList(),
                skillsSupport =weapons?.flatMap{it.supportSkills}?.toSet()?.toList()
            )
            Spacer(Modifier.height(8.dp))
            WeaponsCharacter(weapons)
        }
    }else{
        Text(text = "Chargement du personnage...")
    }
}

@Composable
fun defineIconCategory(blocName: Category): Painter{
    return when(blocName){
        Category.SKILLS -> painterResource(R.drawable.combat_skill_icon)
        Category.HISTORY -> painterResource(R.drawable.logo_ico)
        Category.WEAPONS -> painterResource(R.drawable.thief_sword_icon)
    }

}

@Composable
fun SkillsCharacter(skillsCombat: List<SkillCombat>?,skillsSupport: List<SkillSupport>?){


    if(skillsCombat != null && skillsSupport!= null) {
        ExpendableCard(
            iconRes = defineIconCategory(Category.SKILLS),
            text = Category.SKILLS,
            content = {
                SkillsContentExpandableCard(
                    skillsCombat,
                    skillsSupport
                )
            }
        )
    }else{
        Text(text="Chargement des compétences du personnage")
    }


}

@Composable
fun HistoryCharacter(character: Character){
    ExpendableCard(
        iconRes = defineIconCategory(Category.HISTORY),
        text = Category.HISTORY,
        content = {
            HistoryContentExpandableCard(
                character.description ?: ""
            )
        }
    )
}

@Composable
fun WeaponsCharacter(weapons: List<CompleteWeaponDetails>?){

    if(weapons != null){
        ExpendableCard(
            iconRes = defineIconCategory(Category.WEAPONS),
            text = Category.WEAPONS,
            content = {
                WeaponsContentExpandableCard(weapons)
            }
        )
    }else{
        Text(text="Chargement des armes du personnage...")
    }

}



@Composable
private fun HistoryContentExpandableCard(text:String){
    Row(modifier=Modifier
        .fillMaxSize()
        .padding(5.dp)){
        Text(text=text)
    }
}

@Composable
private fun SkillsContentExpandableCard(skillsCombat: List<SkillCombat>,
                                        skillsSupport: List<SkillSupport>
                                        ){


    Column(modifier=Modifier.fillMaxHeight()){
        GridSection(title=ComposableOrText.Text("Compétences de combat"),
            items=skillsCombat.map{ it ->
                GridItemPair(display=ComposableOrText.Custom({
                    Column{
                        Image(painter= painterResource(R.drawable.combat_skill_icon),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                                               )
                        Text(text=it.name)
                    }
                    }), description = ComposableOrText.Custom({
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())){
                            Column{
                                Text(text=it.description)
                            }
                        }
                    }))
                  },
            modifier = Modifier.height(300.dp)
        )
        if(skillsSupport.isNotEmpty()){
            GridSection(title= ComposableOrText.Text("Compétences de soutien"),
                items=skillsSupport.map{ it ->
                    GridItemPair(display=ComposableOrText.Custom({
                        Column{
                            Image(painter= painterResource(R.drawable.support_skill_icon),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(text=it.name)
                        }
                    }), description = ComposableOrText.Custom({
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())){
                            Column{
                                Text(text=it.description)
                            }
                        }
                    }))
                },
                modifier = Modifier.height(300.dp)
            )
        }

    }
}

@Composable
private fun WeaponsContentExpandableCard(weapons:List<CompleteWeaponDetails>){

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
        Column(modifier=Modifier.fillMaxHeight()){
            GridSection(
                title = ComposableOrText.Text("Armes"),
                items = gridItems,
                modifier = Modifier
            )
        }
    } else {
        Text(text = "Chargement des armes ...")
    }

}



















