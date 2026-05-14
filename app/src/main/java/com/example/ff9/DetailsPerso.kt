package com.example.ff9

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ff9.data.Perso
import com.example.ff9.data.Weapon
import com.example.ff9.data.persos
import com.example.ff9.ui.theme.FF9Theme


enum class Category(val value: String){
    SKILLS("Compétences"),
    HISTORY("Histoire"),
    WEAPONS("Armes")
}
@Composable
fun CardPersoDetails( perso: Perso){

        Column(modifier = Modifier.padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier.fillMaxWidth().padding(12.dp)
                ) {
                Image(painter = painterResource(perso.pictureId),
                    contentDescription = "Profile ${stringResource(perso.firstnameResourceId)} ")
                Row() {
                    Text(text= stringResource(perso.firstnameResourceId),
                        style= MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier=Modifier.width(8.dp))
                    Text(text= stringResource(perso.nameResourceId),
                        style= MaterialTheme.typography.titleLarge
                    )
                }

            }
            HistoryPerso(perso)
            Spacer(Modifier.height(8.dp))
            SkillsPerso(perso)
            Spacer(Modifier.height(8.dp))
            WeaponsPerso(perso)
        }
}

@Composable
fun defineIconCategory(blocName: Category): Painter{
    return when(blocName){
        Category.SKILLS -> painterResource(R.drawable.logo_ico)
        Category.HISTORY -> painterResource(R.drawable.logo_ico)
        Category.WEAPONS -> painterResource(R.drawable.sword_ico)
    }

}

@Composable
fun SkillsPerso(perso:Perso){
    val skillsCombat = getDonneesPerso(
        stringResource(perso.firstnameResourceId),
        type = "competences_combat"
        )
    val skillsCombatDescription = getDonneesPerso(
        stringResource(perso.firstnameResourceId),
        type = "competences_combat_description"
    )

    val skillsSupport= getDonneesPerso(
        stringResource(perso.firstnameResourceId),
        type = "competences_support"
    )
    val skillsSupportDescription = getDonneesPerso(
        stringResource(perso.firstnameResourceId),
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
                stringResource(R.string.djidane_description)
            )
        }
    )
}

@Composable
fun WeaponsPerso(perso:Perso){
    CardBloc(
        iconRes = defineIconCategory(Category.WEAPONS),
        text = Category.WEAPONS,
        content = {
            HistoryContentExpandableCard(
                stringResource(R.string.djidane_description)
            )
        }
    )
}

@Composable
fun CardBloc(iconRes: Painter, text: Category, content: @Composable ()-> Unit){
    var expanded by remember { mutableStateOf(false) }

    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer,
    )

    Card(onClick = { expanded = !expanded }) {
        Column(modifier= Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier.fillMaxWidth()
                ) {
                Image(painter=iconRes,
                    contentDescription = null,
                    modifier=Modifier.size(30.dp).weight(1f))
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
    Row(modifier=Modifier.fillMaxSize().padding(5.dp)){
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
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .padding(5.dp)
    ) {
        TableColumn(
            title = "Combat",
            names = skillsCombat,
            modifier = Modifier.width(200.dp).fillMaxHeight()
        )
        TableColumn(
            title = "Description",
            names = skillsCombatDescription,
            modifier = Modifier.width(200.dp).fillMaxHeight()
        )
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .padding(5.dp)
    ) {
        TableColumn(
            title = "Support",
            names = skillsSupport,
            modifier = Modifier.width(200.dp).fillMaxHeight()
        )
        TableColumn(
            title = "Description",
            names = skillsSupportDescription,
            modifier = Modifier.width(200.dp).fillMaxHeight()
        )
    }
}

@Composable
fun TableColumn(title: String, names: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.wrapContentSize()) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // On affiche chaque compétence avec sa description
        Column() {
            names.forEach { name -> Text(text=name,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
                ) }
        }
    }
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

@Composable
fun getWeaponsData(perso: Perso){
    when(stringResource(perso.firstnameResourceId)){
        stringResource(R.string.djidane_firstname) ->{
            val weaponsList = getDonneesPerso(stringResource(R.string.djidane_firstname),"armes")
            val weaponsListWithData = listOf<Weapon>(
                Weapon(weaponsList[0])
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsPerso(){
    FF9Theme() {
        CardPersoDetails(persos[0])
    }
}