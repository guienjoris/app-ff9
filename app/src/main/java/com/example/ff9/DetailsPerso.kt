package com.example.ff9

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ff9.data.Perso
import com.example.ff9.data.persos
import com.example.ff9.ui.theme.FF9Theme


enum class Category(val value: String){
    SKILLS("Compétence"),
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
fun defineIcon(blocName: Category): Painter{
    return when(blocName){
        Category.SKILLS -> painterResource(R.drawable.logo_ico)
        Category.HISTORY -> painterResource(R.drawable.logo_ico)
        Category.WEAPONS -> painterResource(R.drawable.sword_ico)
    }

}

@Composable
fun SkillsPerso(perso:Perso){
    CardBloc(
        iconRes = defineIcon(Category.SKILLS),
            text = Category.SKILLS,
            description = stringResource(R.string.djidane_description)
        )
}

@Composable
fun HistoryPerso(perso:Perso){
    CardBloc(
        iconRes = defineIcon(Category.HISTORY),
        text = Category.HISTORY,
        description = stringResource(R.string.djidane_description)
    )
}

@Composable
fun WeaponsPerso(perso:Perso){
    CardBloc(
        iconRes = defineIcon(Category.WEAPONS),
        text = Category.WEAPONS,
        description = stringResource(R.string.djidane_description)
    )
}

@Composable
fun CardBloc(iconRes: Painter, text: Category, description: String){
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
                Text(text=description)
            }
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

@Preview(showBackground = true)
@Composable
fun PreviewDetailsPerso(){
    FF9Theme() {
        CardPersoDetails(persos[0])
    }
}