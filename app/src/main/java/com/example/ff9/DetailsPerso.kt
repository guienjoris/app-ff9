package com.example.ff9

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ff9.data.Perso
import com.example.ff9.data.persos
import com.example.ff9.ui.theme.FF9Theme

@Composable
fun CardPersoDetails( perso: Perso){

        Column(modifier = Modifier.padding(8.dp)) {
            HistoryPerso(perso)
            Spacer(Modifier.height(8.dp))
            SkillsPerso(perso)
            Spacer(Modifier.height(8.dp))
            WeaponsPerso(perso)
        }


}

@Composable
fun defineIcon(blocName:String): Painter{
    return when(blocName){
        "Skills" -> painterResource(R.drawable.logo_ico)
        "History" -> painterResource(R.drawable.logo_ico)
        "Weapons" -> painterResource(R.drawable.sword_ico)
        else -> {painterResource(R.drawable.logo_ico)}
    }

}

@Composable
fun SkillsPerso(perso:Perso){
    CardBloc(iconRes = defineIcon("Skills"),
            text="Skills",
            description= stringResource(R.string.djidane_description)
        )
}

@Composable
fun HistoryPerso(perso:Perso){
    CardBloc(iconRes = defineIcon("History"),
        text="History",
        description= stringResource(R.string.djidane_description)
    )
}

@Composable
fun WeaponsPerso(perso:Perso){
    CardBloc(iconRes = defineIcon("Weapons"),
        text="Weapons",
        description= stringResource(R.string.djidane_description)
    )
}

@Composable
fun CardBloc(iconRes: Painter, text:String, description: String){
    var expanded by remember { mutableStateOf(false) }

    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer,
    )

    Card() {
        Column(modifier= Modifier.padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier.fillMaxWidth()
                ) {
                Image(painter=iconRes,
                    contentDescription = null,
                    modifier=Modifier.size(30.dp))
                Text(text = text)
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