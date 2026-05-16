package com.example.ff9

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ff9.data.entities.Perso
import com.example.ff9.data.Weapon
import com.example.ff9.data.weaponsDjidane


enum class Category(val value: String){
    SKILLS("Compétences"),
    HISTORY("Histoire"),
    WEAPONS("Armes")
}
@Composable
fun DetailsPersoScreen(viewModel: DetailsPersoViewModel, onBack: ()-> Unit){

    val perso by viewModel.personnageState.collectAsState()

    if(perso != null){
        Column(modifier = Modifier.padding(8.dp)) {
            ButtonBack(onBack)
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Image(painter = painterResource(getResIdByName(perso?.pictureId)),
                    contentDescription = "Profile ${perso?.firstName ?: ""} ")
                Row() {
                    Text(text= perso?.firstName ?: "",
                        style= MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier=Modifier.width(8.dp))
                    Text(text= perso?.lastName ?: "",
                        style= MaterialTheme.typography.titleLarge
                    )
                }

            }
            HistoryPerso(perso!!)
            Spacer(Modifier.height(8.dp))
            SkillsPerso(perso!!)
            Spacer(Modifier.height(8.dp))
            WeaponsPerso(perso!!)
        }
    }else{
        Text(text = "Chargement des caractéristiques du personnage...")
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
fun SkillsPerso(perso:Perso){
    val skillsCombat = getDonneesPerso(
        perso.firstName,
        type = "competences_combat"
        )
    val skillsCombatDescription = getDonneesPerso(
        perso.firstName,
        type = "competences_combat_description"
    )

    val skillsSupport= getDonneesPerso(
        perso.firstName,
        type = "competences_support"
    )
    val skillsSupportDescription = getDonneesPerso(
        perso.firstName,
        type = "competences_support_description"
    )

    CardBloc(
        iconRes = defineIconCategory(Category.SKILLS),
            text = Category.SKILLS,
            content = {
                SkillsContentExpandableCard(
                    skillsCombat,
                    skillsCombatDescription,
                    skillsSupport,
                    skillsSupportDescription
                )
            }
        )
}

@Composable
fun HistoryPerso(perso:Perso){
    CardBloc(
        iconRes = defineIconCategory(Category.HISTORY),
        text = Category.HISTORY,
        content = {
            HistoryContentExpandableCard(
                perso.description ?: ""
            )
        }
    )
}

@Composable
fun WeaponsPerso(perso:Perso){
    /*val weapons = getWeaponsData(perso)

    CardBloc(
        iconRes = defineIconCategory(Category.WEAPONS),
        text = Category.WEAPONS,
        content = {
            WeaponsContentExpandableCard(weapons)
        }
    )*/
}

@Composable
fun CardBloc(iconRes: Painter, text: Category, content: @Composable ()-> Unit){
    var expanded by remember { mutableStateOf(false) }

    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer,
    )

    Card(onClick = { expanded = !expanded }) {
        Column(modifier= Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier.fillMaxWidth()
                ) {
                Image(painter=iconRes,
                    contentDescription = null,
                    modifier=Modifier
                        .size(30.dp)
                        .weight(1f))
                Text(text = text.value,
                    modifier=Modifier.weight(3f)
                    )
                ExpandCardButton(
                    expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if(expanded){
                content()
            }
        }

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
private fun SkillsContentExpandableCard(skillsCombat: List<String>,
                                        skillsCombatDescription: List<String>,
                                        skillsSupport: List<String>,
                                        skillsSupportDescription:List<String>
                                        ){
    // Si tes listes sont très larges, on ajoute le scroll horizontal

    Column(modifier=Modifier.fillMaxHeight()){
        GridSection(title="Compétences de combat",
            lists=skillsCombat.map{ComposableOrText.Text(it)},
            listDescription = skillsCombatDescription.map{ ComposableOrText.Text(it)},
            globalPainter= painterResource(R.drawable.combat_skill_icon),
            height = 300.dp
        )
        GridSection(title="Compétences de soutien",
            lists=skillsSupport.map{ComposableOrText.Text(it)},
            listDescription = skillsSupportDescription.map{ ComposableOrText.Text(it)},
            globalPainter = painterResource(R.drawable.support_skill_icon),
            height = 100.dp
        )
    }
}

@Composable
fun WeaponsContentExpandableCard(weapons:List<Weapon>){

    val weaponsItem = weapons.map {
        ComposableOrText.Custom(
            {
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,){
                    if (it.iconResourceId != null) {
                        Image(
                            painter = painterResource(it.iconResourceId),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Text(text = it.name)
                }
            }

        )
    }

    val weaponsDescription = weapons.map {
        ComposableOrText.Custom({
            Column() {
                if(it.additionalEffect != null){
                    Text(text="Effets Additionnels : ", fontWeight = FontWeight.Bold)
                    Text(text= it.additionalEffect)
                }
                if(it.competenceCombat != null){
                    Text(text="Compétences de combat : ", fontWeight = FontWeight.Bold)
                    Text(text= it.competenceCombat)
                }
                if(it.competenceSupport != null){
                    Text(text="Compétences de support : ", fontWeight = FontWeight.Bold)
                    Text(text= it.competenceSupport)
                }
                if(it.description != null){
                    Text(text="Description: ", fontWeight = FontWeight.Bold)
                    Text(text= it.description)
                }
            }
        }
        )
    }

    Column(modifier=Modifier.fillMaxHeight()){
        GridSection(title="Armes",
            lists=weaponsItem,
            listDescription = weaponsDescription,
            height = 500.dp,
            null
        )
    }


}

@Composable
private fun GridSection(title:String,
                lists: List<ComposableOrText>,
                listDescription: List<ComposableOrText>,
                height: Dp = 50.dp,
                globalPainter: Painter?,
                ){
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<ComposableOrText?>(null) }
    var selectedItemIndex by remember { mutableIntStateOf(0) }


    Column{
        TextTitle(text=title)
        LazyVerticalGrid(columns= GridCells.Fixed(3),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.height(height)
        ) {
            itemsIndexed(lists) { index, item ->
                Column{
                    Button(onClick = { selectedItem = item
                        selectedItemIndex= index
                        showDialog = true
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = LocalContentColor.current
                        ),
                        elevation = null,
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                        ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,){
                            if(globalPainter != null){
                                Image(painter = globalPainter,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            when(item) {
                                is ComposableOrText.Text -> Text(text=item.value)
                                is ComposableOrText.Custom -> item.content()
                            }
                        }
                    }
                }
            }
        }
        if(showDialog && selectedItem != null){
            ShowDetailDialog(item = selectedItem!!,
                description = listDescription[selectedItemIndex],
                onDismiss = { showDialog = false },
                )
        }
    }
}

@Composable
private fun ShowDetailDialog(
    item: ComposableOrText,
    description: ComposableOrText,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            when(item) {
                is ComposableOrText.Text -> Text(text=item.value)
                is ComposableOrText.Custom -> item.content()
            }
        },
        text = {
            when(description) {
                is ComposableOrText.Text -> Text(text=description.value)
                is ComposableOrText.Custom -> description.content()
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Ok")
            }
        },
    )
}

sealed class ComposableOrText {
    data class Text(val value: String) : ComposableOrText()
    data class Custom(val content: @Composable () -> Unit) : ComposableOrText()
}





@Composable
private fun TextTitle(text:String){
    Text(text=text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        maxLines = 1,
        modifier= Modifier
    )
}

@Composable
private fun ExpandCardButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "icône pour afficher ou pas le contenu",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@SuppressLint("DiscouragedApi")
@Composable
private fun getDonneesPerso(nomPerso: String, type: String): List<String> {
    // Handling preview mode to avoid getStringArray issue in some IDE versions
    if (LocalInspectionMode.current) {
        return listOf("Donnée Preview 1", "Donnée Preview 2")
    }

    val context = LocalContext.current
    // On construit le nom de la clé : ex "mage_competences"
    val resourceName = "${nomPerso.lowercase()}_$type"

    // On cherche l'ID du string-array
    val resId = context.resources.getIdentifier(resourceName, "array", context.packageName)

    return if (resId != 0) {
        // On récupère le tableau et on le convertit en Liste Kotlin
        context.resources.getStringArray(resId).toList()
    } else {
        emptyList()
    }
}



