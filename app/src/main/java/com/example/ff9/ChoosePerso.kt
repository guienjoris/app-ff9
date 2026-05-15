package com.example.ff9

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ff9.data.Perso
import com.example.ff9.data.persos

@Composable
fun ListChoosePersoView(onBack: () -> Unit,onNavigateToPersoDetails:(indexPerso:String)-> Unit){
    var selectedPerso: Int? by remember { mutableStateOf(null)}

    val onSelectedPerso = { it:Int -> selectedPerso = it}

    selectedPerso?.let { onNavigateToPersoDetails(it.toString()) }

    ListChoosePerso(onSelectedPerso,onBack)

}

@Composable
fun ListChoosePerso(onSelectedPerso:(Int)-> Unit,onBack:() -> Unit){
    Column{
        ButtonBack(onBack)

        LazyVerticalGrid(columns= GridCells.Fixed(2),
            contentPadding = PaddingValues(2.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            itemsIndexed(persos){ index,perso ->
                CardChoosePerso(perso,onSelectedPerso,index)
            }
        }
    }
}

@Composable
fun CardChoosePerso(perso: Perso,onSelectedPerso:(Int)-> Unit,index:Int){


    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
                .clickable(enabled=true,
                    onClick = {onSelectedPerso(index)}
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(perso.profilePictureId),
                contentDescription = null,

                )
            Text(text= stringResource(perso.firstnameResourceId),
                style= MaterialTheme.typography.titleLarge
            )
            Text(text= stringResource(perso.nameResourceId),
                style= MaterialTheme.typography.titleLarge
            )


        }
    }

}





